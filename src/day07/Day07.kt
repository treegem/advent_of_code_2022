package day07

import common.readInput

fun main() {

    fun part1(input: List<String>) = FileSystem(input)
        .directories
        .map { it.size }
        .filter { it <= 100_000L }
        .sum()

    fun part2(input: List<String>): Long {
        val fileSystem = FileSystem(input)
        val freeSpace = 70_000_000L - fileSystem.directories.first().size
        val requiredSpace = 30_000_000 - freeSpace

        return fileSystem
            .directories
            .map { it.size }
            .filter { it > requiredSpace }
            .minOf { it }
    }

    val day = "07"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 95_437L)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

