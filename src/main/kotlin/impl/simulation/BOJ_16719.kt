package impl.simulation

fun main() = with(System.`in`.bufferedReader()) {
    val input = readLine()
    val visited = BooleanArray(input.length)
    val sb = StringBuilder()

    fun solve(start: Int, end: Int) {
        if (start > end) return

        var minCharIdx = start
        for (i in start..end) {
            if (input[i] < input[minCharIdx]) {
                minCharIdx = i
            }
        }

        visited[minCharIdx] = true

        for (i in input.indices) {
            if (visited[i]) sb.append(input[i])
        }
        sb.append("\n")

        solve(minCharIdx + 1, end)
        solve(start, minCharIdx - 1)
    }

    solve(0, input.length - 1)
    print(sb)
}