package binary_search

import java.io.BufferedReader
import java.util.*

private const val N_MAX = 200010

private val list = Array(N_MAX) { Array(2) { mutableListOf<Pair<Int, Long>>() } }
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)

private fun binarySearch(idx: Int, sw: Int, s: Int): Long {
    val list = list[idx][sw]
    var l = 0
    var r = list.size - 1
    var mid: Int

    while (l <= r) {
        mid = (l + r) / 2
        if (list[mid].first <= s) l = mid + 1
        else r = mid - 1
    }

    return if (r < 0) 0 else list[r].second
}

fun main() = with(BufferedReader(System.`in`.bufferedReader())) {
    var st = StringTokenizer(readLine())
    val (n, q) = st.run {
        nextToken().toInt() to nextToken().toInt()
    }

    // 아이템 입력
    repeat(n) {
        st = StringTokenizer(readLine())
        val (x, y, w) = st.run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toLong())
        }

        // val[x][0] : x 기준 y를 저장
        // val[y][1] : y 기준 x를 저장
        list[x][0].add(Pair(y, w))
        list[y][1].add(Pair(x, w))
    }

    // 정렬 + 누적합
    for (i in 1 until N_MAX) {
        for (j in 0..1) {
            val list = list[i][j]
            list.sortBy { it.first }
            for (k in 1 until list.size) {
                val prev = list[k - 1].second
                list[k] = Pair(list[k].first, list[k].second + prev)
            }
        }
    }

    var posX = 1
    var posY = 1
    var ans = 0L

    repeat(q) {
        st = StringTokenizer(readLine())
        val d = st.nextToken().toInt()
        val v = st.nextToken().toInt()

        // 아이템 회수
        ans += when (d) {
            0 -> binarySearch(posY, 1, posX + v) - binarySearch(posY, 1, posX)
            1 -> binarySearch(posX, 0, posY + v) - binarySearch(posX, 0, posY)
            2 -> binarySearch(posY, 1, posX - 1) - binarySearch(posY, 1, posX - v - 1)
            else -> binarySearch(posX, 0, posY - 1) - binarySearch(posX, 0, posY - v - 1)
        }

        // 위치 이동
        posX += dx[d] * v
        posY += dy[d] * v
    }

    print(ans)
}