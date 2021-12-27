@file:Suppress("UNREACHABLE_CODE")

fun main() {
    fun evolution(input: List<String>, days: Int): Long {
        val fishes = input.head.split(",").map { it.toInt() }
        val fishGroup = MutableList<Long>(9) { idx -> fishes.count { idx == it }.toLong() }
        repeat(days) {
            val newborn = fishGroup.removeAt(0)
            fishGroup[6] += newborn
            fishGroup += newborn
        }
        return fishGroup.sum()
    }

    fun part1(input: List<String>): Long {
        return evolution(input, 80)
    }

    fun part2(input: List<String>): Long {
        return evolution(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput).also(::println) == 5934L)
    val input = readInput("Day06")
    println(part1(input))
    check(part2(testInput) == 26984457539L)
    println(part2(input))
}
