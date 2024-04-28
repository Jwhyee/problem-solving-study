import java.util.StringTokenizer
import kotlin.math.max

private val dx = intArrayOf(-1, -1, 0)
private val dy = intArrayOf(0, -1, -1)

/**
 * 문제 이름(난이도) : 여왕벌 (GOL4)
 * 시간 : 548 ms
 * 메모리 : 61200 KB
 * 링크 : https://www.acmicpc.net/problem/2174
 */
fun main() = with(System.`in`.bufferedReader()) {
   val bw = System.out.bufferedWriter()
   val (m, n) = StringTokenizer(readLine()).run {
      nextToken().toInt() to nextToken().toInt()
   }

   val comb = Array(m) { IntArray(m) { 1 } }

   repeat(n) {
      val st = StringTokenizer(readLine())
      var zeroCnt = st.nextToken().toInt()
      var oneCnt = st.nextToken().toInt()
      var twoCnt = st.nextToken().toInt()

      for (i in m - 1 downTo 0) {
         if(zeroCnt != 0) zeroCnt--
         else if(oneCnt != 0) { oneCnt--; comb[i][0] += 1 }
         else if(twoCnt != 0) { twoCnt--; comb[i][0] += 2 }
      }

      for (i in 1 until m) {
         if(zeroCnt != 0) zeroCnt--
         else if(oneCnt != 0) { oneCnt--; comb[0][i] += 1 }
         else if(twoCnt != 0) { twoCnt--; comb[0][i] += 2 }
      }
   }

   for (i in 1 until m) {
      for (j in 1 until m) {
         comb[i][j] = max(comb[i - 1][j], max(comb[i - 1][j - 1], comb[i][j - 1]))
      }
   }

   for (r in 0 until m) {
      for (c in 0 until m) {
         bw.append("${comb[r][c]} ")
      }
      bw.newLine()
   }
   bw.flush()
   bw.close()
   close()
}