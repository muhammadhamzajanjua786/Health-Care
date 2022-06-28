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
import com.example.healthcare.core.utils.AppUtils.LOGOUT
import com.example.healthcare.core.utils.AppUtils.greeting
import com.example.healthcare.core.utils.AppUtils.showToast
import com.example.healthcare.features_news.data.local.SPDatabase
import com.example.healthcare.features_news.data.remote.response.Result
import com.example.healthcare.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment(), DashboardAdapter.OnItemClickListener {

    @Inject
    lateinit var spDatabase: SPDatabase
    private val args: DashboardFragmentArgs by navArgs()
    private val viewModel: DashboardViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callGetDataApi()
        viewModel.flow.onEach { list ->
            setUpRecyclerView(list)
        }.launchIn(lifecycleScope)
        binding.apply {
            tvGreeting.text = greeting()
            tvEmail.text = args.email
            btnLogout.setOnClickListener {
                spDatabase.setLogin(false)
                viewModel.deleteData()
                val action = DashboardFragmentDirections.navigateToLoginFragment()
                findNavController().navigate(action)
                showToast(requireContext(),LOGOUT)
            }
        }
    }

    private fun setUpRecyclerView(list: List<Result>) {
        val mAdapter = DashboardAdapter(requireContext(), list,this)
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = mAdapter
    }

    private fun callGetDataApi() {
        viewModel.getData(requireContext(),binding)
    }

    override fun setOnItemClickListener(data: Result) {
        val bundle = Bundle()
        bundle.putParcelable("data",data)
        findNavController().navigate(R.id.navigateToDetailFragment,bundle)
    }
}