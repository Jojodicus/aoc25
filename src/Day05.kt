fun main() {
    fun isInRange(n: Long, ranges: List<LongRange>): LongRange? {
        ranges.forEach { if (n in it) return@isInRange it }
        return null
    }

    fun parse(input: List<String>): Pair<List<LongRange>, List<Long>> {
        val ranges = mutableListOf<LongRange>()
        val numbers = mutableListOf<Long>()

        var section = false
        for (line in input) {
            if (line == "") {
                section = true
                continue
            }

            if (section) {
                numbers += line.toLong()
            } else {
                line.split('-').let { strings ->
                    var start = strings[0].toLong()
                    var end = strings[1].toLong()

                    // normalize
                    start = isInRange(start, ranges)?.last?.plus(1) ?: start
                    end = isInRange(end, ranges)?.first?.minus(1) ?: end

                    if (start <= end) {
                        // this can still happen:
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