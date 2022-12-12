package com.user.posts.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.posts.data.UserPostsRepository
import com.user.posts.feature.users.adapter.UserUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersVieModel @Inject constructor(
    private val userPostsRepository: UserPostsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<UserUiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        getUsers()
    }

    fun onUserItemClicked(userId: Int) {
        viewModelScope.launch {
            _uiEffect.send(UserUiEffect(UsersFragmentDirections.navigateToPostsAction(userId)))
        }
    }

    fun onRetryButtonClicked() {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch() {
            _uiState.update { it.copy(isLoading = true, isError = false) }
            userPostsRepository.getUsers()
                .onSuccess { users ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            users = users
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false, isError = true) }
                    _uiEffect.send(UserUiEffect(isError = true))
                }
        }
    }
}