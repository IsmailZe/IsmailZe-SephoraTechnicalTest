package sephora.android.testtechnique

import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Request
import org.junit.Test

import org.junit.Assert.*
import org.mockito.kotlin.KArgumentCaptor
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import sephora.android.testtechnique.data.repository.ItemRepositoryImpl
import sephora.android.testtechnique.utils.DefaultCoroutineApiClient
import sephora.android.testtechnique.utils.JsonTransformer

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var apiClient: DefaultCoroutineApiClient
    private val jsonTransformer = mock<JsonTransformer>()
    private val httpRequestCaptor: KArgumentCaptor<Request> = argumentCaptor()
    private val httpCall: Call = mock()

    companion object {
        private const val URL = "http://path/to/resource"
        private const val JSON_DATA = "{}"
    }

    @Test
    fun `fetchResult makes post request to api mobile with json content provided in request`() = runBlocking {

        whenever(jsonTransformer.toJson(apiClient)).thenReturn(JSON_DATA)

        apiClient.fetchResult(request)
        with(httpRequestCaptor.firstValue) {
            method.shouldBeEqualTo("POST")
            body!!.contentLength().shouldBeEqualTo(JSON_DATA.length.toLong())
            body!!.contentType().toString().shouldBeEqualTo("application/json; charset=utf-8")
        }
    }

    fun CharSequence?.shouldBeEqualTo(other: CharSequence) {
            assertEquals(this.toString().toLowerCase(),other.toString().toLowerCase())
    }

    fun Long?.shouldBeEqualTo(other: Long) {
        assertEquals(this,other)
    }
}