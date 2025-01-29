package binary_search

import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    val (s, c) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val arr = LongArray(s)
    var max = 0L
    var sum = 0L
    for (i in 0 until s) {
        arr[i] = readLine().toLong()
        sum += arr[i]
        max = max(max, arr[i])
    }

    var left = 1L
    var right = max
    var answer = 0L

    while (left <= right) {
        val mid = (left + right) / 2
        var chickenCnt = 0L

        for (i in 0 until s) {
            chickenCnt += (arr[i] / mid)
        }

        if (chickenCnt >= c) {
            answer = mid
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    println(sum - (answer * c))
}