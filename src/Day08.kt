import kotlin.math.abs

fun main() {
    fun part1(input1: List<String>): Int {

        val uniqueAntinodes = mutableSetOf<Pair<Int, Int>>()

        val antennas = parse8(input1)

        for ((i, rows) in antennas.withIndex()) {
            for ((j, antenna) in rows.withIndex()) {
                if (antenna != ".") {
                    for (x in i..<antennas.size) {
                        val startJ = if (x == i) j + 1 else 0
                        for (y in startJ..<rows.size) {
                            if (antennas[x][y] == antenna) {

                                val xDist = abs(x - i);
                                val yDist = abs(y - j);

                                if ((i - xDist) >= 0) {
                                    if (y < j && (j + yDist) < rows.size) {
                                        uniqueAntinodes.add(Pair(i - xDist, j + yDist))
                                    }
                                    if (y > j && (j - yDist) >= 0) {
                                        uniqueAntinodes.add(Pair(i - xDist, j - yDist))
                                    }
                                }
                                if ((x + xDist) < antennas.size) {
                                    if (y < j && (y - yDist) >= 0) {
                                        uniqueAntinodes.add(Pair(x + xDist, y - yDist))
                                    }
                                    if (y > j && (y + yDist) < rows.size) {
                                        uniqueAntinodes.add(Pair(x + xDist, y + yDist))
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        return uniqueAntinodes.size
    }

    fun part2(input2: List<String>): Int {

        val uniqueAntinodes = mutableSetOf<Pair<Int, Int>>()

        val antennas = parse8(input2)

        for ((i, rows) in antennas.withIndex()) {
            for ((j, antenna) in rows.withIndex()) {
                if (antenna != ".") {
                    for (x in i..<antennas.size) {
                        val startJ = if (x == i) j + 1 else 0
                        for (y in startJ..<rows.size) {
                            if (antennas[x][y] == antenna) {

                                uniqueAntinodes.add(Pair(i, j))
                                uniqueAntinodes.add(Pair(x, y))

                                val xDist = abs(x - i);
                                val yDist = abs(y - j);

                                var antinode1 = Pair(i - xDist, j - yDist)
                                var antinode2 = Pair(x + xDist, y + yDist)

                                if (y < j) {
                                    antinode1 = Pair(i - xDist, j + yDist)
                                    antinode2 = Pair(x + xDist, y - yDist)
                                }

                                while (antinode1.first >= 0 && antinode1.second >= 0 && antinode1.second < rows.size) {
                                    uniqueAntinodes.add(Pair(antinode1.first, antinode1.second))
                                    antinode1 = if (y < j) {
                                        Pair(antinode1.first - xDist, antinode1.second + yDist)
                                    } else {
                                        Pair(antinode1.first - xDist, antinode1.second - yDist)
                                    }
                                }

                                while (antinode2.first < antennas.size && antinode2.second >= 0 && antinode2.second < rows.size) {
                                    uniqueAntinodes.add(Pair(antinode2.first, antinode2.second))
                                    antinode2 = if (y < j) {
                                        Pair(antinode2.first + xDist, antinode2.second - yDist)
                                    } else {
                                        Pair(antinode2.first + xDist, antinode2.second + yDist)
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        return uniqueAntinodes.size
    }

    val input = readInput("Day08")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse8(inputData: List<String>): List<List<String>> {

    val antennas = inputData.map { it.split("").drop(1).dropLast(1) }

    return antennas
}

private fun parseOutput(antennas: List<List<String>>, uniqueAntinodes: Set<Pair<Int, Int>>) {
    var output = ""
    for ((i, rows) in antennas.withIndex()) {
        for ((j, antenna) in rows.withIndex()) {

            if (antenna == ".") {
                var point = "."
                for (anti in uniqueAntinodes) {
                    if (anti.first == i && anti.second == j) {
                        point = "#"
                    }
                }
                output += point
            } else {
                output += antenna
            }
        }
        output += "\n"
    }

    println()
    output.println()
}