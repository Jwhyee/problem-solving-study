import java.util.StringTokenizer
import kotlin.math.max

private const val DAY_OF_YEAR = 365

fun main() = with(System.`in`.bufferedReader()) {
   val n = readLine().toInt()
   val cnt = Array(DAY_OF_YEAR + 1) { 0 }
   repeat(n) {
      val (start, end) = StringTokenizer(readLine()).run {
         nextToken().toInt() to nextToken().toInt()
      }
      for (i in start..end) {
         cnt[i]++
      }
   }
   var result = 0
   var h = 0
   var w = 0

   for (i in 0..DAY_OF_YEAR) {
      if (cnt[i] == 0) {
         result += h * w
         h = 0
         w = 0
         continue
      }
      w++
      h = max(h, cnt[i])
   }
   result += h * w
   println(result)
}