package day08

class Forest(input: List<String>) {

    private val treeHeights: Array<IntArray> = input.to2dArray()

    private val indices1d = treeHeights.indices

    private val trees = indices1d.map { row ->
        indices1d.map { column ->
            Tree(
                height = treeHeights[row][column],
                row = row,
                column = column
            )
        }
    }.flatten()

    fun countVisibleTreesFromOutside() = trees.map(::isVisible).count { it }

    fun calculateMaxScenicScore() = trees.maxOfOrNull(::calculateScenicScore)!!

    private fun calculateScenicScore(tree: Tree) =
        countVisibleTreesAbove(tree) *
                countVisibleTreesBelow(tree) *
                countVisibleTreesToTheLeft(tree) *
                countVisibleTreesToTheRight(tree)

    private fun countVisibleTreesAbove(tree: Tree): Int {
        var currentRow = tree.row + 1
        var visibleTrees = 0
        while (currentRow in indices1d) {
            visibleTrees += 1
            if (treeHeights[currentRow][tree.column] >= tree.height) break
            currentRow += 1
        }
        return visibleTrees
    }

    private fun countVisibleTreesBelow(tree: Tree): Int {
        var currentRow = tree.row - 1
        var visibleTrees = 0
        while (currentRow in indices1d) {
            visibleTrees += 1
            if (treeHeights[currentRow][tree.column] >= tree.height) break
            currentRow -= 1
        }
        return visibleTrees
    }

    private fun countVisibleTreesToTheLeft(tree: Tree): Int {
        var currentColumn = tree.column - 1
        var visibleTrees = 0
        while (currentColumn in indices1d) {
            visibleTrees += 1
            if (treeHeights[tree.row][currentColumn] >= tree.height) break
            currentColumn -= 1
        }
        return visibleTrees
    }

    private fun countVisibleTreesToTheRight(tree: Tree): Int {
        var currentColumn = tree.column + 1
        var visibleTrees = 0
        while (currentColumn in indices1d) {
            visibleTrees += 1
            if (treeHeights[tree.row][currentColumn] >= tree.height) break
            currentColumn += 1
        }
        return visibleTrees
    }

    private fun isVisible(tree: Tree) = when {
        isOnEdge(tree)
                || isVisibleFromAbove(tree)
                || isVisibleFromBelow(tree)
                || isVisibleFromLeft(tree)
                || isVisibleFromRight(tree) -> true

        else -> false
    }

    private fun isVisibleFromAbove(tree: Tree) =
        (0 until tree.row).map { treeHeights[it][tree.column] }.all { it < tree.height }

    private fun isVisibleFromBelow(tree: Tree) =
        (tree.row + 1..indices1d.last).map { treeHeights[it][tree.column] }.all { it < tree.height }

    private fun isVisibleFromLeft(tree: Tree) =
        (0 until tree.column).map { treeHeights[tree.row][it] }.all { it < tree.height }

    private fun isVisibleFromRight(tree: Tree) =
        (tree.column + 1..indices1d.last).map { treeHeights[tree.row][it] }.all { it < tree.height }

    private fun isOnEdge(tree: Tree) =
        listOf(tree.row, tree.column).any { it == 0 || it == indices1d.last }
}

private fun List<String>.to2dArray(): Array<IntArray> =
    this.map { it.toCharArray() }
        .map { charArray -> charArray.map { it.toString() }.toTypedArray() }
        .map { stringArray -> stringArray.map { it.toInt() }.toIntArray() }
        .toTypedArray()
