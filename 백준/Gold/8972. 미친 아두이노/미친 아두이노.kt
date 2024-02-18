import java.util.*
import kotlin.collections.LinkedHashSet
import kotlin.math.abs

private lateinit var jsRobot: Robot
private lateinit var madRobots: Queue<Robot>
private lateinit var map: Array<CharArray>
private val dy = intArrayOf(1, 1, 1, 0, 0, 0, -1, -1, -1)
private val dx = intArrayOf(-1, 0, 1, -1, 0, 1, -1, 0, 1)
private class Robot(var y: Int, var x: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    map = Array(n) { CharArray(m) }
    madRobots = LinkedList()

    repeat(n) { y ->
        val line = readLine().toCharArray()
        repeat(m) { x ->
            val cur = line[x]
            if (cur == 'R') {
                madRobots += Robot(y, x)
            } else if (cur == 'I') {
                jsRobot = Robot(y, x)
            }
            map[y][x] = cur
            map[y][x] = cur
        }
    }

    val cmd = readLine().map { it.digitToInt() - 1 }

    var idx = 0
    var kraj = 0
    for (dir in cmd) {
        // 먼저, 종수가 아두이노를 8가지 방향(수직,수평,대각선)으로 이동시키거나, 그 위치에 그대로 놔둔다.
        if (dir != 4) {
            // 종수의 아두이노가 미친 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되며, 종수는 게임을 지게 된다.
            if (!moveJsRobot(dir)) {
                kraj = idx + 1
                break
            }
        }

        // 미친 아두이노는 8가지 방향 중에서 종수의 아두이노와 가장 가까워 지는 방향으로 한 칸 이동한다.
        if (!moveCrazyRobot(n, m)) {
            kraj = idx + 1
            break
        }

        idx++
    }

    if (kraj != 0) {
        println("kraj $kraj")
    } else {
        for (chars in map) {
            for (c in chars) {
                print(c)
            }
            println()
        }
    }

    bw.flush()
    bw.close()
    close()
}

private fun moveJsRobot(dir: Int): Boolean {
    val ny = jsRobot.y + dy[dir]
    val nx = jsRobot.x + dx[dir]

    if (map[ny][nx] == 'R') return false

    jsRobot.y = ny
    jsRobot.x = nx

    return true
}

private fun moveCrazyRobot(n: Int, m: Int): Boolean {
    val tempMap = Array(n) { IntArray(m) }

    val length = madRobots.size
    val jsY = jsRobot.y
    val jsX = jsRobot.x

    for (i in 0 until length) {
        val cur = madRobots.poll()
        val y = cur.y
        val x = cur.x

        var minDiff = Int.MAX_VALUE
        var minX = 0
        var minY = 0

        // 즉, 종수의 위치를 (r1,s1), 미친 아두이노의 위치를 (r2, s2)라고 했을 때, |r1-r2| + |s1-s2|가 가장 작아지는 방향으로 이동한다.
        for (dir in 0 until 9) {
            if(dir == 4) continue

            val ny = y + dy[dir]
            val nx = x + dx[dir]

            val diff = abs(jsRobot.y - ny) + abs(jsRobot.x - nx)
            if (minDiff > diff) {
                minDiff = diff
                minY = ny
                minX = nx
            }
        }

        // 미친 아두이노가 종수의 아두이노가 있는 칸으로 이동한 경우에는 게임이 끝나게 되고, 종수는 게임을 지게 된다.
        if (minY == jsRobot.y && minX == jsRobot.x) return false

        tempMap[minY][minX] += 1

    }

    // 원래 위치에 있는 종수의 아두이노를 빈 칸으로 만든다.
    map[jsY][jsX] = '.'

    // 2개 또는 그 이상의 미친 아두이노가 같은 칸에 있는 경우에는 큰 폭발이 일어나고, 그 칸에 있는 아두이노는 모두 파괴된다.
    for (y in 0 until n) {
        for (x in 0 until m) {
            if(tempMap[y][x] == 1) {
                // 현재 위치에 미친 아두이노를 두고, 미친 아두이노 큐에 추가한다.
                madRobots += Robot(y, x)
                map[y][x] = 'R'
            } else {
                map[y][x] = '.'
            }

        }
    }

    // 현재 종수의 아두이노 위치를 최신화 해준다.
    map[jsRobot.y][jsRobot.x] = 'I'

    return true
}