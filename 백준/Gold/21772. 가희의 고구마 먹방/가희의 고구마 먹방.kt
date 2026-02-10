import java.util.StringTokenizer
import kotlin.math.max

private val dx = intArrayOf(1, -1, 0, 0, 0)
private val dy = intArrayOf(0, 0, 0, 1, -1)
private var r = 0
private var c = 0
private var t = 0
private var max = Int.MIN_VALUE
private lateinit var map: Array<CharArray>

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    r = st.nextToken().toInt()
    c = st.nextToken().toInt()
    t = st.nextToken().toInt()

    map = Array(r) { CharArray(c) }

    var gy = 0
    var gx = 0

    for (y in 0 until r) {
        val arr = readLine().toCharArray()
        for (x in 0 until c) {
            val cur = arr[x]
            map[y][x] = cur
            if (cur == 'G') {
                gy = y
                gx = x
            }
        }
    }

    backTracking(0, 0, gy, gx)

    println(max)
    close()
}

private fun backTracking(depth: Int, cnt: Int, y: Int, x: Int) {
    if (depth == t) {
        max = max(max, cnt)
        return
    }
    
    for (i in 0 until 5) {
        val ny = y + dy[i]
        val nx = x + dx[i]

        if (ny in 0 until r && nx in 0 until c && map[ny][nx] != '#') {
            val next = map[ny][nx]
            if (next == 'S') {
                map[ny][nx] = '.'
                backTracking(depth + 1, cnt + 1, ny, nx)
                map[ny][nx] = 'S'
            } else {
                backTracking(depth + 1, cnt, ny, nx)   
            }
        }
    }
}