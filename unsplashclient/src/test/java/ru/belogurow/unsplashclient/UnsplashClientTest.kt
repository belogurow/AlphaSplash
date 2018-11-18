package ru.belogurow.unsplashclient

import kotlinx.coroutines.runBlocking
import org.junit.Test


class UnsplashClientTest {

    private val unsplashClient = UnsplashClient("9040400b17a78e105344df1c580a284b372835fccf05d8a0e5ced294f255a8eb")

    @Test
    fun testPopularPhotos10() {
        val result = runBlocking {
            unsplashClient.popularPhotos(1, 10).await()
        }


        assert(result.isSuccessful)
        assert(result.code() == 200)
        assert(result.body()?.size == 10)
    }

    @Test
    fun testLatestPhotos10() {
        val result = runBlocking {
            unsplashClient.latestPhotos(1, 10).await()
        }

        assert(result.isSuccessful)
        assert(result.code() == 200)
        assert(result.body()?.size == 10)
    }
}