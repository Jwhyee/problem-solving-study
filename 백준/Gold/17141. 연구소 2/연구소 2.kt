import java.util.LinkedList
import java.util.Queue

fun getIntList() = br.readLine().trim().split(' ').map { it.toInt() }
private lateinit var map: Array<IntArray>
private val br = System.`in`.bufferedReader()
private val virusList = ArrayList<Pair<Int, Int>>()
private var answer = Int.MAX_VALUE
private val dir = arrayOf(
    arrayOf(0, 1),
    arrayOf(1, 0),
    arrayOf(0, -1),
    arrayOf(-1, 0),
)

fun main() = with(System.out.bufferedWriter()) {
    val (n, m) = getIntList()
    map = Array(n) { y ->
        val list = getIntList()
        for (x in list.indices) {
            if (list[x] == 2) {
                virusList.add(Pair(y, x))
            }
        }
        list.toIntArray()
    }
    combination(IntArray(m), 0, 0, n, m)

    write("${if (answer == Int.MAX_VALUE) -1 else answer}")
    close()
}

fun combination(viruses: IntArray, idx: Int, cnt: Int, n: Int, m: Int) {

    if (cnt == m) {
        answer = answer.coerceAtMost(bfs(viruses, n, m))
        return
    }

    for (i in idx until virusList.size) {
        viruses[cnt] = i
        combination(viruses, i + 1, cnt + 1, n, m)
    }
}

fun bfs(viruses: IntArray, n: Int, m: Int): Int {
    val queue: Queue<Pair<Int, Int>> = LinkedList()
    var time = 0
    val tempGraph = Array(n) { y ->
        IntArray(n) { x ->
            map[y][x]
        }
    }
    for (i in viruses) {
        val (y, x) = virusList[i]
        queue.add(virusList[i])
        tempGraph[y][x] = 1
    }
    if (isFinish(tempGraph, n)) return time

    while (queue.isNotEmpty()) {
        val size = queue.size
        time++
        for (i in 0 until size) {
            val (cy, cx) = queue.poll()

            for (j in 0 until 4) {
                val ny = cy + dir[j][0]
                val nx = cx + dir[j][1]

                if (ny !in 0 until n || nx !in 0 until n) continue
                if (tempGraph[ny][nx] == 1) continue
                tempGraph[ny][nx] = 1
                queue.add(Pair(ny, nx))
            }
        }
        if (isFinish(tempGraph, n)) return time
    }
    return Int.MAX_VALUE
}

fun isFinish(tmp: Array<IntArray>, n: Int): Boolean {
    var wallCnt = 0
    for (r in 0 until n) {
        for (c in 0 until n) {
            if (tmp[r][c] == 1) wallCnt++
        }
    }
    return wallCnt == n * n
}