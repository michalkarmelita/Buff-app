package com.michalkarmelita.buffsdk.api

import com.michalkarmelita.util.CoroutineTestRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkHelperTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlocking {
            val lambdaResult = true
            val result = safeApiCall { lambdaResult }
            assertEquals(RemoteData.Success(lambdaResult), result)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when lambda throws IOException then it should emit the result as IOError`() {
        runBlocking {
            val result = safeApiCall { throw IOException() }
            assertEquals(RemoteData.IOError, result)
        }
    }

    @Test
    fun `when lambda throws HttpException then it should emit the result as GenericError`() {
        val errorBody = ResponseBody.create(
            MediaType.parse("application/json"),
            "{\"error\": \"Unexpected parameter\"," +
                    "\"message\": \"Unexpected parameter message\"}"
        )

        runBlocking {
            val result = safeApiCall {
                throw HttpException(Response.error<Any>(422, errorBody))
            }

            assertTrue(result is RemoteData.GenericError)
            with(result as RemoteData.GenericError) {
                assertEquals(422, code)
            }
        }
    }

    @Test
    fun `when lambda throws SocketTimeoutException then it should emit Timeout`() {
        runBlocking {
            val result = safeApiCall {
                throw SocketTimeoutException()
            }
            assertEquals(RemoteData.Timeout, result)
        }
    }

    @Test
    fun `when lambda throws unknown exception then it should emit Unknown`() {
        runBlocking {
            val result = safeApiCall {
                throw IllegalStateException()
            }
            assertEquals(RemoteData.Unknown, result)
        }
    }
}


