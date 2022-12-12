package com.user.posts.feature.posts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.user.posts.R
import com.user.posts.core.extension.observeWithLifecycle
import com.user.posts.databinding.FragmentPostsBinding
import com.user.posts.feature.posts.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewBinding by viewBinding(FragmentPostsBinding::bind)
    private val viewModel: PostsViewModel by viewModels()
    private val args by navArgs<PostsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostsAdapter()
        with(viewBinding) {
            recycler.adapter = adapter
            recycler.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            error.retryButton.setOnClickListener {
                viewModel.onRetryButtonClicked(args.userId)
            }
        }

        viewModel.onScreenStarted(args.userId)

        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { state ->
            with(viewBinding) {
                progress.isVisible = state.isLoading && !state.isError
                recycler.isVisible = !state.isLoading || !state.isError
                error.errorLayout.isVisible = state.isError
                adapter.submitList(state.posts)
                userLogo.isVisible = !state.isLoading || !state.isError
                Glide
                    .with(requireActivity())
                    .load(state.userLogoUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(viewBinding.userLogo)
            }
        }

        viewModel.uiEffect.observeWithLifecycle(viewLifecycleOwner) {
            Toast.makeText(
                requireContext().applicationContext,
                getString(R.string.error_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}