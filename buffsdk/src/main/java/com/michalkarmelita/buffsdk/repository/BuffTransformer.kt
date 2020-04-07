package com.michalkarmelita.buffsdk.repository

import com.michalkarmelita.buffsdk.api.AnswerApi
import com.michalkarmelita.buffsdk.api.BuffApiResponse
import com.michalkarmelita.buffsdk.api.RemoteData

internal object BuffTransformer {

    fun mapToBuffData(apiResponse: RemoteData<BuffApiResponse>): DataState<BuffData> {
        return when (apiResponse) {
            is RemoteData.Success -> with(apiResponse.value.result) {
                DataState.Success(
                    BuffData(
                        Author(author.firstName, author.lastName, author.imageUrl),
                        timeToShow,
                        question.title,
                        mapToAnswers(answers)
                    )
                )
            }
            is RemoteData.GenericError,
            is RemoteData.Timeout,
            is RemoteData.IOError,
            is RemoteData.Unknown -> DataState.Error() //TODO provide more error context
        }
    }

    private fun mapToAnswers(answers: List<AnswerApi>): List<Answer> {
        return answers.map { answerApi -> Answer(answerApi.title) }
    }
}
