package com.michalkarmelita.buffsdk.viewmodel

import com.michalkarmelita.buffsdk.repository.Answer
import com.michalkarmelita.buffsdk.repository.BuffData

internal object BuffUiTransformer {
    fun convertToBuffUiState(newBuff: BuffData): BuffState {
        return with(newBuff) {
            BuffVisible(
                "${author.firstName} ${author.lastName}",
                author.imageUrl,
                question,
                timeToShow,
                convertAnswers(answers)
            )
        }
    }

    private fun convertAnswers(answers: List<Answer>): List<BuffAnswer> =
        answers.map { answer: Answer ->
            BuffAnswer(
                answer.title
            )
        }
}