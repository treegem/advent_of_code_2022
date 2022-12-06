fun main() {

    fun part1(input: String) = findMarkerEndIndex(input, 4)

    fun part2(input: String) = findMarkerEndIndex(input, 14)

    val day = "06"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").first()
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day${day}").first()
    println(part1(input))
    println(part2(input))
}

private fun findMarkerEndIndex(input: String, markerLength: Int) = input
    .indices
    .firstNotNullOf { startIndex ->
        input
            .substring(startIndex)
            .take(markerLength)
            .toSet()
            .takeIf { it.size == markerLength }
            ?.let { startIndex + markerLength }
    }
