import java.util.StringTokenizer
import kotlin.math.max

private lateinit var map: Array<CharArray>
private val visited = BooleanArray(26) {false}
private const val A = 65
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, -0, -1)
private var cnt = 0

fun main() = with(System.`in`.bufferedReader()){
    val (r, c) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    map = Array(r) { CharArray(c) }
    repeat(r) { y ->
        map[y] = readLine().toCharArray()
    }
    backTracking(0, 0, 1)
    println(cnt)
}

private fun backTracking(y: Int, x: Int, depth: Int) {
    visited[map[y][x].code - A] = true
    // 말은 상하좌우 인접한 네 칸 중 한 칸으로 이동할 수 있다.
    var canMove = false
    for (i in 0 until 4) {
        val ny = y + dy[i]
        val nx = x + dx[i]
        if(ny in map.indices && nx in map[0].indices)  {
            val next = map[ny][nx].code
            if(!visited[next - A]) {
                canMove = true
                backTracking(ny, nx, depth + 1)
                visited[next - A] = false
            }
        }
    }
    if(!canMove) {
        cnt = max(cnt, depth)
    }
}