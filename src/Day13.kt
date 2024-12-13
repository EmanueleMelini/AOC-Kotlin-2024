fun main() {
    fun part1(input1: List<String>): Int {
        val clawMachines = parse13(input1)
        //clawMachines.println()

        var tokens = 0

        for ((cI, claw) in clawMachines.withIndex()) {

            //println("Trying claw $claw ($cI)")

            var minB = claw.third.first / claw.second.first

            //println("[CLAW-$cI] minB $minB")

            if (claw.third.first % claw.second.first == 0 && minB <= 100) {
                //println("[CLAW-$cI] Best case all B")
                tokens += minB
                continue
            }

            minB = minOf(minB, 100)

            //println("[CLAW-$cI] Mid case do the cycle")

            for (i in (1..minB).reversed()) {

                //println("[CLAW-$cI] Trying with $i B")

                var currentX = i * claw.second.first
                var currentY = i * claw.second.second

                //println("[CLAW-$cI] With $i B we have X=$currentX Y=$currentY")

                var aCount = 0

                do {
                    aCount++

                    if (aCount == 100) break

                    //println("[CLAW-$cI] Trying $i B with $aCount A")
                    currentX += claw.first.first
                    currentY += claw.first.second

                    //println("[CLAW-$cI] With $aCount A we have X=$currentX (${claw.first.first}) Y=$currentY (${claw.first.second})")

                } while (currentX < claw.third.first && currentY < claw.third.second)

                if (currentX == claw.third.first && currentY == claw.third.second) {
                    tokens += i + (aCount * 3)
                }

            }

        }

        return tokens
    }

    fun part2(input2: List<String>): Int {

        return 0
    }

    val input = readInput("Day13")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse13(inputData: List<String>): List<Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>>> {
    val clawMachines = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>>>()
    var aButton: Pair<Int, Int>? = null
    var bButton: Pair<Int, Int>? = null
    var step = 0
    for (line in inputData) {
        when (step) {
            0 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toInt() }
                aButton = Pair(x, y)
                step++
            }
            1 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toInt() }
                bButton = Pair(x, y)
                step++
            }
            2 -> {
                val (x, y) = line.split(", ").map { it.replace(Regex("\\D"), "").toInt() }
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