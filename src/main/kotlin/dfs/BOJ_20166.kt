package dfs

import java.lang.StringBuilder
import java.util.StringTokenizer
import kotlin.math.max

private val dy = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
private val dx = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
private val sb = StringBuilder()
private val strMap = mutableMapOf<String, Int>()
private lateinit var graph: Array<String>
private var max = 0

/**
 * 문제 이름(난이도) : 문자열 지옥에 빠진 호석 (GOL4)
 * 시간 : 528 ms
 * 메모리 : 68756 KB
 * 링크 : https://www.acmicpc.net/problem/20166
 */
fun main() = with(System.`in`.bufferedReader()){
   val bw = System.out.bufferedWriter()
   val (n, m, k) = StringTokenizer(readLine()).run {
      Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
   }

   graph = Array(n) { "" }

   for (i in 0 until n) {
      graph[i] = readLine()
   }

   val godStr = Array(k) { "" }

   for (i in 0 until k) {
      godStr[i] = readLine()
      max = max(max, godStr[i].length)
   }

   for (i in graph.indices) {
      for (j in graph[i].indices) {
         sb.clear()
         sb.append(graph[i][j])
         dfs(0, i, j, n, m)
      }
   }

   for(i in 0 until k){
      if (strMap[godStr[i]] == null) {
         bw.append("0\n")
      } else bw.append("${strMap[godStr[i]]}\n")
   }

   bw.flush()
   bw.close()
   close()
}

private fun dfs(depth: Int, y: Int, x: Int, n: Int, m: Int) {
   val cur = sb.toString()
   strMap[cur] = strMap.getOrDefault(cur, 0) + 1

   if (cur.length == max) return

   for (i in 0 until 8) {
      val ny = (y + dy[i] + n) % n
      val nx = (x + dx[i] + m) % m
      sb.append(graph[ny][nx])
      dfs(depth + 1, ny, nx, n, m)
      sb.deleteCharAt(depth + 1)
   }

}