package com.damanjit.tflliveroadstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.damanjit.tflliveroadstatus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // get Reference to viewmodel class so the data survives orientation changes for example.
    private val mViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This will give access to all the views within tht elayout file.
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.resultView.text = "Testing"

        mViewModel.displayName.observe(this
        ) { change ->
            binding.resultView.text = change.toString()
        }

        binding.button.setOnClickListener(View.OnClickListener {
            mViewModel.getRoadStatus()
        })

    }
}