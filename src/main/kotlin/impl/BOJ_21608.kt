package impl

import java.util.StringTokenizer

/**
 * 문제 이름(난이도) : 상어 초등학교(GOL5)
 * 시간 : 1 ms
 * 메모리 : 1 KB
 * 링크 : https://www.acmicpc.net/problem/21608
 */
private var n = 0
private val students = ArrayList<Pair<Int, MutableList<Int>>>()
private lateinit var classRoom: Array<IntArray>
fun main() = with(System.`in`.bufferedReader()) {
    n = readLine()!!.toInt()
    classRoom = Array(n) { IntArray(n) }
    val total = n * n

    // 그래프 생성
    repeat(total) { i ->
        val st = StringTokenizer(readLine())
        val cur = st.nextToken().toInt()
        students += (cur to mutableListOf())
        repeat(4) { j ->
            students[i].second.add(st.nextToken().toInt())
        }
    }

    for (student in students) {
        // 1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
        var flagWithPos = findSeat(student.second)
        var flag = flagWithPos.first
        var pos = flagWithPos.second

        if (!flag) {
            // 2. 1을 만족하는 칸이 여러 개이면, 인접한 칸 중에서 비어있는 칸이 가장 많은 칸으로 자리를 정한다.
            flagWithPos = findSeat(student.second, true)
            flag = flagWithPos.first
            pos = flagWithPos.second

            if(!flag) {
                // 3. 2를 만족하는 칸도 여러 개인 경우에는 행의 번호가 가장 작은 칸으로, 그러한 칸도 여러 개이면 열의 번호가 가장 작은 칸으로 자리를 정한다.

            } else {
                classRoom[pos[0]][pos[1]] = student.first
            }
        } else {
            classRoom[pos[0]][pos[1]] = student.first
        }
    }

    for (i in 0 until n) {
        for (j in 0 until n) {
            print("${classRoom[i][j]} ")
        }
        println()
    }
}

fun findSeat(favoriteList: MutableList<Int>, isFindEmptySeat: Boolean = false): Pair<Boolean, IntArray> {
    var (y, x) = (0 to 0)
    var max = -1
    var maxCnt = 0

    for (i in 0 until n) {
        for (j in 0 until n) {
            if (classRoom[i][j] != 0) continue
            val cnt = findAroundStudent(j, i, favoriteList, isFindEmptySeat)
            if (cnt > max) {
                max = cnt
                maxCnt = 1
                y = i
                x = j
            } else if (cnt == max) maxCnt++
        }
    }

    if (maxCnt == 1) return (true to intArrayOf(y, x))

    return (false to intArrayOf(0, 0))
}

fun findAroundStudent(x: Int, y: Int, favoriteList: MutableList<Int>, isFindEmptySeat: Boolean = false): Int {
    val dx = intArrayOf(1, 0, -1, 0)
    val dy = intArrayOf(0, 1, 0, -1)
    var cnt = 0

    for (i in 0..3) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if (nx in 0 until n && ny in 0 until n) {
            if(isFindEmptySeat) {
                if (classRoom[ny][nx] == 0) cnt++
            } else {
                if (classRoom[ny][nx] != 0 && classRoom[ny][nx] in favoriteList) cnt++
            }
        }
    }
    return cnt
}