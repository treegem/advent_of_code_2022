package day08

import common.readInput

fun main() {

    fun part1(input: List<String>) = Forest(input).countVisibleTreesFromOutside()

    fun part2(input: List<String>) = Forest(input).calculateMaxScenicScore()

    val day = "08"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

