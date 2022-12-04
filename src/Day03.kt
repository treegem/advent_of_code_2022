fun main() {
    val day = "03"

    fun part1(input: List<String>) =
        input
            .map { findBadlyPackedItem(it) }
            .sumOf { getPriority(it) }

    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .map { findBatch(it) }
            .sumOf { getPriority(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun findBadlyPackedItem(line: String): Char {
    val compartmentSize = line.length / 2
    val firstCompartmentItems = line.take(compartmentSize).toSet()
    val secondCompartmentItems = line.takeLast(compartmentSize).toSet()
    val correctlyPackedItemsInFirstCompartment = firstCompartmentItems - secondCompartmentItems

    return (firstCompartmentItems - correctlyPackedItemsInFirstCompartment).single()
}

private fun getPriority(item: Char) = if (item.isLowerCase()) {
    item.code - 'a'.code + 1
} else {
    item.code - 'A'.code + 27
}

private fun findBatch(lines: List<String>): Char {
    require(lines.size == 3)
    val (firstBag, secondBag, thirdBag) = lines.map { it.toSet() }
    return firstBag.toSet()
        .filter { it in secondBag }
        .filter { it in thirdBag }
        .toCharArray()
        .single()
}
