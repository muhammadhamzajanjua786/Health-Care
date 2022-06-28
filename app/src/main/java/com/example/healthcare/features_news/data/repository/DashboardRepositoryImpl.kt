package com.example.healthcare.features_news.data.repository

import android.content.Context
import com.example.healthcare.core.utils.ApiResponse
import com.example.healthcare.core.utils.AppUtils.ERROR_MESSAGE
import com.example.healthcare.core.utils.AppUtils.SUCCESS
import com.example.healthcare.core.utils.AppUtils.isNetworkAvailable
import com.example.healthcare.features_news.data.local.room_database.RoomDAO
import com.example.healthcare.features_news.data.remote.ApiService
import com.example.healthcare.features_news.data.remote.response.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: RoomDAO,
    private val context: Context) {

    operator fun invoke(): Flow<ApiResponse<List<Result>>> = flow {
        emit(ApiResponse.Loading)

        val job = CoroutineScope(Dispatchers.IO).async { dao.count() }
        if (job.await() > 0) {
            val job =
                CoroutineScope(Dispatchers.IO).async { dao.get().map { it.toResult() } }
            emit(ApiResponse.Success(data = job.await()))
        } else {
            if (isNetworkAvailable(context)) {
                try {
                    val response = apiService.getData()
                    if (response.isSuccessful) {
                        val data = response.body()?.results ?: emptyList()
                        emit(ApiResponse.Success(data = data, SUCCESS))
                        CoroutineScope(Dispatchers.IO).launch { dao.insert(data.map { it.toResult() }) }
                    }
                } catch (e: HttpException) {
                    emit(ApiResponse.Failure(message = "Oops, something went wrong!"))
                } catch (e: IOException) {
                    emit(ApiResponse.Failure(message = ERROR_MESSAGE))
                }
            } else
                emit(ApiResponse.Failure(message = ERROR_MESSAGE))
        }
    }

    fun deleteData() {
        CoroutineScope(Dispatchers.IO).launch { dao.delete() }
    }
}