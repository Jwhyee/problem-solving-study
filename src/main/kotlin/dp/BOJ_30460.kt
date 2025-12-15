package dp

import java.util.StringTokenizer
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val st = StringTokenizer(readLine())
    val scores = IntArray(n + 3) { 0 }
    repeat(n) {
        scores[it] = st.nextToken().toInt()
    }

    val dp = Array(n + 3) { 0 }

    for (i in n - 1 downTo 0) {
        println("====== i = $i ======")
        println("dp[${i + 1}] + scores[$i]")
        println("= ${dp[i + 1]} + ${scores[i]} → ${dp[i + 1] + scores[i]}")
        println("dp[${i + 3}] + (scores[$i] + scores[${i + 1}] + scores[${i + 2}]) * 2)")
        println("= ${dp[i + 3]} + 2 * (${scores[i]} + ${scores[i + 1]} + ${scores[i + 2]}) → ${dp[i + 3] + (scores[i] + scores[i + 1] + scores[i + 2]) * 2}")
        dp[i] = max((dp[i + 1] + scores[i]), (dp[i + 3] + (scores[i] + scores[i + 1] + scores[i + 2]) * 2))
        println(dp.joinToString())
    }

    println(dp[0])
}
