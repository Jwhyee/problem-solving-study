import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val st = StringTokenizer(readLine())
    val datum = IntArray(n + 3) { 0 }
    repeat(n) {
        datum[it] = st.nextToken().toInt()
    }

    val dp = Array(n + 3) { 0 }

    for (i in n - 1 downTo 0) {
        dp[i] = max((dp[i + 1] + datum[i]), (dp[i + 3] + (datum[i] + datum[i + 1] + datum[i + 2]) * 2))
    }

    println(dp[0])
}
