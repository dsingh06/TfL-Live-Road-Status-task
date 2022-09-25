package com.damanjit.tflliveroadstatusimport android.util.Logimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport androidx.lifecycle.ViewModelimport androidx.lifecycle.viewModelScopeimport com.damanjit.tflliveroadstatus.network.TfLApiimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.launchimport kotlinx.coroutines.withContextclass MainViewModel : ViewModel() {    private val TAG  = "MA-ViewModel";    private val _busy = MutableLiveData<Boolean>()    val busy: LiveData<Boolean>        get() = _busy    private val _dName = MutableLiveData<String>()    val displayName: LiveData<String>        get() = _dName    private val _sSeverity = MutableLiveData<String>()    val statusSeverity: LiveData<String>        get() = _sSeverity    private val _sSeverityDescription = MutableLiveData<String>()    val statusSeverityDescription: LiveData<String>        get() = _sSeverityDescription    fun getRoadStatus(roadName: String) {        // Couroutine to fetch data from the API        viewModelScope.launch {            try{                //TODO                // Update variables                _busy.value = true                val result = TfLApi.retrofitService.getRoadStatus(roadName)                Log.d(TAG, (result[0].displayName))                Log.d(TAG, (result[0].statusSeverity))                Log.d(TAG, (result[0].statusSeverityDescription))                _dName.value = "Got result"                _sSeverity.value = "Test2"                _sSeverityDescription.value = "Test3"                _busy.value = false            } catch (e:Exception) {                //TODO                // Decide how to show error                println(e)                _busy.value = false            }        }    }}