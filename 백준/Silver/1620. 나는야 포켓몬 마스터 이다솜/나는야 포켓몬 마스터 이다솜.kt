import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val nameMap = hashMapOf<String, Int>()
    val list = mutableListOf<String>()

    for (i in 1 .. n) {
        val name = br.readLine()
        nameMap[name] = i
        list += name
    }

    for (i in 0 until m) {
        val q = br.readLine()
        if (q[0].isDigit()) {
            bw.append(list[q.toInt() - 1])
            bw.newLine()
        } else {
            bw.append("${nameMap[q]}")
            bw.newLine()
        }
    }
    bw.flush()
    br.close()
    bw.close()
}