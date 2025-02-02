package binary_search

import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()
    var max = 0
    st = StringTokenizer(readLine())
    val arr = IntArray(m)
    for (i in 0 until m) {
        arr[i] = st.nextToken().toInt()
        max = max(max, arr[i])
    }

    println(binarySearch(arr, n, max))
}

private fun binarySearch(arr: IntArray, n: Int, max: Int): Int {
    var sum = 0
    var left = 1
    var right = max
    while (left <= right) {
        val mid = (left + right) / 2
        var cnt = 0
        for (i in arr) {
            cnt += (i / mid)
        }
        if (cnt >= n) {
            sum = mid
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    return sum
}