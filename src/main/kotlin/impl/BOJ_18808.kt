package impl

import java.util.*

private class Sticker(var shape: Array<IntArray>) {
    var rows: Int = shape.size
    var cols: Int = if (shape.isEmpty()) 0 else shape[0].size

    fun rotate() {
        if (rows == 0 || cols == 0) return

        val newShape = Array(cols) { IntArray(rows) }
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                newShape[c][rows - 1 - r] = shape[r][c]
            }
        }
        shape = newShape
        rows = shape.size
        cols = shape[0].size
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    // 세로, 가로, 스티커 개수
    val (n, m, k) = StringTokenizer(readLine()).run {
        Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
    }

    val laptop = Array(n) { IntArray(m) { 0 } }

    repeat(k) {
        val (r, c) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }
        val shape = Array(r) {
            StringTokenizer(readLine()).run {
                IntArray(c) { nextToken().toInt() }
            }
        }
        val sticker = Sticker(shape)

        for (rotate in 0..3) {
            if (sticker.rows > n || sticker.cols > m) {
                sticker.rotate()
                continue
            }

            var placed = false

            for (i in 0..(n - sticker.rows)) {
                for (j in 0..(m - sticker.cols)) {
                    if (canPlace(laptop, sticker, i, j)) {
                        place(laptop, sticker, i, j)
                        placed = true
                        break
                    }
                }
                if (placed) break
            }

            if (placed) break

            sticker.rotate()
        }
    }

    println(laptop.sumOf { it.sum() })
}

private fun canPlace(laptop: Array<IntArray>, sticker: Sticker, i: Int, j: Int): Boolean {
    for (r in 0 until sticker.rows) {
        for (c in 0 until sticker.cols) {
            if (sticker.shape[r][c] == 1 && laptop[i + r][j + c] == 1) {
                return false
            }
        }
    }

    return true
}

private fun place(laptop: Array<IntArray>, sticker: Sticker, i: Int, j: Int) {
    for (r in 0 until sticker.rows) {
        for (c in 0 until sticker.cols) {
            if (sticker.shape[r][c] == 1) {
                laptop[i + r][j + c] = 1
            }
        }
    }
}