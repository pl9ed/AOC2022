package completed

fun main() {
    Day4.apply {
        part1()
        part2()
    }
}

object Day4 {

    private val input = Inputs.inputs[3]

    fun part1() {
        var count = 0
        input.forEachLine {
            val pair = it.parseLine()
            val elf1 = pair.first
            val elf2 = pair.second

            if (
                elf1[0] >= elf2[0] &&
                elf1[1] <= elf2[1]
            ) {
                count++
            } else if (
                elf1[0] <= elf2[0] &&
                elf1[1] >= elf2[1]
            ) {
                count++
            }
        }
        println(count)
    }

    fun part2() {
        var count = 0
        input.forEachLine {
            val pair = it.parseLine()
            val range1 = pair.first[0]..pair.first[1]
            val range2 = pair.second[0]..pair.second[1]

            if (
                range1.first in range2 ||
                range1.last in range2
            ) {
                count++
            } else if (
                range2.first in range1 ||
                range2.last in range1
            ) {
                count++
            }
        }
        println(count)
    }

    private fun String.parseLine(): Pair<List<Int>, List<Int>> {
        val stringPair = this.split(",")
        val elf1 = stringPair[0].split("-").map { it.toInt() }
        val elf2 = stringPair[1].split("-").map { it.toInt() }
        return Pair(elf1, elf2)
    }
}
