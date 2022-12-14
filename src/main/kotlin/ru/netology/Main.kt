package ru.netology

fun main() {
}
abstract class Attachment(val type: String)

class AttachVideo(type: String = "video", video: Video) : Attachment(type)
class Video (id: Int, ownerId: Int, title: String?, player: String?)

class AttachPhoto(type: String = "photo", photo: Photo) : Attachment(type)
class Photo (id: Int, ownerId: Int, text: String?, photo: String?)

class AttachLink(type: String = "link", link: Link) : Attachment(type)
class Link(url: String, title: String?, description: String?)

class AttachAudio(type: String = "audio", audio: Audio) : Attachment(type)
class Audio(id: Int, title: String?, artist: String?, url: String?)

class AttachDoc(type: String = "document", document: Document) : Attachment(type)
class Document(id: Int, ownerId: Int, title: String?, url: String?)

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