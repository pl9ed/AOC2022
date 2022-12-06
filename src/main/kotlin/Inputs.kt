import java.io.File

object Inputs {

    private val inputDir = File("src/main/resources/")
    val inputs = mutableListOf<File>()
    val example = File("${inputDir}/example.txt")

    init {
        inputDir.listFiles().apply {
            sort()
            forEach {
                if (it.name.contains("input")) {
                    inputs.add(it)
                }
            }
        }
    }
}
