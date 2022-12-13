fun main() {

    fun part1(input: List<String>): Int {
        val charCodeArray = input.to2dArray()
        val startingPosition = charCodeArray.startingPosition

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val day = "XX"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 1)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.to2dArray(): Array<IntArray> =
    this.map { it.toCharArray().toTypedArray() }
        .map { charArray -> charArray.map { it.code - 'a'.code }.toIntArray() }
        .toTypedArray()
