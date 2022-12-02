import java.io.File

object Inputs {

    val inputDir = File("src/main/resources/")
    val inputs = mutableListOf<File>()

    init {
        inputDir.listFiles()?.forEach {
            inputs.add(it)
        }
    }

}
