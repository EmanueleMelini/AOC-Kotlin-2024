fun main() {
    fun part1(input1: List<String>): Int {
        val trails = parse10(input1)
        return startExplore(trails, true)
    }

    fun part2(input2: List<String>): Int {
        val trails = parse10(input2)
        return startExplore(trails, false)
    }

    val input = readInput("Day10")
    clearDump("Day10")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse10(inputData: List<String>): List<List<Int>> {
    return inputData.map { it.toList().map { char -> char.toString().toInt() } }
}

private fun startExplore(trails: List<List<Int>>, doUnique: Boolean): Int {

    var trailCount = 0
    val uniqueNines = mutableSetOf<Quadruple<Int, Int, Int, Int>>()

    for ((i, rows) in trails.withIndex()) {
        for ((j, position) in rows.withIndex()) {
            if (position == 0) {
                val currentPosition = Pair(i, j)
                val thisCount = explore(trails, currentPosition, currentPosition, uniqueNines, doUnique)
                trailCount += thisCount
            }
        }
    }

    return trailCount
}

private fun explore(trails: List<List<Int>>, currentPosition: Pair<Int, Int>, zeroPosition: Pair<Int, Int>, uniqueNines: MutableSet<Quadruple<Int, Int, Int, Int>>, doUnique: Boolean): Int {

    if (trails[currentPosition.first][currentPosition.second] == 9) {
        if (doUnique && uniqueNines.find { it.first == zeroPosition.first && it.second == zeroPosition.second && it.third == currentPosition.first && it.fourth == currentPosition.second } != null) {
            return 0
        } else {
            uniqueNines.add(Quadruple(zeroPosition.first, zeroPosition.second, currentPosition.first, currentPosition.second))
            return 1
        }
    }

    var sum = 0

    for (d in listOf("RX", "DW", "LF", "UP")) {
        val (dirX, dirY) = d.getDirXY()
        val searchingPosition = Pair(currentPosition.first + dirX, currentPosition.second + dirY)
        if (searchingPosition.first >= 0 && searchingPosition.first < trails[0].size && searchingPosition.second >= 0 && searchingPosition.second < trails.size
            && trails[searchingPosition.first][searchingPosition.second] - trails[currentPosition.first][currentPosition.second] == 1) {
            sum += explore(trails, searchingPosition, zeroPosition, uniqueNines, doUnique)
        }
    }

    return sum

}
