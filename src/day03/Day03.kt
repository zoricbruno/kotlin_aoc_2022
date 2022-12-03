package day03

import readInput

fun getWrongItem(rucksack: String): Char {
    val firstPocket = rucksack.take(rucksack.length / 2).toSet()
    val secondPocket = rucksack.takeLast(rucksack.length / 2).toSet()
    return firstPocket.intersect(secondPocket).first()
}

fun getBadge(elf1: String, elf2: String, elf3: String): Char {
    return elf1.toSet().intersect(elf2.toSet().intersect(elf3.toSet())).first()
}

fun getPoints(item: Char): Int {
    return if (item in 'a'..'z') item.code - 'a'.code + 1 else item.code - 'A'.code + 27
}

fun part1(input: List<String>): Int {
    var total = 0;
    for (rucksack in input) {
        total += getPoints(getWrongItem(rucksack))
    }
    return total
}

fun part2(input: List<String>): Int {
    var total = 0;
    input.chunked(3).forEach{
        total += getPoints(getBadge(it[0], it[1], it[2]))
    }
    return total
}

fun main() {
    val day = "Day03"
    val test = "${day}_test"
    val testInput = readInput(day, test)

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 157)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 70)
    println(part2(input))
}
