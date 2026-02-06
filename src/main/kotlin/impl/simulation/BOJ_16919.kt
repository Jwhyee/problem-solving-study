package impl.simulation

import java.util.*

/**
 * 문제 이름(난이도) : 봄버맨 2 (GOL3)
 * 시간 : 616 ms
 * 메모리 : 22900 KB
 * 링크 : https://www.acmicpc.net/problem/16919
 */

private const val FIELD = '.'
private const val BOMB = 'O'
private data class State(val y: Int, val x: Int)
fun main() = with(System.`in`.bufferedReader()) {
   val (r, c, n) = StringTokenizer(readLine()).run {
      Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
   }

   // 1. 가장 처음에 봄버맨은 일부 칸에 폭탄을 설치해 놓는다. 모든 폭탄이 설치된 시간은 같다.
   // 0 -> 초기 상태 / 1 -> 초기 터진 상태 / 2 -> 새로 채운 폭탄이 터진 상태 / 3 -> 모든 필드가 폭탄
   val map = Array(r) { y ->
      val arr = readLine().toCharArray()
      Array(c) { x ->
         CharArray(4) {
            if(it == 0) arr[x]
            else BOMB
         }
      }
   }

   // 3. 다음 1초 동안 폭탄이 설치되어 있지 않은 모든 칸에 폭탄을 설치한다.
   // - 즉, 모든 칸은 폭탄을 가지고 있게 된다. 폭탄은 모두 동시에 설치했다고 가정한다.
   // 4. 1초가 지난 후에 3초 전에 설치된 폭탄이 모두 폭발한다.
   // 초기 터진 상태와 새로 채운 폭탄이 터진 상태만 알면 되기 때문에 t + 1에만 채움
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
      // 5. 3과 4를 반복한다.
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
   // queue 안에는 폭탄만 들어있기 때문에 현재 자리와 상, 하, 좌, 우를 모두 필드로 변경
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