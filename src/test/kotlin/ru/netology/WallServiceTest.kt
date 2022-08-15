package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService.add
import ru.netology.WallService.update

class WallServiceTest {

    @Test
    fun addPost() {
        val likes1 = Like(0,false,true)
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
        assertEquals(true, result)
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
        assertEquals(false, result)
    }
}