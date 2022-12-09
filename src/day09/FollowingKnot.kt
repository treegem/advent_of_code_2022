package day09

import kotlin.math.abs

class FollowingKnot(private val leadingKnot: Knot) : Knot {
    override var position = Position.startingPosition()

    val visitedDistinctPositions = mutableSetOf(position.copy())

    fun adjustToLeadingKnot() = when {
        isCloseEnoughTo() -> {}
        else -> moveTowards()
    }

    private fun isCloseEnoughTo() = when {
        abs(leadingKnot.position.x - position.x) <= 1 && abs(leadingKnot.position.y - position.y) <= 1 -> true
        else -> false
    }

    private fun moveTowards() {
        val newPosition = when {
            requiresVerticalMovement() -> calculateVerticallyAdjustedPosition()
            requiresHorizontalMovement() -> calculateHorizontallyAdjustedPosition()
            else -> calculateDiagonallyAdjustedPosition()
        }
        visitedDistinctPositions.add(newPosition.copy())
        position = newPosition
    }

    private fun calculateDiagonallyAdjustedPosition(): Position {
        val newX =
            if (leadingKnot.position.x > position.x) {
                position.x + 1
            } else {
                position.x - 1
            }

        val newY =
            if (leadingKnot.position.y > position.y) {
                position.y + 1
            } else {
                position.y - 1
            }

        return Position(newX, newY)
    }

    private fun calculateHorizontallyAdjustedPosition() = Position(
        x = getXPositionNextToLeadingKnot(),
        y = position.y
    )

    private fun getXPositionNextToLeadingKnot() =
        if (leadingKnot.position.x > position.x) {
            leadingKnot.position.x - 1
        } else {
            leadingKnot.position.x + 1
        }

    private fun calculateVerticallyAdjustedPosition() = Position(
        x = position.x,
        y = getYPositionNextToLeadingKnot()
    )

    private fun getYPositionNextToLeadingKnot() =
        if (leadingKnot.position.y > position.y) {
            leadingKnot.position.y - 1
        } else {
            leadingKnot.position.y + 1
        }

    private fun requiresHorizontalMovement() = leadingKnot.position.y == position.y

    private fun requiresVerticalMovement() = leadingKnot.position.x == position.x
}
