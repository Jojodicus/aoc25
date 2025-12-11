import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar

fun main() {
    data class Machine(val lights: List<Boolean>, val buttons: List<List<Int>>, val joltage: List<Int>)

    fun parse(input: List<String>) = input.map { line ->
        val parts = line.split(" ")

        val lights = parts.first().mapNotNull {
            when (it) {
                '.' -> false
                '#' -> true
                else -> null
            }
        }

        val buttons = parts.drop(1).dropLast(1).map { tuple ->
            tuple.filter { it != '(' && it != ')' }.split(",").map(String::toInt)
        }

        val joltage = parts.last().filter { it != '{' && it != '}' }.split(",").map(String::toInt)

        Machine(lights, buttons, joltage)
    }

    fun configureFewestLights(machine: Machine): Long {
        // bfs
        val seenConfigurations = mutableSetOf(machine.lights)
        val workQueue = mutableListOf(machine.lights to 0L)
        while (workQueue.isNotEmpty()) {
            val current = workQueue.removeFirst()

            if (current.first.none { it }) {
                return current.second
            }

            for (pushes in machine.buttons) {
                val applied = current.first.mapIndexed { i, e -> if (i in pushes) !e else e }

                if (applied !in seenConfigurations) {
                    seenConfigurations.add(applied)
                    workQueue.addLast(applied to current.second + 1)
                }
            }
        }

        throw IllegalStateException("Impossible")
    }

    fun configureFewestJoltage(machine: Machine): Long {
        val model = Model()

        val maxPerButton = machine.buttons.map { b ->
            b.minOfOrNull { pos -> machine.joltage[pos] } ?: 0
        }

        val xs = machine.buttons.indices.map { i ->
            model.intVar(0, maxPerButton[i])
        }

        machine.joltage.indices.forEach { pos ->
            val contributing = mutableListOf<IntVar>()
            val coeffs = mutableListOf<Int>()
            machine.buttons.forEachIndexed { i, b ->
                if (pos in b) {
                    contributing += xs[i]
                    coeffs += 1
                }
            }
            if (contributing.isEmpty()) {
                require(machine.joltage[pos] == 0)
            } else {
                model.scalar(contributing.toTypedArray(), coeffs.toIntArray(), "=", machine.joltage[pos]).post()
            }
        }

        val sumVar = model.intVar(0, machine.joltage.sum())
        model.sum(xs.toTypedArray(), "=", sumVar).post()

        var bestSum = Long.MAX_VALUE

        val solver = model.solver
        while (solver.solve()) {
            val s = sumVar.value.toLong()
            if (s < bestSum) {
                bestSum = s
                model.arithm(sumVar, "<", bestSum.toInt()).post()
            }
        }

        if (bestSum == Long.MAX_VALUE) {
            throw IllegalStateException("Impossible")
        }

        println("$bestSum - $machine")
        return bestSum
    }

    fun part1(input: List<String>) = parse(input).sumOf { configureFewestLights(it) }

    fun part2(input: List<String>) = parse(input).sumOf { configureFewestJoltage(it) }

    val input = readInput("Day10")
    printSolution { part1(input) }
    printSolution(precondition = false) { part2(input) }
}