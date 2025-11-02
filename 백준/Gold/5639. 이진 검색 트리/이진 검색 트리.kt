fun preorderToPostorder(pre: IntArray): IntArray {
    val result = ArrayList<Int>()
    fun build(lo: Int, hi: Int) {
        if (lo > hi) return
        val root = pre[lo]
        var mid = lo + 1
        while (mid <= hi && pre[mid] < root) mid++

        build(lo + 1, mid - 1)
        build(mid, hi)
        result.add(root)
    }
    build(0, pre.lastIndex)
    return result.toIntArray()
}

fun main() = with(System.`in`.bufferedReader()) {
    val input = mutableListOf<Int>()

    while(true) {
        val v = readLine()
        if(v == null || v.isEmpty()) {
            break
        }
        input += v.toInt()
    }

    val post = preorderToPostorder(input.toIntArray())
    println(post.joinToString("\n"))
}