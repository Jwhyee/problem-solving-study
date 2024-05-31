import java.util.*

private const val FIELD = '.'
private const val BOMB = 'O'
private data class State(val y: Int, val x: Int)
fun main() = with(System.`in`.bufferedReader()) {
   val (r, c, n) = StringTokenizer(readLine()).run {
      Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
   }

   // 1. 가장 처음에 봄버맨은 일부 칸에 폭탄을 설치해 놓는다. 모든 폭탄이 설치된 시간은 같다.
   // 0 -> 초기 상태 / 1 -> 한 번 터진 상태 / 2 -> 두 번 터진 상태
   val map = Array(r) { y ->
      val arr = readLine().toCharArray()
      Array(c) { x ->
         CharArray(4) {
            if(it == 0) arr[x]
            else BOMB
         }
      }
   }


   repeat(2) { t ->
      val queue: Queue<State> = LinkedList()
      repeat(r) { y ->
         repeat(c) { x ->
            if (map[y][x][t] == BOMB) {
               queue += State(y, x)
            }
         }
      }
      bfs(queue, map, t + 1)
   }

   if (n == 1) {
      printMap(map, 0)
   } else if (n % 2 == 0) {
      printMap(map, 3)
   } else if (n % 4 == 3) {
      printMap(map, 1)
   } else if (n % 4 == 1) {
      printMap(map, 2)
   }

   close()
}

private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(1, 0, -1, 0)

private fun bfs(queue: Queue<State>, map: Array<Array<CharArray>>, t: Int) {
   while (queue.isNotEmpty()) {
      val cur = queue.poll()
      map[cur.y][cur.x][t] = FIELD
      for (i in 0 until 4) {
         val ny = cur.y + dy[i]
         val nx = cur.x + dx[i]

         if (ny in map.indices && nx in map[0].indices) {
            map[ny][nx][t] = FIELD
         }
      }
   }
}

private fun printMap(map: Array<Array<CharArray>>, t: Int) {
   for (y in map.indices) {
      for (x in map[0].indices) {
         print(map[y][x][t])
      }
      println()
   }
}