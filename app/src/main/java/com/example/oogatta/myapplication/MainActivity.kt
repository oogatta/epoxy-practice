package com.example.oogatta.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var vm = arrayListOf<MySweetViewModelable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.add(MySweetViewModel("Hello, Ebisu!"))

        adapter = MySweetAdapter(vm)
        my_sweet_recycler_view.adapter = adapter
    }
}

interface MySweetViewModelable {}

class MySweetViewModel(val text: String): MySweetViewModelable {

}

class MySweetAdapter(val vm: List<MySweetViewModelable>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewType(val type: Int) {
        TITLE(0),
        FEED(1),
        FOOTER(2),
    }


    class TitleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            println("TitleViewHolder.init")
        }
    }

    class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            println("FeedViewHolder.init")
        }
    }

    class FooterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            println("FooterViewHolder.init")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder!!.itemViewType) {
            ViewType.TITLE.type -> {
                println("onBindViewHolder: TITLE")
            }
            ViewType.FEED.type -> {
                println("onBindViewHolder: FEED")
            }
            ViewType.FOOTER.type -> {
                println("onBindViewHolder: FOOTER")
            }
        }
    }

    override fun getItemCount(): Int = vm.size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewType.TITLE.type
            in 1..vm.size - 2 -> ViewType.FEED.type
            vm.size - 1 -> ViewType.FOOTER.type
            else -> ViewType.FEED.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TITLE.type -> TitleViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.title, parent, false))
            ViewType.FEED.type -> FeedViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.feed, parent, false))
            ViewType.FOOTER.type -> FooterViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.footer, parent, false))
            else -> FeedViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.feed, parent, false))
        }
    }
}
