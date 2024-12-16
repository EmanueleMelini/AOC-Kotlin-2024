private const val maxX = 101
private const val maxY = 103

fun main() {
    fun part1(input1: List<String>): Int {
        val robots = parse14(input1)

        val robotsFinal = mutableListOf<Pair<Int, Int>>()

        for (robot in robots) {

            var curX = robot.first.first
            var curY = robot.first.second

            for (i in 0..<100) {

                curX += robot.second.first
                if (curX < 0) {
                    curX += maxX
                } else if (curX >= maxX) {
                    curX -= maxX
                }

                curY += robot.second.second
                if (curY < 0) {
                    curY += maxY
                } else if (curY >= maxY) {
                    curY -= maxY
                }

            }

            robotsFinal.add(Pair(curX, curY))

        }

        var leftUp = 0
        var rightUp = 0
        var leftDown = 0
        var rightDown = 0

        for (robot in robotsFinal) {
            when {
                robot.first in 0..<(maxX / 2) && robot.second in 0..<(maxY / 2) -> leftUp++
                robot.first in 0..<(maxX / 2) && robot.second in ((maxY / 2) +1)..<maxY -> rightUp++
                robot.first in ((maxX / 2) +1)..<maxX && robot.second in ((maxY / 2) +1)..<maxY -> rightDown++
                robot.first in ((maxX / 2) +1)..<maxX && robot.second in 0..<(maxY / 2) -> leftDown++
            }
        }

        return leftUp * rightUp * rightDown * leftDown
    }

    fun part2(input2: List<String>): Int {

        val robots = parse14(input2)

        var days = 0
        var keepDoing = true

        do {

            days++

            val robotsFinal = mutableListOf<MutablePair<Int, Int>>()

            for (robot in robots) {

                var curX = robot.first.first
                var curY = robot.first.second

                curX += robot.second.first
                if (curX < 0) {
                    curX += maxX
                } else if (curX >= maxX) {
                    curX -= maxX
                }

                curY += robot.second.second
                if (curY < 0) {
                    curY += maxY
                } else if (curY >= maxY) {
                    curY -= maxY
                }

                robot.first.first = curX
                robot.first.second = curY

                robotsFinal.add(MutablePair(curX, curY))
            }

            if (robotsFinal.toSet().size == robots.size) {
                keepDoing = false
            }

        } while (keepDoing)

        return days
    }

    val input = readInput("Day14")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse14(inputData: List<String>): List<MutablePair<MutablePair<Int, Int>, MutablePair<Int, Int>>> {
    val regexPosition = "p=-?[0-9]{1,3},-?[0-9]{1,3}".toRegex()
    val regexVelocity = "v=-?[0-9]{1,3},-?[0-9]{1,3}".toRegex()
    val robots = mutableListOf<MutablePair<MutablePair<Int, Int>, MutablePair<Int, Int>>>()
    for (line in inputData) {
        val (posX, posY) = regexPosition.findAll(line).toList().map { it.value.replace("p=", "").split(",").map { replaced -> replaced.toInt() } }[0]
        val (velX, velY) = regexVelocity.findAll(line).toList().map { it.value.replace("v=", "").split(",").map { replaced -> replaced.toInt() } }[0]
        robots.add(MutablePair(MutablePair(posX, posY), MutablePair(velX, velY)))
    }
    return robots
}