package impl

import java.util.StringTokenizer

const val N = 0
const val S = 1
const val DIR_CLOCKWISE = 1
const val DIR_COUNTER_CLOCKWISE = -1

fun main() = with(System.`in`.bufferedReader()) {
    val t = readLine().toInt()

    val cogwheels = Array(t) { Array(8) { 0 } }

    repeat(t) { i ->
        readLine().toCharArray().forEachIndexed { j, c ->
            cogwheels[i][j] = c.digitToInt()
        }
    }

    val k = readLine().toInt()

    val commands = Array(k) { 0 to 0 }
    var st: StringTokenizer
    repeat(k) {
        st = StringTokenizer(readLine())
        commands[it] = (st.nextToken().toInt() - 1) to st.nextToken().toInt()
    }

    commands.forEach { (index, dir) ->
        val turnCog = BooleanArray(t) { false }
        setCogTurn(index, -1, t, cogwheels, turnCog)
        setCogTurn(index, 1, t, cogwheels, turnCog)

        cogwheels[index] = turnCogwheel(cogwheels[index], dir)

        var leftIndex = index - 1
        var rightIndex = index + 1

        var leftDir = dir * -1
        left@while(leftIndex >= 0) {
            if(turnCog[leftIndex]) {
                cogwheels[leftIndex] = turnCogwheel(cogwheels[leftIndex], leftDir)
            } else {
                break@left
            }
            leftDir = leftDir * -1
            leftIndex--
        }
        var rightDir = dir * -1
        right@while(rightIndex <= t - 1) {
            if(turnCog[rightIndex]) {
                cogwheels[rightIndex] = turnCogwheel(cogwheels[rightIndex], rightDir)
            } else {
                break@right
            }
            rightDir = rightDir * -1
            rightIndex++
        }
    }

    println(cogwheels.sumOf { it[0] })
}

private tailrec fun setCogTurn(
    index: Int,
    dir: Int,
    maxSize: Int,
    cogwheels: Array<Array<Int>>,
    turnCog: BooleanArray
): BooleanArray {
    if(index + dir < 0 || index + dir >= maxSize) return turnCog

    val curCog = cogwheels[index]
    val nextCog = cogwheels[index + dir]

    turnCog[index + dir] = when (dir) {
        DIR_CLOCKWISE -> {
            curCog[2] != nextCog[6]
        }
        DIR_COUNTER_CLOCKWISE -> {
            curCog[6] != nextCog[2]
        }
        else -> false
    }

    if(!turnCog[index + dir]) return turnCog

    return setCogTurn(index + dir, dir, maxSize, cogwheels, turnCog)
}

private fun turnCogwheel(
    cogwheels: Array<Int>,
    dir: Int
): Array<Int> {
    val deque = ArrayDeque(cogwheels.toList())
    if(dir == DIR_CLOCKWISE) {
        // 시계 방향 회전
        deque.addFirst(deque.removeLast())
    } else {
        // 반시계 방향 회전
        deque.addLast(deque.removeFirst())
    }
    return deque.toTypedArray()
}