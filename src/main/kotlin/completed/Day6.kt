package completed

import Inputs

fun main() {
    Day6.apply {
        firstNonRepeatingSequence(text, 4)
        firstNonRepeatingSequence(text, 14)
    }
}

object Day6 {

    private val input = Inputs.inputs[5]
    val ex = Inputs.example.readText()
    val text = input.readText().replace("\n", "")

    fun firstNonRepeatingSequence(string: String, length: Int) {
        for (i in length - 1 until string.length) {
            val chars = string.subSequence(i - length + 1 .. i).toSet()
            if (chars.size == length) {
                println("${i + 1}, $chars")
                break
            }
        }
    }
}
