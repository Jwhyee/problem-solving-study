package impl

import java.util.StringTokenizer

const val N = 0
const val S = 1
const val DIR_CLOCKWISE = 1
const val DIR_COUNTER_CLOCKWISE = -1

fun main() = with(System.`in`.bufferedReader()) {
    val t = readLine().toInt()

    val cogwheels = Array(t) { IntArray(8) }

    repeat(t) { i ->
        readLine().forEachIndexed { j, c ->
            cogwheels[i][j] = c.digitToInt()
        }
    }

    val k = readLine().toInt()
    val commands = Array(k) { 0 to 0 }

    repeat(k) {
        val st = StringTokenizer(readLine())
        commands[it] = (st.nextToken().toInt() - 1) to st.nextToken().toInt()
    }

    val turnCog = BooleanArray(t)

    for ((index, dir) in commands) {
        // 회전 여부 초기화
        turnCog.fill(false)

        // 양쪽 회전 설정
        setCogTurnIteratively(index, -1, t, cogwheels, turnCog)
        setCogTurnIteratively(index, 1, t, cogwheels, turnCog)

        // 중심 회전
        turnCogwheelInPlace(cogwheels[index], dir)

        // 왼쪽 회전 처리
        var leftIndex = index - 1
        var leftDir = dir * -1
        while (leftIndex >= 0 && turnCog[leftIndex]) {
            turnCogwheelInPlace(cogwheels[leftIndex], leftDir)
            leftDir *= -1
            leftIndex--
        }

        // 오른쪽 회전 처리
        var rightIndex = index + 1
        var rightDir = dir * -1
        while (rightIndex < t && turnCog[rightIndex]) {
            turnCogwheelInPlace(cogwheels[rightIndex], rightDir)
            rightDir *= -1
            rightIndex++
        }
    }

    // 정답 출력
    println(cogwheels.sumOf { it[0] })
}

private fun setCogTurnIteratively(
    index: Int,
    dir: Int,
    maxSize: Int,
    cogwheels: Array<IntArray>,
    turnCog: BooleanArray
) {
    var current = index
    while (true) {
        val next = current + dir
        if (next < 0 || next >= maxSize) break

        val curCog = cogwheels[current]
        val nextCog = cogwheels[next]

        val shouldTurn = if (dir == DIR_CLOCKWISE) {
            curCog[2] != nextCog[6]
        } else {
            curCog[6] != nextCog[2]
        }

        if (!shouldTurn) break
        turnCog[next] = true
        current = next
    }
}

private fun turnCogwheelInPlace(cogwheel: IntArray, dir: Int) {
    if (dir == DIR_CLOCKWISE) {
        val last = cogwheel[7]
        for (i in 7 downTo 1) {
            cogwheel[i] = cogwheel[i - 1]
        }
        cogwheel[0] = last
    } else {
        val first = cogwheel[0]
        for (i in 0 until 7) {
            cogwheel[i] = cogwheel[i + 1]
        }
        cogwheel[7] = first
    }
}