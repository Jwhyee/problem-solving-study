package graph.dijkstra

import java.util.StringTokenizer

private data class Pos(val conn: Int, val dist: Int)

fun main() = with(System.`in`.bufferedReader()) {
   val t = readLine().toInt()

   repeat(t) { tc ->
      val (n, m) = StringTokenizer(readLine()).run {
         nextToken().toInt() to nextToken().toInt()
      }

      val arr = Array<MutableList<Pos>>(n + 1) { mutableListOf() }

      repeat(m) {
         val (cur, conn, dist) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
         }
         arr[cur] += Pos(conn, dist)
         arr[conn] += Pos(cur, dist)
      }

      val k = readLine().toInt()
      val friendPos = IntArray(n + 1)
      val st = StringTokenizer(readLine())

      while (st.hasMoreTokens()) {
         val r = st.nextToken().toInt()
         friendPos[r] = 1
      }

      println(friendPos.joinToString(","))
      println(arr.joinToString(","))



   }
}

/*
1
6 7
1 2 4
1 3 1
1 5 2
2 3 2
3 4 3
4 5 2
6 5 1
2
3 5
*/