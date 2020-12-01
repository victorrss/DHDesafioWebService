package br.com.eaglecode.desafiowebservice.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.eaglecode.desafiowebservice.R
import br.com.eaglecode.desafiowebservice.model.Result
import br.com.eaglecode.desafiowebservice.service.RetrofitBuilder
import br.com.eaglecode.desafiowebservice.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickItemListener {
    var listHQs = ArrayList<Result>()
    private lateinit var adapter: HQAdapter
    var offset = 0
    val limit = 20

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(RetrofitBuilder.getService()!!, application) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.marvel_logo_actionbar)
        supportActionBar?.setTitle("")

        adapter = HQAdapter(listHQs, this)

        rvHQ.layoutManager = GridLayoutManager(this, 3)

        rvHQ.adapter = adapter

        progress_bar.visibility = View.VISIBLE

        viewModel.getHQs()

        viewModel.hqs.observe(this) {
            listHQs.addAll(it.results)
            adapter = HQAdapter(listHQs, this)
            rvHQ.adapter = adapter
            progress_bar.visibility = View.INVISIBLE
        }

        scroll_view.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    offset += limit
                    progress_bar.visibility = View.VISIBLE
                    viewModel.getHQs(offset)
                }
            }
        )
    }

    override fun OnClickItem(position: Int) {
        val hq = listHQs[position]
        val intent = Intent(this, HQDetailActivity::class.java)
        intent.putExtra("hq", hq.id)
        startActivity(intent)
    }
}