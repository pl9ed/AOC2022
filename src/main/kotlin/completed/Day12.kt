package completed

import java.io.File

fun main() {
    Day12(Inputs.example).apply {
        part1()
        part2()
    }
    println("-------------------")
    Day12(Inputs.getDay(12)).apply {
        part1()
        part2()
    }
}

class Day12(val input: File) {

    private var chars: MutableList<CharArray> = mutableListOf()
    private var graph: MutableList<List<Node>> = mutableListOf()
    private val start = 'S'
    private val end = 'E'
    private lateinit var startNode: Node
    private lateinit var destinationNode: Node

    private fun resetState() {
        chars = mutableListOf()
        graph = mutableListOf()
        input.forEachLine {
            chars.add(it.toCharArray())

            val row = mutableListOf<Node>()
            it.forEach { char ->
                row.add(Node(char, -1))
            }
            graph.add(row)
        }

        linkNodes()
    }

    private fun linkNodes() {
        for (i in 0 until graph.size) {
            for (j in 0 until graph[0].size) {
                graph[i][j].apply {
                    this.x = i
                    this.y = j
                    if (this.height == start) {
                        this.height = 'a'
                        this.distance = 0
                        startNode = this
                    }
                    if (this.height == end) {
                        destinationNode = this
                        destinationNode.height = 'z'
                    }
                    if (i > 0) {
                        this.adjacent[0] = (graph[i - 1][j])
                    }
                    if (i < graph.size - 1) {
                        this.adjacent[1] = graph[i + 1][j]
                    }
                    if (j > 0) {
                        this.adjacent[2] = graph[i][j - 1]
                    }
                    if (j < graph[0].size - 1) {
                        this.adjacent[3] = graph[i][j + 1]
                    }
                }
            }
        }
    }

    fun part1() {
        resetState()
        val graphSet = graph.flatten().toMutableSet()

        var head: Node = startNode
        graphSet.remove(head)

        while (graphSet.isNotEmpty()) {
            head.adjacent.forEach { node ->
                node?.let {
                    if (head.canMove(it) && graphSet.contains(it)) {
                        it.distance = head.distance + 1
                    }
                }
            }

            if (destinationNode.distance > 0) {
                break
            }

            head = graphSet.filter { it.distance > 0 }.minBy { it.distance }
            graphSet.remove(head)
        }
        println(destinationNode.distance)
    }

    fun part2() {
        resetState()
        val startingNodes = graph.flatten().filter { it.height == 'a' }.map { Pair(it.x, it.y) }

        startingNodes.map { start ->
            val graphSet = graph.flatten().toMutableSet()
            var head = graph[start.first][start.second]
            head.distance = 0
            graphSet.remove(head)

            while (graphSet.isNotEmpty()) {
                head.adjacent.forEach { node ->
                    node?.let {
                        if (head.canMove(it) && graphSet.contains(it)) {
                            it.distance = head.distance + 1
                        }
                    }
                }

                if (destinationNode.distance > 0) {
                    break
                }

                try {
                    head = graphSet.filter { it.distance > 0 }.minBy { it.distance }
                } catch (e: Exception) {
                    break
                }
                graphSet.remove(head)
            }

            val temp = destinationNode.distance
            resetState()
            temp
        }.filter { it > 0 }.min().let { println(it) }
    }

    private fun printGraph() {
        graph.forEach { row ->
            println(row.map { it.distance }.joinToString())
        }
    }
}

class Node(var height: Char, var distance: Int) {

    val adjacent: Array<Node?> = Array(4) { null }

    var x: Int = -1
    var y: Int = -1

    fun canMove(other: Node): Boolean {
        return other.height.code - this.height.code <= 1
    }
}
