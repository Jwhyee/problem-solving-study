import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val maxValues = IntArray(3)
    val minValues = IntArray(3)

    repeat(n) {
        val (x1, x2, x3) = StringTokenizer(readLine()).run {
            Triple(nextToken().toInt(), nextToken().toInt(), nextToken().toInt())
        }

        updateValues(maxValues, minValues, x1, x2, x3)
    }

    println("${maxValues.maxOrNull()} ${minValues.minOrNull()}")
}

fun updateValues(max: IntArray, min: IntArray, x1: Int, x2: Int, x3: Int) {
    val prevMax0 = max[0]
    val prevMax2 = max[2]

    max[0] = max(max[0], max[1]) + x1
    max[2] = max(max[1], max[2]) + x3
    max[1] = max(max(prevMax0, max[1]), prevMax2) + x2

    val prevMin0 = min[0]
    val prevMin2 = min[2]

    min[0] = min(min[0], min[1]) + x1
    min[2] = min(min[1], min[2]) + x3
    min[1] = min(min(prevMin0, min[1]), prevMin2) + x2
}