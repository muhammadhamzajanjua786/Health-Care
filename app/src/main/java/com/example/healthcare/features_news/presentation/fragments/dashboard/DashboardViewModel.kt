package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.core.utils.ApiResponse
import com.example.healthcare.core.utils.AppUtils.enableShimmer
import com.example.healthcare.core.utils.AppUtils.showToast
import com.example.healthcare.databinding.FragmentDashboardBinding
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

    private val channel = Channel<List<Result>>()
    var flow = channel.receiveAsFlow()

    fun getData(context: Context, binding: FragmentDashboardBinding) {
        viewModelScope.launch {
            repo().collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        enableShimmer(true, binding)
                    }
                    is ApiResponse.Success -> {
                        binding.ll.visibility = View.VISIBLE
                        enableShimmer(false, binding)
                        response.data?.let {
                            channel.send(it)
                        }
                    }
                    is ApiResponse.Failure -> {
                        showToast(context, response.message.toString())
                        enableShimmer(false, binding)
                        binding.ll.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun deleteData() = repo.deleteData()
}