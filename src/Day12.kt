import common.Position
import common.readInput
import kotlin.math.min

// i am sorry for the mess, but i fell behind in schedule and will not refactor or optimize this code
// still hoping to catch up

fun main() {

    fun part1(input: List<String>): Long {
        val charCodeArray = input.to2dArray()
        val startingPosition = charCodeArray.startingPosition
        val targetPosition = charCodeArray.targetPosition
        val allNodes = createNodes(charCodeArray)
        val startingNode = allNodes.getByPosition(startingPosition)
        return calculateDistanceToTargetPosition(startingNode, targetPosition, allNodes)
    }

    fun part2(input: List<String>): Long {
        val charCodeArray = input.to2dArray()
        val targetPosition = charCodeArray.targetPosition
        val allNodes = createNodes(charCodeArray)

        return allNodes
            .filter { it.height == 'a'.code }
            .minOfOrNull {
                calculateDistanceToTargetPosition(it, targetPosition, allNodes)
                    .also { reset(allNodes) }
            }!!
    }

    val day = "12"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 31L) { part1(testInput) }
    check(part2(testInput) == 29L) { part2(testInput) }

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun reset(nodes: List<Node>) {
    nodes.forEach { it.apply { hasBeenVisited = false } }
}

private fun calculateDistanceToTargetPosition(
    startingNode: Node,
    targetPosition: Position,
    allNodes: List<Node>,
): Long {
    println("-------------")
    println("startingNode: ${startingNode.position}")
    println("-------------")
    val unvisitedNodes = allNodes.toMutableList()
    var currentNode = startingNode.apply { distanceToStart = 0 }
    while (!targetHasBeenReached(allNodes, targetPosition) && existsReachableUnvisitedNode(allNodes)) {
        unvisitedNodes.remove(currentNode)
        currentNode.apply { hasBeenVisited = true }
        val relevantNeighbors =
            unvisitedNodes
                .filter { it.isDirectNeighborOf(currentNode) }
                .filter { it.isClimbableFrom(currentNode) }
        relevantNeighbors.forEach {
            it.apply { distanceToStart = min(distanceToStart, currentNode.distanceToStart + 1) }
        }
        currentNode = unvisitedNodes.minByOrNull { it.distanceToStart } ?: break
    }
    return allNodes
        .getByPosition(targetPosition)
        .distanceToStart
}

private fun createNodes(charCodeArray: Array<IntArray>): List<Node> {
    val heights = charCodeArray.convertToHeights()
    return (charCodeArray.indices).flatMap { row ->
        charCodeArray[0].indices.map { column ->
            Node(
                position = Position(x = column, y = row),
                height = heights[row][column]
            )
        }
    }
}

private fun targetHasBeenReached(allNodes: List<Node>, targetPosition: Position) =
    allNodes.getByPosition(targetPosition).hasBeenVisited

private fun existsReachableUnvisitedNode(allNodes: List<Node>) =
    allNodes
        .filterNot { it.hasBeenVisited }
        .minOf { it.distanceToStart } < Long.MAX_VALUE

private fun List<Node>.getByPosition(position: Position) = this.single { it.position == position }

private fun List<String>.to2dArray(): Array<IntArray> =
    this.map { it.toCharArray().toTypedArray() }
        .map { charArray -> charArray.map { it.code }.toIntArray() }
        .toTypedArray()

private fun Array<IntArray>.getPositionOf(target: Int) = this.mapIndexedNotNull() { index, row ->
    if (row.contains(target)) {
        Position(y = index, x = row.indexOf(target))
    } else null
}.single()

private val Array<IntArray>.startingPosition: Position
    get() = this.getPositionOf('S'.code)

private val Array<IntArray>.targetPosition: Position
    get() = this.getPositionOf('E'.code)

private fun Array<IntArray>.convertToHeights() = this.apply {
    this[this.startingPosition.y][this.startingPosition.x] = 'a'.code
    this[this.targetPosition.y][this.targetPosition.x] = 'z'.code
}

private data class Node(
    val position: Position,
    val height: Int,
) {
    var hasBeenVisited: Boolean = false
    var distanceToStart: Long = Long.MAX_VALUE

    fun isDirectNeighborOf(other: Node) =
        (this.position.x == other.position.x && this.position.y in listOf(
            other.position.y - 1,
            other.position.y + 1
        )
                || this.position.y == other.position.y && this.position.x in listOf(
            other.position.x - 1,
            other.position.x + 1
        ))

    fun isClimbableFrom(other: Node) = this.height <= other.height + 1
}
