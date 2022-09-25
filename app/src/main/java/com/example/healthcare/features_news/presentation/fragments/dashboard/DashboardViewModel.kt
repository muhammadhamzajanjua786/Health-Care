package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.util.Log
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

    init { getRecords() }

    private fun getRecords() {
        viewModelScope.launch {
            repo.getRecords().collect { response ->
                when (response) {
                    is Resource.Success -> channel.send(Resource.Success(data = response.data))
                    is Resource.Failure -> channel.send(Resource.Failure(message = response.message))
                }
            }
        }
    }

    fun deleteRecords() = repo.deleteRecords()
}