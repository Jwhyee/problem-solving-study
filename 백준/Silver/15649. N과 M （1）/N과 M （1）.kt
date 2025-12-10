import java.io.BufferedWriter
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()

    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }

    val visited = BooleanArray(n) { false }

    backtracking(bw, visited, n, m, "", 0)

    bw.flush()

    close()
    bw.close()
}

private fun backtracking(
    bw: BufferedWriter,
    visited: BooleanArray,
    n: Int, m: Int,
    cur: String,
    count: Int
) {
    if(count == m) {
        bw.write(cur.trim())
        bw.newLine()
        return
    }

    repeat(n) { idx ->
        if(!visited[idx]) {
            visited[idx] = true
            backtracking(bw, visited, n, m, "$cur ${idx + 1}", count + 1)
            visited[idx] = false
        }
    }
}