package br.com.eaglecode.desafiowebservice.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.eaglecode.desafiowebservice.model.HQResponse
import br.com.eaglecode.desafiowebservice.service.HQService
import br.com.eaglecode.desafiowebservice.service.RetrofitBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(hqService: HQService, application: Application) :
    AndroidViewModel(application) {

    val hqs = MutableLiveData<HQResponse>()
    private val context = getApplication<Application>().applicationContext

    fun getHQs(offset: Int = 0) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitBuilder.getService()!!.getHQs(offset)
                val results = response.get("data")
                val comics = Gson().fromJson(
                    results,
                    object : TypeToken<HQResponse>() {}.type
                ) as HQResponse
                hqs.value = comics
            } catch (e: Exception) {
                Log.w("MainViewModel", e.toString())
                Toast.makeText(context, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
            }

        }
    }
}