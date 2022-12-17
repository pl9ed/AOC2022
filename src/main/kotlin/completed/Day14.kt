package completed

import java.io.File
import java.lang.IndexOutOfBoundsException
import kotlin.math.abs

fun main() {

    // TODO: clean this up so p1/p2 use same impl

    // Day14(Inputs.getFile("example14.txt")).apply {
    //     part1()
    //     printGrid()
    // }
    // Day14(Inputs.getFile("example14.txt")).apply {
    //     part2()
    //     printGrid2()
    // }

    Day14(Inputs.getFile("input14.txt")).apply {
        part1()
        printGrid()
    }
    Day14(Inputs.getFile("input14.txt")).apply {
        part2()
        printGrid2()
    }
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
    private val grid2 = mutableMapOf<Pair<Int, Int>, Char>()

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

        // println("Coordinates:")
        // walls.forEach {
        //     println(it.joinToString())
        // }

        populateGrid()

        grid[fallingPoint[1]][fallingPoint[0]] = '+'
        grid2[Pair(fallingPoint[1], fallingPoint[0])] = '+'

        // println("Grid:")
        // grid.forEach {
        //     println(it.contentToString())
        // }
    }

    fun part1() {
        try {
            while (true) {
                drop(fallingPoint[0], fallingPoint[1])
                sand++
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Sand: $sand")
        }
        println(sand)
    }

    fun part2() {
        while (grid2(fallingPoint[0], fallingPoint[1]) == '+') {
            drop2(fallingPoint[0], fallingPoint[1])
            sand++
        }
        sand-- // adjust for final drop() call
        println(sand)
    }

    fun printGrid() {
        val file = File("src/main/resources/14_grid.txt")
        file.createNewFile()

        grid.forEach {
            file.appendText(it.contentToString() + "\n")
        }
    }

    fun printGrid2() {
        val x = grid2.keys.maxOf { it.first }
        val y = grid2.keys.maxOf { it.second }
        val offset = abs(grid2.keys.minOf { it.second })
        val arr: Array<Array<Char>> = Array(x + 4) { Array(y + offset + 1) { '.' } }

        grid2.forEach { (key, value) ->
            arr[key.first][key.second + offset] = value
        }

        val file = File("src/main/resources/14_grid2.txt")
        file.createNewFile()
        arr.forEach {
            file.appendText(it.contentToString() + "\n")
        }
    }

    private fun drop(x: Int, y: Int) {
        if (grid(x, y + 1) == '.') drop(x, y + 1) // down
        if (grid(x - 1, y + 1) == '.') drop (x - 1, y + 1) // left
        if (grid(x + 1, y + 1) == '.') drop (x + 1, y + 1) // right
        sand++
        grid[y][x] = 'o'
    }

    private fun drop2(x: Int, y: Int) {
        if (y >= yMax + 2) {
            return
        }
        if (grid2(x, y + 1) == null) drop2(x, y + 1) // down
        if (grid2(x - 1, y + 1) == null) drop2(x - 1, y + 1) // left
        if (grid2(x + 1, y + 1) == null) drop2(x + 1, y + 1) // right
        sand++
        grid2[Pair(y, x)] = 'o'
    }

    private fun grid(x: Int, y: Int) =
        grid[y][x]

    private fun grid2(x: Int, y: Int) =
        grid2[Pair(y, x)]

    private fun populateGrid() {
        walls.forEach { wall ->
            for (i in 1 until wall.size) {
                val xRange = setOf(wall[i - 1][0], wall[i][0]).sorted()
                val yRange = setOf(wall[i - 1][1], wall[i][1]).sorted()

                try {
                    if (xRange.size > 1) {
                        for (x in xRange[0]..xRange[1]) {
                            grid[yRange[0]][x] = '#'
                            grid2[Pair(yRange[0], x)] = '#'
                        }
                    } else {
                        for (y in yRange[0]..yRange[1]) {
                            grid[y][xRange[0]] = '#'
                            grid2[Pair(y, xRange[0])] = '#'
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
