package day02

import readInput

fun getSignPoints(sign: Char): Int {
    val points = hashMapOf<Char, Int>('A' to 1, 'B' to 2, 'C' to 3)
    return points[sign]!!
}

fun mapToOutcome(sign: Char): Char {
    val mapping = hashMapOf<Char, Char>('X' to 'A', 'Y' to 'B', 'Z' to 'C')
    return mapping[sign]!!
}

fun mapToAdapt(sign: Char, elf: Char): Char {
    val wins = hashMapOf<Char, Char>('A' to 'C', 'B' to 'A', 'C' to 'B')
    val loss = hashMapOf<Char, Char>('A' to 'B', 'B' to 'C', 'C' to 'A')

    when (sign) {
        'Y' -> return elf
        'X' -> return wins[elf]!!
        'Z' -> return loss[elf]!!
    }
    throw IllegalArgumentException()
}


fun getUserOutcomePoints(user: Char, elf: Char): Int {
    val wins = hashMapOf<Char, Char>('A' to 'C', 'B' to 'A', 'C' to 'B')
    if (elf == user) return 3
    if (wins[user] == elf) return 6
    return 0
}

fun part1(input: List<String>): Int {
    var total = 0;
    for (line in input) {
        val elf: Char = line.first()
        val user: Char = mapToOutcome(line.last())
        total += getUserOutcomePoints(user, elf) + getSignPoints(user)
    }
    return total
}

fun part2(input: List<String>): Int {
    var total = 0;
    for (line in input) {
        val elf: Char = line.first()
        val user: Char = mapToAdapt(line.last(), elf)
        total += getUserOutcomePoints(user, elf) + getSignPoints(user)
    }
    return total
}

fun main() {
    val day = "Day02"
    val test = "${day}_test"
    val testInput = readInput(day, test)

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 15)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 12)
    println(part2(input))
}
