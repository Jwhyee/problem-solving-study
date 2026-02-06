package search.binarysearch

import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

/**
 * 문제 이름(난이도) : 구간 나누기 2 (GOL4)
 * 시간 : 188 ms
 * 메모리 : 21936 KB
 * 링크 : https://www.acmicpc.net/problem/13397
 */
fun main() = with(System.`in`.bufferedReader()) {
   val (n, m) = StringTokenizer(readLine()).run {
      nextToken().toInt() to nextToken().toInt()
   }

   var right = 0
   val arr = readLine().split(" ").map {
      val num = it.toInt()
      right = max(num, right)
      num
   }.toTypedArray()

   var left = 0

   while (left < right) {
      val mid = (left + right) / 2

      var cnt = 1
      var min = Int.MAX_VALUE
      var max = -Int.MAX_VALUE

      var i = -1
      while (i++ < n - 1) {
         min = min(min, arr[i])
         max = max(max, arr[i])
         if (max - min > mid) {
            cnt++
            max = -Int.MAX_VALUE
            min = Int.MAX_VALUE
            i--
         }
      }

      if (cnt <= m) {
         right = mid
      } else {
         left = mid + 1
      }
   }

   println(right)
}