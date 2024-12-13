private lateinit var garden: List<List<String>>

fun main() {
    fun part1(input1: MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>): Int {
        return getMoney(input1, ::getPerimeter)
    }

    fun part2(input2: MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>): Int {
        return getMoney(input2, ::getSides)
    }

    val input = readInput("Day12")

    garden = parse12(input)

    parseAndDoPartsWithTimes(::part1, ::part2, ::startExploreGarden)

}

private fun parse12(inputData: List<String>): List<List<String>> {
    return inputData.map { it.toList().map { char -> char.toString() } }
}

private fun getPerimeter(uniqueValue: Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>): Int {
    return (4 * uniqueValue.first.size) - (2 * uniqueValue.second.size)
}

private fun getSides(uniqueValue: Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>): Int {
    var sides = 0

    for (node in uniqueValue.first) {
        val letter = garden[node.first][node.second]
        val letterSides = mutableListOf<String>()
        for (d in getStaticDirXY()) {
            val (dirX, dirY) = d.getDirXY()
            val checkNode = Pair(node.first + dirX, node.second + dirY)
            if ((checkNode.first >= 0 && checkNode.first < garden.size && checkNode.second >= 0 && checkNode.second < garden[0].size
                && garden[checkNode.first][checkNode.second] != letter)
                || checkNode.first < 0
                || checkNode.first == garden.size
                || checkNode.second < 0
                || checkNode.second == garden[0].size) {
                letterSides.add(d)
            }
        }

        val letterDiagonals = mutableListOf<String>()
        for (d in getStaticDiagonalDirXY()) {
            val (dirX, dirY) = d.getDiagonalDirXY()
            val diagonalNode = Pair(node.first + dirX, node.second + dirY)
            if (diagonalNode.first >= 0 && diagonalNode.first < garden.size && diagonalNode.second >= 0 && diagonalNode.second < garden[0].size
                && garden[diagonalNode.first][diagonalNode.second] != letter) {
                val dirs = d.getDirsFromDiagonal()
                var dirsOk = 0
                for (dir in dirs) {
                    val (dirX2, dirY2) = dir.getDirXY()
                    val adjacentNode = Pair(node.first + dirX2, node.second + dirY2)
                    if (adjacentNode.first >= 0 && adjacentNode.first < garden.size && adjacentNode.second >= 0 && adjacentNode.second < garden[0].size
                        && garden[adjacentNode.first][adjacentNode.second] == letter) {
                        dirsOk++
                    }
                }
                if (dirsOk == dirs.size) {
                    letterDiagonals.add(d)
                }
            }
        }

        if (letterSides.size == 0 && letterDiagonals.size == 0) {
            continue
        }

        if (letterSides.size == 3) {
            sides += (letterSides.size - 1)
        }

        if (letterSides.size == 4) {
            sides += letterSides.size
        }

        if (letterSides.size == 2) {
            if (letterSides[0].switchDir() == letterSides[1] || letterSides[0] == letterSides[1].switchDir()) {
                sides++
            }
        }

        sides += letterDiagonals.size

    }

    return sides
}

private fun startExploreGarden(): MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>> {
    var areaCount = 0
    val uniqueLetterGarden = mutableMapOf<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>()
    val uniquePositions = mutableSetOf<Pair<Int, Int>>()
    for ((i, rows) in garden.withIndex()) {
        for ((j, letter) in rows.withIndex()) {
            areaCount += exploreGarden(letter, "$i,$j", Pair(i, j), Pair(i, j), uniqueLetterGarden, uniquePositions)
        }
    }
    return uniqueLetterGarden
}

private fun getMoney(
    uniqueLetterGarden: MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>,
    getSecondValue: (Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>) -> Int
): Int {
    var totCount = 0
    for (unique in uniqueLetterGarden) {
        val secondValue = getSecondValue(unique.value)
        val keyCount = unique.value.first.size * secondValue
        totCount += keyCount
    }
    return totCount
}

private fun exploreGarden(
    letter: String,
    mapKey: String,
    letterPosition: Pair<Int, Int>,
    oldPosition: Pair<Int, Int>,
    uniqueLetterGarden: MutableMap<String, Pair<MutableList<Pair<Int, Int>>, MutableList<Quadruple<Int, Int, Int, Int>>>>,
    uniquePositions: MutableSet<Pair<Int, Int>>
): Int {

    if (!uniqueLetterGarden.containsKey(mapKey)) {
        uniqueLetterGarden[mapKey] = Pair(mutableListOf(), mutableListOf())
    }

    if (!(letterPosition.first == oldPosition.first && letterPosition.second == oldPosition.second)) {
        val findArch1 = uniqueLetterGarden[mapKey]!!.second.find {
            it.first == oldPosition.first && it.second == oldPosition.second && it.third == letterPosition.first && it.fourth == letterPosition.second
        }
        val findArch2 = uniqueLetterGarden[mapKey]!!.second.find {
            it.first == letterPosition.first && it.second == letterPosition.second && it.third == oldPosition.first && it.fourth == oldPosition.second
        }
        if (findArch1 == null && findArch2 == null) {
            uniqueLetterGarden[mapKey]!!.second.add(
                Quadruple(
                    oldPosition.first,
                    oldPosition.second,
                    letterPosition.first,
                    letterPosition.second
                )
            )
        }
    }

    if (uniquePositions.find { it.first == letterPosition.first && it.second == letterPosition.second } != null) {
        return 0
    }
    uniquePositions.add(Pair(letterPosition.first, letterPosition.second))

    if (uniqueLetterGarden[mapKey]!!.first.find { it.first == letterPosition.first && it.second == letterPosition.second } != null) {
        return 0
    }
    uniqueLetterGarden[mapKey]!!.first.add(Pair(letterPosition.first, letterPosition.second))

    var letterCount = 1
    for (d in getStaticDirXY()) {
        val (dirX, dirY) = d.getDirXY()
        val newPosition = Pair(letterPosition.first + dirX, letterPosition.second + dirY)
        if (newPosition.first >= 0 && newPosition.first < garden.size && newPosition.second >= 0 && newPosition.second < garden[0].size
            && garden[newPosition.first][newPosition.second] == letter) {
            letterCount += exploreGarden(letter, mapKey, newPosition, letterPosition, uniqueLetterGarden, uniquePositions)
        }
    }

    return letterCount

}