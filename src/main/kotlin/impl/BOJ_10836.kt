package impl

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
   val (m, n) = StringTokenizer(readLine()).run {
      nextToken().toInt() to nextToken().toInt()
   }

   val comb = Array(m) { IntArray(m) { 1 } }
   val diff = Array(m) { IntArray(m) { 0 } }

   val order = Array(m * 2 - 1) { 0 }

   repeat(n) {
      val st = StringTokenizer(readLine())
      var idx = 0
      val zeroCnt = st.nextToken().toInt()
      repeat(zeroCnt) { order[idx++] = 0 }
      val oneCnt = st.nextToken().toInt()
      repeat(oneCnt) { order[idx++] = 1 }
      val twoCnt = st.nextToken().toInt()
      repeat(twoCnt) { order[idx++] = 2 }

      idx = 0
      for (i in m - 1 downTo 0) {
         diff[i][0] = order[idx]
         comb[i][0] += order[idx++]
      }

      for (i in 1 until m) {
         diff[0][i] = order[idx]
         comb[0][i] += order[idx++]
      }

      for (i in 1 until m) {
         for (j in 1 until m) {
            var max = -1
            for (k in 0..2) {
               val ny = i + dy[k]
               val nx = j + dx[k]
               max = max(max, diff[ny][nx])
            }
            diff[i][j] = max
            comb[i][j] += max
         }
      }


   }

   for (ints in comb) {
      println(ints.joinToString(" "))
   }
}