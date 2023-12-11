import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    // K -> 3 : 뒤집어야하는 갯수
    // 5 4 3 2 1
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    val arr = readLine().replace(" ", "").toCharArray()
    arr.sort()

    val copy = arr.copyOf()
    val copyStr = String(copy)
    val arrStr = String(arr)

    fun reverseStr(str: String, i: Int, j: Int): String {
        val sb = StringBuilder()
        sb.append(str.substring(0, i))

        val reverse = str.substring(i, j)
        for (t in k - 1 downTo 0) {
            sb.append(reverse[t])
        }
        sb.append(str.substring(j, n))
        return sb.toString()
    }

    fun bfs(): Int {
        val q: Queue<Pair<String, Int>> = LinkedList()
        val set = HashSet<String>()

        q.offer(copyStr to 0)

        while (!q.isEmpty()) {
            val ci = q.poll()
            val str = ci.first
            val cnt = ci.second

            if(arrStr == str) return cnt

            if (set.contains(str).not()) {
                set.add(str)
                for (i in 0..n - k) {
                    q.offer(reverseStr(str, i, i + k) to cnt + 1)
                }
            }
        }
        return -1
    }

    println(bfs())

}