import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val bw = System.out.bufferedWriter()
    val nameMap = hashMapOf<String, Int>()
    val list = mutableListOf<String>()

    for (i in 1 .. n) {
        val name = readLine()
        nameMap[name] = i
        list += name
    }

    for (i in 0 until m) {
        val q = readLine()
        if (q[0].isDigit()) {
            bw.append(list[q.toInt() - 1])
            bw.newLine()
        } else {
            bw.append("${nameMap[q]}")
            bw.newLine()
        }
    }
    bw.flush()
    bw.close()
}