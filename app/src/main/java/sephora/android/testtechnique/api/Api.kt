package sephora.android.testtechnique.api

import retrofit2.Response
import retrofit2.http.GET
import sephora.android.testtechnique.data.model.Item

interface Api {
    @GET("/items.json")
    suspend fun getItems( ): Response<List<Item>>

}