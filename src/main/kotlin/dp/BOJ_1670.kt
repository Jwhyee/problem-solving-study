package dp

/**
 * 문제 이름(난이도) : 정산 회담 2(GOL3)
 * 시간 : 284 ms
 * 메모리 : 13220 KB
 * 링크 : https://www.acmicpc.net/problem/1670
 */
fun main() = with(System.`in`.bufferedReader()) {
   val n: Int = readLine().toInt()
   val dp = LongArray(100001)
   val div = 987654321L

   dp[0] = 1; dp[2] = 1;

   for (i in 4..n step 2) {
      for (j in 0..i - 2 step 2) {
         dp[i] += dp[j] * dp[i - j - 2]
         dp[i] %= div
      }
   }

   println(dp[n])
}