package binary_search

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val (n, c) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val houses = IntArray(n) {
        readLine().toInt()
    }

    houses.sort()

    println(binarySearch(n, c, houses))

    close()
}

private fun check(n: Int, c: Int, mid: Int, houses: IntArray): Int {
    var lastInstall = houses[0]
    var count = 1

    for (i in 1 until n) {
        val cur = houses[i]

        if (cur - lastInstall >= mid) {
            lastInstall = cur
            count++
        }
    }

    return count
}

private fun binarySearch(n: Int, c: Int, houses: IntArray): Int {
    val max = houses.max()

    var left = 0
    var right = max

    while (left <= right) {
        val mid = (left + right) / 2

        if(check(n, c, mid, houses) >= c) {
            left = mid + 1
        } else {
            right = mid - 1
        }
    }

    return right
}