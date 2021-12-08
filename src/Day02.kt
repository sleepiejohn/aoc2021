@file:Suppress("UNREACHABLE_CODE")

fun main() {

    data class Submarine(val horizontal: Int = 0, val depth: Int = 0) {
        fun parse(command: String): Submarine {
            val (move, x) = command.split(" ")
            val amount = x.toInt()
            return when (move) {
                "forward" -> this.copy(horizontal = this.horizontal + amount)
                "down" -> this.copy(depth = this.depth + amount)
                "up" -> this.copy(depth = this.depth - amount)
                else -> this
            }
        }

        fun reportPosition() = horizontal * depth
    }


    data class AimedSubmarine(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0) {
        fun parse(command: String): AimedSubmarine {
            val (move, x) = command.split(" ")
            val amount = x.toInt()
            return when (move) {
                "forward" -> this.copy(horizontal = this.horizontal + amount, depth = depth + (amount * aim))
                "down" -> this.copy(aim = aim + amount)
                "up" -> this.copy(aim = aim - amount)
                else -> this
            }
        }

        fun reportPosition() = horizontal * depth
    }

    fun part1(input: List<String>): Int {
        return input.fold(Submarine()) { sub, command -> sub.parse(command) }.reportPosition()
    }

    fun part2(input: List<String>): Int {
        return input.fold(AimedSubmarine()) { sub, command -> sub.parse(command) }.reportPosition()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
