package com.orbital.sonic.roomdatabasemvvm.viewmodel

import androidx.lifecycle.*
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.repository.AlarmRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AlarmViewModel(val repository: AlarmRepository) : ViewModel() {

    val allAlarms: LiveData<List<AlarmEntity>> = repository.allAlarms.asLiveData()

    fun insertAlarm(entity: AlarmEntity) = viewModelScope.launch {
        repository.insertAlarm(entity)
    }

    fun deleteAlarm(entity: AlarmEntity) = viewModelScope.launch {
        repository.deleteAlarm(entity)
    }

    fun updateAlarm(entity: AlarmEntity) = viewModelScope.launch {
        repository.updateAlarm(entity)
    }

    fun deleteAllAlarm() = viewModelScope.launch {
        repository.deleteAllAlarm()
    }

}

class AlarmViewModelFactory(private val repository: AlarmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}