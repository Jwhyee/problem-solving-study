package impl

import java.util.*
import kotlin.math.min

fun main() = with(System.`in`.bufferedReader()){
    val (d, n) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val depths = IntArray(d)
    val originDepths = readLine().split(" ").map { it.toInt() }
    val pizzas = readLine().split(" ").map { it.toInt() }

    depths[0] = originDepths[0]

    for (i in 1 until d) {
        depths[i] = min(depths[i - 1], originDepths[i])
    }

    var min = Int.MAX_VALUE
    var count = 0
    var pizzaIndex = 0
    for (depthIndex in  d - 1 downTo 0) {
        if (pizzaIndex == n) break

        val pizza = pizzas[pizzaIndex]
        val depth = depths[depthIndex]

        if (pizza <= depth) {
            count++
            pizzaIndex++
            min = min(min, (depthIndex) + 1)
        }
    }

    println(if(count != n) 0 else min)

    close()
}