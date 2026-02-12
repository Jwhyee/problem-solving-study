import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

private data class AdultSharkSmell(val sharkId: Int, var timeLeft: Int)

private data class AdultShark(
    val id: Int,
    var y: Int,
    var x: Int,
    var dir: Int,
    val priorities: Array<IntArray>
)

private val dy = intArrayOf(-1, 1, 0, 0)
private val dx = intArrayOf(0, 0, -1, 1)

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    var st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()
    val k = st.nextToken().toInt()

    val smellMap = Array(n) { Array<AdultSharkSmell?>(n) { null } }
    val sharks = Array<AdultShark?>(m + 1) { null }

    for (i in 0 until n) {
        st = StringTokenizer(readLine())
        for (j in 0 until n) {
            val cell = st.nextToken().toInt()
            if (cell != 0) {
                sharks[cell] = AdultShark(cell, i, j, 0, emptyArray())
                smellMap[i][j] = AdultSharkSmell(cell, k)
            }
        }
    }

    st = StringTokenizer(readLine())
    for (i in 1..m) {
        sharks[i]?.dir = st.nextToken().toInt() - 1
    }

    for (i in 1..m) {
        val priority = Array(4) { IntArray(4) }
        for (d in 0 until 4) {
            st = StringTokenizer(readLine())
            for (p in 0 until 4) {
                priority[d][p] = st.nextToken().toInt() - 1
            }
        }
        sharks[i]?.let {
            sharks[i] = AdultShark(it.id, it.y, it.x, it.dir, priority)
        }
    }

    var time = 0
    var remainSharks = m

    while (time < 1000) {
        time++

        val newMap = Array(n) { IntArray(n) { 0 } }

        for (i in 1..m) {
            val shark = sharks[i] ?: continue

            var found = false
            var nextY = 0
            var nextX = 0
            var nextDir = 0

            for (pd in shark.priorities[shark.dir]) {
                val ny = shark.y + dy[pd]
                val nx = shark.x + dx[pd]
                if (ny in 0 until n && nx in 0 until n) {
                    if (smellMap[ny][nx] == null) {
                        nextY = ny; nextX = nx; nextDir = pd
                        found = true
                        break
                    }
                }
            }

            if (!found) {
                for (pd in shark.priorities[shark.dir]) {
                    val ny = shark.y + dy[pd]
                    val nx = shark.x + dx[pd]
                    if (ny in 0 until n && nx in 0 until n) {
                        if (smellMap[ny][nx]?.sharkId == i) {
                            nextY = ny; nextX = nx; nextDir = pd
                            found = true
                            break
                        }
                    }
                }
            }

            if (found) {
                if (newMap[nextY][nextX] != 0) {
                    sharks[i] = null
                    remainSharks--
                } else {
                    newMap[nextY][nextX] = i
                    shark.y = nextY
                    shark.x = nextX
                    shark.dir = nextDir
                }
            }
        }

        for (i in 0 until n) {
            for (j in 0 until n) {
                smellMap[i][j]?.let {
                    it.timeLeft--
                    if (it.timeLeft == 0) {
                        smellMap[i][j] = null
                    }
                }
            }
        }

        for (i in 1..m) {
            val shark = sharks[i] ?: continue
            smellMap[shark.y][shark.x] = AdultSharkSmell(i, k)
        }

        if (remainSharks == 1) {
            println(time)
            return
        }
    }

    println(-1)
}