import java.util.StringTokenizer

private const val HAP = "H"
private const val GYEOL = "G"

private class Shape(
    val shape: Int,
    val color: Int,
    val background: Int
)

fun main() = with(System.`in`.bufferedReader()){
    val shapes = Array(9) {
        val st = StringTokenizer(readLine())
        val shape = when(st.nextToken()) {
            "CIRCLE" -> 0
            "SQUARE" -> 1
            "TRIANGLE" -> 2
            else -> -1
        }
        val color = when(st.nextToken()) {
            "YELLOW" -> 0
            "RED" -> 1
            "BLUE" -> 2
            else -> -1
        }
        val background = when(st.nextToken()) {
            "GRAY" -> 0
            "WHITE" -> 1
            "BLACK" -> 2
            else -> -1
        }
        Shape(shape, color, background)
    }

    val answers = Array(9) { Array(9) { BooleanArray(9) { false } } }
    var answerCount = 0
    val answer = findCombination(shapes, answers)

    val memory = Array(9) { Array(9) { BooleanArray(9) { false } } }

    val n = readLine().toInt()

    var result = 0
    var isGyeolSuccess = false

    repeat(n) {
        val st = StringTokenizer(readLine())

        val part = st.nextToken()

        when(part) {
            HAP -> {
                val i1 = st.nextToken().toInt() - 1
                val i2 = st.nextToken().toInt() - 1
                val i3 = st.nextToken().toInt() - 1

                val score = if (!memory[i1][i2][i3]) {
                    if(answers[i1][i2][i3]) {
                        answerCount++
                        memory[i1][i2][i3] = true
                        memory[i1][i3][i2] = true
                        memory[i2][i1][i3] = true
                        memory[i2][i3][i1] = true
                        memory[i3][i1][i2] = true
                        memory[i3][i2][i1] = true
                        1
                    } else -1
                } else {
                    -1
                }

                result += score
            }
            GYEOL -> {
                // 결 외치기
                // 아홉 장의 그림으로 조합 가능한 합들 중 외치지 않은 합이 더이상 없다고 생각될 경우
                // 실제로 합이 더 존재하지 않으면 3점, 그렇지 않으면 -1점
                result += if(answerCount == answer && !isGyeolSuccess) {
                    isGyeolSuccess = true
                    3
                } else -1
            }
        }
    }

    println(result)

    close()
}

/**
 * 합 : 그림의 세 가지 속성이 모두 같거나 모두 다른 세 장의 그림 조합
 * */
private fun isHap(s1: Shape, s2: Shape, s3: Shape): Boolean {
    val checkShape = (s1.shape == s2.shape && s2.shape == s3.shape) ||
            (s1.shape != s2.shape && s1.shape != s3.shape && s2.shape != s3.shape)

    val checkColor = (s1.color == s2.color && s2.color == s3.color) ||
            (s1.color != s2.color && s1.color != s3.color && s2.color != s3.color)

    val checkBg = (s1.background == s2.background && s2.background == s3.background) ||
            (s1.background != s2.background && s1.background != s3.background && s2.background != s3.background)

    return checkShape && checkColor && checkBg
}

private fun findCombination(
    shapes: Array<Shape>,
    answers: Array<Array<BooleanArray>>
): Int {
    var result = 0

    for (i in 0 until 9) {
        for (j in i + 1 until 9) {
            for (k in j + 1 until 9) {
                if(isHap(shapes[i], shapes[j], shapes[k])) {
                    answers[i][j][k] = true
                    answers[i][k][j] = true
                    answers[j][i][k] = true
                    answers[j][k][i] = true
                    answers[k][i][j] = true
                    answers[k][j][i] = true

                    result++
                }
            }
        }
    }

    return result
}