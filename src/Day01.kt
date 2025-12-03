import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun Int.rotate(rotation: String): Int {
        val clicks = rotation.substring(1).toInt()
        return this + when (rotation[0]) {
            'L' -> -clicks
            'R' -> clicks
            else -> throw IllegalStateException()
        }
    }

    fun part1(input: List<String>): Int {
        var knob = 50
        return input.count {
            knob = Math.floorMod(knob.rotate(it), 100)
            knob == 0
        }
    }

    fun part2(input: List<String>): Int {
        var knob = 50
        return input.sumOf {
            val oldKnobSign = knob.sign
            knob = knob.rotate(it)

            // count: rounds of 100s (division)
            // if we had a positive number before, also count hits to zero or the sway into negative zone
            val passedZero = abs(knob) / 100 + if (oldKnobSign == 1 && knob.sign <= 0) 1 else 0

            knob = Math.floorMod(knob, 100)
            passedZero
        }
    }

    val input = readInput("Day01")
    printSolution { part1(input).toLong() }
    printSolution { part2(input).toLong() }
}
