fun main() {
    fun solve(input: List<String>, countCallback: (Long) -> Boolean): Long {
        val ranges = input[0].split(",").map { range -> range.split("-").map { it.toLong() } }
        val stream = ranges.flatMap { (min, max) -> min..max }
        val repeated = stream.filter { countCallback(it) }
        return repeated.sum()
    }

    fun part1(input: List<String>): Long {
        return solve(input) {
            val str = it.toString()
            str.take(str.length / 2) == str.substring(str.length / 2)
        }
    }

    fun part2(input: List<String>): Long {
        return solve(input) { n ->
            val str = n.toString()
            (1..str.length / 2).any { length ->
                str.take(length).repeat(str.length / length) == str
            }
        }
    }

    val input = readInput("Day02")
    part1(input).printSolution()
    part2(input).printSolution()
}