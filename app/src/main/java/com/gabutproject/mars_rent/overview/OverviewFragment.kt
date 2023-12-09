package com.gabutproject.mars_rent.overview

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabutproject.mars_rent.R
import com.gabutproject.mars_rent.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {

    private lateinit var binding: OverviewFragmentBinding

    private val viewModel: OverviewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        return binding.root
    }

    private fun updateLiveData() {
        viewModel.selectedItemData.observe(viewLifecycleOwner) { marsProperty ->
            if (marsProperty != null) {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(marsProperty)
                )
                viewModel.onSelectedItemNavigateComplete()
            }
        }
    }
}