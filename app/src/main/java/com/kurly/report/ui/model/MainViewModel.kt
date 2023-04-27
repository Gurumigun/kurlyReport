package com.kurly.report.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurly.report.data.repository.KurlyRepository
import com.kurly.report.utils.logD
import com.kurly.report.utils.recyclerview.diffutil.HashCodeDiffUtils
import com.kurly.report.utils.recyclerview.state.ListRecyclerViewState
import com.kurly.report.utils.toFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val kurlyRepository: KurlyRepository
) : ViewModel() {
    val recyclerViewState = ListRecyclerViewState(HashCodeDiffUtils())

    init {
        viewModelScope.launch {
            kurlyRepository.getSections(1)
                .toFlow()
                .collectLatest {
                    it.logD("Viewmodel")
                }
        }
    }
}