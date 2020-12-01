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

class HQDetailViewModel(hqService: HQService, application: Application) : AndroidViewModel(application) {
    val hq = MutableLiveData<HQResponse>()
    private val context = getApplication<Application>().applicationContext
    fun getHQ(comicId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitBuilder.getService()!!.getHQ(comicId)
                val results = response.get("data")
                val comic = Gson().fromJson(
                    results,
                    object : TypeToken<HQResponse>() {}.type
                ) as HQResponse
                hq.value = comic
            } catch (e: Exception) {
                Log.e(this.javaClass.name, e.toString())
                Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show()
            }
        }

    }
}
