fun main() {
    fun part1(input1: List<String>): Long {
        val stones = parse11(input1)

        var stoneCount = 0L

        for (stone in stones) {
            stoneCount += exploreBlink(stone, 25, mutableMapOf())
        }

        return stoneCount
    }

    fun part2(input2: List<String>): Long {
        val stones = parse11(input2)

        var stoneCount = 0L

        for (stone in stones) {
            stoneCount += exploreBlink(stone, 75, mutableMapOf())
        }

        return stoneCount
    }

    val input = readInput("Day11")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse11(inputData: List<String>): MutableList<Long> {
    return inputData[0].split(" ").map { char -> char.toLong() }.toMutableList()
}

private fun blinkStone(stone: Long): List<Long> {
    return when {
        stone == 0L -> listOf(1L)
        stone.toString().length % 2 == 0 -> {
            val leftStone = stone.toString().substring(0, stone.toString().length / 2).toLong()
            val rightStone = stone.toString().substring(stone.toString().length / 2, stone.toString().length).toLong()
            listOf(leftStone, rightStone)
        }
        else -> listOf(stone * 2024)
    }
}

private fun exploreBlink(stone: Long, blink: Int, uniqueStoneBlink: MutableMap<Pair<Long, Int>, Long>): Long {

    if (blink == 0) {
        return 1
    }

    uniqueStoneBlink.containsKey(Pair(stone, blink))

    if (uniqueStoneBlink.containsKey(Pair(stone, blink))) {
        return uniqueStoneBlink[Pair(stone, blink)] ?: 0
    }

    var stoneCount = 0L

    val newStones = blinkStone(stone)
    for (newStone in newStones) {
        stoneCount += exploreBlink(newStone, blink - 1, uniqueStoneBlink)
    }

    uniqueStoneBlink[Pair(stone, blink)] = stoneCount

    return stoneCount

}