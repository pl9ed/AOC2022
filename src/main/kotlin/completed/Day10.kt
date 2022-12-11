package completed

import java.io.File

fun main() {
    Day10(Inputs.example).apply {
        println(part1())
        part2()
    }
    println("-------------------")
    Day10(Inputs.getDay(10)).apply {
        println(part1())
        part2()
    }
}

class Day10(input: File) {

    private val cmds: List<String> = input.readText().split("\n")
    private val registerValues: MutableList<Int> = mutableListOf(1)

    fun part1(): Int {
        cmds.forEachIndexed { i, str ->
            if (str.isNotBlank()) {
                val cmd = str.trim().split(" ")
                when (cmd[0]) {
                    "addx" -> {
                        registerValues.add(registerValues.last())
                        try {
                            registerValues.add(registerValues.last() + cmd[1].toInt())
                        } catch (e: NumberFormatException) {
                            println(str)
                            println(cmd[1])
                        }
                    }
                    "noop" -> {
                        registerValues.add(registerValues.last())
                    }
                }
            }
        }

        var signal = 20 * registerValues[19]

        for (i in 59 until registerValues.size step 40) {
            signal += ((i + 1) * registerValues[i])
        }

        return signal
    }

    fun part2() {
        println("Part 2 Output:")
        registerValues.forEachIndexed { i, value ->
            val spritePosition = i % 40
            if (i % 40 == 0) {
                print("\n")
            }
            if (spritePosition in value - 1..value + 1) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}
