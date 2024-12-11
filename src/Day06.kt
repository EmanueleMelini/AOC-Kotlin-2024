import kotlin.time.TimeSource

fun main() {
    fun part1(input1: Quadruple<Pair<Int, Int>, MutableList<MutableList<Pair<String, String>>>, MutableSet<Pair<Int, Int>>, MutableSet<Pair<String, Pair<Int, Int>>>>): Int {

        var stepCount = 0

        input1.second.forEach { row -> row.forEach { char -> if (char.first == "X") stepCount++ } }

        return stepCount
    }

    fun part2(input2: Quadruple<Pair<Int, Int>, MutableList<MutableList<Pair<String, String>>>, MutableSet<Pair<Int, Int>>, MutableSet<Pair<String, Pair<Int, Int>>>>): Int {

        var loopCount = 0
        val loopCountPos = mutableListOf<Pair<Int, Int>>()

        input2.third.forEach { crossed ->
            if (!(crossed.first == input2.first.first && crossed.second == input2.first.second)) {
                val newMap = Quadruple(
                    input2.first.copy(),
                    input2.second.toMutableList().map { it.toMutableList().map { it2 -> it2.copy() }.toMutableList() }.toMutableList(),
                    input2.third.toMutableSet().map { it.copy() }.toMutableSet(),
                    mutableSetOf<Pair<String, Pair<Int, Int>>>()
                )

                newMap.second[crossed.first].removeAt(crossed.second)
                newMap.second[crossed.first].add(crossed.second, Pair("#", ""))

                val resultLooping = doPath(newMap, true)

                if (resultLooping.second) {
                    loopCount++
                    loopCountPos.add(crossed)
                }
            }
        }

        return loopCount
    }


    val source = TimeSource.Monotonic
    val start = source.markNow()

    val input = readInput("Day06")

    val map = parse6(input)

    val fullPath = doPath(map, false)

    part1(fullPath.first).println()
    part2(fullPath.first).println()

    val end = source.markNow()
    val diff = end - start
    println("Time: ${diff.inWholeMilliseconds}ms")

}

private fun parse6(inputData: List<String>):
        Quadruple<Pair<Int, Int>, MutableList<MutableList<Pair<String, String>>>, MutableSet<Pair<Int, Int>>, MutableSet<Pair<String, Pair<Int, Int>>>> {

    val map = mutableListOf<MutableList<Pair<String, String>>>()

    var startingPoint = Pair(0, 0)

    inputData.forEachIndexed { i, row ->
        map.add(row.mapIndexed { j, char ->
            if (char == '^') {
                startingPoint = Pair(i, j)
            }
            Pair(char.toString(), "")
        }.toMutableList())
    }

    return Quadruple(startingPoint, map, mutableSetOf(), mutableSetOf())
}

private fun doPath(map: Quadruple<Pair<Int, Int>, MutableList<MutableList<Pair<String, String>>>, MutableSet<Pair<Int, Int>>, MutableSet<Pair<String, Pair<Int, Int>>>>, endIfLooped: Boolean):
        Pair<Quadruple<Pair<Int, Int>, MutableList<MutableList<Pair<String, String>>>, MutableSet<Pair<Int, Int>>, MutableSet<Pair<String, Pair<Int, Int>>>>, Boolean> {

    var dir = "UP"

    var currentPos = Pair(map.first.first, map.first.second)

    map.second[currentPos.first].removeAt(currentPos.second)
    map.second[currentPos.first].add(currentPos.second, Pair("X", "UP"))

    var looped = false
    var loopedCound = 0

    do {

        val (dirX, dirY) = dir.getDirXY()

        val oldPos = currentPos

        currentPos = Pair(currentPos.first + dirX, currentPos.second + dirY)

        if (currentPos.first < 0 || currentPos.first >= map.second.size || currentPos.second < 0 || currentPos.second >= map.second[0].size) {
            break
        }

        val posChar = map.second[currentPos.first][currentPos.second].first

        if (posChar == "#") {
            dir = dir.switchDir()
            currentPos = oldPos
        } else {

            map.second[currentPos.first].removeAt(currentPos.second)
            map.second[currentPos.first].add(currentPos.second, Pair("X", dir))

            map.third.add(Pair(currentPos.first, currentPos.second))
            val alreadyCrossed = !map.fourth.add(Pair(dir, Pair(currentPos.first, currentPos.second)))

            if (alreadyCrossed) {
                loopedCound++
            }

            if (loopedCound > 3 && endIfLooped) {
                looped = true
                break
            }

        }

    } while (true)

    return Pair(map, looped)
}

private fun parse6Back(output: MutableList<MutableList<Pair<String, String>>>): String {
    val outputParsed = mutableListOf<String>()
    output.forEach { row ->
        outputParsed.add(row.joinToString("") { char -> char.first })
    }
    return outputParsed.toList().joinToString("\n")
}