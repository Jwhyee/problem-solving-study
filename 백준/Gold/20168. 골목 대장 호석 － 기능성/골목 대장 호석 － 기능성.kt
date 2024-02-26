import java.util.*
import kotlin.math.max
import kotlin.math.min

private var N = 0
private var M = 0
private var A = 0
private var B = 0
private var C = 0
private lateinit var graph: Array<MutableList<Street>>

private data class Street(val node: Int, val cost: Int, var max: Int = Int.MIN_VALUE)

fun main() = with(System.`in`.bufferedReader()) {
   var st = StringTokenizer(readLine())
   N = st.nextToken().toInt()
   M = st.nextToken().toInt()
   A = st.nextToken().toInt()
   B = st.nextToken().toInt()
   C = st.nextToken().toInt()

   graph = Array(N + 1) { mutableListOf() }

   repeat(M) {
      st = StringTokenizer(readLine())
      val a = st.nextToken().toInt()
      val b = st.nextToken().toInt()
      val c = st.nextToken().toInt()

      graph[a] += Street(b, c)
      graph[b] += Street(a, c)
   }

   var answer = Int.MAX_VALUE

   // 수치심이 같은 루트가 있을 경우를 대비해 mutableList
   val list = mutableListOf<Street>()

   // 제일 빠른 루트 찾기
   // 시작 지점에서 최소한의 수치심을 받는 경로 구하기
   for (street in graph[A]) {
      val result = dijkstra(street.node, B, C, street.cost, false)
      if (result != -1) {
         list += street
      }
   }

   // 수치심이 같은 루트가 있을 경우 최대 수금액이 적은 경로 탐색
   for (street in list) {
      val temp = dijkstra(street.node, B, C, street.cost, true)
      answer = min(answer, temp)
   }

   println(if(answer == Int.MAX_VALUE) - 1 else answer)
}

private fun dijkstra(start: Int, end: Int, money: Int, cost: Int, flag: Boolean): Int {
   val visited = BooleanArray(N + 1)
   visited[A] = true
   val queue: Queue<Street> = LinkedList()
   queue += Street(start, cost, cost)
   var cnt = 0

   while (queue.isNotEmpty()) {
      val cur = queue.poll()
      val curNode = cur.node

      cnt++

      if (curNode == end) {
         if(flag) return cur.max
         return cnt
      }

      val curCost = cur.cost

      visited[curNode] = true

      for (street in graph[curNode]) {
         val nextNode = street.node
         val nextCost = street.cost

         if (visited[nextNode])  continue

         val max = max(curCost, nextCost)

         if (flag) {
            queue += Street(nextNode, nextCost, max)
         } else {
            val prefix = nextCost + curCost
            if (prefix <= money) {
               queue += Street(nextNode, prefix, max)
            }
         }
      }

   }

   return -1
}