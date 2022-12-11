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

    private fun resetState() {
        monkeys.clear()

        input.readText().split("\n\n").forEach { str ->
            monkeys.add(Monkey(str))
        }
    }

    fun part1() {
        resetState()

        val inspectionCounts = Array(monkeys.size) { 0 }

        for (i in 0 until 20) {
            monkeys.forEachIndexed { n, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val (target, item) = monkey.parseItem(3)
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
        resetState()

        val inspectionCounts = Array(monkeys.size) { 0 }

        for (i in 0 until 20) {
            monkeys.forEachIndexed { n, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val (target, item) = monkey.parseItem(1)
                    inspectionCounts[n]++
                    monkeys[target].items.add(item)
                }
            }
        }

        println(inspectionCounts.contentToString())

        val monkeyBusiness = inspectionCounts.sortedDescending().take(2).reduce { n, m ->
            n * m
        }
        println(monkeyBusiness)
    }
}

class Monkey {

    companion object {

        fun getTest(string: String): (Int) -> Int {
            val lines = string.split("\n")

            val divisibleBy: Int = lines[3].filter { it.isDigit() }.toInt()
            val trueMonkey: Int = lines[4].filter { it.isDigit() }.toInt()
            val falseMonkey: Int = lines[5].filter { it.isDigit() }.toInt()

            return { worry ->
                if (worry % divisibleBy == 0) {
                    trueMonkey
                } else {
                    falseMonkey
                }
            }
        }

        fun getInspect(string: String): (Int) -> Int {
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
                    val value = mutation[2].trim().toInt()
                    operation(worry, value)
                } catch (e: NumberFormatException) {
                    operation(worry, worry)
                }
            }
        }

        fun getItems(string: String): MutableList<Int> {
            val lines = string.split("\n")
            val items = mutableListOf<Int>()

            lines[1].split(":")[1].trim().split(", ").forEach { item ->
                items.add(item.toInt())
            }

            return items
        }
    }

    var inspect: (Int) -> Int
    var test: (Int) -> Int
    var items = mutableListOf<Int>()

    constructor(string: String) {
        items = getItems(string)
        inspect = getInspect(string)
        test = getTest(string)
    }

    constructor(inspect: (Int) -> Int, test: (Int) -> Int, items: MutableList<Int>) {
        this.inspect = inspect
        this.test = test
        this.items = items
    }

    fun parseItem(inspectAdjustment: Int = 1): Array<Int> {
        val item = items.removeFirst()
        val worry = inspect(item) / inspectAdjustment
        val target = test(worry)

        return arrayOf(target, worry)
    }
}
