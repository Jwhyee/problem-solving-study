package search.backtracking

import java.util.StringTokenizer
import kotlin.math.max

private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private var r = 0
private var c = 0
private var t = 0
private var max = 0
private lateinit var map: Array<CharArray>
private lateinit var visited: Array<BooleanArray>
fun main() = with(System.`in`.bufferedReader()) {
   val st = StringTokenizer(readLine())
   r = st.nextToken().toInt()
   c = st.nextToken().toInt()
   t = st.nextToken().toInt()

   map = Array(r) { CharArray(c) }
   visited = Array(r) { BooleanArray(c) }

   var gy = 0
   var gx = 0

   for (y in 0 until r) {
      val arr = readLine().toCharArray()
      for (x in 0 until c) {
         val cur = arr[x]
         map[y][x] = cur
         if(cur == 'G') {
            gy = y
            gx = x
         }
      }
   }

   backTracking(0, gy, gx, 0)

   println(max)
   close()

}

private fun backTracking(depth: Int, y: Int, x: Int, cnt: Int) {
   if (depth == t) {
      max = max(max, cnt)
      return
   }

   visited[y][x] = true

   for (i in 0 until 4) {
      val ny = y + dy[i]
      val nx = x + dx[i]

      if (ny in 0 until r && nx in 0 until c) {
         val next = map[ny][nx]
         if (!visited[ny][nx] && next != '#') {
            if (next == 'S') {
               visited[ny][nx] = true
               backTracking(depth + 1, ny, nx, cnt + 1)
               visited[ny][nx] = false
            } else {
               backTracking(depth + 1, ny, nx, cnt)
            }
         }
      }

   }
}