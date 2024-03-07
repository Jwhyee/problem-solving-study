package dfs

import java.util.StringTokenizer

private var n = 0
private var m = 0
private lateinit var map: Array<CharArray>
private lateinit var visited: Array<BooleanArray>
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {
   val st = StringTokenizer(readLine())
   n = st.nextToken().toInt()
   m = st.nextToken().toInt()

   map = Array(n) { CharArray(m) }
   visited = Array(n) { BooleanArray(m) }

   repeat(n) { y ->
      val arr = readLine().toCharArray()
      repeat(m) { x ->
         map[y][x] = arr[x]
      }
   }

   close()

   repeat(n) { y ->
      repeat(m) { x ->
         if (!visited[y][x]) {
            val flag = dfs(y, x, map[y][x], 0, 0)
            if (flag) {
               println("Yes")
               return@with
            }
         }
      }
   }
   println("No")
}

private fun dfs(y: Int, x: Int, c: Char, dir: Int, t: Int): Boolean {
   val tempNy = y + dy[dir]
   val tempNx = x + dx[dir]
   if (isValid(tempNy, tempNx) && map[tempNy][tempNx] == c && t == 3) {
      return true
   }

   visited[y][x] = true

   for (i in 0 until 4) {
      val ny = y + dy[i]
      val nx = x + dx[i]

      if (isValid(ny, nx)) {
         if (!visited[ny][nx] && map[ny][nx] == c) {
            return dfs(ny, nx, c, i, if(i == dir) t else t + 1)
         }
      }
   }

   return false
}

private fun isValid(y: Int, x: Int) = (y in 0 until n && x in 0 until m)