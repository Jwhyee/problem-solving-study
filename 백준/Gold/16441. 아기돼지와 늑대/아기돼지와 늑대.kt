import java.util.*

private lateinit var map: Array<CharArray>
private lateinit var visited: Array<BooleanArray>
private val queue: Queue<Pair<Int, Int>> = LinkedList()
private var n = 0
private var m = 0

fun main() {
    val br = System.`in`.bufferedReader()
    val bw = System.out.bufferedWriter()

    val st = StringTokenizer(br.readLine())
    n = st.nextToken().toInt()
    m = st.nextToken().toInt()

    map = Array(n) { CharArray(m) }
    visited = Array(n) { BooleanArray(m) }

    for (y in 0 until n) {
        val arr = br.readLine().toCharArray()
        for (x in 0 until m) {
            val cur = arr[x]
            map[y][x] = cur
            if(cur == 'W') queue += (y to x)
        }
    }

    bfs()

    for (y in 0 until n) {
        for (x in 0 until m) {
            val cur = map[y][x]
            val c = if(cur == '.' && !visited[y][x]) 'P' else cur
            bw.append(c)
        }
        bw.newLine()
    }

    br.close()
    bw.flush()
    bw.close()
}


private fun bfs() {
    val dx = intArrayOf(1, 0, -1, 0)
    val dy = intArrayOf(0, -1, 0, 1)
    while (queue.isNotEmpty()) {
        val cur = queue.poll()
        val y = cur.first
        val x = cur.second

        visited[y][x] = true

        for (dir in 0..3) {
            var ny = y + dy[dir]
            var nx = x + dx[dir]

            if (isValid(ny, nx) && !visited[ny][nx]) {
                val stat = map[ny][nx]
                // 다음 좌표가 빙판길일 경우
                if (stat == '+') { //
                    // 산 혹은 평원을 만날 때까지 이동
                    while (isValid(ny + dy[dir], nx + dx[dir])) {
                        if (map[ny][nx] != '+' || map[ny + dy[dir]][nx + dx[dir]] == '#') break
                        ny += dy[dir]
                        nx += dx[dir]
                    }
                }

                // 빙판길을 통해 이동한 좌표에 대해 방문 처리
                if (stat != '#' && !visited[ny][nx]) {
                    visited[ny][nx] = true
                    queue += ny to nx
                }
            }

        }
    }
}

private fun isValid(y: Int, x: Int): Boolean {
    return y in 0 until n && x in 0 until m
}