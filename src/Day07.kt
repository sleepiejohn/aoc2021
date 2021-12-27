@file:Suppress("UNREACHABLE_CODE")

import kotlin.math.abs


fun main() {
    fun crabs(input: List<String>) = input.head.split(",").map { it.toInt() }

    fun allPossiblePositions(crabs: List<Int>) = (0..crabs.maxOf { it })

    fun part1(input: List<String>): Int {
        val crabs = crabs(input)
        return allPossiblePositions(crabs).minOf { alignPosition ->
            crabs.sumOf { crabPosition -> abs(alignPosition - crabPosition) }
        }
    }

    fun allCosts(alignPosition: Int, crabPosition: Int) = abs(alignPosition - crabPosition) downTo 1

    fun part2(input: List<String>): Int {
        val crabs = crabs(input)
        return allPossiblePositions(crabs).minOf { alignPosition ->
            crabs.sumOf { crabPosition -> allCosts(alignPosition, crabPosition).sum() }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also(::println) == 37)
    val input = readInput("Day07")
    println(part1(input))
    check(part2(testInput) == 168)
    println(part2(input))
}
