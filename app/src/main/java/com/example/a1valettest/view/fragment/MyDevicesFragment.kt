package com.example.a1valettest.view.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentMyDevicesBinding
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyDevicesFragment : BaseFragment<FragmentMyDevicesBinding>(R.layout.fragment_my_devices) {


    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    @Inject
    lateinit var myDevicesAdapter: MyDevicesAdapter

    private lateinit var newMyDeviceContentList: MutableList<MyDeviceContent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        getMyDevices()
    }

    private fun getMyDevices() {
        binding.recyclerViewFragmentMyDevices.apply {
            adapter = myDevicesAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            deviceDatabaseViewModel.getDevices
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    newMyDeviceContentList = it.toMutableList()
                    myDevicesAdapter.differMyDeviceContent.apply {
                        submitList(newMyDeviceContentList)

                        if (newMyDeviceContentList.isEmpty())
                            binding.textEmptyMyDeviceList.visibility = VISIBLE
                    }
                    Log.i("TestMyDeviceFragment", it.toString())
                }
        }
    }


    private fun handleToolbar() {
        binding.toolbarFragmentMyDevices.apply {
            setNavigationOnClickListener {
                activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
            }

            val menuItem = menu.findItem(R.id.search)
            val searchView = menuItem.actionView as SearchView

            searchView.apply {
                queryHint = "Search devices"

                binding.apply {
                    setOnSearchClickListener {
                        appBarLayoutFragmentMyDevices.setExpanded(false, true)

                        val anim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                        anim.setAnimationListener(null)
                        menu.forEach {
                            it.actionView.startAnimation(anim)
                        }
                    }

                    setOnQueryTextFocusChangeListener { _, hasFocus ->
                        val fullyExpanded: Boolean =
                            (appBarLayoutFragmentMyDevices.height - appBarLayoutFragmentMyDevices.bottom) == 0
                        if (!hasFocus) {
                            if (fullyExpanded) {
                                appBarLayoutFragmentMyDevices.setExpanded(true, true)
                            }
                        }
                    }
                }

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false
                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newMyDeviceContentList.isNotEmpty())
                            filterDeviceContentList(text = newText!!)
                        return false
                    }
                })
            }
        }
    }

    private fun filterDeviceContentList(text: String) {
        val bindingNoResult = binding.layoutSearchNoResult

        val newList = mutableListOf<MyDeviceContent>()
        newMyDeviceContentList.forEach {
            if (it.title.lowercase(Locale.getDefault()).contains(text))
                newList.add(it)
        }

        bindingNoResult.linearNoResult.apply {
            if (newList.isEmpty()) {
                visibility = VISIBLE
                ValueAnimator.ofFloat(0.92f, 1f).apply {
                    duration = 300

                    addUpdateListener {
                        scaleX = it.animatedValue as Float
                        scaleY = it.animatedValue as Float
                    }
                    start()
                }
            } else
                visibility = View.GONE
        }

        myDevicesAdapter.differMyDeviceContent.submitList(newList)
    }
}