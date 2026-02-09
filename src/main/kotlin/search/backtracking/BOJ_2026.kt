package search.backtracking

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import java.util.ArrayList

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val st1 = StringTokenizer(readLine())
    val k = st1.nextToken().toInt()
    val n = st1.nextToken().toInt()
    val f = st1.nextToken().toInt()

    val areFriends = Array(n + 1) { BooleanArray(n + 1) }
    val graph = Array(n + 1) { ArrayList<Int>() }

    repeat(f) {
        val st2 = StringTokenizer(readLine())
        val a = st2.nextToken().toInt()
        val b = st2.nextToken().toInt()

        areFriends[a][b] = true
        areFriends[b][a] = true
        graph[a].add(b)
        graph[b].add(a)
    }

    for (i in 1..n) {
        graph[i].sort()
    }

    val selected = ArrayList<Int>()

    for (i in 1..n) {
        if (graph[i].size < k - 1) continue

        selected.add(i)
        backTracking(graph, selected, areFriends, n, k, i)
        if (isCompleted) break
        selected.removeAt(selected.size - 1)
    }

    if (!isCompleted) {
        print(-1)
    }
}

var isCompleted = false

fun backTracking(
    graph: Array<ArrayList<Int>>,
    selected: ArrayList<Int>,
    areFriends: Array<BooleanArray>,
    n: Int,
    k: Int,
    cur: Int,
) {
    if (isCompleted) return

    if (selected.size == k) {
        isCompleted = true
        val sb = StringBuilder()
        for (node in selected) {
            sb.append(node).append('\n')
        }
        print(sb)
        return
    }

    for (i in cur + 1..n) {
        if (graph[i].size < k - 1) continue

        var isPossible = true
        for (existing in selected) {
            if (!areFriends[i][existing]) {
                isPossible = false
                break
            }
        }

        if (isPossible) {
            selected.add(i)
            backTracking(graph, selected, areFriends, n, k, i)
            if (isCompleted) return
            selected.removeAt(selected.size - 1)
        }
    }
}