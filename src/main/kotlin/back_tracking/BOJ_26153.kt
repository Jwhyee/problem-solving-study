package back_tracking

import java.util.StringTokenizer

private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)
private var max = 0

fun main() = with(System.`in`.bufferedReader()){
    var st = StringTokenizer(readLine())
    val (n, m) = st.nextToken().toInt() to st.nextToken().toInt()

    val map = Array(n) { IntArray(m) }
    val visited = Array(n) { BooleanArray(m) }

    for (i in 0 until n) {
        st = StringTokenizer(readLine())
        for (j in 0 until m) {
            map[i][j] = st.nextToken().toInt()
        }
    }

    st = StringTokenizer(readLine())
    val (x, y, p) = Triple(st.nextToken().toInt(), st.nextToken().toInt(), st.nextToken().toInt())

//    땅 한 칸에는 일자 파이프나 구부러진 파이프 중 하나만 설치할 수 있다.
//    일자 파이프는 하나당 재료를 1개 소비하고 구부러진 파이프는 2개 소비한다.
//    설치한 모든 파이프는 하나로 연결되어 있어야 하며, 파이프 한쪽 끝은 반드시 자신의 땅이어야 한다.
//    자신의 땅에는 파이프를 설치하지 않는다.
    backTracking(x, y, p, map, visited)
    println(max)
}

private fun backTracking(x: Int, y: Int, pipe: Int, map: Array<IntArray>, visited: Array<BooleanArray>, sum: Int = 0, dir: Int = 0) {
    println("map[$x][$y] = ${map[x][y]} / pipe = $pipe / sum = $sum / dir = $dir / max = $max")
    println(visited.joinToString("\n") {
        it.joinToString("\t")
    })
    if(pipe == 0) {
        max = maxOf(max, sum + map[x][y])
        return
    }

    visited[x][y] = true

    for (i in 0 until 4) {
        val nx = x + dx[i]
        val ny = y + dy[i]

        if(nx < 0 || nx >= map.size || ny < 0 || ny >= map[0].size) continue
        if(visited[nx][ny]) continue

        val curPipe = (if(ny == y || nx == x) pipe - 1 else pipe - 2) - if(dir != i) 1 else 0
        println("curPipe = $curPipe / dir = $dir")
        if(curPipe < 0) {
            continue
        }
        visited[nx][ny] = true
        backTracking(nx, ny, curPipe, map, visited, sum + map[nx][ny], i)
        println("back")
        visited[nx][ny] = false
    }
}