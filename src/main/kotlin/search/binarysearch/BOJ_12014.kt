package search.binarysearch

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()

    val t = readLine().toInt()

    repeat(t) { index ->
        var st = StringTokenizer(readLine())

        val (n, k) = st.nextToken().toInt() to st.nextToken().toInt()

        st = StringTokenizer(readLine())

        val stocks = IntArray(n) { st.nextToken().toInt() }

        val lis = IntArray(n) { 0 }
        var len = 0

        for (i in 0 until n) {
            val stockPrice = stocks[i]

            val left = binarySearch(len, lis, stockPrice)

            lis[left] = stockPrice

            if (left == len) {
                len++
            }
        }

        bw.write("Case #${index + 1}")
        bw.newLine()
        bw.write("${if (len >= k) 1 else 0}")
        bw.newLine()

    }

    bw.flush()

    bw.close()
    close()
}

private fun binarySearch(length: Int, lis: IntArray, stockPrice: Int): Int {
    var left = 0
    var right = length

    while (left < right) {
        val mid = left + (right - left) / 2

        if (lis[mid] < stockPrice) {
            left = mid + 1
        } else {
            right = mid
        }
    }

    return left
}