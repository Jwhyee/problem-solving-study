import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    var max = 0
    val list = StringTokenizer(readLine()).run {
        List(m) {
            val num = nextToken().toInt()
            max = max(max, num)
            num
        }
    }

    var left = 1
    var right = max
    max = 0
    while (left <= right) {
        val mid = (left + right) / 2
        var cnt = 0
        for (i in list) {
            cnt += (i / mid)
        }
        if (cnt >= n) {
            max = mid
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    println(max)
}