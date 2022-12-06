package completed

import java.io.File

fun main() {
    Day3.apply {
        part1()
        part2()
    }
}

object Day3 {
    private val input: File = Inputs.inputs[2]
    /*
    a - 97
    A - 65
     */

    fun part1() {
        var sum = 0
        input.forEachLine {
            val firstHalf = it.subSequence(0, it.length / 2)
            val secondHalf = it.subSequence(it.length / 2, it.length)
            val intersection = firstHalf.toSet().intersect(secondHalf.toSet())
            if (intersection.size == 1) {
                val char = intersection.toList()[0]
                sum += char.toValue()
            }
        }
        println("Part 1 sum: $sum")
    }

    fun part2() {
        var sum = 0
        val inputArr = input.readText().split("\n")

        for (i in 2..inputArr.size step 3) {
            val badge = inputArr[i - 2].toSet()
                .intersect(inputArr[i - 1].toSet())
                .intersect(inputArr[i].toSet())
                .toList()[0]
            sum += badge.toValue()
        }

        // manual steps with %
        // inputArr.forEachIndexed { i, line ->
        //     if ((i+1) % 3 == 0 && i > 1) {
        //         val badge = inputArr[i-2].toSet()
        //             .intersect(inputArr[i-1].toSet())
        //             .intersect(inputArr[i].toSet())
        //             .toList()[0]
        //         sum += badge.toValue()
        //     }
        // }
        //
        println(sum)
    }

    private fun Char.toValue(): Int = if (this.isUpperCase()) {
        this.code - 38
    } else {
        this.code - 96
    }
}
