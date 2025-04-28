import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (n, m) = st.run {
        nextToken().toInt() to nextToken().toInt()
    }

    val map = Array(n + 1) { IntArray(m + 1) }
    val dp = Array(n + 1) { IntArray(m + 1) }

    for(i in 1 until n + 1) {
        st = StringTokenizer(readLine())
        for(j in 1 until m + 1) {
            val s = st.nextToken().toInt()
            map[i][j] = s
        }
    }

    dp[1][1] = map[1][1]

    for(i in 1 until n + 1) {
        for(j in 1 until m + 1) {
            if(i == 1 && j == 1) continue

            if(i == 1) {
                dp[i][j] = map[i][j] + dp[i][j - 1]
            } else {
                dp[i][j] = max(map[i][j] + dp[i][j - 1], map[i][j] + dp[i - 1][j])
            }
        }
    }

    println(dp[n][m])
}