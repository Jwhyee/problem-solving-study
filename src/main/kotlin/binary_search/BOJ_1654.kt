package binary_search

import java.lang.Long.max
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()){
    val (k, n) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    val arr = LongArray(k)
    var max = 0L
    for (i in 0 until k) {
        arr[i] = readLine().toLong()
        max = max(max, arr[i])
    }

    arr.sort()

    println(binarySearch(max, arr, n))
}

private fun binarySearch(max: Long, arr: LongArray, n: Int): Long {
    var left = 0L
    var right: Long = max

    while(left <= right) {
        val mid = (left + right) / 2
        if(mid == 0L) {
            return 1
        }
        var cnt = 0L
        for (num in arr) {
            cnt += (num / mid)
        }
        if(cnt >= n) {
            left = (mid + 1)
        } else {
            right = mid
        }
    }

    return right
}