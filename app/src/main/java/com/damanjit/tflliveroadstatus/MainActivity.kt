package com.damanjit.tflliveroadstatus

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damanjit.tflliveroadstatus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // get Reference to viewmodel class so the data survives orientation changes for example.
    private val mViewModel:MainViewModel by viewModels()
    private val TAG  = "Main Activity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        mViewModel.dataObject.observe(this){
            if (mViewModel.dataObject.value?.httpStatusCode == 404){
                println("Error happened")
                binding.resultView.text = mViewModel.dataObject.value!!.message
            } else {
                binding.resultView.text = "ROAD NAME:\n ${mViewModel.dataObject.value!!.displayName}\n\nROAD STATUS:\n ${mViewModel.dataObject.value!!.statusSeverity}\n\nROAD STATUS DESCRIPTION:\n ${mViewModel.dataObject.value!!.statusSeverityDescription}"
            }
        }

        binding.button.setOnClickListener(View.OnClickListener {
            hideKeyboard()
            val errorString:String = validateString(binding.textInput.text.toString())
            if (errorString.isBlank()) {
                binding.resultView.text = ""
                mViewModel.getRoadStatus(binding.textInput.text.toString())
            } else {
                binding.textInput.error = errorString
            }
        })
    }


    private fun hideKeyboard() {
        try{
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: java.lang.NullPointerException){

        }
    }

    private fun validateString(text: String?): String {
        var result = ""
        if (text == null || text.isBlank()){
            result = "Field cannot be empty"
            return result
        }
        val regex = "^[0-9A-Za-z]*$".toRegex()
        if (!regex.matches(text)) result = "Only letters and numbers are allowed"
        return result;
    }
}