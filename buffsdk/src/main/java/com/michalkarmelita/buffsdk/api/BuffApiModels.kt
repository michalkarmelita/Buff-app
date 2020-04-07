package com.michalkarmelita.buffsdk.api

import com.google.gson.annotations.SerializedName

internal data class BuffApiResponse(val result: BuffApiData)
internal data class BuffApiData(
    @SerializedName("author") val author: AuthorApi,
    @SerializedName("time_to_show") val timeToShow: Int,
    @SerializedName("question") val question: QuestionApi,
    @SerializedName("answers") val answers: List<AnswerApi>
)

internal data class AuthorApi(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("image") val imageUrl: String
)

internal data class QuestionApi(@SerializedName("title") val title: String)
internal data class AnswerApi(@SerializedName("title") val title: String)
