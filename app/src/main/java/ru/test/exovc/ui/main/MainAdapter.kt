package ru.test.exovc.ui.main

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.test.exovc.databinding.ItemRecyclerViewBinding
import ru.test.exovc.network.VideoDto

class MainAdapter(onClick: (VideoDto) -> Unit) {

    val delegationAdapter = ListDelegationAdapter(videoAdapterDelegate(onClick))
    var activeItem = 0

    fun updateList(list: List<VideoDto>) {
        delegationAdapter.items = list
        delegationAdapter.notifyItemChanged(0)
    }

    private fun reselect(newActiveItem: Int) {
        val old = activeItem
        activeItem = newActiveItem
        delegationAdapter.notifyItemChanged(old)
        delegationAdapter.notifyItemChanged(activeItem)
    }

    private fun videoAdapterDelegate(onClick: (VideoDto) -> Unit) =
        adapterDelegateViewBinding<VideoDto, VideoDto, ItemRecyclerViewBinding>({ inflater, root ->
            ItemRecyclerViewBinding.inflate(inflater, root, false)
        }) {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != activeItem) {
                    reselect(absoluteAdapterPosition)
                    onClick(item)
                }
            }
            bind {
                val isActive = activeItem == absoluteAdapterPosition
                Glide.with(binding.root)
                    .load(item.smallThumbnailUrl)
                    .transform(CenterCrop(), RoundedCorners(40))
                    .into(binding.imageView)
                binding.imageView.alpha = if (isActive) 1f else 0.5f
                binding.shapeFrameLayout.isVisible = isActive
            }
        }
}
