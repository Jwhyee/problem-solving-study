package graph.dfs

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val milk = IntArray(n + 1)
    val graph = Array(n + 1) { mutableListOf<Int>() }

    repeat(n - 1) {
        st = StringTokenizer(readLine())
        val (parent, child) = st.nextToken().toInt() to st.nextToken().toInt()
        graph[parent].add(child)
        graph[child].add(parent)
    }


    val m = readLine().toInt()
    repeat(m) {
        st = StringTokenizer(readLine())
        val query = st.nextToken().toInt()
        when(query) {
            1 -> {
                val (start, end) = st.nextToken().toInt() to st.nextToken().toInt()
                dfs(graph, milk, start, end)
            }
            2 -> {
                val room = st.nextToken().toInt()
                bw.append("${milk[room]}")
                bw.newLine()
            }
        }
    }
    bw.flush()
    bw.close()
}

private fun dfs(graph: Array<MutableList<Int>>, milk: IntArray, start: Int, end: Int) {
    val visited = BooleanArray(graph.size)
    val stack = ArrayDeque<Pair<Int, IntArray>>()
    stack.add(start to IntArray(graph.size).also {
        it[0] = start
    })
    visited[start] = true

    var i = 1
    while (stack.isNotEmpty()) {
        val (node, order) = stack.removeLast()
        for (next in graph[node]) {
            if(next == end) {
                order.forEachIndexed { index, room ->
                    if(index != 0) {
                        if(room == 0) {
                            milk[end] += (index)
                            return
                        } else {
                            milk[room] += (index)
                        }
                    }
                }
                return
            }
            if (!visited[next]) {
                visited[next] = true
                stack.add(next to order.copyOf().also {
                    it[i] = next
                })
            }
        }
        i++
    }
}