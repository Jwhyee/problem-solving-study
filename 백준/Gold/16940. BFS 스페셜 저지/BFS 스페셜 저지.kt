import java.util.*

class Node (var parent: Int, var children: Int)

fun main() = with(System.`in`.bufferedReader()) {
   val n = readLine().toInt()
   val arr = Array<ArrayList<Int>>(n+1) { ArrayList() }

   repeat(n-1) {
      val (n1, n2) = StringTokenizer(readLine()).run {
         nextToken().toInt() to nextToken().toInt()
      }

      arr[n1].add(n2)
      arr[n2].add(n1)
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

      for (i in arr[cur]) {
         if (!visited[i]) {
            visited[i] = true
            nodes[i].parent = cur
            nodes[cur].children++
            queue += i
         }
      }
   }

   var parent = 1
   for (i in 1 until n) {
      val cur = order[i]
      if (parent != nodes[cur].parent) {
         println(0)
         return
      }

      if (nodes[cur].children > 0) queue += cur
      nodes[parent].children--
      if (nodes[parent].children == 0 && i < n - 1) parent = queue.poll()
   }

   println(1)
}