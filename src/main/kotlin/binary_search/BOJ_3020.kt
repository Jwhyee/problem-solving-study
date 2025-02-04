package binary_search

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val (n, h) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val down = Array(n / 2) { 0 }
    val up = Array(n / 2) { 0 }

    for (i in 0 until n) {
        val num = readLine().toInt()
        if (i % 2 == 0) {
            down[i / 2] = num
        } else {
            up[i / 2] = num
        }
    }

    up.sort()
    down.sort()

    var min = n
    var cnt = 0

    for (i in 1..h) {
        val upCnt = n / 2 - binarySearch(0, n / 2, i, up)
        val downCnt = n / 2 - binarySearch(0, n / 2, h - i + 1, down)
        val total = upCnt + downCnt

        if (min == total) {
            cnt++
        } else if (min > total) {
            min = total
            cnt = 1
        }
    }
    println("$min $cnt")
}

private fun binarySearch(left: Int, right: Int, target: Int, arr: Array<Int>): Int {
    var l = left
    var r = right

    while (l < r) {
        val mid = (l + r) / 2
        if (arr[mid] < target) {
            l = mid + 1
        } else {
            r = mid
        }
    }

    return l
}