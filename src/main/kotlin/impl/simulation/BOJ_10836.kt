package impl.simulation

import java.util.StringTokenizer

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

   val comb = Array(m * 2 - 1) { 0 }

   repeat(n) {
      val st = StringTokenizer(readLine())
      var zero = st.nextToken().toInt()

      for (i in 1..2) {
         var num = st.nextToken().toInt()
         while (num-- > 0) comb[zero++] += i
      }
   }

   for (i in m - 1 downTo 0) {
      bw.append("${comb[i] + 1} ")
      for (j in m until 2 * m - 1) {
         bw.append("${comb[j] + 1} ")
      }
      bw.newLine()
   }
   bw.flush()
   bw.close()
   close()
}