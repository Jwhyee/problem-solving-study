package bfs

import java.lang.StringBuilder
import java.util.*

/**
 * 문제 이름(난이도) : BFS 스페셜 저지 (GOL3)
 * 시간 : 11 ms
 * 메모리 : 11 KB
 * 링크 : https://www.acmicpc.net/problem/16940
 */
fun main() = with(System.`in`.bufferedReader()) {
   val sb = StringBuilder()

   val n = readLine().toInt()

   val graph = MutableList<MutableList<Int>>(n) { mutableListOf() }

   repeat(n - 1) { idx ->
      val st = StringTokenizer(readLine())
      val parent = st.nextToken().toInt() - 1
      if (idx == 0 && parent != 0) {
         println("0")
         return@with
      }
      val child = st.nextToken().toInt() - 1

      graph[parent].add(child)
      graph[child].add(parent)
   }

   val answer = readLine()
   val visited = BooleanArray(n)

   fun bfs() {
      val queue: Queue<Int> = LinkedList()
      queue += 0

      while (queue.isNotEmpty()) {
         val parent = queue.poll()
         sb.append("${parent + 1} ")
         visited[parent] = true

         for (child in graph[parent]) {
            if(!visited[child]) {
               queue += child
            }
         }
      }
   }

   bfs()
   if(sb.toString().trim() == answer) println("1")
   else println("0")
   close()
}