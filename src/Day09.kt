fun main() {
    fun part1(input1: List<String>): Long {

        val codes = parse9(input1)

        val formattedCodes = generateFormattedCodes(codes)

        val shiftedCodes = mutableListOf(*formattedCodes.toTypedArray())

        for (i in (0..<shiftedCodes.size).reversed()) {
            val indexToShift = shiftedCodes.indexOf(".")
            if (indexToShift > i) {
                break
            }
            if (shiftedCodes[i] != ".") {
                shiftedCodes[indexToShift] = shiftedCodes[i]
                shiftedCodes[i] = "."
            }
        }

        return calcChecksum(shiftedCodes)
    }

    fun part2(input2: List<String>): Long {

        val codes = parse9(input2)

        val formattedCodes = generateFormattedCodes(codes)

        val shiftedCodes = mutableListOf(*formattedCodes.toTypedArray())

        var skipToI = -1

        for (i in (0..<shiftedCodes.size).reversed()) {
            val allIndexesToShift = shiftedCodes.mapIndexedNotNull { index, s -> index.takeIf { s == "." && index < i } }.toMutableList()
            if (allIndexesToShift.isEmpty()) {
                break
            }
            if (skipToI in 0..i) {
                if (skipToI == i) {
                    skipToI = -1
                }
                continue
            }
            if (shiftedCodes[i] != ".") {
                var maxI = i
                while ((maxI - 1) > 0 && shiftedCodes[maxI - 1] == shiftedCodes[i]) {
                    maxI--
                }

                val fileSize = i - maxI + 1

                var found = false

                while (allIndexesToShift.isNotEmpty()) {
                    var canFit = true
                    for (j in 0..<fileSize) {
                        if (shiftedCodes[allIndexesToShift[0] + j] != ".") {
                            canFit = false
                            break
                        }
                    }
                    if (canFit) {
                        found = true
                        for (j in 0..<fileSize) {
                            shiftedCodes[allIndexesToShift[0] + j] = shiftedCodes[i]
                        }
                        for (i2 in maxI..i) {
                            shiftedCodes[i2] = "."
                        }
                        break
                    } else {
                        allIndexesToShift.removeAt(0)
                    }
                }
                if (!found) {
                    skipToI = maxI
                }
            }
        }

        return calcChecksum(shiftedCodes)
    }

    val input = readInput("Day09")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse9(inputData: List<String>): List<Int> {
    return inputData[0].split("").drop(1).dropLast(1).map { it.toInt() }
}

private fun generateFormattedCodes(codes: List<Int>): List<String> {
    val formattedCodes = mutableListOf<String>()
    var lastId = 0
    for ((i, code) in codes.withIndex()) {
        if (i % 2 == 0) {
            formattedCodes.addAll("$lastId|".repeat(code).split("|").dropLast(1))
            lastId++
        } else {
            formattedCodes.addAll(".".repeat(code).split("").drop(1).dropLast(1))
        }
    }
    return formattedCodes
}

private fun calcChecksum(shiftedCodes: List<String>): Long {
    var checksum = 0L
    for ((i, code) in shiftedCodes.withIndex()) {
        if (code.toIntOrNull() == null) continue
        checksum += code.toLong() * i.toLong()
    }
    return checksum
}