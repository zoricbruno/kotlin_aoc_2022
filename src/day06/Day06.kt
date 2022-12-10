package day06

import readInput

fun findStartMarkerIndex(input: String, length: Int): Int {
    val unique = HashSet<Char>()
    var left = 0
    var right = 0
    while (right < input.length){

        while (unique.contains(input[right])){
            unique.remove(input[left])
            left++
        }
        unique.add(input[right])
        right++
        if(unique.size == length)
            return right
    }
    throw IllegalArgumentException("No such luck.")
}

fun part1(input: String): Int {
    return findStartMarkerIndex(input, 4)
}

fun part2(input: String): Int {
    return findStartMarkerIndex(input, 14)
}

fun main() {
    val day = "Day06"
    val test = "${day}_test"
    val testInput = readInput(day, test).first()

    val input = readInput(day, day).first()

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 7)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 19)
    println(part2(input))
}
