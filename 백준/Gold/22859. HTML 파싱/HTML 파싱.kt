private val bw = System.out.bufferedWriter()
fun main() = with(System.`in`.bufferedReader()) {
    val html = readLine()
    parseHTML(html)
    bw.flush()
    bw.close()
    close()
}

fun parseHTML(html: String) {
    val pattern = "<div title=\"(.*?)\">(.*?)</div>".toRegex()
    val matches = pattern.findAll(html)
    for (match in matches) {
        val title = match.groupValues[1]
        val content = match.groupValues[2]
        bw.append("title : $title")
        bw.newLine()
        parseParagraphs(content)
    }
}

fun parseParagraphs(content: String) {
    val pattern = "<p>(.*?)</p>".toRegex()
    val matches = pattern.findAll(content)
    for (match in matches) {
        var sentence = match.groupValues[1]
        sentence = sentence.replace(Regex("<.*?>"), "")
        sentence = sentence.replace(Regex("\\s+"), " ")
        bw.append(sentence.trim())
        bw.newLine()
    }
}
