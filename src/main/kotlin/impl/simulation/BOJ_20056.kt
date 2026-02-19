package impl.simulation

import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

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
        val mass = st.nextToken().toInt()
        val s = st.nextToken().toInt()
        val d = st.nextToken().toInt()

        ballQueue.add(FireBall(r, c, mass, s, d))
    }

    val grid = Array(n) { Array(n) { ArrayList<FireBall>() } }

    repeat(k) {
        while (ballQueue.isNotEmpty()) {
            val movedBall = ballQueue.poll().move(n)
            grid[movedBall.y][movedBall.x].add(movedBall)
        }

        for (y in 0 until n) {
            for (x in 0 until n) {
                val balls = grid[y][x]

                if (balls.isEmpty()) continue

                if (balls.size == 1) {
                    ballQueue.add(balls.first())
                } else {
                    val nextBalls = merge(balls)
                    ballQueue.addAll(nextBalls)
                }

                balls.clear()
            }
        }
    }

    val result = ballQueue.sumOf { it.m }
    println(result)
    close()
}

private fun merge(balls: ArrayList<FireBall>): List<FireBall> {
    val y = balls.first().y
    val x = balls.first().x

    var mSum = 0
    var sSum = 0
    var isOdd = false
    var isEven = false

    for (ball in balls) {
        mSum += ball.m
        sSum += ball.s

        if (ball.d % 2 == 0) isEven = true
        else isOdd = true
    }

    val isAllSameParity = !(isOdd && isEven)
    val nextDirs = if (isAllSameParity) intArrayOf(0, 2, 4, 6) else intArrayOf(1, 3, 5, 7)

    val nextM = mSum / 5
    val nextS = sSum / balls.size

    return if (nextM == 0) emptyList() else nextDirs.map { d -> FireBall(y, x, nextM, nextS, d) }
}