package impl

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    val N = st.nextToken().toInt()

    data class Point(val x: Int, val y: Int)
    val positions = Array(N) { Point(-1, -1) }
    repeat(N) { i ->
        st = StringTokenizer(readLine())
        val x = st.nextToken().toInt()
        val y = st.nextToken().toInt()
        positions[i] = Point(x, y)
    }

    val segCount = (N - 1) / 2

    val arrX1 = IntArray(segCount)
    val arrX2 = IntArray(segCount)
    val segY  = IntArray(segCount)

    val h = BooleanArray(segCount) { false }

    val dSecond = IntArray(segCount) { 0 }

    var idx = 0
    for (i in 0 until N - 1) {
        val (x1, y1) = positions[i]
        val (x2, y2) = positions[i + 1]
        if (y1 == y2) {
            arrX1[idx] = minOf(x1, x2)
            arrX2[idx] = maxOf(x1, x2)
            segY[idx]  = y1
            idx++
        }
    }

    st = StringTokenizer(readLine())
    val K = st.nextToken().toInt()

    repeat(K) {
        st = StringTokenizer(readLine())
        val hx1 = st.nextToken().toInt()
        val hy  = st.nextToken().toInt()
        val hx2 = st.nextToken().toInt()

        for (i in 0 until segCount) {
            if (arrX1[i] == hx1 && arrX2[i] == hx2 && segY[i] == hy) {
                h[i] = true
                break
            }
        }
    }

    var water = 0
    for (i in segCount - 1 downTo 0) {
        if (h[i]) {
            water = segY[i]
        } else {
            if (water > segY[i]) {
                water = segY[i]
            }
        }
        dSecond[i] = water
    }

    water = 0
    for (i in 0 until segCount) {
        if (h[i]) {
            water = segY[i]
        } else {
            if (water > segY[i]) {
                water = segY[i]
            }
        }
        if (dSecond[i] > water) {
            water = dSecond[i]
        }
        dSecond[i] = water
    }

    var answer = 0L
    for (i in 0 until segCount) {
        val width  = arrX2[i] - arrX1[i]
        val height = segY[i] - dSecond[i]
        if (height > 0) {
            answer += width.toLong() * height.toLong()
        }
    }

    println(answer)
}