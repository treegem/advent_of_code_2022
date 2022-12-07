package day07

import day07.CommandLineConstants.COMMAND_CD
import day07.CommandLineConstants.COMMAND_LS
import day07.CommandLineConstants.DIR
import day07.CommandLineConstants.PARENT_DIR
import day07.LineType.*

class FileSystem(input: List<String>) {

    private var currentDirectoryName: String = "/"

    private var currentParents = mutableListOf<String>()

    val directories: MutableSet<Directory> = mutableSetOf(Directory(listOf(), currentDirectoryName))

    init {

        input
            .takeLast(input.size - 1)
            .forEach { line ->
                when (line.type) {
                    CHANGE_DIRECTORY -> changeDirectory(line)
                    LIST_DIRECTORY -> {}
                    PRINT_DIRECTORY -> addDirectoryIfNotExists(line)
                    PRINT_FILE -> addFile(line)
                }
            }
    }

    private fun changeDirectory(line: String) {
        val directoryName = line.substringAfter(COMMAND_CD)
        currentDirectoryName = when (directoryName) {
            PARENT_DIR -> {
                currentParents.removeLast()
            }

            else -> {
                currentParents.add(currentDirectoryName)
                directoryName
            }
        }
    }

    private fun addDirectoryIfNotExists(line: String) {
        val directoryName = line.substringAfter(DIR)
        if (!existsByNameAndParents(directoryName)) {
            val newDirectory = Directory(
                parents = currentParents + currentDirectoryName,
                name = directoryName
            )
            directories.add(newDirectory)
            getCurrentDirectory().directories.add(newDirectory)
        }
    }

    private fun addFile(line: String) {
        getCurrentDirectory()
            .apply { files.add(File.from(line)) }
    }

    private fun getCurrentDirectory() =
        directories.single { it.name == currentDirectoryName && it.path == currentParents.joinToString() }

    private fun existsByNameAndParents(directoryName: String) =
        directories.any {
            it.name == directoryName
                    && it.parents.joinToString() == (currentParents + currentDirectoryName).joinToString()
        }

}

enum class LineType { CHANGE_DIRECTORY, LIST_DIRECTORY, PRINT_DIRECTORY, PRINT_FILE, }

object CommandLineConstants {
    const val COMMAND_CD = "$ cd "
    const val COMMAND_LS = "$ ls"
    const val DIR = "dir "
    const val PARENT_DIR = ".."
}

private val String.type: LineType
    get() = when {
        startsWith(COMMAND_CD) -> CHANGE_DIRECTORY
        startsWith(COMMAND_LS) -> LIST_DIRECTORY
        startsWith(DIR) -> PRINT_DIRECTORY
        else -> PRINT_FILE
    }
