object Completed {
    fun day1() {
        val file = Inputs.inputs[0]

        var currentSum = 0
        var largestSum = 0

        val elves = mutableListOf<Int>()

        file.readLines().forEach{ line ->
            try {
                currentSum += line.toInt()
            } catch (e: NumberFormatException) {
                largestSum = currentSum
                elves.add(currentSum)
                currentSum = 0
            }
        }

        val total = elves.sortedDescending().take(3).sum()
        println(total)
    }

    fun day1Linear() {
        val file = Inputs.inputs[0]

        var currentSum = 0

        val elves = mutableListOf<Int>()

        var first = 0
        var second = 0
        var third = 0

        file.readLines().forEach{ line ->
            try {
                currentSum += line.toInt()
            } catch (e: NumberFormatException) {
                elves.add(currentSum)

                if (currentSum >= first) {
                    second = first
                    third = second
                    first = currentSum
                } else if (currentSum >= second) {
                    third = second
                    second = currentSum
                } else if (currentSum >= third) {
                    currentSum = third
                }
                currentSum = 0
            }
        }

        println(first + second + third)
    }
}
