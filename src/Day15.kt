fun main() {
    fun part1(input1: List<String>): Int {
        val warehouse = parse15(input1)

        var currentPosition = MutablePair(warehouse.first.first, warehouse.first.second)

        for (move in warehouse.third) {

            val (dirX, dirY) = move.getArrowDirXY()

            val nextChar = warehouse.second[currentPosition.first + dirX]!![currentPosition.second + dirY]

            if (nextChar == "#") {
                continue
            }

            if (nextChar == ".") {
                currentPosition = MutablePair(currentPosition.first + dirX, currentPosition.second + dirY)
                continue
            }

            if (nextChar == "O") {
                var keepMoving = true
                var doMove = false
                var boxPosition = MutablePair(currentPosition.first + dirX, currentPosition.second + dirY)
                do {

                    val boxChar = warehouse.second[boxPosition.first + dirX]!![boxPosition.second + dirY]
                    when (boxChar) {
                        "#" -> keepMoving = false
                        "." -> doMove = true
                        else -> boxPosition = MutablePair(boxPosition.first + dirX, boxPosition.second + dirY)
                    }

                } while (keepMoving && !doMove)

                if (doMove) {
                    warehouse.second[currentPosition.first + dirX]!![currentPosition.second + dirY] = "."
                    warehouse.second[boxPosition.first + dirX]!![boxPosition.second + dirY] = "O"
                    currentPosition = MutablePair(currentPosition.first + dirX, currentPosition.second + dirY)
                }

            }

        }

        var sumBoxes = 0
        for ((i, line) in warehouse.second) {
            for ((j, space) in line) {
                if (space == "O") {
                    sumBoxes += ((i * 100) + j)
                }
            }
        }

        return sumBoxes
    }

    fun part2(input2: List<String>): Int {

        return 0
    }

    val input = readInput("Day15")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse15(inputData: List<String>): MutableTriple<Pair<Int, Int>, MutableMap<Int, MutableMap<Int, String>>, MutableList<String>> {
    val warehouse = MutableTriple(Pair(0, 0), mutableMapOf<Int, MutableMap<Int, String>>(), mutableListOf<String>())
    var isDirections = false
    for ((i, line) in inputData.withIndex()) {
        if (line.isBlank()) {
            isDirections = true
            continue
        }
        if (isDirections) {
            warehouse.third.addAll(line.split("").drop(1).dropLast(1))
        } else {
            val wareLine = mutableListOf<MutableTriple<Int, Int, String>>()
            val chars = line.split("").drop(1).dropLast(1)
            for ((j, char) in chars.withIndex()) {
                var thisChar = char
                if (char == "@") {
                    thisChar = "."
                    warehouse.first = Pair(i, j)
                }
                wareLine.add(MutableTriple(i, j, thisChar))
                if (warehouse.second[i] == null) {
                    warehouse.second[i] = mutableMapOf(Pair(j, thisChar))
                } else {
                    warehouse.second[i]!![j] = thisChar
                }
            }
        }
    }
    return warehouse
}