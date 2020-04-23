package com.aleksandarmironov.irishrail.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aleksandarmironov.irishrail.R
import com.aleksandarmironov.irishrail.databinding.ActivityMainBinding
import com.aleksandarmironov.irishrail.ui.recycler.TrainsAdapter


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.firstStation.observe(this, Observer {
            it?.let {
                binding.startStationText.text = it
            }
        })

        viewModel.destinationsStation.observe(this, Observer {
            it?.let {
                binding.tripText.text =
                    String.format(getString(R.string.trip), viewModel.firstStation.value, it)
            }
        })

        binding.rotateBtn.setOnClickListener {
            viewModel.rotateDirection()
        }

        binding.refreshBtn.setOnClickListener {
            viewModel.loadData()
        }

        viewModel.firstStationError.observe(this, Observer {
            it?.let {
                binding.firstStationErrText.visibility = it
            }
        })

        viewModel.brayStationError.observe(this, Observer {
            it?.let {
                binding.brayStationErrText.visibility = it
            }
        })

        viewModel.internetConnectionError.observe(this, Observer {
            it?.let {
                binding.noInternetImg.visibility = it
            }
        })

        val firstStationAdapter = TrainsAdapter()
        binding.firstStationRecycler.adapter = firstStationAdapter
        viewModel.firstStationTrainList.observe(this, Observer {
            it?.let { firstStationAdapter.updateList(it) }
        })

        val adapterBray = TrainsAdapter()
        binding.brayStationRecycler.adapter = adapterBray
        viewModel.brayStationTrainList.observe(this, Observer {
            it?.let { adapterBray.updateList(it) }
        })

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewModel.internetConnectionStatus(connectivityManager.activeNetwork != null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        viewModel.internetConnectionStatus(true)
                    }
                    override fun onLost(network: Network?) {
                        viewModel.internetConnectionStatus(false)
                    }
                })
            }
        }

    }
}
