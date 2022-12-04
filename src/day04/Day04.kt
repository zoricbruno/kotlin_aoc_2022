package day04

import readInput

fun getSetFromInput(input: String): Set<Int> {
    val start = input.takeWhile { it != '-' }.toInt()
    val end = input.takeLastWhile { it != '-' }.toInt()
    return (start..end).toSet()
}

fun isFullOverlap(left: Set<Int>, right: Set<Int>): Boolean {
    return left.containsAll(right) || right.containsAll(left)
}

fun doesOverlapExist(left: Set<Int>, right: Set<Int>): Boolean {
    return left.intersect(right).isNotEmpty()
}

fun part1(input: List<String>): Int {
    var total = 0;
    for (line in input) {
        val left = getSetFromInput(line.takeWhile { it != ',' })
        val right = getSetFromInput(line.takeLastWhile { it != ',' })
        if (isFullOverlap(left, right))
            total++
    }
    return total
}

fun part2(input: List<String>): Int {
    var total = 0
    for (line in input) {
        val left = getSetFromInput(line.takeWhile { it != ',' })
        val right = getSetFromInput(line.takeLastWhile { it != ',' })
        if (doesOverlapExist(left, right))
            total++
    }
    return total
}

fun main() {
    val day = "Day04"
    val test = "${day}_test"
    val testInput = readInput(day, test)

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 2)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 4)
    println(part2(input))
}
