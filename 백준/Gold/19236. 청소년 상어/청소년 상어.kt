import java.util.*

// 상어 0, 0으로 이동 -> 물고기 이동
// 상어는 방향에 있는 칸으로 이동 가능 -> 여러 칸도 이동 가능
// 물고기가 있는 칸으로 이동할 경우, 그 칸의 물고기를 먹고 방향을 가지게 된다.
// 단, 이동 중에 지나가는 칸에 있는 물고기는 먹지 않음
// 물고기가 없는 칸으로 이동 불가
// 이동할 칸이 없으면, 끝

private class Fish(
    val id: Int,
    var y: Int,
    var x: Int,
    var dir: Int
) {
    /**
     * 물고기의 이동 -> 한 칸 이동 가능하며, 번호가 작은 순서
     * 이동 가능 : 물고기는 빈 칸 || 다른 물고기가 있는 칸
     * 이동 불가 : 상어가 있는 칸 || 공간의 경계를 넘는 칸
     * 다른 물고기가 있는 칸으로 이동할 경우, 서로의 위치를 바꿈
     * */
    private fun move(y: Int, x: Int) {
        this.y = y
        this.x = x
    }

    fun swap(map: Array<Array<Fish?>>, sy: Int, sx: Int) {
        var count = 0
        while (true) {
            if (count == 8) break
            if (canMove(sy, sx)) {
                val ny = y + dy[dir]
                val nx = x + dx[dir]

                val nextFish = map[ny][nx]

                map[y][x] = nextFish
                map[ny][nx] = this

                nextFish?.move(y, x)
                move(ny, nx)

                break
            } else {
                turn()
            }
            count++
        }
    }

    private fun canMove(sy: Int, sx: Int): Boolean {
        val ny = y + dy[dir]
        val nx = x + dx[dir]

        if (ny == sy && nx == sx) return false

        return ny in 0 until n && nx in 0 until n
    }

    private fun turn() {
        val nextDir = dir + 1
        dir = if (nextDir == 8) 0 else nextDir
    }

    fun copy() = Fish(id, y, x, dir)
}

private class YouthShark(
    var y: Int,
    var x: Int,
    var dir: Int,
    var score: Int
)

// ↑, ↖, ←, ↙, ↓, ↘, →, ↗
private val dy = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
private val dx = intArrayOf(0, -1, -1, -1, 0, 1, 1, 1)

private const val n = 4
private var maxScore = 0

fun main() = with(System.`in`.bufferedReader()) {
    val fishes: Array<Fish?> = Array(17) { null }
    val map: Array<Array<Fish?>> = Array(4) { y ->
        val st = StringTokenizer(readLine())
        Array(4) { x ->
            val id = st.nextToken().toInt()
            val dir = st.nextToken().toInt() - 1
            val fish = Fish(id, y, x, dir)
            fishes[id] = fish
            fish
        }
    }

    backTracking(map, fishes, YouthShark(0, 0, 0, 0))

    println(maxScore)

    close()
}

private fun backTracking(map: Array<Array<Fish?>>, fishes: Array<Fish?>, shark: YouthShark) {
    val sy = shark.y
    val sx = shark.x

    val fish = map[sy][sx]

    if (fish == null) {
        maxScore = maxOf(maxScore, shark.score)
        return
    }

    shark.dir = fish.dir
    shark.score += fish.id

    map[sy][sx] = null
    fishes[fish.id] = null

    moveFishes(map, fishes, shark)

    val dir = shark.dir

    for (d in 1 until 4) {
        val ny = sy + (dy[dir] * d)
        val nx = sx + (dx[dir] * d)

        if (ny in 0 until n && nx in 0 until n && map[ny][nx] != null) {
            val newFishes = fishCopy(fishes)
            val newMap = mapCopy(newFishes)

            backTracking(newMap, newFishes, YouthShark(ny, nx, dir, shark.score))
        } else {
            maxScore = maxOf(maxScore, shark.score)
        }
    }
}

private fun fishCopy(fishes: Array<Fish?>) = Array(17) { fishes[it]?.copy() }
private fun mapCopy(fishes: Array<Fish?>): Array<Array<Fish?>> {
    val map: Array<Array<Fish?>> = Array(n) { Array(n) { null } }
    fishes.forEach { fish ->
        if (fish != null) {
            map[fish.y][fish.x] = fish
        }
    }
    return map
}

private fun moveFishes(map: Array<Array<Fish?>>, fishes: Array<Fish?>, shark: YouthShark) {
    fishes.forEach { fish ->
        fish?.swap(map, shark.y, shark.x)
    }
}