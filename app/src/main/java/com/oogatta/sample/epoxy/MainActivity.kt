package com.oogatta.sample.epoxy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.TypedEpoxyController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val controller = MySweetController()
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

        my_sweet_recycler_view.adapter = controller.adapter

        updateController()
    }

    private fun updateController() {
        controller.setData(vmList)
    }
}

interface MySweetViewModelable {}

class TitleViewModel(val text: String): MySweetViewModelable

class FeedViewModel(
        val title: String,
        val text: String
) : MySweetViewModelable

class FooterViewModel(val text: String): MySweetViewModelable


// buildModels に欲しい引数が1つなら TypedEpoxyController
// 2つなら Typed2EpoxyController 。4まである
class MySweetController: TypedEpoxyController<List<MySweetViewModelable>>() {

    // 自動生成モデルをそのまま使える場合は AutoModel でアノテートすると buildModels の中で簡単に書ける
    @AutoModel lateinit var title: TitleBindingModel_
    @AutoModel lateinit var footer: FooterBindingModel_

    override fun buildModels(data: List<MySweetViewModelable>?) {
        data ?: return

        // implicitlyAddAutoModels を true にしていれば、
        // BindingModel_ にデータをアサインするだけで表示される
        title.vm(data.first() as TitleViewModel)


        // シンプルな view の連続であればここでそのままやってしまう
        // データの多い複雑な view 場合は ModelGroup が使える
        data.subList(1, data.size - 1).forEachIndexed { index, vm ->
            FeedBindingModel_()
                .id(index)
                .vm(vm as FeedViewModel)
                .addTo(this)
        }

        footer.vm(data.last() as FooterViewModel)
    }
}
