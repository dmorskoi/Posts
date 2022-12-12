package com.user.posts.feature.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.user.posts.R
import com.user.posts.feature.users.UiUser

class UsersAdapter(
    private val itemClickListener: ((Int) -> Unit)? = null
) : ListAdapter<UiUser, UserViewHolder>(UsersDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}