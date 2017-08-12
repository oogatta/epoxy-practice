package com.oogatta.sample.epoxy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.oogatta.sample.epoxy.databinding.FeedBinding
import com.oogatta.sample.epoxy.databinding.FooterBinding
import com.oogatta.sample.epoxy.databinding.TitleBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var vmList = arrayListOf<MySweetViewModelable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Title
        vmList.add(TitleViewModel("Hello, Ebisu!"))

        // Feed
        vmList.add(FeedViewModel("Franklin's Tower", "The main riff of \"Franklin's Tower\" was partly inspired by the chorus of Lou Reed's 1973 hit \"Walk on the Wild Side.\"[10]"))
        vmList.add(FeedViewModel("China Cat Sunflower", "\"China Cat Sunflower\" is a song performed by the Grateful Dead which was first recorded for their third studio album Aoxomoxoa. The lyrics were written by Robert Hunter and the music composed by Jerry Garcia. The song was typically sung by Jerry Garcia."))
        vmList.add(FeedViewModel("St. Stephen", "\"St. Stephen\" is a song by the Grateful Dead, written by Jerry Garcia, Phil Lesh and Robert Hunter and originally released on the 1969 studio album Aoxomoxoa.[1][2][3][4] The same year, a live version of the song was released on Live/Dead, their first concert album."))
        vmList.add(FeedViewModel("The Eleven", "\"The Eleven\" is a song by American rock band The Grateful Dead. It was written by long-time lyricist Robert Hunter and bassist Phil Lesh. The title of the song is a direct reference to its time signature, 11/8."))
        vmList.add(FeedViewModel("Dark Star", "\"Dark Star\" is a song released as a single by the Grateful Dead on Warner Bros. records in 1968. It was written by lyricist Robert Hunter and composed by lead guitarist Jerry Garcia;[1] however, compositional credit is sometimes extended to include Phil Lesh, Bill Kreutzmann, Mickey Hart, Ron \"Pigpen\" McKernan, and Bob Weir.[2][3]"))

        // Footer
        vmList.add(FooterViewModel("from oogatta, Inc"))

        adapter = MySweetAdapter(vmList)
        my_sweet_recycler_view.adapter = adapter
    }
}

interface MySweetViewModelable {}

class TitleViewModel(val text: String): MySweetViewModelable

class FeedViewModel(
        val title: String,
        val text: String
) : MySweetViewModelable

class FooterViewModel(val text: String): MySweetViewModelable

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

        fun bind(vm: TitleViewModel) {
            binding.vm = vm
        }
    }

    class FeedViewHolder(val binding: FeedBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            println("FeedViewHolder.init")
        }

        fun bind(vm: FeedViewModel) {
            binding.vm = vm
        }
    }

    class FooterViewHolder(val binding: FooterBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            println("FooterViewHolder.init")
        }

        fun bind(vm: FooterViewModel) {
            binding.vm = vm
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder!!.itemViewType) {
            ViewType.TITLE.type -> {
                println("onBindViewHolder: TITLE")
                (holder as? TitleViewHolder)?.bind(vmList[position] as TitleViewModel)
            }
            ViewType.FEED.type -> {
                println("onBindViewHolder: FEED")
                (holder as? FeedViewHolder)?.bind(vmList[position] as FeedViewModel)
            }
            ViewType.FOOTER.type -> {
                println("onBindViewHolder: FOOTER")
                (holder as? FooterViewHolder)?.bind(vmList[position] as FooterViewModel)
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
            ViewType.FEED.type -> FeedViewHolder(FeedBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
            ViewType.FOOTER.type -> FooterViewHolder(FooterBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
            else -> FeedViewHolder(FeedBinding.inflate(LayoutInflater.from(parent!!.context), parent, false))
        }
    }
}
