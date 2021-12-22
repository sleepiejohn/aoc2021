import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/** Reads lines from the given input txt file. */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/** Reads lines from the given input file as integers */
fun readInputAsInt(name: String) = readInput(name).map(String::toInt)

/** Converts string to md5 hash. */
fun String.md5(): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun <E> List<List<E>>.transpose(): List<List<E>> {
    fun <E> doTranspose(xs: List<List<E>>): List<List<E>> {
        xs.filter { it.isNotEmpty() }.let { ys ->
            return when (ys.isNotEmpty()) {
                true -> listOf(ys.map { it.first() })
                    .plus(doTranspose(ys.map { it.takeLast(it.size - 1) }))
                else -> emptyList()
            }
        }
    }
    return doTranspose(this);
}

fun <T> identity(t: T) = t

val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()
