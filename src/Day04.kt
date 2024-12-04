fun main() {
    fun part1(input: List<String>): Int {

        val edgeIndex = 4

        val xmasMatrix = parse4(input)

        val alreadyFound = mutableListOf<Quadruple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>>>()

        val word = listOf("X", "M", "A", "S")

        for ((i, row) in xmasMatrix.withIndex()) {

            for ((j, column) in row.withIndex()) {

                if (column == "X") {

                    // UP
                    if (i >= 3) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i- k][j] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i - 1, j), Pair(i - 2, j), Pair(i - 3, j))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }

                    }

                    //RIGHT
                    if (j <= row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i][j + k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i, j + 1), Pair(i, j + 2), Pair(i, j + 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //DOWN
                    if (i <= xmasMatrix.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i + k][j] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i + 1, j), Pair(i + 2, j), Pair(i + 3, j))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //LEFT
                    if (j >= 3) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i][j - k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i, j - 1), Pair(i, j - 2), Pair(i, j - 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //UP-LEFT
                    if (i >= 3 && j >= 3) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i - k][j - k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i - 1, j - 1), Pair(i - 2, j - 2), Pair(i - 3, j - 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //UP-RIGHT
                    if (i >= 3 && j <= row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i - k][j + k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i - 1, j + 1), Pair(i - 2, j + 2), Pair(i - 3, j + 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //DOWN-LEFT
                    if (i <= xmasMatrix.size - edgeIndex && j >= 3) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i + k][j - k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i + 1, j - 1), Pair(i + 2, j - 2), Pair(i + 3, j - 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                    //DOWN-RIGHT
                    if (i <= xmasMatrix.size - edgeIndex && j <= row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i + k][j + k] != letter) {
                                found = false
                                break
                            }
                        }
                        if (found) {
                            val newQuad = Quadruple(Pair(i, j), Pair(i + 1, j + 1), Pair(i + 2, j + 2), Pair(i + 3, j + 3))
                            if (!alreadyFound.stream().map { quad -> quad.checkIsCopy(newQuad) }.filter { it }.findAny().orElse(false)) {
                                alreadyFound.add(newQuad)
                            }
                        }
                    }

                }

            }

        }

        return alreadyFound.size
    }

    fun part2(input: List<String>): Int {

        val edgeIndex = 1
        val minIndex = 0

        val xmasMatrix = parse4(input)

        val xFound = mutableListOf<Pair<Int, Int>>()

        val word = listOf("M", "A", "S")

        for ((i, row) in xmasMatrix.withIndex()) {

            for ((j, column) in row.withIndex()) {

                if (column == "A") {

                    var foundX = 0

                    //UP-LEFT -> DOWN-RIGHT
                    if (i > minIndex && i < xmasMatrix.size - edgeIndex && j > minIndex && j < row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i + (k - 1)][j + (k - 1)] != letter) {
                                found = false
                            }
                        }
                        if (found) {
                            foundX++
                        }
                    }

                    //UP-RIGHT -> DOWN-LEFT
                    if (i > minIndex && i < xmasMatrix.size - edgeIndex && j > minIndex && j < row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i + (k - 1)][j - (k - 1)] != letter) {
                                found = false
                            }
                        }
                        if (found) {
                            foundX++
                        }
                    }

                    //DOWN-LEFT -> UP-RIGHT
                    if (i > minIndex && i < xmasMatrix.size - edgeIndex && j > minIndex && j < row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i - (k - 1)][j + (k - 1)] != letter) {
                                found = false
                            }
                        }
                        if (found) {
                            foundX++
                        }
                    }

                    //DOWN-RIGHT -> UP-LEFT
                    if (i > minIndex && i < xmasMatrix.size - edgeIndex && j > minIndex && j < row.size - edgeIndex) {
                        var found = true
                        for ((k, letter) in word.withIndex()) {
                            if (xmasMatrix[i - (k - 1)][j - (k - 1)] != letter) {
                                found = false
                            }
                        }
                        if (found) {
                            foundX++
                        }
                    }

                    if (foundX == 2) {
                        xFound.add(Pair(i, j))
                    }

                }
            }
        }

        return xFound.size
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()

}

private fun parse4(inputData: List<String>): List<List<String>> {

    val xmasMatrix = inputData.map { row -> row.toCharArray().toList().map { c -> c.toString() } }

    return xmasMatrix
}