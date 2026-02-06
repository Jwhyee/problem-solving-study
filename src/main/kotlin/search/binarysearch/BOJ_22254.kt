package search.binarysearch

import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, x) = st.run {
        nextToken().toInt() to nextToken().toInt()
    }

    val times = IntArray(n) { 0 }

    st = StringTokenizer(readLine())
    repeat(n) {
        times[it] = st.nextToken().toInt()
    }

    println(binarySearch(n, x, times))

    close()
}

private fun check(n: Int, x: Int, mid: Int, times: IntArray): Boolean {
    val pq = PriorityQueue<Int>()

    for (i in 0..mid) {
        pq += times[i]
    }

    for (i in mid + 1 until n) {
        val cur = pq.poll()

        if (cur + times[i] > x) {
            return false
        }
        pq += (cur + times[i])

    }

    return true
}

private fun binarySearch(n: Int, x: Int, times: IntArray): Int {
    var min = 0
    var max = n

    while (min < max) {
        val mid = (min + max) / 2

        if (check(n, x, mid, times)) {
            max = mid
        } else {
            min = mid + 1
        }
    }

    return max + 1
}