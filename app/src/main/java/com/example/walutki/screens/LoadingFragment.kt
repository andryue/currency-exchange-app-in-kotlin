package com.example.walutki.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.walutki.R
import com.example.walutki.currencies_from_api.retrofit.RetrofitClient
import com.example.walutki.screens.view_models.LoadingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoadingFragment : Fragment() {


    private lateinit var loadingViewModel: LoadingViewModel


    companion object {
        fun newInstance() = LoadingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loading_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingViewModel = ViewModelProvider(requireActivity()).get(LoadingViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = RetrofitClient.instance.getCurrencyAsync().await().body()
                Log.d("TAG", result!!.rates.pLN.toString())
                requireActivity().runOnUiThread {
                    loadingViewModel.setCurrency(result)
                    findNavController().navigate(R.id.action_loadingFragment_to_valuesFragment)
                }
            } catch (ex: Exception) {
                Log.d("TAG", ex.message)
            }
        }

    }


}