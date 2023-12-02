package day2;

import readInput
import kotlin.math.log10
import kotlin.math.max

fun checkIsGood(id: Int, line: String): Boolean {
    val lettersToSkip: Int = 7 + (log10((id + 1).toFloat()).toInt() + 1);
    val s = line.substring(lettersToSkip)
    val regex = Regex("(\\d+) (green|red|blue)")

    val maxValues = mapOf("red" to 12, "green" to 13, "blue" to 14)

    for (set in s.split("; ")) {
        for (numberAndColor in set.split(", ")) {
            val regexRes = regex.find(numberAndColor)!!
            val (numberStr, color) = regexRes.destructured;
            val number = numberStr.toInt();
            val maxValue = maxValues[color]!!;
            if (number > maxValue) {
                return false;
            }
        }
    }
    return true;
}


fun maxByColor(id: Int, line: String): Map<String, Int> {
    val lettersToSkip: Int = 7 + (log10((id + 1).toFloat()).toInt() + 1);
    val s = line.substring(lettersToSkip)
    val regex = Regex("(\\d+) (green|red|blue)")

    val maxValues = HashMap<String, Int>()

    for (set in s.split("; ")) {
        for (numberAndColor in set.split(", ")) {
            val regexRes = regex.find(numberAndColor)!!
            val (numberStr, color) = regexRes.destructured;
            val number = numberStr.toInt();
            if(maxValues.containsKey(color)) {
                maxValues[color] = max(maxValues[color]!!, number);
            } else {
                maxValues[color] = number;
            }

        }
    }
    return maxValues;
}

fun solveTask1(fileName: String): Int {
    val lines = readInput("day2/${fileName}")
    return lines.mapIndexed { index, s -> Pair(index, checkIsGood(index, s)) }
        .filter { it.second }
        .sumOf { it.first + 1 }
}

fun solveTask2(fileName: String): Int {
    val lines = readInput("day2/${fileName}")
    return lines.mapIndexed { index, s -> maxByColor(index, s) }
        .map { it.values.fold(1) { a, b -> a * b } }
        .sum()
}

fun main() {
    check(solveTask1("example_1") == 8)
    check(solveTask2("example_1") == 2286)

    println("First star answer: ${solveTask1("input")}")
    println("Second star answer: ${solveTask2("input")}")
}
