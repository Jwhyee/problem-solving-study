import java.util.*

private class Document(
    val index: Int,
    val weight: Int,
    val isTarget: Boolean
)

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tc = readLine().toInt()

    repeat(tc) {
        val (n, m) = StringTokenizer(readLine()).run {
            nextToken().toInt() to nextToken().toInt()
        }

        var min = Int.MAX_VALUE
        var max = 0

        val st = StringTokenizer(readLine())
        val queue: Queue<Document> = LinkedList()
        repeat(n) {
            val doc = st.nextToken().toInt()
            min = minOf(doc, min)
            max = maxOf(doc, max)

            queue.add(Document(it, doc, it == m))
        }

        var index = 1
        while (queue.isNotEmpty()) {
            val document = queue.poll()
            val weight = document.weight

            val hasHighWeight = queue.any { weight < it.weight }
            if (hasHighWeight) {
                queue.add(document)
            } else {
                if (document.isTarget) sb.append(index).append("\n")
                index++
            }
        }
    }

    print(sb)
}