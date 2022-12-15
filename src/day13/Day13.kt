package day13

import common.readInput

// i am sorry for the mess, but i fell behind in schedule and will not refactor or optimize this code
// still hoping to catch up

fun main() {

    fun part1(input: List<String>) =
        input
            .asSequence()
            .filter { it.isNotBlank() }
            .chunked(2)
            .map { list -> list.map { it.toListContent() } }
            .map { it.first().getOutcome(it.last()) }
            .mapIndexedNotNull { index, outcome ->
                if (outcome < 0) index + 1 else null
            }
            .sum()

    fun part2(input: List<String>): Int {
        val firstSeparator = "[[2]]".toListContent()
        val secondSeparator = "[[6]]".toListContent()
        val packets = input
            .filter { it.isNotBlank() }
            .map { it.toListContent() }
            .toMutableList()
            .also { it.add(firstSeparator) }
            .also { it.add(secondSeparator) }

        while (!packets.isSorted()) {
            packets
                .indices
                .toList()
                .dropLast(1)
                .map { index ->
                    if (packets[index].getOutcome(packets[index + 1]) > 0) {
                        val originalLeftValue = packets[index]
                        packets[index] = packets[index + 1]
                        packets[index + 1] = originalLeftValue
                    }
                }
        }

        return (packets.indexOf(firstSeparator) + 1) * (packets.indexOf(secondSeparator) + 1)
    }

    val day = "13"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<ListContent>.isSorted() =
    this.windowed(2)
        .map { it.first().getOutcome(it.last()) }
        .all { it < 0 }

private fun String.toListContent(): ListContent {
    val iterator = this.toCharArray().iterator()
    require(iterator.nextChar() == '[')
    return parseListContent(iterator)
}

fun parseNumberContent(currentNumber: String, iterator: CharIterator): Pair<NumberContent, Boolean> {
    val nextChar = iterator.nextChar()
    return if (nextChar.isDigit()) {
        parseNumberContent(currentNumber + nextChar, iterator)
    } else {
        val isEndOfList = nextChar == ']'
        NumberContent(currentNumber.toInt()) to isEndOfList
    }
}

fun parseListContent(iterator: CharIterator): ListContent {
    val resultList = mutableListOf<PacketContent>()
    var currentChar = iterator.nextChar()
    while (currentChar != ']') {
        when {
            currentChar.isDigit() -> {
                val parseResult = parseNumberContent(currentChar.toString(), iterator)
                resultList.add(parseResult.first)
                if (parseResult.second) break
            }

            currentChar == '[' -> resultList.add(parseListContent(iterator))
        }
        currentChar = iterator.nextChar()

    }
    return ListContent(resultList.toList())
}
