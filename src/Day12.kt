fun main() {
    fun part1(input1: List<String>): Int {
        val garden = parse12(input1)
        return startExploreGarden(garden)
    }

    fun part2(input2: List<String>): Int {

        return 0
    }

    val input = readInput("Day12_test")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse12(inputData: List<String>): List<List<String>> {
    return inputData.map { it.toList().map { char -> char.toString() } }
}

private fun startExploreGarden(garden: List<List<String>>): Int {
    var areaCount = 0
    val uniqueLetterGarden = mutableMapOf<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>()
    for ((i, rows) in garden.withIndex()) {
        for ((j, letter) in rows.withIndex()) {
            areaCount += exploreGarden(letter, Pair(i, j), Pair(i, j), garden, uniqueLetterGarden)
        }
    }
    uniqueLetterGarden.println()
    var totCount = 0
    for (unique in uniqueLetterGarden) {
        var keyCount = unique.value.first.size * unique.value.second.size
        println("Calculating ${unique.key} = $keyCount")
        totCount += keyCount
    }
    return totCount
}

private fun exploreGarden(letter: String, letterPosition: Pair<Int, Int>, oldPosition: Pair<Int, Int>, garden: List<List<String>>, uniqueLetterGarden: MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>): Int {

    if (!uniqueLetterGarden.containsKey(letter)) {
        uniqueLetterGarden[letter] = Pair(mutableListOf(), mutableListOf())
        println("[$letter $letterPosition] Added letter to unique garden $uniqueLetterGarden")
    }

    if (!(letterPosition.first == oldPosition.first && letterPosition.second == oldPosition.second)) {
        if (uniqueLetterGarden[letter]!!.second.find { it.first == oldPosition.first && it.second == oldPosition.second && it.third == letterPosition.first && it.fourth == letterPosition.second } == null) {
            uniqueLetterGarden[letter]!!.second.add(
                Quadruple(
                    oldPosition.first,
                    oldPosition.second,
                    letterPosition.first,
                    letterPosition.second
                )
            )
        }
    }

    if (uniqueLetterGarden[letter]!!.first.find { it.first == letterPosition.first && it.second == letterPosition.second } != null) {
        println("[$letter $letterPosition] Unique found")
        return 0
    }
    uniqueLetterGarden[letter]!!.first.add(Pair(letterPosition.first, letterPosition.second))

    var letterCount = 1
    for (d in listOf("RX", "DW", "LF", "UP")) {
        val (dirX, dirY) = d.getDirXY()
        val newPosition = Pair(letterPosition.first + dirX, letterPosition.second + dirY)
        println("[$letter $letterPosition] New position $newPosition $d")
        if (newPosition.first >= 0 && newPosition.first < garden.size && newPosition.second >= 0 && newPosition.second < garden[0].size
            && garden[newPosition.first][newPosition.second] == letter) {
            println("[$letter $letterPosition] Going inside $newPosition")
            letterCount += exploreGarden(letter, newPosition, letterPosition, garden, uniqueLetterGarden)
        }
    }

    return letterCount

}