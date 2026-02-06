package graph.bfs

import java.util.*

private lateinit var graph: Array<MutableList<Int>>

private var n: Int = 0
private var s: Int = 0
private var p: Int = 0

/**
 * 문제 이름(난이도) : 얼음깨기 펭귄(GOL5)
 * 시간 : 692 ms
 * 메모리 : 157392 KB
 * 링크 : https://www.acmicpc.net/problem/21738
 */
fun main() = with(System.`in`.bufferedReader()) {

   var st = StringTokenizer(readLine())

   n = st.nextToken().toInt()
   s = st.nextToken().toInt()
   p = st.nextToken().toInt()

   graph = Array(n + 1) { mutableListOf() }

   for (i in 1 until n) {
      st = StringTokenizer(readLine())
      val p = st.nextToken().toInt()
      val c = st.nextToken().toInt()

      graph[p].add(c)
      graph[c].add(p)
   }

   println(bfs())
}

private fun bfs(): Int {
   val queue: Queue<Int> = LinkedList()
   val visited = BooleanArray(n + 1)
   queue += p
   visited[p] = true

   var answer = n - 1

   var supportBlockCnt = 0
   var distance = -1

   // 지지대는 2개 이상 있어야 무너지지 않음
   while (queue.isNotEmpty() && supportBlockCnt < 2) {
      distance++
      val size = queue.size

      for (i in 0 until size) {
         // 현재 위치
         val cur = queue.poll()

         // 지지대는 맨 끝에 있고, 가장 낮은 수를 갖고 있다.
         // 때문에 지지대가 될 수 있는 조건은 현재 위치의 번호보다 작아야 하며, 최소 2개가 남아야 한다.
         // 현재 위치가 지지대일 경우 부시지 않고 다음 칸으로 이동한다.
         if (cur <= s && supportBlockCnt < 2) {
            answer -= distance
            supportBlockCnt++
            continue
         }

         for (child in graph[cur]) {
            if (!visited[child]) {
               queue += child
               visited[child] = true
            }
         }
      }
   }
   return answer
}