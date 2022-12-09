package day09

data class Position(
    var x: Int,
    var y: Int,
) {
    companion object {
        fun startingPosition() = Position(0, 0)
    }
}
