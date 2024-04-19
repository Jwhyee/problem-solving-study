package greedy

import java.util.StringTokenizer
import kotlin.math.max

private const val DAY_OF_YEAR = 365

fun main() = with(System.`in`.bufferedReader()) {
   val n = readLine().toInt()
   val cnt = Array(DAY_OF_YEAR + 1) { 0 }
   repeat(n) {
      val st = StringTokenizer(readLine())
      val start = st.nextToken().toInt()
      val end = st.nextToken().toInt()
      for (i in start..end) {
         cnt[i]++
      }

   }
   var sum = 0
   var maxHeight = 0
   var width = 0

   for (i in 0..DAY_OF_YEAR) {
      if (cnt[i] == 0) {
         sum += maxHeight * width
         maxHeight = 0
         width = 0
         continue
      }
      width++
      maxHeight = max(maxHeight, cnt[i])
   }
   sum += maxHeight * width
   println(sum)
}