import java.util.PriorityQueue
import java.util.StringTokenizer

private class Node(val node: Int, val cost: Long) : Comparable<Node> {
   override fun compareTo(other: Node): Int {
      return if(this.cost > other.cost) 1 else -1
   }
}
private lateinit var graph: Array<MutableList<Node>>
private const val MAX:Long = 100_000L * 300_000L + 1L
private lateinit var xCost: Array<Long>
private lateinit var yCost: Array<Long>
private lateinit var zCost: Array<Long>
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
      val w = st.nextToken().toLong()

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

private fun dijkstra(start: Int, arr: Array<Long>, yDiff: Boolean, y: Int = 0) {
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