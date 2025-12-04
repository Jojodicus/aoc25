fun main() {
    // would have been a lot nicer if there was a conv2d function

    data class Coordinate(val x: Int, val y: Int)

    fun parse(input: List<String>): MutableSet<Coordinate> {
        val set = mutableSetOf<Coordinate>()

        input.withIndex().forEach { (y, l) ->
            l.withIndex().forEach { (x, e) ->
                if (e == '@') {
                    set.add(Coordinate(x, y))
                }
            }
        }

        return set
    }

    fun Set<Coordinate>.has(x: Int, y: Int) = if (this.contains(Coordinate(x, y))) 1 else 0

    fun cleanup(nums: MutableSet<Coordinate>): Int {
        val move = mutableSetOf<Coordinate>()

        nums.forEach { (x, y) ->
            val neighbours = nums.has(x - 1, y - 1) + nums.has(x, y - 1) + nums.has(x + 1, y - 1) +
                    nums.has(x - 1, y) + nums.has(x + 1, y) +
                    nums.has(x - 1, y + 1) + nums.has(x, y + 1) + nums.has(x + 1, y + 1)
            if (neighbours < 4) {
                move.add(Coordinate(x, y))
            }
        }

        move.forEach { nums.remove(it) }
        return move.size
    }

    fun part1(input: List<String>) = cleanup(parse(input)).toLong()

    fun part2(input: List<String>): Long {
        val nums = parse(input)

        var count = 0L
        var clean = cleanup(nums)

        while (clean > 0) {
            count += clean
            clean = cleanup(nums)
        }

        return count
    }

    val input = readInput("Day04")
    printSolution { part1(input) }
    printSolution { part2(input) }
}