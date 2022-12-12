package com.user.posts.feature.splash

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.user.posts.R
import com.user.posts.core.extension.observeWithLifecycle
import com.user.posts.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewBinding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.error.retryButton.setOnClickListener {
            viewModel.onRetryButtonClicked()
        }

        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { state ->
            with(viewBinding) {
                progress.isVisible = state.isLoading && !state.isError
                error.errorLayout.isVisible = state.isError
            }
        }

        viewModel.uiEffect.observeWithLifecycle(viewLifecycleOwner) { uiEffect ->
            if (uiEffect.isError) {
                Toast.makeText(
                    requireContext().applicationContext,
                    getString(R.string.error_message),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                uiEffect.navAction?.let {
                    findNavController().navigate(it)
                }
            }
        }
    }
}