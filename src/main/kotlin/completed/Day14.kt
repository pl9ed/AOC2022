package completed

import java.io.File
import java.lang.IndexOutOfBoundsException

fun main() {
    Day14(Inputs.getFile("example14.txt")).apply {
        part1()
        printGrid()
    }
    Day14(Inputs.getFile("input14.txt")).part1()
}

class Day14(input: File) {

    private val walls: MutableList<MutableList<MutableList<Int>>> = mutableListOf()
    private var xMax = 0
    private var yMax = 0
    private var xMin = Int.MAX_VALUE
    private var yMin = Int.MAX_VALUE

    private val width: Int
    private val height: Int

    private val grid: Array<Array<Char>>

    var fallingPoint: List<Int>
    var sand: Int = 0

    init {
        input.forEachLine { line ->
            val coordinates: MutableList<MutableList<Int>> = mutableListOf()
            line.split(" -> ").forEach { coordinate ->
                coordinate.split(",").map {
                    it.toInt()
                }.toMutableList().let {
                    val x = it[0]
                    val y = it[1]
                    coordinates.add(it)
                    if (x > xMax) xMax = x
                    if (x < xMin) xMin = x

                    if (y > yMax) yMax = y
                    if (y < yMin) yMin = y
                }
            }
            walls.add(coordinates)
        }

        width = xMax - xMin + 1
        height = yMax + 1

        fallingPoint = listOf(500 - xMin, 0)
        grid = Array(height) { Array(width) { '.' } }

        walls.forEach { wall ->
            wall.forEach { coordinate ->
                coordinate[0] -= xMin
            }
        }

        println("Coordinates:")
        walls.forEach {
            println(it.joinToString())
        }

        populateGrid()

        println(fallingPoint.joinToString())
        grid[fallingPoint[1]][fallingPoint[0]] = '+'

        println("Grid:")
        grid.forEach {
            println(it.contentToString())
        }
    }

    fun part1() {
        try {
            while (true) {
                drop(fallingPoint[0], fallingPoint[1])
                sand++
            }
        } catch (e: IndexOutOfBoundsException) {
            println(e)
            println("Sand: $sand")
        }

    }

    fun printGrid() {
        println("Grid state:")
        grid.forEach {
            println(it.contentToString())
        }
    }

    private fun drop(x: Int, y: Int) {
        if (grid(x, y + 1) == '.') drop(x, y + 1) // down
        if (grid(x - 1, y + 1) == '.') drop (x - 1, y + 1) // left
        if (grid(x + 1, y + 1) == '.') drop (x + 1, y + 1) // right
        sand++
        grid[y][x] = 'o'
    }

    private fun grid(x: Int, y: Int) =
        grid[y][x]

    private fun populateGrid() {
        walls.forEach { wall ->
            for (i in 1 until wall.size) {
                val xRange = setOf(wall[i - 1][0], wall[i][0]).sorted()
                val yRange = setOf(wall[i - 1][1], wall[i][1]).sorted()

                try {
                    if (xRange.size > 1) {
                        for (x in xRange[0]..xRange[1]) {
                            grid[yRange[0]][x] = '#'
                        }
                    } else {
                        for (y in yRange[0]..yRange[1]) {
                            grid[y][xRange[0]] = '#'
                        }
                    }
                } catch (e: Exception) {
                    grid.forEach {
                        println(it.contentToString())
                    }
                    throw e
                }

            }
        }
        walls
    }
}
