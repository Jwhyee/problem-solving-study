import java.util.*

private data class Pos(val y: Int, val x: Int, val player: Char)
private val dx = intArrayOf(1, 0, -1, 0)
private val dy = intArrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {

    val (m, n, p) = StringTokenizer(readLine()).run { 
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }
//    val m = st.nextToken().toInt()
//    val n = st.nextToken().toInt()
//    val p = st.nextToken().toInt()

    val map = Array(m) { CharArray(n) }
    val queue: Queue<Pos> = LinkedList()
    
    repeat(m) { y ->
        val line = readLine().toCharArray()
        repeat(n) { x ->
            val cur = line[x]
            map[y][x] = when(cur) {
                'B' -> cur
                'X' -> cur
                else -> {
                    if (cur == '.') cur
                    else {
                        queue += Pos(y, x, cur)
                        cur
                    }
                }
            }
        }
    }

    val damageArr = IntArray(p)

    repeat(p) {
        val st = StringTokenizer(readLine())
        st.nextToken()
        damageArr[it] = st.nextToken().toInt()
    }

    var bossHp = readLine().toInt()

    // 각 플레이어에 대한 방문 배열
    val visited = Array(m) { Array(n) { BooleanArray(p) } }

    // 공격할 수 있는 플레이어 리스트
    val attackablePlayer = mutableListOf<Char>()
    
    while (bossHp > 0) {
        var size = queue.size
        while (size > 0) {
            val cur = queue.poll()
            size--

            if(attackablePlayer.contains(cur.player)) continue

            for (i in 0 until 4) {
                val nx = cur.x + dx[i]
                val ny = cur.y + dy[i]

                if (ny in 0 until m && nx in 0 until n) {
                    val idx = (cur.player - 97).code
                    if (!visited[ny][nx][idx] && (map[ny][nx] != 'X')) {
                        visited[ny][nx][idx] = true
                        if(map[ny][nx] == 'B') attackablePlayer += cur.player
                        else queue += Pos(ny, nx, cur.player)
                    }
                }
            }
        }
        for (player in attackablePlayer) {
            val idx = (player - 97).code
            bossHp -= damageArr[idx]
        }
    }

    println(attackablePlayer.size)

}
