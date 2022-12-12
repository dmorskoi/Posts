package com.user.posts.feature.users.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.user.posts.R
import com.user.posts.databinding.ItemUserBinding
import com.user.posts.feature.users.UiUser

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val viewBinding by viewBinding(ItemUserBinding::bind)

    fun bind(user: UiUser, itemClickListener: ((Int) -> Unit)?) {
        with(viewBinding) {
            Glide
                .with(itemView.context)
                .load(user.userLogoUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(userLogo)
            userName.text = user.userName
            postsCount.text = itemView.context.getString(R.string.posts_count, user.postsCount)
            root.setOnClickListener {
                itemClickListener?.invoke(user.userId)
            }
        }
    }
}