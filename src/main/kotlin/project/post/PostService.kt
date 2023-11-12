package project.post

class PostService {
    private val repo: Repository = PostRepository()
    fun savePost(dto:PostDto) {
        repo.save(dto)
    }

    fun findById(id: Int): PostDto {
        return repo.findById(id) ?: throw NullPointerException()
    }

    fun findAll(): List<PostDto> {
        return repo.findAll()
    }

    fun update(id: Int, data: PostDto) {
        repo.update(id, data)
    }

    fun delete(id: Int) {
        repo.delete(id)
    }
}