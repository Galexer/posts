package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService.add
import ru.netology.WallService.update
import ru.netology.WallService.createComment
import ru.netology.WallService.complain

class WallServiceTest {

    @Test
    fun addPost() {
        val likes1 = Like(0,false,true)
       /* to check if attachments work (it works)
        val link = Link("www.good.link", "My Link", "the best link")
        val attachLink = AttachLink(link = link)
        var attachmentPost1 = emptyArray<Attachment>()
        attachmentPost1 += attachLink
        val post1 = Post(0,0, 0,11_8_22, likes = likes1, attachments = attachmentPost1)*/

        val post1 = Post(0,0, 0,11_8_22, likes = likes1)
        val result = add(post1)

        assertEquals(1, result.id)
    }

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun updateGood() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0,0, 0,11_8_22, likes =  likes1))
        val likes2 = Like(userLikes = false, canLike = true)
        add(Post(0,1, 1,11_8_22, likes = likes2))
        val likes3 = Like(0,false,true)
        add(Post(0,2, 2,11_8_22, likes =  likes3))

        val update = Post(3,2, 2,11_8_22, "good post", likes =  likes3)

        val result = update(update)
        assertTrue(result)
    }

    @Test
    fun updateFalse() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0,0, 0,11_8_22, likes =  likes1))
        val likes2 = Like(userLikes = false, canLike = true)
        add(Post(0,1, 1,11_8_22, likes = likes2))
        val likes3 = Like(userLikes = false, canLike = true)

        val update = Post(3,2, 2,11_8_22, "another good post", likes = likes3)

        val result = update(update)
        assertFalse(result)
    }

    @Test
    fun createComment() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0,0, 0,11_8_22, likes =  likes1))
        val comment = Comment( fromId = 1, date = 25822, text = "comment")

        val result = createComment(1 , comment)
        assertEquals(1, result.id)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrowCreateComment() {
        val comment = Comment( fromId = 1, date = 25822, text = "comment")

        createComment(1 , comment)
    }

    @Test
    fun complain() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0, 0, 0, 11_8_22, likes = likes1))
        val comment = Comment(fromId = 1, date = 25822, text = "comment")
        createComment(1, comment)
        val rightComplain = Report(1,1, 1, 1)

        val result = complain(1, 1, 1)
        assertEquals(result, rightComplain)
    }

    @Test(expected = CommentNotFoundException::class)
    fun shouldThrowComment() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0, 0, 0, 11_8_22, likes = likes1))
        val comment = Comment(fromId = 1, date = 25822, text = "comment")
        createComment(1, comment)

        complain(1, 2, 1)
    }
    @Test(expected = ReasonNotFoundException::class)
    fun shouldThrowReason() {
        val likes1 = Like(userLikes = false, canLike = true)
        add(Post(0, 0, 0, 11_8_22, likes = likes1))
        val comment = Comment(fromId = 1, date = 25822, text = "comment")
        createComment(1, comment)

        complain(1, 1, 10)
    }
}