package completed

import java.io.File
import java.lang.NumberFormatException

fun main() {
    Day11(Inputs.example).apply {
        part1()
        part2()
    }
    println("--------------------")
    Day11(Inputs.getDay(11)).apply {
        part1()
    }
}

class Day11(val input: File) {

    private val monkeys = mutableListOf<Monkey>()

    init {
        resetState()
    }

    private fun resetState() {
        input.readText().split("\n\n").forEach { str ->
            monkeys.add(Monkey(str))
        }
    }

    fun part1() {
        val inspectionCounts = Array(monkeys.size) { 0 }

        for (i in 0 until 20) {
            monkeys.forEachIndexed { n, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val (target, item) = monkey.parseItem()
                    inspectionCounts[n]++
                    monkeys[target].items.add(item)
                }
            }
        }

        val monkeyBusiness = inspectionCounts.sortedDescending().take(2).reduce { n, m ->
            n * m
        }
        println(monkeyBusiness)
    }

    fun part2() {

    }
}

class Monkey {

    val inspect: (Int) -> Int
    val test: (Int) -> Int
    var items = mutableListOf<Int>()

    constructor(string: String) {
        val lines = string.split("\n")

        lines[1].split(":")[1].trim().split(", ").forEach { item ->
            items.add(item.toInt())
        }
        inspect = getOperation(lines[2].split(":")[1])
        val divisibleBy: Int = lines[3].filter { it.isDigit() }.toInt()
        val trueMonkey: Int = lines[4].filter { it.isDigit() }.toInt()
        val falseMonkey: Int = lines[5].filter { it.isDigit() }.toInt()

        test = { worry ->
            if (worry % divisibleBy == 0) {
                trueMonkey
            } else {
                falseMonkey
            }
        }
    }

    constructor(inspect: (Int) -> Int, test: (Int) -> Int, items: MutableList<Int>) {
        this.inspect = inspect
        this.test = test
        this.items = items
    }

    fun parseItem(): Array<Int> {
        val item = items.removeFirst()
        val worry = inspect(item) / 3
        val target = test(worry)

        return arrayOf(target, worry)
    }

    private fun getOperation(string: String): (Int) -> Int {
        val mutation = string.split("=")[1].trim().split(" ")
        // should be old {operation} {param}
        val operation: (Int, Int) -> Int = when (mutation[1]) {
            "+" -> Int::plus
            "-" -> Int::minus
            "*" -> Int::times
            "/" -> Int::div
            else -> throw Exception("Unexpected operation ${mutation[1]}")
        }
        return { worry ->
            try {
                val value = mutation[2].toInt()
                operation(worry, value)
            } catch (e: NumberFormatException) {
                operation(worry, worry)
            }
        }
    }
}
