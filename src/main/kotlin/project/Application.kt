package project

import project.post.PostDto
import project.post.PostService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val service = PostService();

fun main() {
    loop@while (true) {
        showMenu()

        when (readln().toInt()) {
            1 -> service.savePost(inputPost())
            2 -> printDto(*service.findAll().toTypedArray())
            3 -> printDto(service.findById(readln().toInt()))
            4 -> service.update(readln().toInt(), inputPost())
            5 -> service.delete(readln().toInt())
            0 -> break@loop
        }
    }
}
fun printDto(vararg dtos: PostDto) {
    for (dto in dtos) {
        println("제목 : ${dto.title}")
        println("내용 : ${dto.content}")
        println("날짜 : ${dto.date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))}")
        println()
    }
}

fun showMenu() {
    println("""
        -------------------
        1. 게시글 저장
        2. 전체 게시글 조회
        3. 게시글 단건 조회
        4. 게시글 수정
        5. 게시글 삭제
        0. 종료
        -------------------
    """.trimIndent())
}

fun inputPost(): PostDto {
    print("제목을 입력하세요 : ")
    val title = readln()
    print("내용을 입력하세요 : ")
    val content = readln()
    val time = LocalDateTime.now()

    return PostDto(title, content, time)
}