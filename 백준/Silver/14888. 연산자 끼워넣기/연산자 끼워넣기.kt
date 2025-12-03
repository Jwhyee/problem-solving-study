import java.util.StringTokenizer
import kotlin.math.max
import kotlin.math.min

private enum class Operator(
    val f: (Int, Int) -> Int
) {
    PLUS(Int::plus), MINUS(Int::minus), MULTIPLY(Int::times), DIVIDE(Int::div)
}

private var min = Int.MAX_VALUE
private var max = Int.MIN_VALUE

fun main() = with(System.`in`.bufferedReader()){
    val n = readLine().toInt()

    val numbers = readLine().split(" ").map { it.toInt() }

    val st = StringTokenizer(readLine())

    val operators = arrayOf(*Operator.entries.map {
        it to st.nextToken().toInt()
    }.toTypedArray())
    
    backTracking(numbers, operators, numbers[0], 1)

    println(max)
    println(min)

    close()
}

private fun backTracking(
    numbers: List<Int>,
    operators: Array<Pair<Operator, Int>>,
    result: Int,
    index: Int
) {
    if (index == numbers.size) {
        min = min(min, result)
        max = max(max, result)
        return
    }

    repeat(4) { i ->
        val (operator, count) = operators[i]

        if (count > 0) {
            val nextOperators = operators.clone()

            nextOperators[i] = operator to count - 1
            
            backTracking(numbers, nextOperators, operator.f(result, numbers[index]), index + 1)
        }
    }
}