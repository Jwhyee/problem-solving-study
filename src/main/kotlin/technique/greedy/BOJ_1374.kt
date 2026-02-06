package technique.greedy

import java.util.PriorityQueue
import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 강의실(GOL5)
 * 시간 : 1016 ms
 * 메모리 : 77988 KB
 * 링크 : https://www.acmicpc.net/problem/1374
 */
fun main() = with(System.`in`.bufferedReader()) {

   val n = readLine().toInt()

   val queue = PriorityQueue<Int>()
   val list = mutableListOf<Pair<Int, Int>>()

   repeat(n) {
      val (num, startTime, endTime) = StringTokenizer(readLine()).run {
         Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
      }
      list += (startTime to endTime)
   }

   list.sortedWith(compareBy({ it.first }, { it.second })).forEach { range ->
      if (queue.isNotEmpty()) {
         val cur = queue.peek()
         if (cur <= range.first) {
            queue.poll()
         }
      }
      queue += range.second
   }

   println(queue.size)
   close()
}