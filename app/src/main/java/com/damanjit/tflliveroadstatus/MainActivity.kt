package com.damanjit.tflliveroadstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
            val errorString:String = validateString(binding.textInput.text.toString())
            if (errorString.isBlank()) {
                mViewModel.getRoadStatus(binding.textInput.text.toString())
            } else {
                binding.textInput.error = errorString
            }
        })

    }

    private fun validateString(text: String?): String {
        var result = ""
        if (text == null || text.isBlank()){
            result = "Field cannot be empty"
            return result
        }
        val regex = "^[1-9A-Za-z]*$".toRegex()
        if (!regex.matches(text)) result = "Only letters and numbers are allowed"
        return result;
    }
}