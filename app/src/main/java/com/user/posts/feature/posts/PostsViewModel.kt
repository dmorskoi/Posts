package com.user.posts.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.posts.data.UserPostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val userPostsRepository: UserPostsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<Unit>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onScreenStarted(userId: Int) {
        getPostsByUserId(userId)
    }

    fun onRetryButtonClicked(userId: Int) {
        getPostsByUserId(userId)
    }

    private fun getPostsByUserId(userId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = false) }

            userPostsRepository.getPostsByUserId(userId)
                .onSuccess { posts ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            posts = posts,
                            userLogoUrl = posts.firstNotNullOf { post ->
                                post.userLogoUrl
                            }
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false, isError = true) }
                    _uiEffect.send(Unit)
                }
        }
    }
}