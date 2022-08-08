package com.example.vismamusic.presentation.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vismamusic.R
import com.example.vismamusic.databinding.FragmentStorageBinding
import com.example.vismamusic.presentation.storage.adapters.StorageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StorageFragment : Fragment(R.layout.fragment_storage) {

    private lateinit var binding: FragmentStorageBinding
    private val viewModel by viewModels<StorageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStorageBinding.inflate(inflater, container, false)
        binding.songContainer.layoutManager = LinearLayoutManager(container?.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView()
    }

    private fun prepareView() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            setLoading(state.isLoading)
            if (!state.isLoading) {
                binding.title.text = state.storageType
                binding.songContainer.adapter =
                    StorageAdapter(state.songs, state.storageType) { id -> viewModel.saveSong(id) }
            }
            state.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.container.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        }
    }
}