package common

data class Position(
    var x: Int,
    var y: Int,
) {
    companion object {
        fun defaultPosition() = Position(0, 0)
    }
}
