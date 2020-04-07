package com.michalkarmelita.buffsdk.repository

import com.michalkarmelita.buffsdk.api.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BuffTransformerTest {

    @Test
    fun `test BuffApiResponse mapping to Buff local Data`() {
        val result = BuffTransformer.mapToBuffData(BUFF_API_SUCCESS_RESPONSE)

        with((result as DataState.Success).data) {
            assertEquals(author.firstName, FIRST_NAME)
            assertEquals(author.lastName, LAST_NAME)
            assertEquals(author.imageUrl, URL)
            assertEquals(question, QUESTION)
            assertEquals(answers[0].title, ANSWER_1)
            assertEquals(answers[1].title, ANSWER_2)
        }
    }

    companion object {
        private const val FIRST_NAME = "name"
        private const val LAST_NAME = "last"
        private const val URL = "url"
        private const val TIME_TO_SHOW = 10
        private const val QUESTION = "question"
        private const val ANSWER_1 = "answer 1"
        private const val ANSWER_2 = "answer 2"
        private val BUFF_API_SUCCESS_RESPONSE = RemoteData.Success(
            BuffApiResponse(
                BuffApiData(
                    AuthorApi(FIRST_NAME, LAST_NAME, URL),
                    TIME_TO_SHOW,
                    QuestionApi(QUESTION),
                    listOf(AnswerApi(ANSWER_1), AnswerApi(ANSWER_2))
                )
            )
        )
    }

}