package structure.hash

import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()){
    val bw = System.out.bufferedWriter()
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    val map = hashMapOf<String, String>()
    repeat(n) {
        val (site, pw) = StringTokenizer(readLine()).run {
            nextToken() to nextToken()
        }
        map += site to pw
    }
    repeat(m) {
        bw.append(map[readLine()]).appendLine()
    }
    bw.flush()
}