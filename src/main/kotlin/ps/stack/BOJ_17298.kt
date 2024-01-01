package ps.stack

import java.util.Stack

/**
 * 문제 이름(난이도) : 오큰수(GOL4)
 * 시간 : 1244 ms
 * 메모리 : 308952 KB
 * 링크 : https://www.acmicpc.net/problem/17298
 */
fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val list = readLine().split(" ").map { it.toInt() }.toMutableList()
    val stack = Stack<Int>()

    for (i in 0 until list.size) {
        // 스택이 비어있지 않고, 오큰수가 있을 때까지
        while (stack.isNotEmpty() && (list[stack.peek()] < list[i])) {
            // pop해서 idx 값을 오큰수로 변경
            list[stack.pop()] = list[i]
        }
        // 스택에 인덱스 넣기
        stack.push(i)
    }

    // 스택에 처리되지 않은 인덱스가 남아있으면, -1로 채우기
    while (stack.isNotEmpty()) {
        list[stack.pop()] = -1
    }

    println(list.joinToString(" "))

}