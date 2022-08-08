package com.example.vismamusic.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vismamusic.R
import com.example.vismamusic.databinding.FragmentDetailsBinding
import com.example.vismamusic.domain.model.Song
import com.example.vismamusic.presentation.details.adapters.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private val songList: MutableList<Song> = mutableListOf()
    private val adapter = DetailsAdapter(songList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.songContainer.adapter = adapter
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
                binding.title.text = state.categoryTitle
                songList.addAll(state.songs)
                adapter.notifyDataSetChanged()
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