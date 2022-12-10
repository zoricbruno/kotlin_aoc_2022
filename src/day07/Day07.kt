package day07

import readInput

abstract class FileAbstraction(
    private val name: String,
    private val parent: FileAbstraction?
) {
    fun getParent(): FileAbstraction? = parent
    fun getName(): String = name
    abstract fun getTotalSize(): Int
    abstract fun add(file: FileAbstraction)
    abstract fun getChildren(): List<FileAbstraction>
    abstract fun getChildSizes(): List<Int>
}

class File(name: String, parent: FileAbstraction, private val size: Int) : FileAbstraction(name, parent) {

    override fun getTotalSize(): Int = size
    override fun add(file: FileAbstraction) {}
    override fun getChildren() = listOf<FileAbstraction>()
    override fun getChildSizes(): List<Int> = listOf()
}

class Directory(name: String, parent: FileAbstraction?) : FileAbstraction(name, parent) {

    private val contents = mutableListOf<FileAbstraction>()

    override fun getTotalSize(): Int {
        var total = 0
        for (child in getChildren()) {
            total += child.getTotalSize()
        }
        return total
    }

    override fun add(file: FileAbstraction) {
        contents.add(file)
    }

    override fun getChildren(): List<FileAbstraction> = contents
    override fun getChildSizes(): List<Int> {
        val sizes = mutableListOf<Int>()
        sizes.add(this.getTotalSize())
        for (child in getChildren()) {
            sizes.addAll(child.getChildSizes())
        }
        return sizes
    }
}

class FileSystem {
    private val root = Directory("/", null)
    private var current: FileAbstraction = root

    fun run(commands: List<String>) {
        var i = 0
        while (i < commands.size) {
            if (commands[i] == "$ cd /") {
                current = root
            } else if (commands[i] == "$ ls") {
                i++
                while (i < commands.size && !commands[i].startsWith("$")) {
                    add(commands[i])
                    i++
                }
                i--
            } else if (commands[i] == "$ cd ..") {
                current = current.getParent() ?: root
            } else if (commands[i].startsWith("$ cd")) {
                val parts = commands[i].split(" ")
                current = current.getChildren().find { it.getName() == parts.last() } ?: root
            }
            i++
        }
    }

    private fun getDirectorySizes(): List<Int> = root.getChildSizes()

    fun getSumOfSmallDirectoriesToDelete(): Int = getDirectorySizes().filter { it < 100000 }.sum()

    fun getSmallestDirectoryToDelete(): Int {
        val sizes = getDirectorySizes()
        val freeSpace = 70_000_000 - sizes.first()
        val spaceToFree = 30_000_000 - freeSpace
        return sizes.filter { it >= spaceToFree }.min()
    }

    private fun add(node: String) {
        val parts = node.split(" ")
        if (node.startsWith("dir")) {
            current.add(Directory(parts.last(), current))
        } else {
            current.add(File(parts.last(), current, parts.first().toInt()))
        }
    }
}

fun part1(input: List<String>): Int {
    val filesystem = FileSystem()
    filesystem.run(input)
    return filesystem.getSumOfSmallDirectoriesToDelete()
}

fun part2(input: List<String>): Int {
    val filesystem = FileSystem()
    filesystem.run(input)
    return filesystem.getSmallestDirectoryToDelete()
}

fun main() {
    val day = "Day07"
    val test = "${day}_test"
    val testInput = readInput(day, test)

    val input = readInput(day, day)

    val testFirst = part1(testInput)
    println(testFirst)
    check(testFirst == 95437)
    println(part1(input))

    val testSecond = part2(testInput)
    println(testSecond)
    check(testSecond == 24933642)
    println(part2(input))
}
