package com.michalkarmelita.buffsdk.viewmodel

internal sealed class BuffState
internal object BuffDismissed : BuffState()
internal object BuffLoadingError : BuffState()
internal data class BuffVisible(
    val sender: String,
    val senderImageUrl: String,
    val question: String,
    val timeToDisplay: Int,
    val answers: List<BuffAnswer>
) : BuffState()

internal data class BuffAnswer(val answer: String)