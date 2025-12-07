import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.measureTime

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    println(name)
    return Path("src/inputs/$name.txt").readText().trim().lines()
}

/**
 * The clean shorthand for printing solutions.
 */
fun printSolution(solver: () -> Long) {
    // precondition
    (1..3).forEach { _ -> measureTime { solver() } }

    val answer: Long
    val time = measureTime {
        answer = solver()
    }
    println("part ${part++}: $answer")
    println("took: $time")
}
private var part = 1
