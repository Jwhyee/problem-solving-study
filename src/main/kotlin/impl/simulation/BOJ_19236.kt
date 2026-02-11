package impl.simulation

import java.util.StringTokenizer
import kotlin.math.max

private data class Fish(val id: Int, var y: Int, var x: Int, var dir: Int) {
    fun move(map: Array<Array<Fish?>>, sy: Int, sx: Int) {
        repeat(8) {
            val ny = y + dy[dir]
            val nx = x + dx[dir]

            if (ny in 0 until 4 && nx in 0 until 4 && !(ny == sy && nx == sx)) {
                map[ny][nx]?.let { target ->
                    target.y = y
                    target.x = x
                }

                map[y][x] = map[ny][nx]
                map[ny][nx] = this

                this.y = ny
                this.x = nx
                return
            }

            dir = (dir + 1) % 8
        }
    }
}

private data class YouthShark(val y: Int, val x: Int, val dir: Int, val score: Int)

private val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
private val dx = intArrayOf(0, -1, -1, -1, 0, 1, 1, 1)

private var maxScore = 0

fun main() = with(System.`in`.bufferedReader()) {
    val map = Array(4) { Array<Fish?>(4) { null } }
    val fishes = Array<Fish?>(17) { null }

    for (i in 0 until 4) {
        val st = StringTokenizer(readLine())
        for (j in 0 until 4) {
            val id = st.nextToken().toInt()
            val dir = st.nextToken().toInt() - 1
            val fish = Fish(id, i, j, dir)
            map[i][j] = fish
            fishes[id] = fish
        }
    }

    val firstFish = map[0][0]!!
    val startScore = firstFish.id
    val startDir = firstFish.dir

    map[0][0] = null
    fishes[firstFish.id] = null

    dfs(map, fishes, YouthShark(0, 0, startDir, startScore))
    println(maxScore)
}

private fun dfs(map: Array<Array<Fish?>>, fishes: Array<Fish?>, shark: YouthShark) {
    maxScore = max(maxScore, shark.score)

    fishes.forEach { fish ->
        fish?.move(map, shark.y, shark.x)
    }

    for (dist in 1..3) {
        val ny = shark.y + dy[shark.dir] * dist
        val nx = shark.x + dx[shark.dir] * dist

        if (ny !in 0 until 4 || nx !in 0 until 4) continue

        val targetFish = map[ny][nx] ?: continue

        val nextFishes = Array(17) { fishes[it]?.copy() }
        val nextMap = Array(4) { Array<Fish?>(4) { null } }

        nextFishes.forEach { fish ->
            if (fish != null) nextMap[fish.y][fish.x] = fish
        }

        nextMap[ny][nx] = null
        nextFishes[targetFish.id] = null

        dfs(nextMap, nextFishes, YouthShark(ny, nx, targetFish.dir, shark.score + targetFish.id))
    }
}