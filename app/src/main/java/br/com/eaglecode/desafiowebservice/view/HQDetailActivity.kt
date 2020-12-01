package br.com.eaglecode.desafiowebservice.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.eaglecode.desafiowebservice.R
import br.com.eaglecode.desafiowebservice.model.HQResponse
import br.com.eaglecode.desafiowebservice.service.RetrofitBuilder
import br.com.eaglecode.desafiowebservice.viewmodel.HQDetailViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_hq_detail.*
import java.text.SimpleDateFormat
import java.util.*

class HQDetailActivity : AppCompatActivity() {
    private lateinit var hq: HQResponse
    private val viewModel by viewModels<HQDetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HQDetailViewModel(RetrofitBuilder.getService()!!, application) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq_detail)
        supportActionBar?.hide()

        val comicId: Int = intent.getIntExtra("hq", 0).toInt()

        if (comicId == 0) {
            Toast.makeText(this, "Comic not found", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        ivBack.setOnClickListener { onBackPressed() }
        try {
            viewModel.getHQ(comicId)
        } catch (e: Exception) {
            Log.i(this.javaClass.name, e.message.toString())
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_LONG).show()
        }

        viewModel.hq.observe(this) {
            hq = it
            val comic = hq.results[0]
            tvTitle.text = comic.title

            tvDescription.text = comic.description

            if (comic.pageCount != 0)
                tvPages.text = comic.pageCount.toString()
            else tvPages.text = "No data"

            if (comic.prices[0].price != 0.0)
                tvPrice.text = comic.prices[0].price.toString()
            else tvPrice.text = "No data"

            val jsDate = (comic.dates.find { s -> s.type == "focDate" })?.date.toString()
            if (jsDate.toCharArray()[0] != '-') {
                val javaDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(jsDate)
                val dateFmt = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
                val date = dateFmt.format(javaDate)
                tvPublish.text = date
            } else tvPublish.text = "No data"

            Glide.with(this)
                .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fallback(R.drawable.ic_baseline_image_not_supported_24)
                .into(ivCover)

            Glide.with(this)
                .load("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fallback(R.drawable.ic_baseline_image_not_supported_24)
                .into(ivBanner)

        }

    }
}