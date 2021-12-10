@file:Suppress("UNREACHABLE_CODE")

typealias RateType = (frequencies: Map<String, Int>) -> String?

fun main() {
    fun columns(input: List<String>) = input.map { it.split("").filterNot(String::isBlank) }
        .transpose()

    fun commonRating(
        columns: List<List<String>>,
        rateType: RateType
    ) = columns.map {
        val freq = it.groupingBy(::identity).eachCount()
        rateType(freq)
    }

    fun rate(columns: List<List<String>>, rateType: RateType): Int {
        return commonRating(columns, rateType)
            .joinToString(separator = "")
            .toInt(2)
    }

    val comparator = { a: Map.Entry<String, Int>, b: Map.Entry<String, Int> ->
        if (a.value > b.value) 1
        else if (a.value < b.value) -1
        else if (a.key > b.key) 1 else -1
    }

    fun gamma(frequencies: Map<String, Int>) = frequencies.maxWithOrNull(comparator)?.key
    fun epsilon(frequencies: Map<String, Int>) = frequencies.minWithOrNull(comparator)?.key

    fun findRating(acc: List<String>, rateType: RateType, position: Int = 0): String {
        return if (acc.size <= 1) {
            acc.first()
        } else {
            val columns = columns(acc)
            val freq = columns.map { it.groupingBy(::identity).eachCount() }
            val posMostCommon = rateType(freq[position])
            findRating(acc.filter { it[position].toString() == posMostCommon }, rateType, position + 1)
        }
    }


    fun part1(input: List<String>): Int {
        val columns = columns(input)
        val gammaRate = rate(columns, ::gamma)
        val epsilonRate = rate(columns, ::epsilon)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val oxigenGenRating = findRating(input, ::gamma).toInt(2)
        val co2ScrubberRating = findRating(input, ::epsilon).toInt(2)
        return oxigenGenRating * co2ScrubberRating
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")
    check(part1(testInput) == 198)
    println(part1(input))
    check(part2(testInput) == 230)
    println(part2(input))
}
