package com.damanjit.tflliveroadstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.damanjit.tflliveroadstatus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // get Reference to viewmodel class so the data survives orientation changes for example.
    private val mViewModel:MainViewModel by viewModels()
    private val TAG  = "Main Activity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This will give access to all the views within the layout file.
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.INVISIBLE

        mViewModel.busy.observe(this
        ) { busyResult ->
            if (busyResult){
                binding.button.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.button.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        mViewModel.displayName.observe(this
        ) { change ->
            binding.resultView.text = change.toString()
        }

        binding.button.setOnClickListener(View.OnClickListener {
            mViewModel.getRoadStatus()
        })

    }
}