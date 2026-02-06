package search.backtracking

import java.util.StringTokenizer

private lateinit var used: BooleanArray
private lateinit var kits: IntArray
private var cnt = 0

fun main() = with(System.`in`.bufferedReader()){
    val (n, k) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    kits = IntArray(n) { 0 }
    val st = StringTokenizer(readLine())
    var idx = 0
    used = BooleanArray(n) { false }
    while (st.hasMoreTokens()) {
        kits[idx++] = st.nextToken().toInt()
    }
    backTracking(0, 500, k, n)
    println(cnt)
}

private fun backTracking(depth: Int, hp: Int, k: Int, n: Int) {
    if(hp < 500) return
    if(depth == n) {
        if(hp >= 500) cnt++
        return
    }
    // 하루가 지날 때마다 중량이 K만큼 감소
    var curHp = hp - k
    for (i in kits.indices) {
        if(!used[i]) {
            used[i] = true
            backTracking(depth + 1, curHp + kits[i], k, n)
            used[i] = false
        }
    }
    // 운동 키트들은 각각의 중량 증가량을 가지고 있으며, 사용할 때마다 즉시 중량이 증가
    // 몇몇 운동 키트들의 중량 증가량이 같을 수 있으나, 서로 다른 운동 키트로 간주
    // N일 동안 한 번씩만 사용
}