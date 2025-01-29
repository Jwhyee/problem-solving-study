package binary_search

import java.util.StringTokenizer

private data class Item(
    var x: Int,
    var y: Int,
    var w: Int
)
private data class Car(var x: Int, var y: Int) {
    fun move(dir: Int, moveCnt: Int) {
        x += dx[dir] * moveCnt
        y += dy[dir] * moveCnt
    }
}
private val dx = arrayOf(1, 0, -1, 0)
private val dy = arrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()){
    // 상자의 개수 : N / 이동 횟수 : Q
    val (n, q) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val itemList = Array(n) { Item(0, 0, 0) }

    repeat(n) {
        StringTokenizer(readLine()).run {
            itemList[it].x = nextToken().toInt()
            itemList[it].y = nextToken().toInt()
            itemList[it].w = nextToken().toInt()
        }
    }

    val car = Car(1, 1)

    var result = 0

    repeat(q) {
        val (dir, moveCnt) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        val carRangeY = car.y.let {
            val target = (it + (dy[dir] * moveCnt))
            if(it > target) target..it
            else it..target
        }

        val carRangeX = car.x.let {
            val target = (it + (dx[dir] * moveCnt))
            if(it > target) target..it
            else it..target
        }

        for (item in itemList) {
            val (x, y, w) = item
            if(x in carRangeX && y in carRangeY) {
                if(!(car.x == x && car.y == y)) {
                    result += w
                }
            }
        }
        car.move(dir, moveCnt)
    }

    println(result)
}