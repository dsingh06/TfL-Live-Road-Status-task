package com.damanjit.tflliveroadstatusimport android.util.Logimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport androidx.lifecycle.ViewModelimport androidx.lifecycle.viewModelScopeimport com.damanjit.tflliveroadstatus.network.TfLApiimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.launchimport kotlinx.coroutines.withContextimport java.util.concurrent.TimeoutException/** * This class does all the data-handling functions and is independent of the Activity orientation lifecycle state. * It contains link to the UI Controller class and does not hold any direct reference to any of the UI elements. */class MainViewModel : ViewModel() {    // To help with Log statements    private val TAG  = "MA-ViewModel";    // Reference to the dataclass object    private val _dataObject = MutableLiveData<DataClassRoadStatus>()    val dataObject: LiveData<DataClassRoadStatus>        get() = _dataObject    // This helps hide the button    private val _busy = MutableLiveData<Boolean>()    val busy: LiveData<Boolean>        get() = _busy    fun getRoadStatus(roadName: String) {        // Coroutine to fetch data from the API        viewModelScope.launch {            try{                _busy.value = true                println("Getting result")                _dataObject.value = TfLApi.retrofitService.getRoadStatus(roadName)[0]                _busy.value = false            } catch (e:TimeoutException) {                _dataObject.value = DataClassRoadStatus(message = "Time-out occurred while waiting for a response from the server. Please try again.", httpStatusCode = 404) //TODO Change the status code to something more useful.                _busy.value = false            } catch (e:Exception) {                println(e)                //Todo - use error received from the server, but not sure why exception is getting throw instead of loading servers response                _dataObject.value = DataClassRoadStatus(message = e.toString(), httpStatusCode = 404)                _busy.value = false            }        }    }}