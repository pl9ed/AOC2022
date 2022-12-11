package completed

import java.io.File
import java.lang.NumberFormatException

fun main() {
    Day11(Inputs.example).apply {
        part1()
        part2()
    }
}

class Day11(val input: File) {

    private val monkeys = mutableListOf<Monkey>()

    init {
        input.readText().split("\n\n").forEach { str ->
            monkeys.add(str.toMonkey())
        }

        monkeys.forEach {
            it.items.joinToString()
        }
    }

    private fun String.toMonkey(): Monkey {
        lateinit var items: MutableList<Int>
        lateinit var inspect: (Int) -> Int
        var divisibleBy: Int = -1
        var trueMonkey: Int = -1
        var falseMonkey: Int = -1

        val lines = this.split("\n")

        lines[1].split(":")[1].trim().split(", ").forEach { item ->
            items.add(item.toInt())
        }
        inspect = getOperation(lines[2].split(":")[1])
        divisibleBy = lines[3].filter { it.isDigit() }.toInt()
        trueMonkey = lines[4].filter { it.isDigit() }.toInt()
        falseMonkey = lines[5].filter { it.isDigit() }.toInt()
        val test: (Int) -> Int = { worry ->
            if (worry % divisibleBy == 0) {
                trueMonkey
            } else {
                falseMonkey
            }
        }

        return Monkey(inspect, test, items)
    }

    private fun getOperation(string: String): (Int) -> Int {
        val mutation = string.split("=")[1].trim().split(" ")
        // should be old {operation} {param}
        val value = mutation[2].toInt()
        val operation: (Int, Int) -> Int = when (mutation[1]) {
            "+" -> Int::plus
            "-" -> Int::minus
            "*" -> Int::times
            "/" -> Int::div
            else -> throw Exception("Unexpected operation ${mutation[1]}")
        }
        return { worry ->
            try {
                operation(worry, value)
            } catch (e: NumberFormatException) {
                operation(worry, worry)
            }
        }
    }

    fun part1() {

    }

    fun part2() {

    }
}

data class Monkey(
    val inspect: (Int) -> Int,
    val test: (Int) -> Int,
    val items: MutableList<Int> = mutableListOf(),
)