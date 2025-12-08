import java.util.*

fun main() {
    data class Box(val x: Long, val y: Long, val z: Long) {
        operator fun rangeTo(other: Box): Long {
            val dx = x - other.x
            val dy = y - other.y
            val dz = z - other.z
            return dx * dx + dy * dy + dz * dz
        }
    }

    data class Connection(var box1: Box, var box2: Box) : Comparable<Connection> {
        private val distance = box1..box2
        override fun compareTo(other: Connection) = this.distance.compareTo(other.distance)
    }

    fun parse(input: List<String>) = input.map { line ->
        val (x, y, z) = line.split(",").map(String::toLong)
        Box(x, y, z)
    }

    fun allConnectionsSorted(boxes: List<Box>) = PriorityQueue(
        boxes.flatMapIndexed { i, box1 ->
            boxes.drop(i + 1).map { box2 ->
                Connection(box1, box2)
            }
        }
    )

    fun solve(input: List<String>, part: Int, steps: Int = 1000): Long {
        val boxes = parse(input)
        val circuit = mutableMapOf(*boxes.zip(boxes).toTypedArray())

        val connections = allConnectionsSorted(boxes)
        var i = 0

        while (connections.isNotEmpty()) {
            if (part == 1 && i++ > steps) break
            val (b1, b2) = connections.poll()

            if (circuit[b1] != circuit[b2]) {
                circuit.filter { (_, e) -> e == circuit[b2] }.forEach {
                    circuit[it.key] = circuit[b1]!!
                }
            }

            if (part == 2 && circuit.values.distinct().size == 1) {
                return b1.x * b2.x
            }
        }

        val sizes = circuit.values.groupBy { it }.map { it.value.size }.sortedDescending()
        return sizes.take(3).reduce(Int::times).toLong()
    }

    fun part1(input: List<String>) = solve(input, part = 1)

    fun part2(input: List<String>) = solve(input, part = 2)

    val input = readInput("Day08")
    printSolution { part1(input) }
    printSolution { part2(input) }
}