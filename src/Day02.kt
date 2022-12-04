import Move.*
import Outcome.*

val possibleMovesNumber = Move.values().size

fun main() {
    val day = "02"

    fun part1(input: List<String>) = input.sumOf { calculateScorePart1(it) }

    fun part2(input: List<String>) = input.sumOf { calculateScorePart2(it) }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private enum class Move(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
}

private enum class Outcome(val score: Int) {
    WIN(6),
    DRAW(3),
    LOSS(0),
}

private fun getMyMovePart1(line: String) = when (line.last()) {
    'X' -> ROCK
    'Y' -> PAPER
    'Z' -> SCISSORS
    else -> throw IllegalArgumentException(line)
}

private fun getOpponentMove(line: String) = when (line.first()) {
    'A' -> ROCK
    'B' -> PAPER
    'C' -> SCISSORS
    else -> throw IllegalArgumentException(line)
}

private fun calculateOutcomePart1(myMove: Move, opponentMove: Move) = when (myMove.ordinal) {
    (opponentMove.ordinal + 1) % possibleMovesNumber -> WIN
    opponentMove.ordinal -> DRAW
    else -> LOSS
}

private fun getOutcomePart2(line: String) = when (line.last()) {
    'X' -> LOSS
    'Y' -> DRAW
    'Z' -> WIN
    else -> throw IllegalArgumentException(line)
}

private fun calculateScorePart1(line: String): Int {
    val myMove = getMyMovePart1(line)
    val opponentMove = getOpponentMove(line)

    val outcome = calculateOutcomePart1(myMove, opponentMove)

    return myMove.score + outcome.score
}

private fun calculateMyMovePart2(opponentMove: Move, outcome: Outcome) = when (outcome) {
    WIN -> Move.values()[(opponentMove.ordinal + 1) % possibleMovesNumber]
    DRAW -> opponentMove
    LOSS -> Move.values()[(opponentMove.ordinal + 2) % possibleMovesNumber]
}

private fun calculateScorePart2(line: String): Int {
    val opponentMove = getOpponentMove(line)
    val outcome = getOutcomePart2(line)

    val myMove: Move = calculateMyMovePart2(opponentMove, outcome)

    return myMove.score + outcome.score
}
