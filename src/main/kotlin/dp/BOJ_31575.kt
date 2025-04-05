package dp

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.run {
        nextToken().toInt() to nextToken().toInt()
    }
    val map = Array(m + 1) { IntArray(n + 1) }
    val dp = Array(m + 1) { BooleanArray(n + 1) }

    for (i in 1 until m + 1) {
        st = StringTokenizer(readLine())
        for (j in 1 until n + 1) {
            map[i][j] = st.nextToken().toInt()
        }
    }

    dp[1][1] = true

    for (i in 1 until m + 1) {
        for (j in 1 until n + 1) {
            if(i == 1 && j == 1) continue
            if(map[i][j] == 0) continue
            dp[i][j] = dp[i - 1][j] || dp[i][j - 1]
        }
    }

    println(if(dp[m][n]) "Yes" else "No")
}