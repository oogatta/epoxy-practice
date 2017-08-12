package com.example.oogatta.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oogatta.myapplication.databinding.FooterBinding
import com.example.oogatta.myapplication.databinding.TitleBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var vmList = arrayListOf<MySweetViewModelable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vmList.add(MySweetViewModel("Hello, Ebisu!"))
        vmList.add(MySweetViewModel("from oogatta, Inc"))

        adapter = MySweetAdapter(vmList)
        my_sweet_recycler_view.adapter = adapter
    }
}

interface MySweetViewModelable {}

class MySweetViewModel(val text: String): MySweetViewModelable {

}

class MySweetAdapter(val vmList: List<MySweetViewModelable>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ViewType(val type: Int) {
        TITLE(0),
        FEED(1),
        FOOTER(2),
    }


    class TitleViewHolder(val binding: TitleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            println("TitleViewHolder.init")
        }

        fun bind(vm: MySweetViewModel) {
            binding.vm = vm
        }
    }

    class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            println("FeedViewHolder.init")
        }
    }

    class FooterViewHolder(val binding: FooterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            println("FooterViewHolder.init")
        }

        fun bind(vm: MySweetViewModel) {
            binding.vm = vm
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder!!.itemViewType) {
            ViewType.TITLE.type -> {
                println("onBindViewHolder: TITLE")
                (holder as? TitleViewHolder)?.bind(vmList[position] as MySweetViewModel)
            }
            ViewType.FEED.type -> {
                println("onBindViewHolder: FEED")
            }
            ViewType.FOOTER.type -> {
                println("onBindViewHolder: FOOTER")
                (holder as? FooterViewHolder)?.bind(vmList[position] as MySweetViewModel)
            }
        }
    }

    override fun getItemCount(): Int = vmList.size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ViewType.TITLE.type
            in 1..vmList.size - 2 -> ViewType.FEED.type
            vmList.size - 1 -> ViewType.FOOTER.type
            else -> ViewType.FEED.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TITLE.type -> TitleViewHolder(TitleBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
            ViewType.FEED.type -> FeedViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.feed, parent, false))
            ViewType.FOOTER.type -> FooterViewHolder(FooterBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
            else -> FeedViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.feed, parent, false))
        }
    }
}
