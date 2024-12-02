import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val reports = parse2(input)

        var fineReports = 0
        for (report in reports) {
            if (checkReport(report)) {
                fineReports++
            }
        }

        return fineReports
    }

    fun part2(input: List<String>): Int {

        val reports = parse2(input)

        var fineReports = 0
        for (report in reports) {

            var checkReport = checkReport(report)

            if (!checkReport) {

                val reportIterator = report.listIterator()

                while (reportIterator.hasNext()) {
                    val tempPopped = reportIterator.next()
                    reportIterator.remove()
                    checkReport = checkReport(report)
                    if (checkReport) {
                        break
                    }
                    reportIterator.add(tempPopped)
                }

            }

            if (checkReport) {
                fineReports++
            }

        }

        return fineReports
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun checkReport(report: List<Int>): Boolean {
    var isReportOk = false
    val isSorted = report.asSequence().zipWithNext { a, b -> a <= b }.all { it } || report.asSequence().zipWithNext { a, b -> a >= b }.all { it }
    if (isSorted) {
        var isLevelOk = true
        var lastLevel: Int? = null
        for (level in report) {
            if (lastLevel != null) {
                val diff = abs(level - lastLevel)
                if (diff < 1 || diff > 3) {
                    isLevelOk = false
                }
            }
            lastLevel = level
        }
        if (isLevelOk) {
            isReportOk = true
        }
    }
    return isReportOk
}

private fun parse2(inputData: List<String>): MutableList<MutableList<Int>> {
    val output = mutableListOf<MutableList<Int>>()

    for (inp in inputData) {
        output.add(inp.split(" ").stream().map { it.toInt() }.toList().toMutableList())
    }

    return output
}