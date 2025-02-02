import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    var sum = 0
    val list = StringTokenizer(readLine()).run {
        List(m) {
            nextToken().toInt()
        }
    }

    var left = 1
    var right = list.maxOrNull()!!
    while (left <= right) {
        val mid = (left + right) / 2
        var cnt = 0
        for (i in list) {
            cnt += (i / mid)
        }
        if (cnt >= n) {
            sum = mid
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    println(sum)
}