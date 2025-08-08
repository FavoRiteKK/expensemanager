package com.naveenapps.expensemanager.feature.filter.datefilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.common.utils.toCompleteDate
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetAllDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.SaveDateRangeUseCase
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.TextFieldValue
import com.naveenapps.expensemanager.core.model.isCustom
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DateFilterViewModel (
    getDateRangeUseCase: GetDateRangeUseCase,
    private val getAllDateRangeUseCase: GetAllDateRangeUseCase,
    private val saveDateRangeUseCase: SaveDateRangeUseCase,
) : ViewModel() {

    private val _event = Channel<DateFilterEvent>()
    val event = _event.receiveAsFlow()

    @OptIn(ExperimentalTime::class)
    private val _state = MutableStateFlow(
        DateFilterState(
            dateRangeType = TextFieldValue(
                value = DateRangeType.THIS_MONTH,
                valueError = false,
                onValueChange = this::setFilterType
            ),
            fromDate = TextFieldValue(
                value = Clock.System.now().asCurrentDateTime(),
                valueError = false,
                onValueChange = this::setFromDate
            ),
            toDate = TextFieldValue(
                value = Clock.System.now().asCurrentDateTime(),
                valueError = false,
                onValueChange = this::setToDate
            ),
            dateRangeTypeList = emptyList(),
            showCustomRangeSelection = false,
            showDateFilter = false,
            dateFilterType = DateFilterType.TO_DATE
        )
    )
    val state = _state.asStateFlow()

    init {
        getDateRangeUseCase.invoke().onEach {
            updateFilterType(it.type)
            updateDateRanges()
        }.launchIn(viewModelScope)

        updateDateRanges()
    }

    private fun updateDateRanges() {
        viewModelScope.launch {
            when (val response = getAllDateRangeUseCase.invoke()) {
                is Resource.Error -> Unit
                is Resource.Success -> {
                    _state.update {
                        it.copy(dateRangeTypeList = response.data)
                    }
                }
            }
        }
    }

    private fun updateFilterType(dateRangeType: DateRangeType) {
        _state.update {
            it.copy(
                dateRangeType = it.dateRangeType.copy(value = dateRangeType),
                showCustomRangeSelection = dateRangeType.isCustom()
            )
        }
    }

    private fun setFilterType(dateRangeType: DateRangeType?) {
        dateRangeType ?: return
        viewModelScope.launch {
            updateFilterType(dateRangeType)
        }
    }

    private fun save() {
        viewModelScope.launch {
            val selectedFilter = _state.value.dateRangeType.value

            val customRanges =
                if (selectedFilter == DateRangeType.CUSTOM) {
                    listOf(
                        _state.value.fromDate.value,
                        _state.value.toDate.value,
                    )
                } else {
                    _state.value.dateRangeTypeList.filter {
                        it.type == selectedFilter
                    }.let { it ->
                        it.firstOrNull()?.dateRanges?.map {
                            it.toCompleteDate()
                        }
                    }
                }

            if (customRanges != null) {
                saveDateRangeUseCase.invoke(
                    selectedFilter,
                    customRanges,
                )
            }

            _event.send(DateFilterEvent.Saved)
        }
    }

    private fun setFromDate(date: LocalDateTime) {
        _state.update {
            it.copy(
                showDateFilter = false,
                fromDate = it.fromDate.copy(value = date)
            )
        }
    }

    private fun setToDate(date: LocalDateTime) {
        _state.update {
            it.copy(
                showDateFilter = false,
                toDate = it.toDate.copy(value = date)
            )
        }
    }

    fun processAction(action: DateFilterAction) {
        when (action) {
            DateFilterAction.Save -> save()
            DateFilterAction.DismissDateSelection -> {
                _state.update { it.copy(showDateFilter = false) }
            }

            DateFilterAction.ShowFromDateSelection -> {
                _state.update {
                    it.copy(
                        showDateFilter = true,
                        dateFilterType = DateFilterType.FROM_DATE
                    )
                }
            }

            DateFilterAction.ShowToDateSelection -> {
                _state.update {
                    it.copy(
                        showDateFilter = true,
                        dateFilterType = DateFilterType.TO_DATE
                    )
                }
            }

            is DateFilterAction.SaveFromDate -> {
                setFromDate(action.date)
            }

            is DateFilterAction.SaveToDate -> {
                setToDate(action.date)
            }
        }
    }
}
