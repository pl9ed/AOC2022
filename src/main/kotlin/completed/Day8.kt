import java.io.File
import java.util.*

fun main() {
    Day8(Inputs.example).apply {
        println(part1())
        println(part2())
    }
    println("-------------")
    Day8(Inputs.inputs[7]).apply {
        println(part1())
        println(part2())
    }
}

class Day8(input: File) {

    private var height = 0
    private var width = 0
    private val rows: MutableList<List<Int>> = mutableListOf()
    private val cols: Array<IntArray>
    private val outerTrees: Int

    init {
        input.forEachLine {
            height++
            width = it.length

            it.toCharArray().map { char ->
                char.digitToInt()
            }.let { row -> rows.add(row) }
        }

        cols = Array(width) { IntArray(height) }
        rows.forEachIndexed { i, row ->
            row.forEachIndexed { j, int ->
                cols[j][i] = int
            }
        }

        outerTrees = (height * 2) + (width * 2) - 4
    }

    fun part1(): Int {
        var count = 0
        rows.forEachIndexed { i, row ->
            row.forEachIndexed { j, tree ->
                val cardinals = getTreesToEdge(i, j)
                var isGreatest = false
                cardinals.forEach {
                    if (it.isNotEmpty()) {
                        isGreatest = isGreatest || tree > it.max()
                    }
                }
                if (isGreatest && !isEdge(i, j)) {
                    count++
                    // println("Tree ${rows[i][j]} at $i,$j")
                }
            }
        }
        return count + outerTrees
    }

    fun part2(): Int {
        var max = 0
        rows.forEachIndexed { i, row ->
            row.forEachIndexed { j, tree ->
                val (north, east, south, west) = getTreesToEdge(i, j)

                val scenicScore = getUnidirectionalScore(north, tree, true) *
                    getUnidirectionalScore(west, tree, true) *
                    getUnidirectionalScore(east, tree) *
                    getUnidirectionalScore(south, tree)

                if (scenicScore > max) {
                    max = scenicScore
                }
            }
        }

        return max
    }

    private fun getUnidirectionalScore(trees: List<Int>, tree: Int, decrement: Boolean = false): Int {
        val range = if (decrement) trees.indices.reversed() else trees.indices
        var score = 0

        for (i in range) {
            score++
            if (trees[i] >= tree) {
                break
            }
        }

        return score
    }

    private fun getTreesToEdge(i: Int, j: Int): List<List<Int>> {
        val west = rows[i].subList(0, j)
        val east = rows[i].subList(j + 1, width)
        val north = cols[j].toList().subList(0, i)
        val south = cols[j].toList().subList(i + 1, height)

        return listOf(north, east, south, west)
    }

    private fun isEdge(i: Int, j: Int): Boolean =
        i == 0 || j == 0 || i == width - 1 || j == height - 1
}
