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
        // example 1:
        //     (3)  (1,3)  (2)  (2,3) (0,2) (0,1)
        // 3 =                         #b4 + #b5
        // 5 =       #b1 +                   #b5
        // 4 =             #b2 + #b3 + #b4
        // 7 = #b0 + #b1 +       #b3
        // min(sum(#bX))

        return 0
    }

    fun part1(input: List<String>) = parse(input).sumOf { configureFewestLights(it) }

    fun part2(input: List<String>) = parse(input).sumOf { configureFewestJoltage(it) }

    println(part1(readInput("Day10-test")))
    println(part2(readInput("Day10-test")))

    val input = readInput("Day10")
    printSolution { part1(input) }
//    printSolution { part2(input) }
}