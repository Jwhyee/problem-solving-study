package pq

import java.util.ArrayList
import java.util.Arrays
import java.util.PriorityQueue
import java.util.StringTokenizer

fun main() = with(System.`in`.bufferedReader()){
    val (n, m) = StringTokenizer(readLine()).run {
        nextToken().toInt() to nextToken().toInt()
    }
    val list = IntArray(n) { 0 }
    // 전자 기기들은 한 번에 하나의 콘센트에서만 충전이 가능함
    // 충전 최소 시간 구하기
    // 큰 수부터 넣기
    val pq = PriorityQueue<Int>()
    StringTokenizer(readLine()).run {
        repeat(n){
            list[it] = nextToken().toInt()
        }
    }

    list.sortDescending()

    var time = 0
    repeat(n) {
        // 콘센트에 자리가 없을 경우 가장 나중에 사용되는 전자기기를 뽑아낸다.
        // 새로운 전자기기를 꽂는다.
        if(pq.size >= m) time = pq.poll()
        // 새로운 전자기기를 꽂는다.
        pq += time + list[it]
    }

    while (pq.isNotEmpty()) {
        time = pq.poll()
    }

    println(time)
}