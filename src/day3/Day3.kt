package day3

import enumerate
import readInput

fun findSymbolsPositions(lines: List<String>): List<Pair<Int, Int>> =
    lines
        .map { line ->
            line
                .enumerate()
                .filter { c -> !c.second.isDigit() && c.second != '.' }
                .map { it.first }
        }
        .enumerate()
        .flatMap { line -> line.second.map { index -> Pair(line.first, index) } }

fun findNumbers(lines: List<String>): List<Int> {
    val symbolsPositions = findSymbolsPositions(lines)
    val findNumbersRegex = Regex("(\\d+)")
    val numbers = mutableListOf<Int>();
    lines.forEachIndexed { y, line ->
        val numbersMatchResults = findNumbersRegex.findAll(line)
        matchLoop@ for (match in numbersMatchResults) {
            val xRange = match.range.toMutableSet()
            xRange.add(xRange.last() + 1)
            xRange.add(xRange.first() - 1)
            for (x in xRange.sorted()) {
                for (dy in listOf(-1, 0, 1)) {
                    if (symbolsPositions.contains(Pair(y + dy, x))) {
                        numbers.add(match.value.toInt())
                        continue@matchLoop;
                    }
                }
            }
        }
    }
    return numbers
}

fun solveTask1(input: String): Int {
    val lines = readInput("day3/${input}")
    val numbers = findNumbers(lines)
    return numbers.sum();
}

fun findGears(lines: List<String>): List<Int> {
    val possibleGears = findSymbolsPositions(lines)
        .filter { lines[it.first][it.second] == '*' }
    val possibleGearsMap = HashMap<Pair<Int, Int>, Pair<Int, Int>>()
    val findNumbersRegex = Regex("(\\d+)")
    lines.forEachIndexed { y, line ->
        val numbersMatchResults = findNumbersRegex.findAll(line)
        matchLoop@ for (match in numbersMatchResults) {
            val xRange = match.range.toMutableSet()
            xRange.add(xRange.last() + 1)
            xRange.add(xRange.first() - 1)
            for (x in xRange.sorted()) {
                for (dy in listOf(-1, 0, 1)) {
                    if (possibleGears.contains(Pair(y + dy, x))) {
                        val current = possibleGearsMap.getOrPut(Pair(y + dy, x)) {
                            Pair(0, 1)
                        }
                        possibleGearsMap[Pair(y + dy, x)] =
                            current.copy(first = current.first + 1, second = current.second * match.value.toInt())
                    }
                }
            }
        }
    }
    return possibleGearsMap.filter { it.value.first == 2 }
        .map { it.value.second }
}

fun solveTask2(input: String): Int {
    val lines = readInput("day3/${input}")
    val gears = findGears(lines)
    return gears.sum()
}

fun main() {
    check(solveTask1("example") == 4361)
    check(solveTask2("example") == 467835)
    println("First star answer: ${solveTask1("input")}");
    println("Second star answer: ${solveTask2("input")}");
}