import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val map = mutableMapOf<String, Int>()
    val n = readLine().toInt()

    repeat(n) {
        val (fileName, ext) = StringTokenizer(readLine(), ".").run {
            nextToken() to nextToken()
        }

        val cur = (map[ext] ?: 0) + 1
        map[ext] = cur
    }

    map.keys.sorted().forEach { ext ->
        sb.append("$ext ${map[ext]}").append("\n")
    }

    print(sb)

    close()
}