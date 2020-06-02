package com.gabutproject.mars_rent.overview

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabutproject.mars_rent.R
import com.gabutproject.mars_rent.databinding.OverviewFragmentBinding
import com.gabutproject.mars_rent.network.MarsApiFilter

class OverviewFragment : Fragment() {

    private lateinit var binding: OverviewFragmentBinding
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false)

        // set lifecycle owner if the layout to this fragment
        binding.lifecycleOwner = this
        // set up viewModel
        binding.viewModel = viewModel

        // used for changing recyclerView-layout idiomatically
        val layoutManager = GridLayoutManager(activity, 2)
        binding.photosGrid.layoutManager = layoutManager

        // run after item clicked
        binding.photosGrid.adapter =
            PhotosGridAdapter(PhotosGridAdapter.MarsRealEstateListener { marsProperty ->
                viewModel.onSelectedItemNavigate(marsProperty)
            })

        updateLiveData()

        // set up overflow menu
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateLiveData() {
        viewModel.selectedItemData.observe(viewLifecycleOwner, Observer { marsProperty ->
            if (marsProperty != null) {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(marsProperty)
                )
                viewModel.onSelectedItemNavigateComplete()
            }
        })
    }

    // show overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateListFilter(
            when (item.itemId) {
                R.id.SHOW_RENT -> MarsApiFilter.SHOW_RENT
                R.id.SHOW_BUY -> MarsApiFilter.SHOW_BUY
                else -> MarsApiFilter.SHOW_ALL
            }
        )

        return true
    }
}