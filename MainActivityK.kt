package ltd.nickolay.listclick

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ltd.nickolay.listclick.Kt.DataAdapterK
import ltd.nickolay.listclick.Kt.DataItemK
import kotlin.random.Random


class MainActivityK: AppCompatActivity(), DataAdapterK.OnItemListClick {

    val dataAdapter = DataAdapterK()

    //Variables for List
    var var_Title: Int = 0
    val var_SubText = arrayOf("Статически", "типизированный", "язык", "программирования",
            "работающий", "поверх JVM", "разрабатываемый", "компанией",
            "JetBrains", "Также")
    val var_Image = intArrayOf(R.drawable.ic_0, R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3,
            R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8,
            R.drawable.ic_9)

    var toast: Toast? = null

    companion object{
        private const val TAG = "myLOG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setup listView
        val recyclerView = findViewById<RecyclerView>(R.id.rv_List)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dataAdapter

        //TODO save data on Rotate screen
        AddItem(5)
        dataAdapter.onItemListClickListener  = this
    }

    fun AddItem(count: Int) {
        for (i in 0 until count) {
            dataAdapter.addItem(
                    var_Image[Random.nextInt(10)],
                    "Title ${var_Title++}",
                    var_SubText[Random.nextInt(10)])
        }
    }

    override fun onSelectItem(position: Int, title: String) {
        toast?.cancel()
        toast = Toast.makeText(this, "Clicked $title at position $position", Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun beforeDeleteItem(dataItem: DataItemK): Boolean {
        toast?.cancel()
        toast = Toast.makeText(this, "Delete item ${dataItem.title}", Toast.LENGTH_LONG)
        toast!!.show()
        Log.d(TAG, "beforeDeleteItem: $dataItem")
        //TODO add dialog for delete
        return true
    }

    fun AddFun(view: View) {
        when (view.id) {
            R.id.b_add_1 -> AddItem(1)
            R.id.b_add_10 -> AddItem(10)
            else -> Log.d(TAG, "AddFun: Error Add Click")
        }
    }
}