import java.util.StringTokenizer

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
    val preferenceGraph = Array(n * n) { IntArray(5) { 0 } }
    val sortedGraph = Array(n * n) { IntArray(5) { 0 } }

    repeat(n * n) {
        val st = StringTokenizer(readLine())
        preferenceGraph[it] = IntArray(5) { st.nextToken().toInt() }
        sortedGraph[preferenceGraph[it][0] - 1] = preferenceGraph[it]
    }

    val classroom = Array(n) { IntArray(n) { 0 } }

    preferenceGraph.forEachIndexed { index, preferences ->
        val student = preferences[0]
        val (r, c) = targetPosition(n, preferences.sliceArray(1..4), classroom)

        classroom[r][c] = student
    }

    var sum = 0

    for (row in 0 until n) {
        for (col in 0 until n) {
            var point = 0
            val currentStudent = classroom[row][col]
            val preferences = sortedGraph[currentStudent - 1].sliceArray(1..4)

//            println("preferences[$currentStudent] = ${preferences.joinToString()}")

            for (dir in 0..3) {
                val ny = row + dy[dir]
                val nx = col + dx[dir]

                if(ny in 0 until n && nx in 0 until n) {
                    val target = classroom[ny][nx]
                    if (target in preferences) {
                        point++
                    }
                }
            }

            sum += when(point) {
                0 -> 0
                1 -> 1
                2 -> 10
                3 -> 100
                else -> 1000
            }
        }
    }

//    println(classroom.joinToString("\n") { it.joinToString() })
    println(sum)
}

private fun targetPosition(
    n: Int,
    preferences: IntArray,
    classroom: Array<IntArray>
): Pair<Int, Int> {
    val possibles = arrayListOf<Seat>()
    var maxFavoriteCount = 0

    for (row in 0 until n) {
        for (col in 0 until n) {
            var favoriteCount = 0
            var emptyCount = 0

            if (classroom[row][col] != 0) {
                continue
            }

            for (dir in 0..3) {
                val ny = row + dy[dir]
                val nx = col + dx[dir]

                if (ny in 0 until n && nx in 0 until n) {
                    val target = classroom[ny][nx]

                    if (target == 0) emptyCount++
                    else {
                        if (target in preferences) favoriteCount++
                    }
                }
            }

            when {
                maxFavoriteCount < favoriteCount -> {
                    maxFavoriteCount = favoriteCount
                    possibles.clear()
                    possibles.add(Seat(favoriteCount, emptyCount, row, col))
                }
                maxFavoriteCount == favoriteCount -> {
                    possibles.add(Seat(favoriteCount, emptyCount, row, col))
                }
            }
        }
    }

    // 1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
    val maxFavoriteList = possibles.filter { it.favoriteCount == maxFavoriteCount }
    val (r, c) = if (maxFavoriteList.size == 1) {
        maxFavoriteList[0].run { row to col }
    } else {
        // 2. 1을 만족하는 칸이 여러 개이면, 인접한 칸 중에서 비어있는 칸이 가장 많은 칸으로 자리를 정한다.
        val maxEmpty = maxFavoriteList.maxOf { it.emptyCount }
        val maxEmptyList = maxFavoriteList.filter { it.emptyCount == maxEmpty }
        if (maxEmptyList.size == 1) {
            maxEmptyList[0].run { row to col }
        } else {
            // 3. 2를 만족하는 칸도 여러 개인 경우에는 행의 번호가 가장 작은 칸으로 정한다.
            val minRow = maxEmptyList.minOf { it.row }
            val minRows = maxEmptyList.filter { it.row == minRow }
            if(minRows.size == 1) {
                minRows[0].run { row to col }
            } else {
                minRows.minBy { it.col }.run { row to col }
            }
        }
    }

    return r to c
}