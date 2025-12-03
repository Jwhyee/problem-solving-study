package graph

import java.util.*

private data class Node(var v: Int, var count: Int)

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()

    var isCycle = BooleanArray(n + 1)
    val stations = Array<ArrayList<Int>>(n + 1) { arrayListOf() }

    repeat(n) {
        val (n1, n2) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        stations[n1] += n2
        stations[n2] += n1
    }

    for (i in 1..n) {
        if (checkCycle(i, i, i, isCycle, stations)) break
        isCycle = BooleanArray(n + 1)
    }

    val result = IntArray(n + 1)
    for (i in 1..n) {
        if (!isCycle[i]) result[i] = bfs(n, i, isCycle, stations)
    }

    for (i in 1..n) print(result[i].toString() + " ")
}

private fun bfs(
    n: Int,
    node: Int,
    isCycle: BooleanArray,
    list: Array<ArrayList<Int>>
): Int {
    val q: Queue<Node> = LinkedList()
    val visited = BooleanArray(n + 1)
    q.add(Node(node, 0))
    visited[node] = true

    while (!q.isEmpty()) {
        val current = q.poll()
        if (isCycle[current.v]) return current.count

        for (i in list[current.v].indices) {
            val next: Int = list[current.v][i]
            if (!visited[next]) {
                visited[next] = true
                q.add(Node(next, current.count + 1))
            }
        }
    }
    return 0
}

private fun checkCycle(
    prev: Int,
    node: Int,
    start: Int,
    isCycle: BooleanArray,
    list: Array<ArrayList<Int>>
): Boolean {
    isCycle[node] = true

    for (i in list[node].indices) {
        val next: Int = list[node][i]

        if (!isCycle[next]) {
            if (checkCycle(node, next, start, isCycle, list)) return true
        } else if (next != prev && next == start) return true
    }
    isCycle[node] = false

    return false
}
