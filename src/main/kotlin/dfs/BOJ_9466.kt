package dfs

private var cnt = 0

fun main() = with(System.`in`.bufferedReader()) {
    val bw = System.out.bufferedWriter()
    val t = readLine().toInt()

    repeat(t) {
        val n = readLine().toInt()
        val students = readLine().split(" ").map { it.toInt() - 1 }.toIntArray()

        val visited = BooleanArray(n) { false }
        val finished = BooleanArray(n) { false }

        cnt = 0

        repeat(n) { student ->
            dfs(visited, finished, students, student)
        }

        bw.write("${n - cnt}")
        bw.newLine()
    }

    bw.flush()

    bw.close()
    close()
}

private fun dfs(
    visited: BooleanArray,
    finished: BooleanArray,
    students: IntArray,
    current: Int
) {
    if (visited[current]) return

    visited[current] = true

    val next = students[current]

    if (!visited[next]) {
        dfs(visited, finished, students, next)
    } else {
        if (!finished[next]) {
            cnt++

            var i = next
            while (i != current) {
                cnt++
                i = students[i]
            }
        }
    }

    finished[current] = true
}
