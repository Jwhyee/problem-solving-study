package impl.simulation

/**
 * 문제 이름(난이도) : HTML 파싱 (GOL3)
 * 시간 : 776 ms
 * 메모리 : 58704 KB
 * 링크 : https://www.acmicpc.net/problem/19598
 */
private val bw = System.out.bufferedWriter()
fun main() = with(System.`in`.bufferedReader()) {
    val html = readLine()
    parseHTML(html)
    bw.flush()
    bw.close()
    close()
}

fun parseHTML(html: String) {
    // div 태그 내부를 탐색
    val pattern = "<div title=\"(.*?)\">(.*?)</div>".toRegex()
    // 패턴에 맞는 모든 부분을 가져옴
    val matches = pattern.findAll(html)
    for (match in matches) {
        // title 값
        val title = match.groupValues[1]
        // div 내부 태그들
        val content = match.groupValues[2]
        bw.append("title : $title")
        bw.newLine()
        // p 태그 내부 값들 탐색
        parseParagraphs(content)
    }
}

fun parseParagraphs(content: String) {
    // p 태그 안에 있는 모든 태그들 찾기
    val pattern = "<p>(.*?)</p>".toRegex()
    val matches = pattern.findAll(content)
    for (match in matches) {
        // p 태그 사이에는 다른 태그가 올 수 있음
        var sentence = match.groupValues[1]
        // 모든 태그 정보 제거
        sentence = sentence.replace(Regex("<.*?>"), "")
        // 여러 공백이 있을 경우 1개의 공백으로 줄임
        sentence = sentence.replace(Regex("\\s+"), " ")
        // 앞 뒤 공백을 제거하고 출력
        bw.append(sentence.trim())
        bw.newLine()
    }
}
