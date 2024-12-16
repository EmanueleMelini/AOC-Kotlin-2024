private const val OFFSET = 10000000000000L

fun main() {
    fun part1(input1: List<String>): Long {
        val clawMachines = parse13(input1)
        return doMaths(clawMachines)
    }

    fun part2(input2: List<String>): Long {
        val clawMachines = parse13(input2)
        return doMaths(clawMachines, true)
    }

    val input = readInput("Day13")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse13(inputData: List<String>): List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> {
    val clawMachines = mutableListOf<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>()
    var aButton: Pair<Long, Long>? = null
    var bButton: Pair<Long, Long>? = null
    var step = 0
    for (line in inputData) {
        when (step) {
            0 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toLong() }
                aButton = Pair(x, y)
                step++
            }
            1 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toLong() }
                bButton = Pair(x, y)
                step++
            }
            2 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toLong() }
                val prize = Pair(x, y)
                clawMachines.add(Triple(aButton!!, bButton!!, prize))
                step++
            }
            3 -> {
                step = 0
            }
            else -> {}
        }
    }
    return clawMachines
}

private fun doMaths(clawMachines: List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>, part2: Boolean = false): Long {
    var tokens = 0L
    for (claw in clawMachines) {
        val cX = if (part2) claw.third.first + OFFSET else claw.third.first
        val cY = if (part2) claw.third.second + OFFSET else claw.third.second
        val aX = ((cX * claw.second.second) - (claw.second.first * cY))
        val aY = ((claw.first.first * claw.second.second) - (claw.first.second * claw.second.first))
        val bX = ((claw.first.first * cY) - (cX * claw.first.second))
        val bY = ((claw.first.first * claw.second.second) - (claw.first.second * claw.second.first))
        if (aX % aY == 0L && bX % bY == 0L) {
            tokens += ((aX / aY * 3) + (bX / bY))
        }
    }
    return tokens
}