package day09

import common.Position

class Head : Knot {
    override val position: Position = Position.defaultPosition()

    fun move(direction: Direction): Unit = when (direction) {
        Direction.UP -> position.y += 1
        Direction.DOWN -> position.y -= 1
        Direction.LEFT -> position.x -= 1
        Direction.RIGHT -> position.x += 1
    }
}
