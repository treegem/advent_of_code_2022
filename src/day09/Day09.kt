package day09

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val head = Head()
        val tail = FollowingKnot(head)
        input
            .toDirections()
            .forEach {
                head.move(it)
                tail.adjustToLeadingKnot()
            }

        return tail.visitedDistinctPositions.size
    }

    fun part2(input: List<String>): Int {
        val head = Head()
        val middleKnots = mutableListOf(FollowingKnot(head))
        repeat((3..9).count()) { middleKnots.add(FollowingKnot(middleKnots.last())) }
        val tail = FollowingKnot(middleKnots.last())

        input
            .toDirections()
            .forEach {
                head.move(it)
                middleKnots.forEach(FollowingKnot::adjustToLeadingKnot)
                tail.adjustToLeadingKnot()
            }

        return tail.visitedDistinctPositions.size
    }

    val day = "09"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toDirections() =
    this.map(::Command)
        .flatMap { command -> List(command.repetition) { command.direction } }


private class Command(string: String) {
    private val commandComponents = string.split(" ")
    val repetition = commandComponents.last().toInt()
    val direction = Direction.fromString(commandComponents.first())
}

