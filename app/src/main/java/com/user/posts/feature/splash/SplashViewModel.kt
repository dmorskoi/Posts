package com.user.posts.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.posts.data.UserPostsRepository
import com.user.posts.feature.users.UsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPostsRepository: UserPostsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<SplashUiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        fetchData()
    }

    fun onRetryButtonClicked() {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            runCatching {
                userPostsRepository.fetchData()
            }.onSuccess {
                _uiEffect.send(SplashUiEffect(SplashFragmentDirections.navigateToUsersAction()))
            }.onFailure {
                _uiState.update { it.copy(isLoading = false, isError = true) }
                _uiEffect.send(SplashUiEffect(isError = true))
            }
        }
    }
}