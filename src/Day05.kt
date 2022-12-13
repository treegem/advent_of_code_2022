import common.readInput

fun main() {

    fun part1(input: List<String>): String {
        val crates = input.getCrates()

        input
            .getInstructions()
            .forEach { it.applyMovingSingleBoxes(crates) }

        return crates.readTopCrates()
    }

    fun part2(input: List<String>): String {
        val crates = input.getCrates()

        input
            .getInstructions()
            .forEach { it.applyMovingMultipleBoxes(crates) }

        return crates.readTopCrates()
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

private fun List<String>.getCrates() =
    this.filterNot { it.startsWith('m') }
        .filterNot { it.isBlank() }
        .filter { it.trim().startsWith('[') }
        .toCrates()

private fun List<String>.getInstructions() =
    this.filter { it.startsWith('m') }
        .toInstructions()

private fun List<MutableList<Char>>.readTopCrates() =
    this.map { it.first() }
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
