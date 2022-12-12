package com.user.posts.feature.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.user.posts.R
import com.user.posts.core.extension.observeWithLifecycle
import com.user.posts.databinding.FragmentUsersBinding
import com.user.posts.feature.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewBinding by viewBinding(FragmentUsersBinding::bind)
    private val viewModel: UsersVieModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UsersAdapter { userId ->
            viewModel.onUserItemClicked(userId)
        }

        with(viewBinding) {
            recycler.adapter = adapter
            recycler.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )

            error.retryButton.setOnClickListener {
                viewModel.onRetryButtonClicked()
            }
        }

        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { state ->
            with(viewBinding) {
                progress.isVisible = state.isLoading && !state.isError
                recycler.isVisible = !state.isLoading || !state.isError
                error.errorLayout.isVisible = state.isError
                adapter.submitList(state.users)
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