package day4

import enumerate
import readInput
import kotlin.math.log10
import kotlin.math.pow

fun calculateForCard(id: Int, card: String): Int {
    val actual = findWinningNumbers(id, card)
    return 2.toDouble().pow((actual.size - 1).toDouble()).toInt();
}

fun solveTask1(input: String): Int {
    val lines = readInput("day4/${input}")
    return lines.enumerate().sumOf { (id, it) -> calculateForCard(id, it) }
}

fun findWinningNumbers(id: Int, card: String): List<Int> {
    val lettersToSkip: Int = 7 + (log10((id + 1).toFloat()).toInt() + 1);
    val s = card.substring(lettersToSkip)
    val sSplitted = s.split(" | ")
    val winning = sSplitted[0].split(" ").mapNotNull { it.toIntOrNull() }
        .toSet()
    return sSplitted[1].split(" ")
        .mapNotNull { it.toIntOrNull() }
        .filter { winning.contains(it) }
        .toList()
}


fun solveTask2(input: String): Int {
    val lines = readInput("day4/${input}")
    val cardCopies = mutableMapOf<Int, Int>()
    for (i in 1..lines.size) {
        cardCopies[i] = 1
    }
    for (i in 1 until lines.size) {
        val line = lines[i - 1]
        val won = findWinningNumbers(id = i - 1, line).size
        val currentCardCopies = cardCopies[i]!!
        for (j in i + 1 .. i + won) {
            cardCopies[j] = cardCopies[j]!! + currentCardCopies
        }

    }
    return cardCopies.values.sum()
}

fun main() {
    check(solveTask1("example") == 13)
    check(solveTask2("example") == 30)
    println("first task: ${solveTask1("input")}")
    println("second task: ${solveTask2("input")}")
}