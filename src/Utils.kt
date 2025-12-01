import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/inputs/$name.txt").readText().trim().lines()

/**
 * The clean shorthand for printing solutions.
 */
fun Any?.printSolution() {
    println("part ${part++}: $this")
}
private var part = 1
