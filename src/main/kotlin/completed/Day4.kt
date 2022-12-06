package completed

fun main() {
    Day4.apply {
        part1()
        part2()
    }
}

object Day4 {

    private val input = Inputs.inputs[3]

    init {
    }

    fun part1() {
        var count = 0
        input.forEachLine { line ->
            val pair = line.split(",")
            val elf1 = pair[0].split("-")
            val elf2 = pair[1].split("-")

            val range1 = elf1[0].toInt().rangeTo(elf1[1].toInt())
            val range2 = elf2[0].toInt().rangeTo(elf2[1].toInt())

            if (
                elf1[0].toInt() >= elf2[0].toInt() &&
                elf1[1].toInt() <= elf2[1].toInt()
            ) {
                println(line)
                count++
            }

            if (
                elf1[0].toInt() <= elf2[0].toInt() &&
                elf1[1].toInt() >= elf2[1].toInt()
            ) {
                println(line)
                count++
            }
        }
        println(count)
    }

    fun part2() {
    }
}
