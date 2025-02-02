package binary_search

import java.util.*

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

    if (startIdx >= items.size || endIdx == 0) return 0
    if (endIdx > items.size) return items.last().w - if (startIdx > 0) items[startIdx - 1].w else 0

    return if (startIdx == 0) {
        items[endIdx - 1].w
    } else {
        items[endIdx - 1].w - items[startIdx - 1].w
    }
}

private val dx = arrayOf(1, 0, -1, 0)
private val dy = arrayOf(0, 1, 0, -1)

fun main() = with(System.`in`.bufferedReader()) {
    val (n, q) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val xMap = mutableMapOf<Int, MutableList<Item>>()
    val yMap = mutableMapOf<Int, MutableList<Item>>()

    repeat(n) {
        val (x, y, w) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }
        xMap.computeIfAbsent(x) { mutableListOf() }.add(Item(y, w))
        yMap.computeIfAbsent(y) { mutableListOf() }.add(Item(x, w))
    }

    // 누적 합 계산 (아이템을 빠르게 합산할 수 있도록)
    xMap.forEach { (_, value) ->
        value.sortBy { it.target }
        for (i in 1 until value.size) {
            value[i].w += value[i - 1].w
        }
    }
    yMap.forEach { (_, value) ->
        value.sortBy { it.target }
        for (i in 1 until value.size) {
            value[i].w += value[i - 1].w
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

        val r = when (dir) {
            0 -> yMap[car.y]?.let { items ->
                getResult(items, if (minX == car.x) minX + 1 else minX, maxX)
            } ?: 0
            1 -> xMap[car.x]?.let { items ->
                getResult(items, if (minY == car.y) minY + 1 else minY, maxY)
            } ?: 0
            2 -> yMap[car.y]?.let { items ->
                getResult(items, minX, if (maxX == car.x) maxX - 1 else maxX)
            } ?: 0
            3 -> xMap[car.x]?.let { items ->
                getResult(items, minY, if (maxY == car.y) maxY - 1 else maxY)
            } ?: 0
            else -> 0
        }
        result += r

        car.move(dir, moveCnt)
    }

    println(result)
}