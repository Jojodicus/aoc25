fun main() {
    fun solve(input: List<String>, countCallback: (String) -> Boolean): Long {
        val ranges = input[0].split(",").map { range -> range.split("-").map { it.toLong() } }
        val stream = ranges.flatMap { (min, max) -> min..max }.parallelStream()
        val repeated = stream.filter { countCallback(it.toString()) }
        return repeated.reduce(0, Long::plus)
    }

    fun part1(input: List<String>): Long = solve(input) { n ->
        n.take(n.length / 2) == n.substring(n.length / 2)
    }

    fun part2(input: List<String>): Long = solve(input) { n ->
        (1..n.length / 2).any { length ->
            n.take(length).repeat(n.length / length) == n
        }
    }

    val input = readInput("Day02")
    printSolution { part1(input) }
    printSolution { part2(input) }
}