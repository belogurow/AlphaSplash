package ru.belogurow.unsplashclient.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.belogurow.unsplashclient.model.PhotoResponse

internal interface UnsplashApiService {

    @GET("photos")
    fun photos(@Query("page") page: String,
               @Query("per_page") perPage: String,
               @Query("order_by") orderBy: String): Deferred<Response<List<PhotoResponse>>>

    @GET("photos/{id}")
    fun photoById(@Path("id") photoId: String): Deferred<Response<PhotoResponse>>


}
