fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    println(minStepsToOne(n))
}

// 1로 만들기
private fun minStepsToOne(n: Int) = when(n) {
    1 -> 0
    2 -> 1
    3 -> 1
    else -> {
        val dp = IntArray(n + 1) { Int.MAX_VALUE }
        dp[1] = 0
        dp[2] = 1
        dp[3] = 1

        for (i in 4..n) {
            dp[i] = dp[i - 1] + 1
            if(i % 2 == 0) {
                dp[i] = minOf(dp[i], dp[i / 2] + 1)
            }
            if(i % 3 == 0) {
                dp[i] = minOf(dp[i], dp[i / 3] + 1)
            }
        }
        dp[n]
    }
}