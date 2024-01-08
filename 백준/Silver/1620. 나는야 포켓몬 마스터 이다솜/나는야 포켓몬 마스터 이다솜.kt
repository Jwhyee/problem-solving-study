import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val bw = System.out.bufferedWriter()
    val nameMap = hashMapOf<String, Int>()
    val idxMap = hashMapOf<Int, String>()

    for (i in 1.. n) {
        val name = readLine()
        nameMap[name] = i
        idxMap[i] = name
    }

    for (i in 1 .. m) {
        val q = readLine()
        if (q.toIntOrNull() == null) {
            bw.append("${nameMap[q]}\n")
        } else {
            bw.append("${idxMap[q.toInt()]}\n")
        }
    }
    bw.flush()
    bw.close()
}