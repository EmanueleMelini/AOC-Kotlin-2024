import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val pair = parse1(input)

        pair.first.sort()
        pair.second.sort()

        var sum = 0
        for ((index, _) in pair.first.withIndex()) {
            sum += abs(pair.first[index] - pair.second[index])
        }

        return sum
    }

    fun part2(input: List<String>): Int {

        val pair = parse1(input)

        var sim = 0
        for (lf in pair.first) {
            sim += (lf * pair.second.stream().filter {
                it == lf
            }.count().toInt())
        }

        return sim
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()

}

private fun parse1(inputData: List<String>): Pair<MutableList<Int>, MutableList<Int>> {

    val left: MutableList<Int> = ArrayList()
    val right: MutableList<Int> = ArrayList()

    for (line in inputData) {
        val data = line.split("   ")
        left.add(data[0].toInt())
        right.add(data[1].toInt())
    }

    return Pair(left, right)
}