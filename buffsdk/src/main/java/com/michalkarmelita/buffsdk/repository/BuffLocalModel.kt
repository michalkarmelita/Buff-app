package com.michalkarmelita.buffsdk.repository

sealed class DataState<T> {
    class Success<T>(val data: T) : DataState<T>()
    class Error<T> : DataState<T>() //TODO expose error detail for better error handling
}

internal data class BuffData(
    val author: Author,
    val timeToShow: Int,
    val question: String,
    val answers: List<Answer>
)

internal data class Author(
    val firstName: String,
    val lastName: String,
    val imageUrl: String
)

internal data class Answer(val title: String)