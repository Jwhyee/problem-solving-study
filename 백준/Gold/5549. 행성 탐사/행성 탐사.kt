import java.lang.StringBuilder
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    var st = StringTokenizer(readLine())

    val m = st.nextToken().toInt()
    val n = st.nextToken().toInt()
    val k = readLine().toInt()

    val graph = Array (m + 1) { Array(n + 1) { IntArray(3) } }

    for (i in 0 until m) {
        val arr = readLine().toCharArray()
        for (j in 0 until n) {
            when (arr[j]) {
                'J' -> graph[i + 1][j + 1][0] = 1
                'O' -> graph[i + 1][j + 1][1] = 1
                'I' -> graph[i + 1][j + 1][2] = 1
            }
        }
    }

    for (i in 1..m) {
        for (j in 0..n) {
            for (l in 0 until 3) {
                graph[i][j][l] += graph[i - 1][j][l]
            }
        }
    }

    for (i in 0..m) {
        for (j in 1..n) {
            for (l in 0 until 3) {
                graph[i][j][l] += graph[i][j - 1][l]
            }
        }
    }

    repeat(k) {
        st = StringTokenizer(readLine())
        val y1 = st.nextToken().toInt()
        val x1 = st.nextToken().toInt()
        val y2 = st.nextToken().toInt()
        val x2 = st.nextToken().toInt()

        val sb = StringBuilder()

        for (i in 0..2) {
            sb.append("${graph[y2][x2][i] - graph[y1 - 1][x2][i] - graph[y2][x1 - 1][i] + graph[y1 - 1][x1 - 1][i]} ")
        }
        bw.append(sb)
        bw.newLine()
    }

    bw.flush()
    bw.close()
    close()
}