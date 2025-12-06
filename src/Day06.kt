fun main() {
    data class Homework(val rows: List<List<Long>>, val operator: List<Char>)

    fun parse(input: List<String>): Homework {
        return Homework(
            rows = input.dropLast(1).map {
                it.split(" ").filter(String::isNotEmpty).map(String::toLong)
            },
            operator = input.last().split(" ").filter(String::isNotEmpty).map { it[0] },
        )
    }

    fun parse2(input: List<String>): Homework {
        val max = input.maxOf { it.length }
        val padded = input.map {
            it.padEnd(max, ' ')
        }

        val rows = mutableListOf<List<Long>>()
        val operator = mutableListOf<Char>()

        var row = mutableListOf<Long>()
        var runningOperator = ' '
        for ((i, op) in padded.last().withIndex()) {
            // column finished?
            if (padded.map { it[i] }.all { it == ' ' }) {
                rows += row
                operator += runningOperator
                row = mutableListOf()
                runningOperator = ' '
                continue
            }

            val col = padded.dropLast(1).map { it[i] }.filter { it != ' ' }
            val num = col.map(Char::digitToInt).reduce { acc, e -> acc * 10 + e }
            row += num.toLong()

            if (op != ' ') {
                runningOperator = op
            }
        }

        rows += row
        operator += runningOperator

        return Homework(rows, operator)
    }

    fun part1(input: List<String>): Long {
        val homework = parse(input)

        return homework.operator.withIndex().sumOf { (i, op) ->
            when (op) {
                '+' -> homework.rows.sumOf { it[i] }
                '*' -> homework.rows.map { it[i] }.reduce(Long::times)
                else -> 0
            }
        }
    }

    fun part2(input: List<String>): Long {
        val homework = parse2(input)

        return homework.operator.withIndex().sumOf { (i, op) ->
            when (op) {
                '+' -> homework.rows[i].sum()
                '*' -> homework.rows[i].reduce(Long::times)
                else -> 0
            }
        }
    }

    val input = readInput("Day06")
    printSolution { part1(input) }
    printSolution { part2(input) }
}