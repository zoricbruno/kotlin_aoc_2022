package day05

import readInput
import java.util.*
import kotlin.collections.HashMap

fun findSpacer(input: List<String>): Int {
    var blank_index = 0
    while (input[blank_index] != "")
        blank_index++
    return blank_index
}

fun extractNumbersFromString(input: String): List<Int> {
    return Regex("[0-9]+")
        .findAll(input)
        .map(MatchResult::value)
        .map { it.toInt() }
        .toList()
}

fun extractStacks(input: List<String>): HashMap<Int, Stack<Char>> {
    val split_index = findSpacer(input)
    val stack_names = extractNumbersFromString(input[split_index - 1])

    val stacks = hashMapOf<Int, Stack<Char>>()
    for (stack_name in stack_names) {
        val stack = Stack<Char>()
        for (i in split_index - 2 downTo 0) {
            val value_index = 1 + (stack_name - 1) * 4
            if (value_index >= input[i].length || input[i][value_index] == ' ')
                break
            stack.push(input[i][value_index])
        }
        stacks[stack_name] = stack
    }
    return stacks
}

fun extractMove(input: String): Triple<Int, Int, Int> {
    val numbers = extractNumbersFromString(input)
    return Triple(numbers[0], numbers[1], numbers[2])
}


fun performMove(repetition: Int, from: Stack<Char>, to: Stack<Char>) = repeat(repetition) { to.push(from.pop()) }

fun performBulkMove(repetition: Int, from: Stack<Char>, to: Stack<Char>) {
    val bulkChars = mutableListOf<Char>()
    repeat(repetition) {
        bulkChars.add(from.pop())
    }
    for (char in bulkChars.asReversed())
        to.push(char)
}

fun extractTops(stacks: HashMap<Int, Stack<Char>>) = stacks.map { it.value.pop() }.joinToString("")

fun part1(input: List<String>): String {

    val moves_index = findSpacer(input) + 1
    val stacks = extractStacks(input)
    for (i in moves_index until input.size) {
        val (repetitions, from_name, to_name) = extractMove(input[i])
        performMove(repetitions, stacks[from_name]!!, stacks[to_name]!!)
    }
    return extractTops(stacks)
}

fun part2(input: List<String>): String {
    val moves_index = findSpacer(input) + 1
    val stacks = extractStacks(input)
    for (i in moves_index until input.size) {
        val (repetitions, from_name, to_name) = extractMove(input[i])
        performBulkMove(repetitions, stacks[from_name]!!, stacks[to_name]!!)
    }
    return extractTops(stacks)
}

fun main() {
    val day = "Day05"
    val test = "${day}_test"
    val testInput = readInput(day, test)

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == "CMZ")
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == "MCD")
    println(part2(input))
}
