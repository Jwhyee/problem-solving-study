import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

private const val MAX = 1_000_000_000

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    var max = 0L

    val times = LongArray(n) {
        val time = readLine().toLong()
        max = max(max, time)
        time
    }

    println(binarySearch(n, m, max, times))

    close()
}

private fun binarySearch(n: Int, m: Int, max: Long, times: LongArray): Long {
    var left = 0L
    var right = max * m

    var answer = right

    while(left <= right) {
        val mid = (left + right) / 2

        var pass = 0L
        for (time in times) {
            val c = mid / time

            if (pass >= m) break

            pass += c
        }

        if (pass >= m) {
            answer = min(answer, mid)
            right = mid - 1
        } else {
            left = mid + 1
        }
    }

    return answer
}