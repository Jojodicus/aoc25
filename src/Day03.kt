fun main() {
    fun jolt(bank: String, n: Int): String {
        if (n == 1) {
            return "${bank.max()}"
        }
        val relevant = bank.dropLast(n - 1)
        val max = relevant.max()
        val lowerJolt = jolt(bank.drop(relevant.indexOf(max) + 1), n - 1)
        return "$max$lowerJolt"
    }

    fun part1(input: List<String>): Long = input.sumOf { jolt(it, 2).toLong() }

    fun part2(input: List<String>): Long = input.sumOf { jolt(it, 12).toLong() }

    val input = readInput("Day03")
    printSolution { part1(input) }
    printSolution { part2(input) }
}