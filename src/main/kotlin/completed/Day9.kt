package completed

import java.io.File
import kotlin.math.abs

fun main() {
    Day9(Inputs.example).apply {
        part1()
        part2()
    }

    println("-----------")

    Day9(Inputs.getDay(9)).apply {
        part1()
        part2()
    }
}

class Day9(val input: File) {

    private val ropePart1 = Array(2) { arrayOf(0, 0) }
    private val ropePart2: Array<Array<Int>> = Array(10) { arrayOf(0, 0) }

    private fun run(rope: Array<Array<Int>>) {
        val visited = mutableSetOf(Pair(0, 0))

        input.forEachLine { line ->
            val step = line.trim().split(" ")
            val value = step[1].toInt()
            rope.apply {
                for (iterations in 0 until value) {
                    when (step[0]) {
                        "U" -> rope[0][1]++
                        "D" -> rope[0][1]--
                        "L" -> rope[0][0]--
                        "R" -> rope[0][0]++
                    }

                    for (i in 1 until this.size) {
                        val prev = this[i - 1]
                        val current = this[i]
                        if (!isTooFar(prev, current)) continue

                        if (prev[0] > current[0]) current[0]++
                        if (prev[0] < current[0]) current[0]--
                        if (prev[1] > current[1]) current[1]++
                        if (prev[1] < current[1]) current[1]--
                    }
                    visited.add(Pair(this.last()[0], this.last()[1]))
                }
            }
        }

        println(visited.size)
    }

    fun part1() {
        run(ropePart1)
    }

    fun part2() {
        run(ropePart2)
    }

    private fun isTooFar(head: Array<Int>, tail: Array<Int>) =
        abs(head[0] - tail[0]) > 1 ||
            abs(head[1] - tail[1]) > 1
}
