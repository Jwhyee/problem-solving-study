package trie

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val targetList = ArrayList<String>()
    repeat(n) { targetList += readLine() }
    targetList.sort()

    var count = 0
    repeat(m) {
        val q = readLine()
        val idx = targetList.binarySearch(q).let { if (it < 0) -it - 1 else it }

        if (idx < targetList.size && targetList[idx].startsWith(q)) {
            count++
        }
    }
    println(count)
}