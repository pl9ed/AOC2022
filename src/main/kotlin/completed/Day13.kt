package completed

import java.io.File
import java.util.Stack

fun main() {
    Day13(Inputs.example).case(6)
}

class Day13(input: File) {

    val pairs = mutableListOf<List<String>>()

    init {
        input.readText().split("\n\n").forEach {
            pairs.add(it.split("\n"))
        }
    }

    fun case(n: Int) {
        println("$n : ${inOrder(pairs[n][0], pairs[n][1])}")

    }

    fun part1() {
        pairs.forEachIndexed { i, pair ->
            println("$i : ${inOrder(pair[0], pair[1])}")

        }
    }

    private fun inOrder(first: String, second: String): Boolean {
        val left = first.toMutableList()
        val right = second.toMutableList()

        val leftValues = mutableListOf<Int>()
        val rightValues = mutableListOf<Int>()

        var ld = 0
        var rd = 0

        while (left.isNotEmpty() && right.isNotEmpty()) {
            var leftChar = left.removeFirst()
            var rightChar = right.removeFirst()

            while (left.isNotEmpty() && !leftChar.isDigit()) {
                when (leftChar) {
                    '[' -> ld++
                    ']' -> ld--
                }
                leftChar = left.removeFirst()

            }

            while (right.isNotEmpty() && !rightChar.isDigit()) {
                when (rightChar) {
                    '[' -> rd++
                    ']' -> rd--
                }
                rightChar = right.removeFirst()
            }

            if (left.isEmpty()) return true
            if (leftChar > rightChar) return false
            if (right.isEmpty() && ld > rd) return false
        }

        return true
    }

}
