@file:Suppress("UNREACHABLE_CODE")

import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

data class Vent(val start: Position, val end: Position)

fun main() {
    fun vents(input: List<String>) = input.map {
        val (start, end) = it.split(" -> ")
        val (x1, y1) = start.split(",")
        val (x2, y2) = end.split(",")
        Vent(
            Position(x1.toInt(), y1.toInt()),
            Position(x2.toInt(), y2.toInt())
        )
    }

    fun allPositions(vent: Vent): List<Position> {
        return (0..max(abs(vent.start.x - vent.end.x), abs(vent.start.y - vent.end.y)))
            .map { step ->
                val x =
                    if (vent.end.x > vent.start.x) vent.start.x + step else if (vent.end.x < vent.start.x) vent.start.x - step else vent.start.x
                val y =
                    if (vent.end.y > vent.start.y) vent.start.y + step else if (vent.end.y < vent.start.y) vent.start.y - step else vent.start.y
                Position(x, y)
            }
    }

    fun part1(input: List<String>): Int {
        return vents(input)
            .filter { (start, end) -> start.x == end.x || start.y == end.y }
            .flatMap(::allPositions)
            .groupBy(::identity)
            .count { it.value.size > 1 }
    }

    fun part2(input: List<String>): Int {
        return vents(input)
            .flatMap(::allPositions)
            .groupBy(::identity)
            .count { it.value.size > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == 5)
    val input = readInput("Day05")
    println(part1(input))
    check(part2(testInput) == 12)
    println(part2(input))
}
