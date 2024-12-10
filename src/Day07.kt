import kotlin.math.pow

fun main() {
    fun part1(input1: List<String>): Long {

        val equations = parse7(input1)

        val operators = listOf("*", "+")

        return doEquations(equations, operators)
    }

    fun part2(input2: List<String>): Long {

        val equations = parse7(input2)

        val operators = listOf("*", "+", "|")

        return doEquations(equations, operators)
    }

    val input = readInput("Day07")
    doPartsWithTimes(input, ::part1, ::part2)

}

private fun parse7(inputData: List<String>): MutableList<Pair<Long, MutableList<Long>>> {

    val equations = mutableListOf<Pair<Long, MutableList<Long>>>()

    for (line in inputData) {

        val splitLine = line.split(": ")

        equations.add(Pair(splitLine[0].toLong(), splitLine[1].split(" ").map { it.toLong() }.toMutableList()))

    }

    return equations
}

private fun doEquations(equations: MutableList<Pair<Long, MutableList<Long>>>, operators: List<String>): Long {

    var equationsOk = 0L

    for (equation in equations) {

        val maxOperations = operators.size
            .toDouble()
            .pow(equation.second.size - 1)
            .toInt()

        for (dec in 0..<maxOperations) {

            var binary = if (operators.size == 2) Integer.toBinaryString(dec) else dec.toTernaryString()
            if (binary.length != equation.second.size - 1) {
                binary = "0".repeat(equation.second.size - 1 - binary.length) + binary
            }

            binary = binary.replace("0", operators[0])
                .replace("1", operators[1])

            if (operators.size == 3) {
                binary = binary.replace("2", operators[2])
            }

            val operatorsRun = binary.split("")
                .drop(1)
                .dropLast(1)

            var result = equation.second[0]

            for (i in 1..<equation.second.size) {
                val number = equation.second[i]

                val thisOperator = operatorsRun[i - 1]
                result = if (thisOperator == "|") {
                    "$result$number".toLong()
                } else {
                    evaluate("$result $thisOperator $number").toLong()
                }

            }

            if (result == equation.first) {
                equationsOk += result
                break
            }

        }

    }

    return equationsOk
}