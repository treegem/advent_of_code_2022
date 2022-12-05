fun main() {

    fun part1(input: List<String>): String {
        val crates = getCrates(input)

        val instructions = getInstructions(input)

        instructions.forEach { it.applyMovingSingleBoxes(crates) }

        return getTopCratesString(crates)
    }

    fun part2(input: List<String>): String {
        val crates = getCrates(input)

        val instructions = getInstructions(input)

        instructions.forEach { it.applyMovingMultipleBoxes(crates) }

        return getTopCratesString(crates)
    }

    val day = "05"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toInstructions() =
    this.map { it.split(' ') }
        .map { splitStrings ->
            splitStrings.mapNotNull { it.toIntOrNull() }
        }
        .map {
            Instruction(
                amount = it[0],
                source = it[1] - 1,
                target = it[2] - 1
            )
        }

private fun List<String>.toCrates(): List<MutableList<Char>> {
    val informationIndices = (1..this.first().length).step(4)

    val crates = List(informationIndices.count()) { mutableListOf<Char>() }

    this.forEach { line ->
        informationIndices.forEachIndexed { index, informationIndex ->
            if (line[informationIndex] != ' ') crates[index].add(line[informationIndex])
        }
    }

    return crates
}

private fun MutableList<Char>.putOneOnTop(char: Char) = this.add(0, char)

private fun MutableList<Char>.putSomeOnTop(chars: List<Char>) =
    chars
        .reversed()
        .forEach { this.add(0, it) }

private fun MutableList<Char>.takeOneFromTop() = this.removeFirst()

private fun MutableList<Char>.takeSomeFromTop(amount: Int) =
    this.take(amount)
        .also {
            repeat(amount) { this.removeFirst() }
        }

private fun getCrates(input: List<String>) = input
    .filterNot { it.startsWith('m') }
    .filterNot { it.isBlank() }
    .filter { it.trim().startsWith('[') }
    .toCrates()

private fun getInstructions(input: List<String>) = input
    .filter { it.startsWith('m') }
    .toInstructions()

private fun getTopCratesString(crates: List<MutableList<Char>>) = crates
    .map { it.first() }
    .joinToString(separator = "")

private class Instruction(
    val amount: Int,
    val source: Int,
    val target: Int,
) {
    fun applyMovingSingleBoxes(crates: List<MutableList<Char>>) {
        repeat(amount) {
            crates[source]
                .takeOneFromTop()
                .let { crates[target].putOneOnTop(it) }

        }
    }

    fun applyMovingMultipleBoxes(crates: List<MutableList<Char>>) {
        crates[source]
            .takeSomeFromTop(amount)
            .let { crates[target].putSomeOnTop(it) }

    }
}
