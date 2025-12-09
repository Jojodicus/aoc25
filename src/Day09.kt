import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class CommunistTile(val x: Long, val y: Long)

    fun parse(input: List<String>) = input.map {
        val (x, y) = it.split(",").map(String::toLong)
        CommunistTile(x, y)
    }

    fun part1(input: List<String>): Long {
        val tiles = parse(input)

        var maxArea = 0L

        tiles.forEachIndexed { i, tile1 ->
            tiles.drop(i + 1).forEach { tile2 ->
                val area = (abs(tile1.x - tile2.x) + 1) * (abs(tile1.y - tile2.y) + 1)
                maxArea = max(maxArea, area)
            }
        }

        return maxArea
    }

    fun part2(input: List<String>): Long {
        val tiles = parse(input)

        val xMap = tiles.asSequence().map { it.x }.distinct().sorted().withIndex().associate { (i, x) -> x to i }
        val yMap = tiles.asSequence().map { it.y }.distinct().sorted().withIndex().associate { (i, y) -> y to i }

        val grid = MutableList(yMap.size) {
            MutableList(xMap.size) { '.' }
        }

        // green
        for ((tile0, tile1) in (tiles + tiles.first()).zip(listOf(tiles.first()) + tiles)) {
            val tile0y = yMap[tile0.y]!!
            val tile0x = xMap[tile0.x]!!
            val tile1y = yMap[tile1.y]!!
            val tile1x = xMap[tile1.x]!!
            for (y in min(tile0y, tile1y)..max(tile0y, tile1y)) {
                for (x in min(tile0x, tile1x)..max(tile0x, tile1x)) {
                    grid[y][x] = 'X'
                }
            }
        }

        // red
        for (tile in tiles) {
            grid[yMap[tile.y]!!][xMap[tile.x]!!] = '#'
        }

        // flood
        val startX = grid.first().withIndex().find { it.value == 'X' }?.index ?: throw IllegalStateException()
        val queue = LinkedBlockingQueue(listOf(CommunistTile(startX.toLong(), 1)))
        while (queue.isNotEmpty()) {
            val tile = queue.poll()

            if (grid[tile.y.toInt()][tile.x.toInt()] != '.') continue
            grid[tile.y.toInt()][tile.x.toInt()] = 'X'

            for (next in listOf(
                CommunistTile(tile.x - 1, tile.y), CommunistTile(tile.x + 1, tile.y),
                CommunistTile(tile.x, tile.y - 1), CommunistTile(tile.x, tile.y + 1)
            )) {
                queue.offer(next)
            }
        }

        // optional: print
//        grid.forEach { println(it.joinToString("")) }

        var maxArea = 0L

        tiles.forEachIndexed { i, tile1 ->
            tiles.drop(i + 1).forEach inner@{ tile2 ->
                // optimization idea: check borders first
                for (y in min(yMap[tile1.y]!!, yMap[tile2.y]!!)..max(yMap[tile1.y]!!, yMap[tile2.y]!!)) {
                    for (x in min(xMap[tile1.x]!!, xMap[tile2.x]!!)..max(xMap[tile1.x]!!, xMap[tile2.x]!!)) {
                        if (grid[y][x] == '.') return@inner
                    }
                }

                val area = (abs(tile1.x - tile2.x) + 1) * (abs(tile1.y - tile2.y) + 1)
                maxArea = max(maxArea, area)
            }
        }

        return maxArea
    }

    val input = readInput("Day09")
    printSolution { part1(input) }
    printSolution { part2(input) }
}