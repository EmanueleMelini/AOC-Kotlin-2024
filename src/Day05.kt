import kotlin.time.TimeSource

fun main() {
    fun part1(formattedInput: List<Pair<List<Int>, Boolean>>): Int {

        var middlePagesSum = 0

        for (checked in formattedInput) {
            if (checked.second) {
                middlePagesSum += checked.first[checked.first.size / 2]
            }
        }

        return middlePagesSum
    }

    fun part2(formattedInput: List<Pair<List<Int>, Boolean>>): Int {

        var middlePagesSum = 0

        for (checked in formattedInput) {
            if (!checked.second) {
                middlePagesSum += checked.first[checked.first.size / 2]
            }
        }

        return middlePagesSum
    }

    val input = readInput("Day05")

    val source = TimeSource.Monotonic
    val start = source.markNow()

    val formattedInput = checkAllRules(parse5(input))

    val end = source.markNow()
    val diff = end - start
    println("Time: ${diff.inWholeMilliseconds}ms")

    part1(formattedInput).println()
    part2(formattedInput).println()

}

private fun checkRule(page1: Int, page2: Int, firstBefore: Boolean, rules: List<Pair<Int, Int>>): Boolean {
    var ruleOk = true

    for (rule in rules) {

        if ((rule.first == page2 && rule.second == page1 && firstBefore) || (rule.first == page1 && rule.second == page2 && !firstBefore)) {
            ruleOk = false
            break
        }

    }

    return ruleOk
}

private fun checkAllRules(printUpdates: Pair<List<Pair<Int, Int>>, List<MutableList<Int>>>): List<Pair<List<Int>, Boolean>> {

    val checkedPrintUpdates = mutableListOf<Pair<MutableList<Int>, Boolean>>()

    for (pages in printUpdates.second) {

        var initialPagesOk: Boolean? = null

        var reorder = true
        var reorderNum = 0

        while (reorder) {

            var restart = false

            for ((i, pageChecking) in pages.withIndex()) {

                if (restart) {
                    break
                }

                var thisPageOk = true

                for ((j, pageToCheck) in pages.withIndex()) {

                    if (i == j) {
                        continue
                    }

                    if (!checkRule(pageChecking, pageToCheck, i < j, printUpdates.first)) {
                        thisPageOk = false
                        if (initialPagesOk == null) {
                            initialPagesOk = false
                        }
                        val append = pages[i]
                        pages[i] = pages[j]
                        pages[j] = append
                        restart = true
                        break
                    }

                }

                if (thisPageOk) {
                    reorderNum++
                }

            }

            if (reorderNum == pages.size) {
                reorder = false
            } else {
                reorderNum = 0
            }

        }

        checkedPrintUpdates.add(Pair(pages, initialPagesOk ?: true))

    }

    return checkedPrintUpdates
}

private fun parse5(inputData: List<String>): Pair<List<Pair<Int, Int>>, List<MutableList<Int>>> {

    val printUpdates = Pair(mutableListOf<Pair<Int, Int>>(), mutableListOf<MutableList<Int>>())

    var isRules = true
    for (row in inputData) {
        if (row == "") {
            isRules = false
            continue
        }

        if (isRules) {
            val (left, right) = row.split("|")
            printUpdates.first.add(Pair(left.toInt(), right.toInt()))
        } else {
            val pages = row.split(",").map { it.toInt() }
            printUpdates.second.add(pages.toMutableList())
        }

    }


    return printUpdates
}