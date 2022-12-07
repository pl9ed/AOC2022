package completed

import java.io.File
import java.util.*

fun main() {
    Day7(Inputs.example).apply {
        println("Part 1: ${part1()}")
        println("Part 2: ${part2()}")
    }
    println("---------------------")
    Day7(Inputs.inputs[6]).apply {
        println("Part 1: ${part1()}")
        println("Part 2: ${part2()}")
    }
    println("---------------------")
    Linear(Inputs.inputs[6]).apply {
        println("Part 1: ${part1()}")
        println("Part 2: ${part2()}")
    }
}

class Linear(input: File = Inputs.inputs[6]) {

    private val directories = mutableMapOf<String, Long>()
    private val dirs = Stack<String>()

    init {
        input.forEachLine {
            val arr = it.split(" ")
            when (arr[0]) {
                "$" -> cmd(arr)
                "dir" -> {}
                else -> {
                    updateValue(arr[0].toLong())
                }
            }
        }
    }

    fun part1(): Long =
        directories.map {
            it.value
        }.filter {
            it <= 100000
        }.sum()

    fun part2(): Long {
        val totalSize = 70000000L
        val requiredSize = 30000000L
        val root = Stack<String>()
        root.push("/")
        val neededSize = requiredSize - (totalSize - directories[root.toKey()]!!)

        return directories.toList()
            .filter {
                it.second >= neededSize
            }
            .sortedBy {
                it.second
            }[0].second
    }

    private fun cmd(args: List<String>) {
        if (args[1] == "cd") {
            when (args[2]) {
                "/" -> {
                    dirs.removeAllElements()
                    dirs.push("/")
                }
                ".." -> dirs.pop()
                else -> dirs.push(args[2])
            }
        }
    }

    private fun updateValue(value: Long) {
        val dirsCopy = Stack<String>()
        dirs.forEach {
            dirsCopy.push(it)
        }
        while (dirsCopy.isNotEmpty()) {
            val currentValue: Long = directories[dirsCopy.toKey()] ?: 0L
            directories[dirsCopy.toKey()] = currentValue + value
            dirsCopy.pop()
        }
    }

    private fun Stack<String>.toKey(): String =
        when (this.size) {
            1 -> "/"
            2 -> "/${this.peek()}/"
            else -> {
                val builder = StringBuilder()
                this.forEach {
                    builder.append("$it/")
                }
                builder.toString()
            }
        }
}

class Day7(input: File = Inputs.inputs[6]) {

    private val root = TreeNode.Directory("/", null)
    private var head = root
    private val directories = mutableListOf<TreeNode.Directory>()

    init {
        input.forEachLine { line ->
            val arr = line.split(" ").toMutableList()
            if (arr[0] == "$") {
                arr.removeFirst()
                if (arr[0] == "cd") {
                    head = when (arr[1]) {
                        ".." -> {
                            head.parent!!
                        }

                        "/" -> {
                            root
                        }

                        else -> {
                            head.children.filter { it.name == arr[1] }.filterIsInstance<TreeNode.Directory>().first()
                        }
                    }
                }
            } else {
                head.addChild(TreeNode.createNode(arr))
            }
        }

        addDirectories(root)
    }

    fun part1(): Long {
        directories.filter {
            it.size <= 100000
        }.sumOf {
            it.size
        }.let { return it }
    }

    fun part2(): Long {
        val totalSize = 70000000L
        val requiredSize = 30000000L
        val neededSize = requiredSize - (totalSize - root.size)

        return directories.filter {
            it.size >= neededSize
        }.sortedBy {
            it.size
        }[0].size
    }

    private fun addDirectories(node: TreeNode.Directory) {
        directories.add(node)
        node.children.forEach {
            if (it is TreeNode.Directory) {
                addDirectories(it)
            }
        }
    }
}

// this really doesn't need to be a sealed class, but I wanted more practice with different sealed class features
sealed class TreeNode {

    companion object Factory {
        fun createNode(input: List<String>): TreeNode {
            val node: TreeNode = if (input[0] == "dir") {
                Directory(input[1])
            } else {
                Leaf(input[1], input[0].toLong())
            }
            return node
        }
    }

    abstract val name: String
    abstract val size: Long
    abstract var parent: Directory?

    class Directory(
        override val name: String,
        override var parent: Directory? = null,
        val children: MutableList<TreeNode> = mutableListOf()
    ) : TreeNode() {
        override val size: Long
            get() = run {
                var total: Long = 0
                children.forEach { node ->
                    total += node.size
                }
                return total
            }

        fun addChild(node: TreeNode) {
            this.children.add(node)
            node.parent = this
        }
    }

    data class Leaf(
        override val name: String,
        override val size: Long,
        override var parent: Directory? = null
    ) : TreeNode()
}
