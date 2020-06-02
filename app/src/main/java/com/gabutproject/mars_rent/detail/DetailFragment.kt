package com.gabutproject.mars_rent.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gabutproject.mars_rent.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        // get data from selectedItem
        val marsProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedMarsItem
        // get application context
        val application = requireNotNull(activity).application

        Log.i("detailfragment", "hello || $marsProperty")
        val viewModelFactory = DetailViewModelFactory(marsProperty, application)

        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.lifecycleOwner = this

        return binding.root
    }
}