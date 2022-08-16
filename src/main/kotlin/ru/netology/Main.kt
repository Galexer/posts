package ru.netology

fun main() {
}

sealed class Attachment(val type: String)

data class AttachVideo(val video: Video) : Attachment("video")
data class Video (val id: Int, val ownerId: Int, val title: String?, val player: String?)

data class AttachPhoto(val photo: Photo) : Attachment("photo")
data class Photo (val id: Int, val ownerId: Int, val text: String?, val photo: String?)

data class AttachLink(val link: Link) : Attachment("link")
data class Link(val url: String, val title: String?,val description: String?)

data class AttachAudio(val audio: Audio) : Attachment("audio")
data class Audio(val id: Int, val title: String?, val artist: String?, val url: String?)

data class AttachDoc(val document: Document) : Attachment("document")
data class Document(val id: Int, val ownerId: Int, val title: String?, val url: String?)

data class Post (
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val date: Int,
    val text: String? = "text",
    val postType: String = "post",
    val isPinned: Boolean = false,
    val canEdit: Boolean = true,
    val friendsOnly: Boolean = false,
    val isFavorite: Boolean = false,
    val likes: Like,
    val attachments: Array<Attachment> = emptyArray()
        )

data class Like(
    val count: Int = 0,
    val userLikes: Boolean,
    val canLike: Boolean
)

object WallService {
    private var posts = emptyArray<Post>()
    private var id = 0

    fun add(post: Post): Post {
        id++
        val postNew = post.copy(id = id)
        posts += postNew
        return posts.last()
    }

    fun update(postMy: Post): Boolean {
        for((index, post) in posts.withIndex()){
            if(postMy.id == post.id) {
                posts[index]= post.copy(text = post.text, postType = post.postType,
                    isPinned = post.isPinned, canEdit = post.canEdit, friendsOnly = post.friendsOnly,
                    isFavorite = post.isFavorite, likes = post.likes)
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        id = 0
    }
}