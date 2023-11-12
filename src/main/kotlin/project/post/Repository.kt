package project.post

interface Repository {
    fun save(dto: PostDto)
    fun delete(id: Int)
    fun update(id: Int, dto: PostDto)
    fun findById(id: Int): PostDto
    fun findAll(): List<PostDto>
    fun findAllByTitle(title: String): List<PostDto>
}