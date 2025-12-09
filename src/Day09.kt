import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class CommunistTile(val x: Long, val y: Long)

    fun part1(input: List<String>): Long {
        val tiles = input.map {
            val (x, y) = it.split(",").map(String::toLong)
            CommunistTile(x, y)
        }

        var maxArea = 0L

        tiles.forEach { tile1 ->
            tiles.forEach { tile2 ->
                val area = (abs(tile1.x - tile2.x) + 1) * (abs(tile1.y - tile2.y) + 1)
                maxArea = max(maxArea, area)
            }
        }

        return maxArea
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    printSolution { part1(readInput("Day09-test")) }
    printSolution { part2(readInput("Day09-test")) }

    val input = readInput("Day09")
    printSolution { part1(input) }
    printSolution { part2(input) }
}