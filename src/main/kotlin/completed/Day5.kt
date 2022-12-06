package completed

import java.util.Stack

fun main() {
    Day5.apply {
        part1()
        part2()
    }
}

object Day5 {
    private val input = Inputs.inputs[4].readText().split("\n\n")

    private val cols = 1..9
    private var stacks: MutableMap<Int, Stack<Char>> = mutableMapOf()
    private val instructions: MutableList<List<Int>> = mutableListOf()

    init {
        for (i in cols) {
            stacks[i] = Stack<Char>()
        }
        parseInstructions()
    }

    private fun getStackFromInput() {
        parseStack()
        println("Initial stacks:")
        printStack()
    }

    fun part1() {
        getStackFromInput()
        instructions.forEach { instruction ->
            val number = instruction[0]
            val source = instruction[1]
            val destination = instruction [2]
            for (i in 1..number) {
                stacks[destination]!!.push(stacks[source]!!.pop())
            }
        }
        print("Part 1: ")
        for (i in cols) {
            print("${stacks[i]!!.pop()}")
        }
        println("\n-----------------------")
    }

    fun part2() {
        getStackFromInput()
        instructions.forEach { instruction ->
            val number = instruction[0]
            val source = instruction[1]
            val destination = instruction [2]

            // intermediate so stack goes in the correct order
            val temp = Stack<Char>()
            for (i in 1..number) {
                temp.push(stacks[source]!!.pop())
            }

            while (temp.isNotEmpty()) {
                stacks[destination]!!.push(temp.pop())
            }
        }

        print("Part 2: ")
        for (i in cols) {
            print("${stacks[i]!!.pop()}")
        }
    }

    private fun printStack() {
        stacks.keys.forEach {
            println("$it - ${stacks[it]}")
        }
    }

    private fun parseStack() {
        val stackString = input[0].split("\n").reversed()
        for (i in 1 until stackString.size) {
            stackString[i].parseLine().forEachIndexed { j, char ->
                if (char.isLetter()) {
                    stacks[j + 1]!!.push(char)
                }
            }
        }
    }

    private fun String.parseLine(): List<Char> {
        val lineArr = mutableListOf<Char>()
        for (i in 1 until this.length step 4) {
            lineArr.add(this[i])
        }
        return lineArr
    }

    private fun parseInstructions() {
        input[1].split("\n").forEach {
            if (it.isNotBlank()) {
                val instruction = it.split(Regex("\\D+")).filterIndexed { i, _ ->
                    i != 0
                }.map { num ->
                    num.toInt()
                }
                instructions.add(instruction)
            }
        }
    }
}
