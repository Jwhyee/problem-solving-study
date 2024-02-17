import java.lang.StringBuilder
import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

private val dy = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
private val dx = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)

fun main() = with(System.`in`.bufferedReader()) {
   val (n, m, k) = StringTokenizer(readLine()).run {
      Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
   }

   val map = Array(n) { CharArray(m) }

   repeat(n) { map[it] = readLine().toCharArray() }

   var min = Int.MAX_VALUE
   var max = Int.MIN_VALUE

   val godsLikesString = mutableMapOf<String, Int>()

   repeat(k) {
      val str = readLine()
      godsLikesString[str] = 0
      min = min(min, str.length)
      max = max(max, str.length)
   }

   val range = min..max

   val sb = StringBuilder()

   lateinit var visited: Array<BooleanArray>

   fun backTracking(depth: Int, y: Int, x: Int) {
      if (depth + 1 in range) {
         val cur = sb.toString()
         if(godsLikesString.containsKey(cur)) {
            godsLikesString[cur] = godsLikesString[cur]!! + 1
         }
         return
      }

      visited[y][x] = true

      for (i in 0 until 8) {
         val next = nextPos(y, x, i, n, m)
         val ny = next.first
         val nx = next.second
         sb.append(map[ny][nx])
         backTracking(depth + 1, ny, nx)
         sb.deleteCharAt(depth + 1)
      }

   }

   val startsSet = godsLikesString.map { it.key[0] }.toSet()

   for (i in 0 until n) {
      for (j in 0 until m) {
         val cur = map[i][j]

         if (startsSet.contains(cur)) {
            sb.clear()
            visited = Array(n) { BooleanArray(m) }
            sb.append(cur)
            backTracking(0, i, j)
         }

      }
   }

   val bw = System.out.bufferedWriter()
   for (value in godsLikesString.values) {
      bw.append("$value")
      bw.newLine()
   }

   bw.flush()
   bw.close()
   close()

}
private fun nextPos(y: Int, x: Int, dir: Int, n: Int, m: Int): Pair<Int, Int> {
   val nx = x + dx[dir]
   val ny = y + dy[dir]

//   println("cur ($ny, $nx)")
   val cy = if(ny < 0) n + (ny % n) else if (ny > n - 1) ny % n else ny
   val cx = if(nx < 0) m + (nx % m) else if (nx > m - 1) nx % m else nx
//   println("cal ($cy, $cx)")

   return (cy to cx)
}