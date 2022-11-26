package ru.test.exovc.ui.main

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.test.exovc.R
import ru.test.exovc.databinding.ItemRecyclerViewBinding
import ru.test.exovc.model.Video

class MainAdapter(private val glide: RequestManager, onClick: (Video) -> Unit) {

    private val delegationAdapter = ListDelegationAdapter(videoAdapterDelegate(onClick))
    private var activeItem = -1

    fun updateList(list: List<Video>) {
        delegationAdapter.items = list
        delegationAdapter.notifyItemChanged(0)
    }

    fun attachToRecyclerView(view: RecyclerView) {
        view.adapter = delegationAdapter
    }

    private fun reselect(newActiveItem: Int) {
        delegationAdapter.items?.get(activeItem)?.isSelected = false
        delegationAdapter.notifyItemChanged(activeItem)
        activeItem = newActiveItem
        delegationAdapter.notifyItemChanged(activeItem)
    }

    private fun videoAdapterDelegate(onClick: (Video) -> Unit) =
        adapterDelegateViewBinding<Video, Video, ItemRecyclerViewBinding>({ inflater, root ->
            ItemRecyclerViewBinding.inflate(inflater, root, false)
        }) {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != activeItem) {
                    reselect(absoluteAdapterPosition)
                    onClick(item)
                    item.isSelected = true
                }
            }
            bind {
                val isActive = item.isSelected
                if (isActive) activeItem = absoluteAdapterPosition
                glide.load(item.smallThumbnailUrl)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .transition(withCrossFade())
                    .placeholder(R.drawable.shape_recycler_item_loading)
                    .into(binding.imageView)
                binding.imageView.alpha = if (isActive) 1f else 0.5f
                binding.shapeFrameLayout.isVisible = isActive
            }
        }
}
