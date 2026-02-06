package graph.dfs

import java.util.*

private class Udp(val m: Char, val node: Int)
private lateinit var graph: Array<MutableList<Int>>
private const val A = 'A'
private const val B = 'B'
private const val C = 'C'
private var a = 0
private var b = 0
private var c = 0

/**
 * 문제 이름(난이도) : 윤이는 엄청난 것을 훔쳐갔습니다(GOL3)
 * 시간 : 1148 ms
 * 메모리 : 106364 KB
 * 링크 : https://www.acmicpc.net/problem/27924
 */
fun main() = with(System.`in`.bufferedReader()) {
   val bw = System.out.bufferedWriter()

   val n = readLine().toInt()

   graph = Array(n + 1) { mutableListOf() }

   for (i in 0 until n - 1) {
      val st = StringTokenizer(readLine())
      val u = st.nextToken().toInt()
      val v = st.nextToken().toInt()
      // 양방향 매핑
      graph[u] += v
      graph[v] += u
   }

   val st = StringTokenizer(readLine())

   a = st.nextToken().toInt()
   b = st.nextToken().toInt()
   c = st.nextToken().toInt()

   if(graph[a].size == 1) bw.append("YES")
   else {
      if(bfs(n)) bw.append("YES")
      else bw.append("NO")
   }

   bw.flush()
   bw.close()
   close()
}

private fun bfs(n : Int): Boolean {
   val queue: Queue<Udp> = LinkedList()

   // 윤이의 위치
   val aPos = BooleanArray(n + 1)

   // 각 인원의 방문 여부 배열
   val aVisited = BooleanArray(n + 1)
   val bVisited = BooleanArray(n + 1)
   val cVisited = BooleanArray(n + 1)

   queue += Udp(A, a)
   queue += Udp(B, b)
   queue += Udp(C, c)
   // 각자의 시작 위치 저장
   aPos[a] = true

   // 윤이의 수(BFS로 풀기 때문에 여러 곳에 있을 수 있음)
   var aCnt = 1

   while (queue.isNotEmpty()) {
      val cur = queue.poll()
      val node = cur.node

      val nextList = graph[node]

      // 마을에 윤이가 없으면 종료
      if(aCnt == 0) return false

      when (cur.m) {
         // 윤이일 경우
         A -> {
            // 현재 위치를 다시 못가도록 방문 처리
            aVisited[node] = true

            // 만약 달구나 포닉스가 윤이를 잡았을 경우 윤이의 수를 줄이고 다음 루프
            if(!aPos[node]) {
               aCnt--
               continue
            }
            // 윤이가 리프 노드에 도달하면 즉시 탈출
            if (nextList.size == 1) return true

            // 탈출하지 못하면 다음 노드로 이동
            for (next in nextList) {
               if (!aVisited[next]) {
                  aCnt++
                  aPos[next] = true
                  queue += Udp(A, next)
               }
            }
            // 현재 위치에서 다음 좌표로 움직임
            aPos[node] = false
            aCnt--
         }
         // 달구나 포닉스일 경우
         else -> {
            // 현재 칸에 윤이가 있으면 잡기 (윤이가 큐에 남아있을 수도 있음)
            when(cur.m) {
               B -> bVisited[node] = true
               C -> cVisited[node] = true
            }
            // 현재 위치에 윤이가 있을 경우 윤이를 잡기(false 처리)
            if(aPos[node]) aPos[node] = false
            for (next in nextList) {
               if(aPos[next]) aPos[next] = false
               // 달구나 포닉스가 다음 좌표를 방문하지 않았을 경우에만 추가
               when(cur.m) {
                  B -> if(!bVisited[next]) queue += Udp(B, next)
                  C -> if(!cVisited[next]) queue += Udp(C, next)
               }
            }
         }
      }

   }
   return false
}