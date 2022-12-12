package com.user.posts.feature.posts.adapter

import androidx.recyclerview.widget.DiffUtil
import com.user.posts.feature.posts.UiPost

class PostsDiffUtilCallback : DiffUtil.ItemCallback<UiPost>() {

    override fun areItemsTheSame(oldItem: UiPost, newItem: UiPost) =
        oldItem.postId == newItem.postId

    override fun areContentsTheSame(oldItem: UiPost, newItem: UiPost) =
        oldItem == newItem
}