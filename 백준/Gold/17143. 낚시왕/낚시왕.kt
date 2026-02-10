import java.util.PriorityQueue
import java.util.StringTokenizer
import kotlin.math.abs

private data class Shark(
    var y: Int,
    var x: Int,
    val s: Int,
    var d: Int,
    var z : Int
) : Comparable<Shark> {
    override fun compareTo(other: Shark) = y - other.y

    private fun nextDirection() {
        d = when(d) {
            0 -> 1
            1 -> 0
            2 -> 3
            3 -> 2
            else -> throw IllegalArgumentException()
        }
    }

    private fun move2(max: Int, curPos: Int, dir: IntArray, remainDistance: Int): Int {
        val nextPos = curPos + (dir[d] * remainDistance)

        return when {
            nextPos < 0 -> {
                // 이동할 수 있는 만큼 이동하고 남은 거리
                nextDirection()
                val remain = remainDistance - curPos
                move2(max, 0, dir, abs(remain))
            }
            nextPos > max -> {
                nextDirection()
                // 이동 가능한 거리
                val remain = remainDistance - (max - curPos)
                move2(max, max, dir, abs(remain))
            }
            else -> {
                nextPos
            }
        }
    }

    fun move(r: Int, c: Int): Shark {
        val ny = move2(r - 1, y, dy, s)
        val nx = move2(c - 1, x, dx, s)

        y = ny
        x = nx

        return this
    }

    override fun toString() = "Shark(y=$y,x=$x,s=$s,d=$d,z=$z)"
}

private val dy = intArrayOf(-1, 1, 0, 0)
private val dx = intArrayOf(0, 0, 1, -1)

fun main() = with(System.`in`.bufferedReader()) {
    val (r, c, m) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    var sharks = Array(c) { PriorityQueue<Shark>() }

    repeat(m) {
        val st = StringTokenizer(readLine())

        // (r, c)는 상어의 위치
        val r = st.nextToken().toInt() - 1
        val c = st.nextToken().toInt() - 1

        // s는 속력, d는 이동 방향, z는 크기
        val s = st.nextToken().toInt()
        val d = st.nextToken().toInt() - 1
        val z = st.nextToken().toInt()

        sharks[c].add(Shark(r, c, s, d, z))
    }

    var answer = 0

    for (kingPosition in 0 until c) {
        val caught = fishing(sharks[kingPosition])
        answer += caught?.z ?: 0
        sharks = moveSharks(sharks, r, c)
    }

    println(answer)

    close()
}

private fun moveSharks(
    sharks: Array<PriorityQueue<Shark>>,
    r: Int, c: Int
): Array<PriorityQueue<Shark>> {
    val positioned = Array(r) { Array<Shark?>(c) { null } }
    val newPositions = Array(c) { PriorityQueue<Shark>() }

    val prevSharks = sharks.map { it.toList() }.flatten()
    val nextSharks = ArrayList<Shark>()

    for (shark in prevSharks) {
        shark.move(r, c).also {
            if (positioned[it.y][it.x] != null) {
                val targetShark = positioned[it.y][it.x]!!
                if (it.z > targetShark.z) {
                    nextSharks.add(it)
                    nextSharks.remove(targetShark)
                    positioned[it.y][it.x] = it
                }
            } else {
                nextSharks.add(it)
                positioned[it.y][it.x] = it
            }
        }
    }

    for (shark in nextSharks) {
        newPositions[shark.x].add(shark)
    }

    return newPositions
}

private fun fishing(sharkQueue: PriorityQueue<Shark>) = if (sharkQueue.isNotEmpty()) {
    sharkQueue.poll()
} else null