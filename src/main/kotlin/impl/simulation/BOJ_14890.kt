package impl.simulation

import java.util.StringTokenizer
import kotlin.math.abs

fun main() = with(System.`in`.bufferedReader()) {
    val (n, l) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val map = Array(n) {
        val st = StringTokenizer(readLine())
        IntArray(n) { st.nextToken().toInt() }
    }

    var count = 0

    for (row in 0 until n) {
        if (canPass(map[row], n, l)) count++
    }

    for (col in 0 until n) {
        val columnArray = IntArray(n) { row -> map[row][col] }
        if (canPass(columnArray, n, l)) count++
    }

    println(count)
}

private fun canPass(road: IntArray, n: Int, l: Int): Boolean {
    val visited = BooleanArray(n)

    for (i in 0 until n - 1) {
        val current = road[i]
        val next = road[i + 1]
        val diff = current - next

        if (diff == 0) continue

        if (abs(diff) > 1) return false

        if (diff == 1) {
            for (j in 1..l) {
                if (i + j >= n || road[i + j] != next || visited[i + j]) return false
                visited[i + j] = true
            }
        } else {
            for (j in 0 until l) {
                if (i - j < 0 || road[i - j] != current || visited[i - j]) return false
                visited[i - j] = true
            }
        }
    }
    return true
}