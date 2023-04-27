package com.kurly.report.ui.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurly.report.data.model.Section
import com.kurly.report.data.repository.KurlyRepository
import com.kurly.report.data.repository.PrefRepository
import com.kurly.report.ui.uimodel.*
import com.kurly.report.utils.logD
import com.kurly.report.utils.onSuspendOnSuccess
import com.kurly.report.utils.recyclerview.diffutil.HashCodeDiffUtils
import com.kurly.report.utils.recyclerview.state.ListRecyclerViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 * Create : Kwon IkYoung
 * Date : 2023/04/26
 */
private const val SAVE_STATE_INDEX = "SAVE_STATE_INDEX"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val kurlyRepository: KurlyRepository,
    private val prefRepository: PrefRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recyclerViewState = ListRecyclerViewState(HashCodeDiffUtils())

    private var requestProductsJob: Job? = null
    private var currentSectionIndex: Int? = 1
        get() = savedStateHandle[SAVE_STATE_INDEX] ?: 1
        set(value) {
            savedStateHandle[SAVE_STATE_INDEX] = value
            field = value
        }

    private val likeAction : (ProductBase) -> Unit = {
        logD("likeAction : $it")
        viewModelScope.launch {
            val isReadyLike = prefRepository.isLikeProduct(it.data)
            if (isReadyLike) {
                prefRepository.removeProduct(it.data)
            } else {
                prefRepository.insertProduct(it.data)
            }
            it.isLike.emit(isReadyLike.not())
        }
    }

    init {
        requestSection()
    }

    private fun requestSection() {
        currentSectionIndex?.also {page ->
            requestProductsJob = viewModelScope.launch {
                kurlyRepository.getSections(page)
                    .onSuspendOnSuccess {
                        currentSectionIndex = it.page?.nextPage
                        createSectionUIModel(it.data)
                    }
            }
        } ?: return
    }

    private suspend fun createSectionUIModel(list: List<Section>) = withContext(viewModelScope.coroutineContext) {
            list.forEach { section ->
                kurlyRepository.getSectionsProducts(section.id)
                    .onSuspendOnSuccess {
                        val result = recyclerViewState.getCurrentItems().toMutableList()
                        result.add(SectionUIModel(section.title))

                        val list = ListRecyclerViewState(HashCodeDiffUtils())
                        list.submitList(it.data.map {
                            HorizontalProductUIModel(
                                it,
                                prefRepository.isLikeProduct(it),
                                likeAction
                            )
                        })
                        when (section.type) {
                            "grid" -> result.add(GridProductUIModel(list))
                            "horizontal" ->  result.add(HorizontalListUIModel(list))
                            "vertical" -> result.addAll(it.data.map {
                                VerticalProductUIModel(
                                    it,
                                    prefRepository.isLikeProduct(it),
                                    likeAction
                                )
                            })
                            else -> throw IllegalArgumentException("Unknown type")
                        }

                        recyclerViewState.submitList(result)
                    }
            }
        }

    fun requestNextSection() {
        if (requestProductsJob?.isCompleted == true) {
            requestSection()
        }
    }
}