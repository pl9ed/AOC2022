package completed

fun main() {
    part1()
    part2()
    part2mod()
}

private val input = Inputs.inputs[1]

fun part1() {
    var points = 0

    input.forEachLine { line ->
        val me = line[2].shapeValue()
        val opponent = line[0].shapeValue()

        points += me + when (me - opponent) {
            0 -> 3
            1, -2 -> 6
            -1, 2 -> 0
            else -> throw RuntimeException()
        }
    }

    println("Part1: $points")
}

fun part2() {
    var points = 0

    input.forEachLine { line ->
        val opponent = line[0].shapeValue()
        val result = line[2]

        val me: Int

        when (result) {
            'X' -> {
                me = opponent - 1
            }
            'Y' -> {
                points += 3
                me = opponent
            }
            'Z' -> {
                points += 6
                me = opponent + 1
            }
            else -> throw RuntimeException()
        }

        points += me.wrap()
    }

    println("Part2: $points")
}

fun part2mod() {
    var points = 0

    input.forEachLine { line ->
        val opponent = line[0].shapeValue() - 1
        val result = line[2].shapeValue() - 2
        val resultPoints = line[2].resultValue()

        /*
        Map to 0-2 so we can directly % 3
        A: 0
        B: 1
        C: 2

        Result is same logic as previously, lose = char-1, tie = char, win = char+1
        X: char+(-1), lose
        Y: char+0, tie
        Z: char+1, win
        So we map XYZ -> -1 to 1 and add them together

        me = opponent + (-1/0/1)

        +3, then % 3 to handle negative

        Finally, +1 to map 0-2 back to 1-3
        */

        val me = (opponent + result + 3) % 3 + 1
        points += me + resultPoints
    }

    println("Part2Mod: $points")
}

private fun Int.wrap(): Int =
    when {
        this > 3 -> 1
        this < 1 -> 3
        else -> this
    }

private fun Char.resultValue(): Int =
    when (this) {
        'X' -> 0
        'Y' -> 3
        'Z' -> 6
        else -> throw Exception()
    }

private fun Char.shapeValue(): Int =
    when (this) {
        'A', 'X' -> 1
        'B', 'Y' -> 2
        'C', 'Z' -> 3
        else -> throw RuntimeException()
    }
