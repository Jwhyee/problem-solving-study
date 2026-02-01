package impl

import java.util.StringTokenizer

private val SHAPES = mapOf("CIRCLE" to 0, "SQUARE" to 1, "TRIANGLE" to 2)
private val COLORS = mapOf("YELLOW" to 0, "RED" to 1, "BLUE" to 2)
private val BACKGROUNDS = mapOf("GRAY" to 0, "WHITE" to 1, "BLACK" to 2)

private data class Shape(val shape: Int, val color: Int, val background: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val cards = Array(9) {
        val st = StringTokenizer(readLine())
        Shape(
            shape = SHAPES[st.nextToken()]!!,
            color = COLORS[st.nextToken()]!!,
            background = BACKGROUNDS[st.nextToken()]!!
        )
    }

    val answerTable = Array(9) { Array(9) { BooleanArray(9) } }
    val totalHapCount = precomputeAnswers(cards, answerTable)

    val visited = Array(9) { Array(9) { BooleanArray(9) } }
    var foundHapCount = 0
    var isGyeolSuccess = false
    var totalScore = 0

    val n = readLine().toInt()

    repeat(n) {
        val st = StringTokenizer(readLine())
        val command = st.nextToken()

        when (command) {
            "H" -> {
                val (i1, i2, i3) = sortedIndices(
                    st.nextToken().toInt() - 1,
                    st.nextToken().toInt() - 1,
                    st.nextToken().toInt() - 1
                )

                val scoreChange = when {
                    visited[i1][i2][i3] -> -1
                    answerTable[i1][i2][i3] -> {
                        visited[i1][i2][i3] = true
                        foundHapCount++
                        1
                    }
                    else -> -1
                }
                totalScore += scoreChange
            }
            "G" -> {
                if (foundHapCount == totalHapCount && !isGyeolSuccess) {
                    isGyeolSuccess = true
                    totalScore += 3
                } else {
                    totalScore -= 1
                }
            }
        }
    }

    println(totalScore)
    close()
}

/**
 * 3개의 인덱스를 오름차순으로 정렬하여 반환 (직접 비교 방식 유지 -> 성능 최적화)
 */
private fun sortedIndices(a: Int, b: Int, c: Int): Triple<Int, Int, Int> {
    var min = a
    var mid = b
    var max = c

    if (min > mid) min = mid.also { mid = min }
    if (mid > max) mid = max.also { max = mid }
    if (min > mid) min = mid.also { mid = min }

    return Triple(min, mid, max)
}

/**
 * 모든 가능한 조합을 확인하여 정답 테이블을 채우고, 총 합의 개수를 반환
 */
private fun precomputeAnswers(
    cards: Array<Shape>,
    answerTable: Array<Array<BooleanArray>>
): Int {
    var count = 0
    for (i in 0 until 9) {
        for (j in i + 1 until 9) {
            for (k in j + 1 until 9) {
                if (isHap(cards[i], cards[j], cards[k])) {
                    answerTable[i][j][k] = true
                    count++
                }
            }
        }
    }
    return count
}

/**
 * 합 : 그림의 세 가지 속성이 모두 같거나 모두 다른 세 장의 그림 조합을 의미한다.
 */
private fun isHap(c1: Shape, c2: Shape, c3: Shape): Boolean {
    return isAttributeMatch(c1.shape, c2.shape, c3.shape) &&
            isAttributeMatch(c1.color, c2.color, c3.color) &&
            isAttributeMatch(c1.background, c2.background, c3.background)
}

/**
 * 단일 속성에 대한 합 조건 검사
 */
private fun isAttributeMatch(a: Int, b: Int, c: Int): Boolean {
    return (a == b && b == c) || (a != b && b != c && a != c)
}