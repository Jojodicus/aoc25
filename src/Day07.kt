fun main() {
    data class Solution(val splits: Long, val timelines: Long)

    fun solve(input: List<String>): Solution {
        val indexS = input.first().indexOf('S')
        var lastTimelines = List<Long>(input.first().length) { if (it == indexS) 1 else 0 }
        var runningTimelines = MutableList<Long>(lastTimelines.size) { 0 }
        var splits = 0L

        input.drop(1).forEach { line ->
            lastTimelines.forEachIndexed { col, timeline ->
                if (line[col] == '^') {
                    // split
                    runningTimelines[col - 1] += timeline
                    runningTimelines[col + 1] += timeline
                    splits++
                } else {
                    // go down
                    runningTimelines[col] += timeline
                }
            }

            lastTimelines = runningTimelines
            runningTimelines = MutableList(lastTimelines.size) { 0 }
        }

        return Solution(splits, lastTimelines.sum())
    }

    fun part1(input: List<String>) = solve(input).splits

    fun part2(input: List<String>) = solve(input).timelines

    val input = readInput("Day07")
    printSolution { part1(input) }
    printSolution { part2(input) }
}