package ru.test.exovc.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.test.exovc.App
import ru.test.exovc.R
import ru.test.exovc.databinding.FragmentMainBinding
import ru.test.exovc.ui.views.AppPlayer
import ru.test.exovc.utils.viewBinding
import ru.test.exovc.viewmodel.MainViewModel

class MainFragment : Fragment(R.layout.fragment_main), Player.Listener {

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val adapter by lazy { MainAdapter(Glide.with(binding.root), viewModel::clickItem) }
    private val appPlayer by lazy { AppPlayer(binding.styledPlayerView) }
    private val viewModel by lazy {
        ViewModelProvider(this, App.getComponent().viewModelFactory())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.data.collect { adapter.updateList(it) }
            viewModel.activeVideo.collectLatest { appPlayer.play(it?.fileUrl) }
        }
    }

    private fun bindViews() = with(binding) {
        adapter.attachToRecyclerView(recyclerView)
        textButton.setOnClickListener { editText.isVisible = !editText.isVisible }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
