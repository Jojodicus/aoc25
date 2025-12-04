fun main() {
    // would have been a lot nicer if there was a conv2d function

    fun get2d(list: List<List<Int>>, y: Int, x: Int): Int {
        return list.getOrNull(y)?.getOrNull(x) ?: 0
    }

    fun cleanup(nums: List<List<Int>>): List<List<Int>> {
        return nums.withIndex().map { (y, l) ->
            l.withIndex().map { (x, e) ->
                when (e) {
                    0 -> 0
                    else -> {
                        val neighbours =
                            get2d(nums, y - 1, x - 1) + get2d(nums, y - 1, x) + get2d(nums, y - 1, x + 1) +
                                    get2d(nums, y, x - 1) + 0 + get2d(nums, y, x + 1) +
                                    get2d(nums, y + 1, x - 1) + get2d(nums, y + 1, x) + get2d(nums, y + 1, x + 1)

                        if (neighbours < 4) 1 else 0
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val nums = input.map { line ->
            line.map { (it - '.') / ('@' - '.') }
        }

        return cleanup(nums).sumOf { it.sum() }.toLong()
    }

    fun part2(input: List<String>): Long {
        var nums = input.map { line ->
            line.map { (it - '.') / ('@' - '.') }
        }

        var clean = cleanup(nums)
        var cleanups = clean.sumOf { it.sum() }

        while (clean.any { l -> l.any { it == 1}}) {
            nums = nums.zip(clean).map { l ->
                l.first.zip(l.second).map {
                    it.first - it.second
                }
            }
            clean = cleanup(nums)
            cleanups += clean.sumOf { it.sum() }
        }

        return cleanups.toLong()
    }

    val input = readInput("Day04")
    printSolution { part1(input) }
    printSolution { part2(input) }
}