fun main() {
    val day = "04"

    fun part1(input: List<String>) =
        input
            .map { it.toSectionAssignmentPair() }
            .map { it.isOneFullyContainedByOther() }
            .count { it }

    fun part2(input: List<String>) =
        input
            .map { it.toSectionAssignmentPair() }
            .map { it.doAssignmentsOverlap() }
            .count { it }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun String.toSectionAssignmentPair() =
    this
        .split(',')
        .map { it.toSectionAssignment() }
        .let { SectionAssignmentPair(it.first(), it.last()) }

private fun String.toSectionAssignment() =
    this
        .split('-')
        .let {
            SectionAssignment(
                start = it.first().toInt(),
                end = it.last().toInt()
            )
        }

private class SectionAssignmentPair(
    val first: SectionAssignment,
    val second: SectionAssignment,
) {
    fun isOneFullyContainedByOther() = first.containsFully(second) || second.containsFully(first)

    fun doAssignmentsOverlap(): Boolean = first.overlapsWith(second)
}

private class SectionAssignment(
    val start: Int,
    val end: Int,
) {
    fun containsFully(other: SectionAssignment) = this.start <= other.start && this.end >= other.end

    fun overlapsWith(other: SectionAssignment) =
        this.start in other.start..other.end
                || this.end in other.start..other.end
                || other.start in this.start..this.end
                || other.end in this.start..this.end
}
