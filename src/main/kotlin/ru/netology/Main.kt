package ru.netology

import ru.netology.WallService.createComment

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

data class Comment(
    val id: Int = 0,
    val fromId: Int,
    val date: Int,
    val text: String,
    val attachments: Array<Attachment> = emptyArray()
)
data class Report(
        val reportId: Int,
        val ownerId: Int,
        val commentId: Int,
        val reason: Int
)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()
    private var id = 0
    private var idForComments = 0
    private var idReport = 0

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

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (postId == post.id) {
                idForComments++
                val newComment = comment.copy(id = idForComments)
                comments += newComment
                return comments.last()
            }
        }
        throw PostNotFoundException("Post with this id not found")
    }

    fun complain (ownerId: Int, commentId: Int, reason: Int): Report{
        val arrayOfReasons: Array<String> = arrayOf("spam", "children's ponography", "extremism", "violence",
            "drug propaganda", "adult material", " insult; abuse")
        for (comment in comments) {
            if (commentId == comment.id) {
                if(reason in 0..arrayOfReasons.size) {
                    idReport++
                    val report = Report(idReport, ownerId, commentId, reason)
                    reports += report
                    return reports.last()
                } else {
                    throw ReasonNotFoundException("This reason for complain not found")
                }
            }
        }
        throw CommentNotFoundException("Comment with this id not found")
    }

    fun clear() {
        posts = emptyArray()
        id = 0
        idForComments = 0
    }
}

class PostNotFoundException(val text: String) : RuntimeException(text)

class CommentNotFoundException(val text: String) : RuntimeException(text)

class ReasonNotFoundException(val text: String) : RuntimeException(text)