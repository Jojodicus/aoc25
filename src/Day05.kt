fun main() {
    fun isInRange(n: Long, ranges: List<LongRange>) = ranges.find { n in it }

    fun parse(input: List<String>): Pair<List<LongRange>, List<Long>> {
        val ranges = mutableListOf<LongRange>()

        val section1 = input.takeWhile { it != "" }
        val numbers = input.drop(section1.size + 1).map(String::toLong)

        for (line in section1) {
            line.split('-').let { strings ->
                var start = strings[0].toLong()
                var end = strings[1].toLong()

                // normalize
                start = isInRange(start, ranges)?.first ?: start
                end = isInRange(end, ranges)?.last ?: end

                if (start <= end) {
                    // check for
                    // range:     |----|
                    // it:    |--------------|

                    val newRange = start..end
                    ranges.removeIf {
                        it.first in newRange
                    }
                    ranges += newRange
                }
            }
        }

        return ranges to numbers
    }

    fun part1(input: List<String>): Long {
        val (ranges, numbers) = parse(input)

        return numbers.count { number ->
            ranges.any { number in it }
        }.toLong()
    }

    fun part2(input: List<String>): Long {
        val (ranges, _) = parse(input)
        return ranges.sumOf { it.last - it.first + 1 }
    }

    val input = readInput("Day05")
    printSolution { part1(input) }
    printSolution { part2(input) }
}