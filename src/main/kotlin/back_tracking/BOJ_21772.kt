package back_tracking

import java.util.StringTokenizer
import kotlin.math.max

private val dx = intArrayOf(1, -1, 0, 0)
private val dy = intArrayOf(0, 0, 1, -1)
private var r = 0
private var c = 0
private var t = 0
private var max = 0
private var cnt = 0
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

   backTracking(0, gy, gx)

   println(max)
   close()

}

private fun backTracking(depth: Int, y: Int, x: Int) {
   if (depth == t) {
      max = max(max, cnt)
      return
   }

   visited[y][x] = true
   println("cur = ($y, $x)")
   printMap()

   for (i in 0 until 4) {
      val ny = y + dy[i]
      val nx = x + dx[i]

      if (ny in 0 until r && nx in 0 until c) {
         val next = map[ny][nx]
         if (!visited[ny][nx] && next != '#') {
            if (next == 'S') {
               map[ny][nx] = '.'
               cnt += 1
               backTracking(depth + 1, ny, nx)
               cnt -= 1
               map[ny][nx] = 'S'
            } else {
               backTracking(depth + 1, ny, nx)
            }
            visited[ny][nx] = false
         }
      }

   }
   println("loop end")

}

private fun printMap() {
   println("--------------------")
   for (chars in map) {
      for (char in chars) {
         print("$char \t")
      }
      println()
   }

   for (chars in visited) {
      for (char in chars) {
         print("$char \t")
      }
      println()
   }

   println("--------------------")
}