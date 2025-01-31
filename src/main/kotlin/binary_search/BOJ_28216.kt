package binary_search

import java.util.StringTokenizer

private data class Item(val target: Int, var w: Int)

private data class Car(var x: Int, var y: Int) {
    fun move(dir: Int, moveCnt: Int) {
        x += dx[dir] * moveCnt
        y += dy[dir] * moveCnt
    }
}

private fun lowerBound(list: List<Item>, target: Int): Int {
    var left = 0
    var right = list.size

    while (left < right) {
        val mid = (left + right) / 2

        if (list[mid].target < target) {
            left = mid + 1
        } else {
            right = mid
        }
    }

    return left
}

private fun upperBound(list: List<Item>, target: Int): Int {
    var left = 0
    var right = list.size

    while (left < right) {
        val mid = (left + right) / 2

        if (list[mid].target <= target) {
            left = mid + 1
        } else {
            right = mid
        }
    }

    return left
}

private fun getResult(items: List<Item>, min: Int, max: Int): Int {
    val startIdx = lowerBound(items, min)
    val endIdx = upperBound(items, max)

    return if(startIdx != 0) {
        items[endIdx - 1].w - items[startIdx - 1].w
    } else {
        items[endIdx - 1].w
    }
}

private val dx = arrayOf(1, 0, -1, 0)
private val dy = arrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, q) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val xMap = mutableMapOf<Int, ArrayList<Item>>()
    val yMap = mutableMapOf<Int, ArrayList<Item>>()

    repeat(n) {
        val (x, y, w) = StringTokenizer(readLine()).run {
            Triple(
                nextToken().toInt(),
                nextToken().toInt(),
                nextToken().toInt()
            )
        }
        xMap[x] = xMap.getOrDefault(x, arrayListOf()).apply { add(Item(y, w)) }
        yMap[y] = yMap.getOrDefault(y, arrayListOf()).apply { add(Item(x, w)) }
    }

    xMap.forEach { (_, value) ->
        value.sortBy { it.target }
        value.fold(0) { acc, item ->
            item.w += acc
            item.w
        }
    }
    yMap.forEach { (_, value) ->
        value.sortBy { it.target }
        value.fold(0) { acc, item ->
            item.w += acc
            item.w
        }
    }

    val car = Car(1, 1)
    var result = 0

    repeat(q) {
        val (dir, moveCnt) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        val startX = car.x
        val endX = car.x + dx[dir] * moveCnt
        val startY = car.y
        val endY = car.y + dy[dir] * moveCnt

        val minX = minOf(startX, endX)
        val maxX = maxOf(startX, endX)
        val minY = minOf(startY, endY)
        val maxY = maxOf(startY, endY)

        println("===========$car[$dir]===========")
        println("minX = $minX, maxX = $maxX, minY = $minY, maxY = $maxY")

        when (dir) {
            0 -> {
                yMap[car.y]?.let { items ->
                    val r = getResult(items, if(minX == car.x) minX + 1 else minX, maxX)
                    println("r = $r")
                    result += r
                }
            }
            2 -> {
                yMap[car.y]?.let { items ->
                    val r = getResult(items, minX, if(maxX == car.x) maxX - 1 else maxX)
                    println("r = $r")
                    result += r
                }
            }
            1 -> {
                xMap[car.x]?.let { items ->
                    val r = getResult(items, if(minY == car.y) minY + 1 else minY, maxY)
                    println("r = $r")
                    result += r
                }
            }
            3 -> {
                xMap[car.x]?.let { items ->
                    val r = getResult(items, minY, if(maxY == car.y) maxY - 1 else maxY)
                    println("r = $r")
                    result += r
                }
            }
        }

        car.move(dir, moveCnt)
    }

    println(result)
}