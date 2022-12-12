package com.user.posts.feature.users.adapter

import androidx.recyclerview.widget.DiffUtil
import com.user.posts.feature.users.UiUser

class UsersDiffUtilCallback : DiffUtil.ItemCallback<UiUser>() {

    override fun areItemsTheSame(oldItem: UiUser, newItem: UiUser) =
        oldItem.userId == newItem.userId

    override fun areContentsTheSame(oldItem: UiUser, newItem: UiUser) =
        oldItem == newItem
}