package impl.simulation

import java.util.*

private val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
private val dx = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

private data class FireBall(
    val y: Int, val x: Int,
    val m: Int, val s: Int, val d: Int
) {
    fun move(n: Int): FireBall {
        val ny = normalization(y + (dy[d] * s), n)
        val nx = normalization(x + (dx[d] * s), n)

        return FireBall(ny, nx, m, s, d)
    }

    private fun normalization(pos: Int, n: Int) = when {
        pos < 0 -> ((pos % n) + n) % n
        pos >= n -> pos % n
        else -> pos
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (n, m, k) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val ballQueue: Queue<FireBall> = LinkedList()

    repeat(m) {
        val st = StringTokenizer(readLine())

        val r = st.nextToken().toInt() - 1
        val c = st.nextToken().toInt() - 1
        val m = st.nextToken().toInt()
        val s = st.nextToken().toInt()
        val d = st.nextToken().toInt()

        ballQueue.add(FireBall(r, c, m, s, d))
    }

    repeat(k) {
        val map = mutableMapOf<Pair<Int, Int>, ArrayList<FireBall>>()

        while (ballQueue.isNotEmpty()) {
            val movedBall = ballQueue.poll().move(n)

            map.computeIfAbsent(movedBall.y to movedBall.x) { arrayListOf() }.add(movedBall)
        }

        for (key in map.keys) {
            val balls = map[key]!!

            if (balls.size == 1) {
                ballQueue.add(balls.first())
            } else {
                val nextBalls = merge(balls)
                ballQueue.addAll(nextBalls)
            }
        }
    }

    val result = if (ballQueue.isNotEmpty()) {
        ballQueue.sumOf { it.m }
    } else {
        0
    }

    println(result)

    close()
}

private fun merge(balls: ArrayList<FireBall>): List<FireBall> {
    var y = 0
    var x = 0

    var m = 0
    var s = 0
    var isOdd = false
    var isEven = false
    var flag = true

    balls.forEachIndexed { index, ball ->
        y = ball.y
        x = ball.x

        m += ball.m
        s += ball.s

        if (index == 0) {
            if (ball.d % 2 == 0) {
                isEven = true
            } else isOdd = true
        } else {
            if (flag) {
                if (isOdd) {
                    if (ball.d % 2 != 1) {
                        flag = false
                    }
                } else if(isEven) {
                    if (ball.d % 2 != 0) {
                        flag = false
                    }
                }
            }
        }
    }

    val nextDirs = if(flag) intArrayOf(0, 2, 4, 6) else intArrayOf(1, 3, 5, 7)
    val nextM = m / 5
    val nextS = s / balls.size

    return if(nextM == 0) listOf() else nextDirs.map { d -> FireBall(y, x, nextM, nextS, d) }
}