package structure.tree

data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

// 전위 순회 배열로부터 BST 생성
fun buildBST(pre: IntArray, start: Int = 0, end: Int = pre.lastIndex): TreeNode? {
    if (start > end) return null

    val root = TreeNode(pre[start])

    // 처음으로 root보다 큰 값의 인덱스 찾기
    var mid = start + 1
    while (mid <= end && pre[mid] < root.value) mid++

    root.left = buildBST(pre, start + 1, mid - 1)
    root.right = buildBST(pre, mid, end)

    return root
}

// 후위 순회
fun postorder(node: TreeNode?, result: MutableList<Int>) {
    if (node == null) return
    postorder(node.left, result)
    postorder(node.right, result)
    result.add(node.value)
}

fun main() = with(System.`in`.bufferedReader()) {
    val input = mutableListOf<Int>()

    while (true) {
        val line = readLine()
        if (line == null || line.isEmpty()) break
        input += line.toInt()
    }

    if (input.isEmpty()) return@with

    val root = buildBST(input.toIntArray())
    val result = mutableListOf<Int>()
    postorder(root, result)

    println(result.joinToString("\n"))
}