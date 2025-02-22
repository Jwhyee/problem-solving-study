import java.util.*

private val dr = intArrayOf(1, -1, 0, 0)
private val dc = intArrayOf(0, 0, 1, -1)

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val (r, c) = st.run {
        nextToken().toInt() to nextToken().toInt()
    }

    val map = Array(r) { readLine().toCharArray() }
    val vision = Array(r) { CharArray(c) { '#' } }

    st = StringTokenizer(readLine())
    val (hrInit, hcInit) = st.run {
        nextToken().toInt() - 1 to nextToken().toInt() - 1
    }
    var hr = hrInit
    var hc = hcInit

    val act = readLine().toCharArray()

    for (action in act) {
        when (action) {
            'W' -> if (vision[hr][hc] == '#') ward(hr, hc, map, vision)
            'U' -> if (hr > 0) hr--
            'D' -> if (hr < r - 1) hr++
            'L' -> if (hc > 0) hc--
            'R' -> if (hc < c - 1) hc++
        }
    }

    vision[hr][hc] = '.'

    for (i in 0 until 4) {
        val rr = hr + dr[i]
        val cc = hc + dc[i]

        if (rr in 0 until r && cc in 0 until c) {
            vision[rr][cc] = '.'
        }
    }

    println(vision.joinToString("\n") { it.concatToString() })
}

fun ward(nr: Int, nc: Int, map: Array<CharArray>, vision: Array<CharArray>) {
    val area = map[nr][nc]
    val queue: Queue<Pair<Int, Int>> = ArrayDeque()

    vision[nr][nc] = '.'
    queue.add(nr to nc)

    while (queue.isNotEmpty()) {
        val (curR, curC) = queue.poll()

        for (i in 0 until 4) {
            val rr = curR + dr[i]
            val cc = curC + dc[i]

            if (rr !in map.indices || cc !in map[rr].indices) continue
            if (vision[rr][cc] == '.' || map[rr][cc] != area) continue

            vision[rr][cc] = '.'
            queue.add(rr to cc)
        }
    }
}