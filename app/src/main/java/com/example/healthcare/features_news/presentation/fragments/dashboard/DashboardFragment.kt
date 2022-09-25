package com.example.healthcare.features_news.presentation.fragments.dashboard

import android.os.Bundle
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
import com.example.healthcare.common.Resource
import com.example.healthcare.common.Utility.LOGOUT
import com.example.healthcare.common.Utility.greeting
import com.example.healthcare.common.gone
import com.example.healthcare.common.toast
import com.example.healthcare.common.visible
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
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
        val mAdapter = DashboardAdapter()
        binding.rv.apply {
            setHasFixedSize(true)
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
                is Resource.Failure -> {
                    binding.shimmer.gone()
                    toast(response.message.toString())
                }
            }
        }.launchIn(lifecycleScope)
        binding.apply {
            tvGreeting.text = greeting()
            tvEmail.text = args.email
        }

    }
}