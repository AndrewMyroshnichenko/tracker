package com.example.tracker.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tracker.databinding.FragmentMapBinding
import com.example.tracker.mvi.fragments.HostedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : HostedFragment<MapContract.View, MapContract.ViewModel, MapContract.Host>(),
    MapContract.View {

    private var bind: FragmentMapBinding? = null

    override fun createModel(): MapContract.ViewModel {
        val viewModel: MapViewModel by viewModels()
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentMapBinding.inflate(layoutInflater)
        return (bind?.root)
    }

}