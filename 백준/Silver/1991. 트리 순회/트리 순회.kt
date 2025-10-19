import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.StringTokenizer

private data class Tree(
    val alphabet: String,
    var left: Tree? = null,
    var right: Tree? = null
)

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()

    val tree = Tree("A")
    val map = mutableMapOf<String, Tree?>()

    map += "A" to tree

    repeat(n) {
        val (root, left, right) = StringTokenizer(readLine()).run {
            Triple(nextToken(), nextToken(), nextToken())
        }

        val leftTree = if(left == ".") null else Tree(left)
        val rightTree = if(right == ".") null else Tree(right)

        map[root]?.also {
            it.left = leftTree
            it.right = rightTree

            map += left to leftTree
            map += right to rightTree
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    preorder(bw, tree)
    bw.newLine()
    inorder(bw, tree)
    bw.newLine()
    postorder(bw, tree)

    bw.flush()
    bw.close()
    close()
    Unit
}

private fun preorder(bw: BufferedWriter, node: Tree?) {
    if (node == null) return
    bw.write(node.alphabet)
    preorder(bw, node.left)
    preorder(bw, node.right)
}

private fun inorder(bw: BufferedWriter, node: Tree?) {
    if (node == null) return
    inorder(bw, node.left)
    bw.write(node.alphabet)
    inorder(bw, node.right)
}

private fun postorder(bw: BufferedWriter, node: Tree?) {
    if (node == null) return
    postorder(bw, node.left)
    postorder(bw, node.right)
    bw.write(node.alphabet)
}