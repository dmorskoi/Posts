package com.user.posts.feature.posts.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.user.posts.databinding.ItePostBinding
import com.user.posts.feature.posts.UiPost

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItePostBinding::bind)

    fun bind(post: UiPost) {
        with(viewBinding) {
            userName.text = post.title
            postsCount.text = post.body
        }
    }
}