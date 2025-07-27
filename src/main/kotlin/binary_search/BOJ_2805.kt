package binary_search

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()

    st = StringTokenizer(readLine())
    val trees = LongArray(n) {
        st.nextToken().toLong()
    }
    trees.sort()

    val prefix = LongArray(n + 1)
    for (i in 0 until n) {
        prefix[i + 1] = prefix[i] + trees[i]
    }

    var low = 0L
    var high = trees.last()
    var answer = 0L

    while (low <= high) {
        val mid = (low + high) ushr 1

        val idx = trees.binarySearch(mid).let {
            if (it >= 0) {

                var pos = it
                while (pos + 1 < n && trees[pos + 1] == mid) pos++
                pos + 1
            } else {

                -it - 1
            }
        }

        val wood = (prefix[n] - prefix[idx]) - mid * (n - idx)

        if (wood >= m) {
            answer = mid
            low = mid + 1
        } else {
            high = mid - 1
        }
    }

    println(answer)
}