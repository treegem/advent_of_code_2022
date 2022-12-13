import common.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val registerHistory = input.toRegisterHistory()

        return (20..220 step 40).sumOf { registerHistory[it]!! * it }
    }

    fun part2(input: List<String>) =
        input.toRegisterHistory()
            .toSortedMap()
            .values
            .mapIndexed { pixel, spritePosition ->
                if ((pixel % 40) in (spritePosition - 1..spritePosition + 1)) '█'
                else ' '
            }
            .chunked(40)
            .joinToString("\n") { it.joinToString("") }

    val day = "10"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 13140)
    println(part2(testInput))

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toRegisterHistory(): Map<Int, Int> {
    val history = mutableMapOf(1 to 1)
    var cycle = 1
    this.forEach {
        history[++cycle] = history[cycle - 1]!!
        if (it.startsWith("addx")) {
            history[++cycle] = history[cycle - 1]!! + it.substringAfterLast(" ").toInt()
        }
    }
    return history
}


