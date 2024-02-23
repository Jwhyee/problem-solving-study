package greedy

import java.lang.StringBuilder

private var n = 0
private var result = ""
private var origin = ""
private var answer = -1
private lateinit var data: Array<Boolean>
private lateinit var visited: Array<Boolean>

fun main() = with(System.`in`.bufferedReader()) {
   n = readLine().toInt()

   origin = readLine()
   result = readLine()

   data = Array(n) {
      if (origin[it] == '0') false
      else true
   }



   visited = Array(n) { false }

   backTracking(0, 1)

   println(answer)

}

private fun backTracking(depth: Int, cnt: Int) {
   if (depth == n - 1) {
      println("cnt = $cnt")
      println("${data.contentDeepToString()}")
      val sb = StringBuilder()
      for (b in data) {
         sb.append(if (b) "1" else "0")
      }
      if(sb.toString() == result) answer = cnt
//      if(data == result) answer = cnt
      println("${sb}")
      return
   }

   for (i in depth until n) {
      if (!visited[depth]) {
         visited[depth] = true
         if (i - 1 < 0) {
            data[i] = !data[i]
            data[i + 1] = !data[i + 1]
         } else if (i + 1 == n) {
            data[i - 1] = !data[i - 1]
            data[i] = !data[i]
         } else {
            data[i - 1] = !data[i - 1]
            data[i] = !data[i]
            data[i + 1] = !data[i + 1]
         }
         backTracking(depth + 1, cnt + 1)
//         if (i - 1 < 0) {
//            data[i] = !data[i]
//            data[i + 1] = !data[i + 1]
//         } else if (i + 1 == n) {
//            data[i - 1] = !data[i - 1]
//            data[i] = !data[i]
//         } else {
//            data[i - 1] = !data[i - 1]
//            data[i] = !data[i]
//            data[i + 1] = !data[i + 1]
//         }
         visited[depth] = false
      }
   }
}