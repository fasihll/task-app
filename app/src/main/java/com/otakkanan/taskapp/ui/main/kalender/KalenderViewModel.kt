package com.otakkanan.taskapp.ui.main.kalender


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class KalenderViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _taskDay = MutableStateFlow(emptyList<TaskDay>())
    val taskDay: StateFlow<List<TaskDay>> = _taskDay.asStateFlow()

    private val _data = MutableStateFlow(emptyList<TaskDay>())
    val data: StateFlow<List<TaskDay>> = _data.asStateFlow()


    init {
        observe()
    }

    private fun observe() {
        observeTaskDay()
        observeData()
    }

    // FIXME if data gets changed, notify calendar to reflect to removed dates
    private fun observeTaskDay() {
        viewModelScope.launch {
            repository.getTasksDay().collectLatest { data ->
                _taskDay.emit(data)
            }
        }
    }


    private fun observeData() {
        viewModelScope.launch {
            combine(selectedDate, taskDay) { selectedDate, taskDay ->
                val currentTaskDay = taskDay.filter { it.date == selectedDate }
                return@combine currentTaskDay
            }.collectLatest { data ->
                _data.emit(data)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onSelectedDateChange(value: LocalDate) {
        viewModelScope.launch {
            _selectedDate.emit(value)
        }
    }
}