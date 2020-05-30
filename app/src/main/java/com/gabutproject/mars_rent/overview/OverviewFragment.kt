package com.gabutproject.mars_rent.overview

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabutproject.mars_rent.R
import com.gabutproject.mars_rent.databinding.FragmentOverviewBinding
import com.gabutproject.mars_rent.databinding.GridviewItemBinding

class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        // set lifecycle owner if the layout to this fragment
        binding.lifecycleOwner = this
        // set up viewModel
        binding.viewModel = viewModel

        // used for changing recyclerView-layout idiomatically
        val layoutManager = GridLayoutManager(activity, 2)
        binding.photosGrid.layoutManager = layoutManager

        binding.photosGrid.adapter = PhotosGridAdapter()

        // set up overflow menu
        setHasOptionsMenu(true)
        return binding.root
    }

    // show overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}