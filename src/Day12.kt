import common.Position
import common.readInput

fun main() {

    fun part1(input: List<String>): Int {
        val charCodeArray = input.to2dArray()
        val startingPosition = charCodeArray.startingPosition
        val targetPosition = charCodeArray.targetPosition
        val routes = listOf<List<Position>>()
        val heights = charCodeArray.convertToHeights()
        val visitedPositions = mutableListOf(startingPosition)
        var currentRoute =
        var currentPosition = startingPosition
        while (currentPosition != targetPosition) {
            val nextPossiblePositions = mutableListOf<Position>()
            val xs = (currentPosition.x -1 .. currentPosition.x + 1)
            val ys = (currentPosition.y -1 .. currentPosition.y + 1)
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val day = "12"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 31)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.to2dArray(): Array<IntArray> =
    this.map { it.toCharArray().toTypedArray() }
        .map { charArray -> charArray.map { it.code }.toIntArray() }
        .toTypedArray()

private fun Array<IntArray>.getPositionOf(target: Int) = this.mapIndexedNotNull() { index, row ->
    if (row.contains(target)) {
        Position(index, row.indexOf(target))
    } else null
}.single()

private val Array<IntArray>.startingPosition: Position
    get() = this.getPositionOf('S'.code)

private val Array<IntArray>.targetPosition: Position
    get() = this.getPositionOf('E'.code)

private fun Array<IntArray>.convertToHeights() = this.apply {
    this[this.startingPosition.y][this.startingPosition.x] = 'a'.code
    this[this.targetPosition.y][this.targetPosition.x] = 'z'.code
}
