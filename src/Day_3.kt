fun main() {
    fun part1(input: List<String>): Int {

        val numbers = parse3(input)

        var sumMul = 0
        for (pair in numbers) {

            sumMul += pair.second * pair.third

        }

        return sumMul
    }

    fun part2(input: List<String>): Int {

        val numbers = parse3(input)

        var sumMul = 0
        for (pair in numbers) {

            sumMul += pair.first * pair.second * pair.third

        }

        return sumMul
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()

}

private fun parse3(inputData: List<String>): List<Triple<Int, Int, Int>> {

    val regex = "(mul\\([0-9]{1,3},[0-9]{1,3}\\))|(do\\(\\))|(don't\\(\\))".toRegex()

    val numbers = mutableListOf<Triple<Int, Int, Int>>()

    var lastInstruction = 1
    for (line in inputData) {

        val matched = regex.findAll(line)

        for (match in matched) {
            when (match.value) {
                "do()" -> {
                    lastInstruction = 1
                }
                "don't()" -> {
                    lastInstruction = 0
                }
                else -> {
                    val parsedNumbers = match.value.replace("mul(", "")
                        .replace(")", "")
                        .split(",")
                    numbers.add(Triple(lastInstruction, parsedNumbers[0].toInt(), parsedNumbers[1].toInt()))
                }
            }
        }

    }

    return numbers
}