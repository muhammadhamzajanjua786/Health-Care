package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcare.R
import com.example.healthcare.common.*
import com.example.healthcare.common.Utility.LOGOUT
import com.example.healthcare.common.Utility.greeting
import com.example.healthcare.databinding.FragmentDashboardBinding
import com.example.healthcare.features_news.data.local.SPDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    @Inject
    lateinit var spDatabase: SPDatabase
    private val args: DashboardFragmentArgs by navArgs()
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var mAdapter:DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnLogout.setOnClickListener {
            spDatabase.setLogin(false)
            viewModel.deleteRecords()
            val action = DashboardFragmentDirections.navigateToLoginFragment()
            findNavController().navigate(action)
            toast(LOGOUT)
        }
    }

    private fun init() {
        viewModel.getRecords()
        mAdapter = DashboardAdapter()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        mAdapter.onItemClick = { data ->
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            findNavController().navigate(R.id.navigateToDetailFragment, bundle)
        }

        viewModel.flow.onEach { response ->
            when (response) {
                is Resource.Success -> {
                    binding.shimmer.gone()
                    binding.ll.visible()
                    mAdapter.updateList(response.data!!)
                }
                is Resource.Error -> {
                    binding.shimmer.gone()
                    toast(response.exception.getError(requireContext()))
                }
            }
        }.launchIn(lifecycleScope)
        binding.apply {
            tvGreeting.text = greeting()
            tvEmail.text = args.email
        }

    }
}