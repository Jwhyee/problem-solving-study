package project.post

class PostRepository : Repository {
    private val db = hashMapOf<Int, PostDto>()
    private var lastId:Int = 1
    override fun save(dto: PostDto) {
        db[lastId++] = dto
    }

    override fun delete(id: Int) {
        db.remove(id)
    }

    override fun update(id: Int, dto: PostDto) {
        db[id] = dto
        println("수정되었습니다.")
    }

    override fun findById(id: Int): PostDto {
        return db[id] ?: throw NullPointerException()
    }

    override fun findAll(): List<PostDto> {
        return db.values.toList()
    }

    override fun findAllByTitle(title: String):List<PostDto> {
        TODO("Not yet implemented")
    }

}