package day07

class Directory(
    val parents: List<String>,
    val name: String,
) {
    val directories: MutableSet<Directory> = mutableSetOf()

    val files: MutableSet<File> = mutableSetOf()

    val size: Long
        get() = files.sumOf { it.size } + directories.sumOf { it.size }

    val path: String
        get() = parents.joinToString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Directory

        if (name != other.name) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }


}
