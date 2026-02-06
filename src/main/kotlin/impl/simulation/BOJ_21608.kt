package impl.simulation

import java.util.StringTokenizer
import kotlin.math.pow

private val dy = intArrayOf(0, 1, 0, -1)
private val dx = intArrayOf(1, 0, -1, 0)

private data class Seat(
    val favoriteCount: Int,
    val emptyCount: Int,
    val row: Int,
    val col: Int
)

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val preferenceGraph = IntArray(n * n) { 0 }
    val sortedGraph = Array(n * n) { BooleanArray(n * n + 1) { false } }

    repeat(n * n) {
        val st = StringTokenizer(readLine())

        val target = st.nextToken().toInt()
        val index = target - 1
        val s1 = st.nextToken().toInt()
        val s2 = st.nextToken().toInt()
        val s3 = st.nextToken().toInt()
        val s4 = st.nextToken().toInt()

        preferenceGraph[it] = target

        sortedGraph[index][s1] = true
        sortedGraph[index][s2] = true
        sortedGraph[index][s3] = true
        sortedGraph[index][s4] = true
    }

    val classroom = Array(n) { IntArray(n) { 0 } }

    for (student in preferenceGraph) {
        val preferences = sortedGraph[student - 1]
        targetPosition(n, student, preferences, classroom)
    }

    val sum = getSatisfy(n, sortedGraph, classroom)

    println(sum)
}

private fun getSatisfy(
    n: Int,
    sortedGraph: Array<BooleanArray>,
    classroom: Array<IntArray>
): Int {
    var sum = 0

    for (row in 0 until n) {
        for (col in 0 until n) {
            val currentStudent = classroom[row][col]
            var point = 0

            for (dir in 0..3) {
                val ny = row + dy[dir]
                val nx = col + dx[dir]

                if (ny in 0 until n && nx in 0 until n) {
                    if (sortedGraph[currentStudent - 1][classroom[ny][nx]]) {
                        point++
                    }
                }
            }

            if (point > 0) {
                // 10의 (count-1)승 계산. 1->1, 2->10, 3->100, 4->1000
                sum += 10.0.pow((point - 1).toDouble()).toInt()
            }
        }
    }

    return sum
}

private fun targetPosition(
    n: Int,
    student: Int,
    preferences: BooleanArray,
    classroom: Array<IntArray>
) {
    var bestR = -1
    var bestC = -1
    var maxEmptyCount = -1
    var maxFavoriteCount = -1

    for (row in 0 until n) {
        for (col in 0 until n) {
            if (classroom[row][col] != 0) {
                continue
            }

            var favoriteCount = 0
            var emptyCount = 0

            for (dir in 0..3) {
                val ny = row + dy[dir]
                val nx = col + dx[dir]

                if (ny in 0 until n && nx in 0 until n) {
                    if (classroom[ny][nx] == 0) emptyCount++
                    else if (preferences[classroom[ny][nx]]) {
                        favoriteCount++
                    }
                }
            }

            if (bestR == -1 ||
                favoriteCount > maxFavoriteCount ||
                (favoriteCount == maxFavoriteCount && emptyCount > maxEmptyCount)
            ) {
                bestR = row
                bestC = col
                maxFavoriteCount = favoriteCount
                maxEmptyCount = emptyCount
            }
        }
    }

    classroom[bestR][bestC] = student
}