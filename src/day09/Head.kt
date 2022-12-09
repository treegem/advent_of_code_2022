package day09

class Head : Knot {
    override val position: Position = Position.startingPosition()

    fun move(direction: Direction): Unit = when (direction) {
        Direction.UP -> position.y += 1
        Direction.DOWN -> position.y -= 1
        Direction.LEFT -> position.x -= 1
        Direction.RIGHT -> position.x += 1
    }
}
