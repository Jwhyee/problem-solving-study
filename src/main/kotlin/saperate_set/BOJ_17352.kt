package saperate_set

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    if (n == 2) {
        println("1 2")
    } else {
        val graph = Array(n + 1) { mutableListOf<Int>() }


        for (i in 0 until n - 2) {
            val st = StringTokenizer(readLine())
            val p = st.nextToken().toInt()
            val c = st.nextToken().toInt()

            graph[p] += c
            graph[c] += p
        }
        for (i in 1 until n) {
            val visited = BooleanArray(n + 1)
            visited[i] = true
            val list = graph[i]
            val size = list.size

            for (node in list) {
                visited[node] = true
            }

            var notConnected = 0

            for (j in 1 until n + 1) {
                if(!visited[j])  {
                    notConnected = j
                }
            }
            if (notConnected != 0) {
                println("$i $notConnected")
                break
            }
        }
    }
}