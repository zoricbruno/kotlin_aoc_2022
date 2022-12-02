fun findCalories(input: List<String>): List<Int> {
    val calories = mutableListOf<Int>()
    var currentCalories = 0
    var index = 0
    while (index < input.size) {
        if (input[index] == "") {
            calories.add(currentCalories)
            currentCalories = 0;
        } else if (index == input.size - 1) {
            currentCalories += input[index].toInt()
            calories.add(currentCalories)
        } else {
            currentCalories += input[index].toInt()
        }
        index++
    }
    return calories
}


fun part1(input: List<String>): Int {
    return findCalories(input).max()
}

fun part2(input: List<String>): Int {
    return findCalories(input).sorted().takeLast(3).sum()
}

fun main() {
    val day = "Day01"
    val testInput = readInput(day, "Day01_test")

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 24000)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 45000)
    println(part2(input))
}
