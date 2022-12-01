fun main() {
    val day = "01"

    fun groupCaloriesByElf(input: List<String>): MutableList<MutableList<Int>> {
        val caloriesGrouped = mutableListOf<MutableList<Int>>()
        input.forEachIndexed() { index, inputString ->
            if (index == 0 || inputString.isBlank()) {
                caloriesGrouped.add(mutableListOf())
            } else {
                caloriesGrouped.last().add(inputString.toInt())
            }
        }
        return caloriesGrouped
    }

    fun part1(input: List<String>): Int {
        val caloriesGrouped = groupCaloriesByElf(input)

        return caloriesGrouped.maxOf { it.sum() }
    }

    fun part2(input: List<String>): Int {
        val caloriesGrouped = groupCaloriesByElf(input)

        return caloriesGrouped
            .map { it.sum() }
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 24_000)
    check(part2(testInput) == 45_000)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}
