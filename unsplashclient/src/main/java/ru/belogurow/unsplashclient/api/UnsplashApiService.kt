package ru.belogurow.unsplashclient.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.belogurow.unsplashclient.model.PhotoResponse

internal interface UnsplashApiService {

    @GET("photos")
    suspend fun photos(@Query("page") page: String,
                       @Query("per_page") perPage: String,
                       @Query("order_by") orderBy: String): Response<List<PhotoResponse>>

    @GET("photos/{id}")
    suspend fun photoById(@Path("id") photoId: String): Response<PhotoResponse>

    @GET("/collections/featured")
    suspend fun featuredPhotos(@Query("page") page: String,
                               @Query("per_page") perPage: String): Response<List<PhotoResponse>>


}
