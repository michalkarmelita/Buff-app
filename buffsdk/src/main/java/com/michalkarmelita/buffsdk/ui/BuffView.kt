package com.michalkarmelita.buffsdk.ui

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michalkarmelita.buffsdk.R
import com.michalkarmelita.buffsdk.api.BuffServiceFactory
import com.michalkarmelita.buffsdk.repository.BuffRepository
import com.michalkarmelita.buffsdk.viewmodel.BuffAnswer
import com.michalkarmelita.buffsdk.viewmodel.BuffDismissed
import com.michalkarmelita.buffsdk.viewmodel.BuffLoadingError
import com.michalkarmelita.buffsdk.viewmodel.BuffViewModel
import com.michalkarmelita.buffsdk.viewmodel.BuffVisible
import kotlinx.android.synthetic.main.buff.view.*
import kotlinx.android.synthetic.main.buff_question.view.*
import kotlinx.android.synthetic.main.buff_sender.view.*
import java.util.concurrent.TimeUnit

class BuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var adapter = BuffAdapter()
    private var layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            stackFromEnd = true
        }

    private lateinit var viewModel : BuffViewModel

    /**
     * Initialise Buff feed. This method will automatically start polling for most recent buffs.
     *
     * @param url feed url.
     */
    fun initFeed(url:String) {
        viewModel = BuffViewModel(
            BuffRepository(BuffServiceFactory.create(url)))
        initObservers()
    }

    private fun initObservers() {
        adapter.attachOnClickListener(object : BuffAdapter.OnItemClickListener {
            override fun onItemClick(item: BuffAnswer) {
                viewModel.onAnswerSelected()
            }
        })
        viewModel.getBuffs().observeForever { state ->
            when (state) {
                is BuffVisible -> showBuff(state)
                is BuffDismissed -> hideBuff()
                is BuffLoadingError -> showError()
            }
        }
        buff_close.setOnClickListener { viewModel.onClosePressed() }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        LayoutInflater.from(context).inflate(R.layout.buff, this)
        list_view.layoutManager = layoutManager
        list_view.adapter = adapter
    }

    private fun showBuff(buffState: BuffVisible) {
        buff_container.visibility = VISIBLE
        buff_container.animation = AnimationUtils.loadAnimation(context, R.anim.entry_anim)
        with(buffState) {
            sender_name.text = sender
            question_text.text = question
            question_time.text = timeToDisplay.toString()
            adapter.setItems(answers)
        }

        val timer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(buffState.timeToDisplay.toLong()),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onFinish() {
                viewModel.onTimeElapsed()
            }

            override fun onTick(time: Long) {
                question_time.text = (time / 1000).toString()
                question_time_progress.progress =
                    (time / TimeUnit.SECONDS.toMillis(buffState.timeToDisplay.toLong())).toInt()
            }
        }
        timer.start()
    }

    private fun hideBuff() {
        buff_container.animation = AnimationUtils.loadAnimation(context, R.anim.exit_anim)
        buff_container.visibility = INVISIBLE
    }

    private fun showError() {
        //TODO show error to the user
    }
}