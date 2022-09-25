package com.example.healthcare.features_news.presentation.fragments.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.common.Resource
import com.example.healthcare.features_news.data.remote.response.Result
import com.example.healthcare.features_news.data.repository.DashboardRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepositoryImpl
) : ViewModel() {

    private val channel = Channel<Resource<List<Result>>>()
    var flow = channel.receiveAsFlow()

    fun getRecords() = viewModelScope.launch { repo.getRecords().collect { channel.send(it) } }

    fun deleteRecords() = repo.deleteRecords()
}