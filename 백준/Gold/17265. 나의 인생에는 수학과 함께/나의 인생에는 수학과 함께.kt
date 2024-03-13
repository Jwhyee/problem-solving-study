import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

private var n = 0
private lateinit var map: Array<IntArray>
private const val PLUS = "+"
private const val MUL = "*"
private const val MINUS = "-"

private var rMax = Int.MIN_VALUE
private var rMin = Int.MAX_VALUE

private val dx = intArrayOf(0, 1)
private val dy = intArrayOf(1, 0)

fun main() = with(System.`in`.bufferedReader()) {

    n = readLine().toInt()
    map = Array(n) { IntArray(n) }

    for (i in 0 until n) {
        val st = StringTokenizer(readLine())
        for (j in 0 until n) {
            val cur = st.nextToken()
            map[i][j] = when(cur) {
                PLUS -> -1
                MINUS -> -2
                MUL -> -3
                else -> cur.toInt()
            }
        }
    }

    dfs(0, 0, map[0][0], 0)

    println("$rMax $rMin")

}

private fun dfs(y: Int, x: Int, cur: Int, op: Int) {
    if (y == n - 1 && x == n - 1) {
        rMax = max(cur, rMax)
        rMin = min(cur, rMin)
        return
    }

    if (op == 0) {
        for (i in 0 until 2) {
            val ny = y + dy[i]
            val nx = x + dx[i]
            if (ny in 0 until n && nx in 0 until n) {
                dfs(ny, nx, cur, map[ny][nx])
            }
        }
    } else {
        for (i in 0 until 2) {
            val ny = y + dy[i]
            val nx = x + dx[i]
            if (ny in 0 until n && nx in 0 until n) {
                val next = map[ny][nx]
                when (op) {
                    -1 -> dfs(ny, nx, cur + next, 0)
                    -2 -> dfs(ny, nx, cur - next, 0)
                    -3 -> dfs(ny, nx, cur * next, 0)
                }
            }
        }
    }


}