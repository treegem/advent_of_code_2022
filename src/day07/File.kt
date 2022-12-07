package day07

data class File(
    val name: String,
    val size: Long,
) {
    companion object {
        fun from(inputLine: String) = inputLine
            .split(' ')
            .let {
                File(
                    name = it.last(),
                    size = it.first().toLong()
                )
            }
    }
}
