private data class Point(var x: Int, var y: Int, var dir: Dir = Dir.DOWN) {
    fun turnRight() {
        dir = when(dir) {
            Dir.UP -> Dir.RIGHT
            Dir.RIGHT -> Dir.DOWN
            Dir.DOWN -> Dir.LEFT
            Dir.LEFT -> Dir.UP
        }
    }
    fun turnLeft() {
        dir = when(dir) {
            Dir.UP -> Dir.LEFT
            Dir.LEFT -> Dir.DOWN
            Dir.DOWN -> Dir.RIGHT
            Dir.RIGHT -> Dir.UP
        }
    }
    fun turnBack() {
        dir = when(dir) {
            Dir.UP -> Dir.DOWN
            Dir.DOWN -> Dir.UP
            Dir.LEFT -> Dir.RIGHT
            Dir.RIGHT -> Dir.LEFT
        }
    }

    fun nextPoint() = when(dir) {
        Dir.UP -> y - 1 to x
        Dir.DOWN -> y + 1 to x
        Dir.LEFT -> y to x - 1
        Dir.RIGHT -> y to x + 1
    }
}
private enum class Dir {
    UP, DOWN, LEFT, RIGHT;
}
private lateinit var map: Array<Array<String>>
private lateinit var lightMap: Array<Array<Boolean>>
private val dy = intArrayOf(1, -1, 0, 0, 1, -1, 1, -1)
private val dx = intArrayOf(0, 0, 1, -1, 1, 1, -1, -1)
private val zombieList = arrayListOf<Point>()
private val ari = Point(0, 0)

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val n = readLine().toInt()

    map = Array(n) { Array(n) { "" } }
    lightMap = Array(n) { Array(n) { false } }

    val command = readLine().toCharArray().map { "$it" }

    for (i in 0 until n) {
        val line = readLine().toCharArray().map { "$it" }
        for (j in 0 until n) {
            val cur = line[j]
            when(cur) {
                "Z" -> {
                    zombieList += Point(j, i)
                }
            }
            map[i][j] = cur
        }
    }

    if(action(command)) {
        bw.write("Phew...")
    } else {
        bw.write("Aaaaaah!")
    }

    bw.flush()
    bw.close()
}

private fun action(command: List<String>): Boolean {
    command.forEach { c ->
        when(c) {
            "F" -> {
                if(!move(ari, false)) {
                    return false
                }
            }
            "R" -> {
                ari.turnRight()
            }
            "L" -> {
                ari.turnLeft()
            }
        }

        zombieList.forEach { zombie ->
            if(!move(zombie, true)) {
                return false
            }
        }
    }
    return true
}

private fun isRange(y: Int, x: Int) = y in map.indices && x in map[0].indices

private fun move(point: Point, isZombie: Boolean): Boolean {
    val (ny, nx) = point.nextPoint()
    if(isRange(ny, nx)) {
        val next = map[ny][nx]
        if(next == "S") {
            turnOnTheLight(ny, nx)
        }
        if(isZombie) {
            if(ny == ari.y && nx == ari.x && !lightMap[ny][nx]) {
                return false
            }
            map[point.y][point.x] = "O"
            map[ny][nx] = "Z"
        } else {
            when(next) {
                "Z" -> {
                    if(!lightMap[ny][nx]) {
                        return false
                    }
                }
            }
        }
        point.y = ny
        point.x = nx
    } else {
        if(isZombie) {
            point.turnBack()
        }
    }
    return true
}

private fun turnOnTheLight(y: Int, x: Int) {
    lightMap[y][x] = true
    for (i in 0 until 8) {
        val ny = y + dy[i]
        val nx = x + dx[i]
        if(isRange(ny, nx)) {
            lightMap[ny][nx] = true
        }
    }
}