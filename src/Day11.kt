fun main() {
    fun parse(input: List<String>) = (input + "out: ").associate { line ->
        val split = line.split(": ")
        split.first() to split.last().split(" ").filter { it != "" }
    }

    // optional
    fun printDot(adjMap: Map<String, List<String>>) {
        println("digraph G {")
        adjMap.forEach { (k, v) ->
            v.forEach {
                println("  $k -> $it;")
            }
        }
        println("  you [shape=Mdiamond];")
        println("  svr [shape=Mdiamond];")
        println("  out [shape=Mdiamond];")
        println("  dac [shape=Msquare];")
        println("  fft [shape=Msquare];")
        println("}")
    }

    fun countPaths(entry: String, exit: String, adjMap: Map<String, List<String>>): Long {
        val needs = adjMap.entries.associate { it.key to if (exit in it.value) 0 else it.value.size }.toMutableMap()
        val paths = adjMap.keys.associateWith { 0L }.toMutableMap()
        paths[exit] = 1

        // fixpoint
        while (needs[entry]!! != -1) {
            needs.filter { it.value == 0 }.forEach { (compute, _) ->
                paths[compute] = adjMap[compute]!!.sumOf { paths[it]!! }
                // mark me as done
                for ((node, neigh) in adjMap) {
                    if (compute in neigh) {
                        needs[node] = needs[node]!! - 1
                    }
                }
                // we are done
                needs[compute] = -1
            }
        }

        return paths[entry]!!
    }

    fun part1(input: List<String>) = countPaths("you", "out", parse(input))

    fun part2(input: List<String>): Long {
        val adjMap = parse(input)

        val fft2dac = countPaths("fft", "dac", adjMap)
        if (fft2dac != 0L) {
            val svr2fft = countPaths("svr", "fft", adjMap)
            val dac2out = countPaths("dac", "out", adjMap)
            return svr2fft * fft2dac * dac2out
        } else {
            val svr2dac = countPaths("svr", "dac", adjMap)
            val dac2fft = countPaths("dac", "fft", adjMap)
            val fft2out = countPaths("fft", "out", adjMap)
            return svr2dac * dac2fft * fft2out
        }
    }

    val input = readInput("Day11")
    printSolution { part1(input) }
    printSolution(false) { part2(input) }
}