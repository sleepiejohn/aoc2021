@file:Suppress("UNREACHABLE_CODE")


data class Entry(var value: Int, var marked: Boolean = false)

class Board(input: List<List<Int>>) {
    private val iBoard: MutableMap<Position, Entry> = mutableMapOf()

    init {
        for ((x, row) in input.withIndex()) {
            for ((y, col) in row.withIndex()) {
                iBoard[Position(x,y)] = Entry(col)
            }
        }
    }

    fun shouldWin(): Boolean {
        return check { (x, _), i -> x == i } || check { (_, y), i -> y == i }
    }

    fun sumOfUnmarked(): Int =
        iBoard.map { e -> if (e.value.marked) 0 else e.value.value }.sum()

    fun mark(drawnValue: Int) {
        iBoard.filterValues { (value, marked) -> !marked && value == drawnValue }
            .forEach { entry ->
                iBoard[entry.key] = entry.value.copy(marked = true)
            }
    }

    private fun check(positionFilter: (Position, Int) -> Boolean) =
        (0 until 5).any { i ->
            iBoard.filterKeys { positionFilter(it, i) }.all { it.value.marked }
        }
}

class GameSet(private val boards: List<Board>) {
    private var winner: Board? = null
    fun findWinner() = boards.find { it.shouldWin() }.also { winner = it }


    fun mark(drawnValue: Int) {
        boards.forEach { it.mark(drawnValue) }
    }

    fun winnerValue(drawnValue: Int): Int? =
        winner?.sumOfUnmarked()
            ?.let { it * drawnValue }

   fun findLastWinner(draws: List<Int>): Pair<Board?, Int> {
        var mutBoards = boards
        var last : Pair<Board?, Int> = Pair(null, 0)
        for (draw in draws) {
            for (board in mutBoards) {
                board.mark(draw)
                if (board.shouldWin()) {
                    mutBoards = mutBoards - board
                    last = board to draw
                }
            }
        }
        return last
    }
}


fun main() {
    fun makeBoards(rest: List<String>) = rest
        .filter { it.isNotBlank() }// remove blank lines
        .chunked(5)// each board is a piece of 5 lines
        .map { line ->
            line.map {
                it
                    .trim()
                    .split(Regex("\\W+"))
                    .map(String::toInt)
            } // a line is a list of numbers
        }
        .map { Board(it) }


    fun drawsAndBoards(input: List<String>): Pair<List<Int>, List<String>> {
        val draws = input.head.split(",").map(String::toInt)
        val rest = input.tail
        return draws to rest
    }

    fun part1(input: List<String>): Int {
        val (draws, rest) = drawsAndBoards(input)
        val set = GameSet(makeBoards(rest))

        return draws.find {
            set.mark(it)
            set.findWinner() != null
        }?.let { set.winnerValue(it) } ?: -1

    }

    fun part2(input: List<String>): Int {
        val (draws, rest) = drawsAndBoards(input)
        val set = GameSet(makeBoards(rest))
        val (board, draw) = set.findLastWinner(draws)
        return board?.let { it.sumOfUnmarked() * draw } ?: -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")
    check(part1(testInput).also(::println) == 4512)
    println(part1(input))


    check(part2(testInput).also(::println) == 1924)
    println(part2(input))
}
