package ru.test.exovc.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.test.exovc.R
import ru.test.exovc.databinding.FragmentMainBinding
import ru.test.exovc.viewBinding

class MainFragment : Fragment(R.layout.fragment_main), Player.Listener {

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val adapter by lazy { MainAdapter(viewModel::clickItem) }
    private lateinit var viewModel: MainViewModel
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        lifecycleScope.launch {
            viewModel.data.collect { adapter.updateList(it) }
            viewModel.activeVideo.collectLatest { video ->
                video?.let {
                    player.setMediaItem(MediaItem.fromUri(it.fileUrl))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = ExoPlayer.Builder(requireContext()).build()
        player.repeatMode = Player.REPEAT_MODE_ONE
        binding.player.player = player
        player.prepare()
        player.play()
        player.addListener(this@MainFragment)
        binding.recyclerView.adapter = adapter.delegationAdapter
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == Player.STATE_READY) {
            binding.player.isVisible = true
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
