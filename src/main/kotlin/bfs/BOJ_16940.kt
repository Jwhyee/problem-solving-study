package bfs

import java.util.*

private class Node (var parent: Int, var children: Int)

/**
 * 문제 이름(난이도) : BFS 스페셜 저지 (GOL3)
 * 시간 : 620 ms
 * 메모리 : 73328 KB
 * 링크 : https://www.acmicpc.net/problem/16940
 */
fun main() = with(System.`in`.bufferedReader()) {
   val n = readLine().toInt()
   val graph = Array<MutableList<Int>>(n + 1) { mutableListOf() }

   repeat(n-1) {
      val st = StringTokenizer(readLine())
      val n1 = st.nextToken().toInt()
      val n2 = st.nextToken().toInt()

      graph[n1].add(n2)
      graph[n2].add(n1)
   }

   val order = mutableListOf<Int>()

   val st = StringTokenizer(readLine())
   while (st.hasMoreTokens()) {
      order += st.nextToken().toInt()
   }

   val queue: Queue<Int> = LinkedList()
   val nodes = Array(n + 1) { Node(0, 0) }
   val visited = BooleanArray(n + 1)

   queue += 1
   visited[1] = true

   while (!queue.isEmpty()) {
      val cur = queue.poll()

      for (i in graph[cur]) {
         if (!visited[i]) {
            visited[i] = true
            nodes[i].parent = cur
            nodes[cur].children++
            queue += i
         }
      }
   }

   val bw = System.out.bufferedWriter()

   var parent = 1
   var flag = true
   for (i in 1 until n) {
      val cur = order[i]
      if (parent != nodes[cur].parent) {
         flag = false
         break
      }

      if (nodes[cur].children > 0) queue += cur
      nodes[parent].children--
      if (nodes[parent].children == 0 && i < n - 1) parent = queue.poll()
   }

   if(!flag) bw.append("0")
   else bw.append("1")

   bw.flush()
   bw.close()
   close()


}