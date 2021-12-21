package sephora.android.testtechnique.utils

import com.google.gson.GsonBuilder
import java.io.IOException
import retrofit2.Response

object Utils {
    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error =
        AppResult.Error(Exception(ApiErrorUtils.parseError(resp).message))

    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> = response.body()?.let {
        AppResult.Success(it)
    } ?: handleApiError(response)
}

object ApiErrorUtils {
    fun parseError(response: Response<*>): APIError =
        try {
            GsonBuilder().create().fromJson(response.errorBody()?.string(), APIError::class.java)
        } catch (e: IOException) {
            APIError()
        }
}

data class APIError(val message: String = "")
