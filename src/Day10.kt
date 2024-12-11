fun main() {
    fun part1(input1: List<String>): Int {

        val trails = parse10(input1)

        val startingPositions = mutableSetOf<Pair<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>>()

        for ((i, rows) in trails.withIndex()) {
            for ((j, position) in rows.withIndex()) {
                if (position == "0") {
                    startingPositions.add(Pair(Pair(i, j), mutableSetOf()))
                }
            }
        }

        var dir = "RX"

        for ((i, position) in startingPositions.withIndex()) {

            val positionsOfOkPaths = mutableSetOf<Triple<Int, Int, Int>>()

            var keepGoing = true
            var searchingValue = 1
            var currentPosition = Pair(position.first.first, position.first.second)
            val lastPosition = mutableListOf<Triple<Int, Int, String>>()
            lastPosition.add(Triple(currentPosition.first, currentPosition.second, dir))
            var startingDirections = 1

            printAndWrite("Day10", "Starting at $currentPosition")

            var isThisPathOk = false

            while (keepGoing) {

                var changeDir = false
                var isDirUp = false

                printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Trying searching $searchingValue at $dir")

                val (dirX, dirY) = dir.getDirXY()

                val searchingPosition = Pair(currentPosition.first + dirX, currentPosition.second + dirY)

                if (searchingPosition.first < 0
                    || searchingPosition.second < 0
                    || searchingPosition.first == trails[0].size
                    || searchingPosition.second == trails.size) {
                    printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Out of bound $currentPosition $dir")
                    if (dir == "UP") {
                        isDirUp = true
                    }
                    changeDir = true
                } else {

                    if (trails[searchingPosition.first][searchingPosition.second].toInt() == searchingValue) {

                        if (positionsOfOkPaths.find { it.first == searchingPosition.first && it.second == searchingPosition.second } != null) {
                            if (dir == "UP") {
                                isDirUp = true
                            }
                            printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Already used this path successsfully $currentPosition $dir")
                            changeDir = true
                        } else {

                            if (searchingValue == 9) {
                                isThisPathOk = true
                                position.second.add(searchingPosition)
                                printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Found $searchingValue at $searchingPosition, switching direction from $dir to ${dir.switchDir()}")

                                if (dir == "UP") {
                                    isDirUp = true
                                }
                                changeDir = true
                            } else {
                                lastPosition.add(Triple(currentPosition.first, currentPosition.second, dir))
                                currentPosition = Pair(searchingPosition.first, searchingPosition.second)
                                printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Added ${lastPosition[lastPosition.size - 1]} to the lastPosition stack")
                                printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Found $searchingValue at $currentPosition, resetting direction from $dir to RX")
                                dir = "RX"
                                searchingValue++
                            }
                        }
                    } else {
                        printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Did not find $searchingValue at $searchingPosition, switching direction from $dir to ${dir.switchDir()}")
                        if (dir == "UP") {
                            isDirUp = true
                        }
                        changeDir = true
                    }

                }

                if (isDirUp) {

                    if (currentPosition.first == position.first.first && currentPosition.second == position.first.second) {
                        printAndWrite("Day10", "[$i/${startingPositions.size - 1}] We are back to the starting point $currentPosition")
                        startingDirections++
                        if (startingDirections == 4) {
                            printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Finished direction cycle of the starting point, going to the next starting point")
                            keepGoing = false
                        }
                        isThisPathOk = false
                    } else {

                        printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Finished direction cycle, going back to ${trails[lastPosition[lastPosition.size - 1].first][lastPosition[lastPosition.size - 1].second]} ${lastPosition[lastPosition.size - 1]}")
                        currentPosition = Pair(lastPosition[lastPosition.size - 1].first, lastPosition[lastPosition.size - 1].second)
                        dir = lastPosition[lastPosition.size - 1].third
                        printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Removed ${lastPosition[lastPosition.size - 1]} from the lastPosition stack]")
                        lastPosition.removeLastOrNull()
                        searchingValue--

                        if (isThisPathOk) {
                            positionsOfOkPaths.add(Triple(currentPosition.first, currentPosition.second, trails[currentPosition.first][currentPosition.second].toInt()))
                        }

                        while (lastPosition.isNotEmpty() && dir == "UP") {
                            printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Finished direction cycle, going back to ${trails[lastPosition[lastPosition.size - 1].first][lastPosition[lastPosition.size - 1].second]} ${lastPosition[lastPosition.size - 1]}")
                            currentPosition = Pair(lastPosition[lastPosition.size - 1].first, lastPosition[lastPosition.size - 1].second)
                            dir = lastPosition[lastPosition.size - 1].third
                            printAndWrite("Day10", "[$i/${startingPositions.size - 1}] Removed ${lastPosition[lastPosition.size - 1]} from the lastPosition stack]")
                            lastPosition.removeLastOrNull()
                            searchingValue--

                            if (isThisPathOk) {
                                positionsOfOkPaths.add(Triple(currentPosition.first, currentPosition.second, trails[currentPosition.first][currentPosition.second].toInt()))
                            }
                        }
                    }

                }

                if (changeDir) {
                    dir = dir.switchDir()
                }

            }

        }

        return startingPositions.sumOf { it.second.size }
    }

    fun part2(input2: List<String>): Int {

        return 0
    }

    val input = readInput("Day10")
    clearDump("Day10")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse10(inputData: List<String>): List<List<String>> {
    return inputData.map { it.toList().map { char -> char.toString() } }
}
