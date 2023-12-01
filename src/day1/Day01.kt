fun findSumTask1(str: String): Int {
    val digits = str.filter { it.isDigit() }.map { it.digitToInt() }
    val firstDigit = digits.first()
    val lastDigit = digits.last()
    return firstDigit * 10 + lastDigit;
}

fun findSumTask2(str: String): Int {
    val spelledDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        )
    var firstDigit = -1;
    var lastDigit = -1;
    for (i in str.indices) {
        var currentDigit = -1;
        for ((spelling, digit) in spelledDigits) {
            if (i + spelling.length > str.length) {
                continue;
            }
            if (str.substring(i until i + spelling.length) == spelling) {
                currentDigit = digit;
                break;
            }
        }
        if (currentDigit == -1) {
            if (str[i].isDigit()) {
                currentDigit = str[i].digitToInt();
            }
        }
        if (currentDigit != -1) {
            if (firstDigit == -1) {
                firstDigit = currentDigit;
            }
            lastDigit = currentDigit;
        }
    }

    return firstDigit * 10 + lastDigit;
}

fun main() {
    val lines = readInput("day1/input")
    val sum = lines.sumOf { findSumTask2(it) }
    println("Answer is $sum")
}
