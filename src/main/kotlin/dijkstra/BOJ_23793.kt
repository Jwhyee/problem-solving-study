package dijkstra

import java.util.PriorityQueue
import java.util.StringTokenizer

private class Node(val node: Int, val cost: Int) : Comparable<Node> {
   override fun compareTo(other: Node): Int {
      return if(this.cost > other.cost) 1 else -1
   }
}
private lateinit var graph: Array<MutableList<Node>>
private const val MAX = Int.MAX_VALUE
private lateinit var xCost: Array<Int>
private lateinit var yCost: Array<Int>
private lateinit var zCost: Array<Int>

/**
 * 문제 이름(난이도) : 두 단계 최단 경로1(GOL4)
 * 시간 : 1476 ms
 * 메모리 : 119716 KB
 * 링크 : https://www.acmicpc.net/problem/23793
 */
fun main() = with(System.`in`.bufferedReader()) {


   val (n, m) = StringTokenizer(readLine()).run {
      (nextToken().toInt() to nextToken().toInt())
   }

   graph = Array(n + 1) { mutableListOf() }
   xCost = Array(n + 1) { MAX }
   yCost = Array(n + 1) { MAX }
   zCost = Array(n + 1) { MAX }

   repeat(m) {
      val st = StringTokenizer(readLine())

      val u = st.nextToken().toInt()
      val v = st.nextToken().toInt()
      val w = st.nextToken().toInt()

      graph[u] += Node(v, w)
   }

   val (x, y, z) = StringTokenizer(readLine()).run {
      Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
   }

   dijkstra(x, xCost, false)
   dijkstra(y, zCost, false)

   val a1 = if (xCost[y] + zCost[z] >= MAX) -1
   else xCost[y] + zCost[z]

   dijkstra(x, yCost, true, y)

   val a2 = if(yCost[z] >= MAX) -1
   else yCost[z]

   println("$a1 $a2")
   close()
}

private fun dijkstra(start: Int, arr: Array<Int>, yDiff: Boolean, y: Int = 0) {
   val pq = PriorityQueue<Node>()
   pq += Node(start, 0)
   while (pq.isNotEmpty()) {
      val cur = pq.poll()
      val from = cur.node
      val cost = cur.cost

      if(cost > arr[from]) continue

      for (next in graph[from]) {

         if(yDiff && next.node == y) continue

         if (arr[next.node] > cost + next.cost) {
            arr[next.node] = cost + next.cost
            pq += Node(next.node, cost + next.cost)
         }
      }
   }
}