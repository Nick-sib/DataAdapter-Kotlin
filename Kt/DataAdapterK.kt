package ltd.nickolay.listclick.Kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ltd.nickolay.listclick.R


/**Adapter with Interfaces
 * onSelect
 * BeforeDelete*/

class DataAdapterK(mList: ArrayList<DataItemK>?): RecyclerView.Adapter<DataAdapterK.DataViewHolder>() {

    private var mList: ArrayList<DataItemK>

    constructor(): this(null)

    init {
        if (mList != null)
            this.mList = mList
        else
            this.mList = ArrayList()
    }

    //Interactions
    private var itemSeleted = -1

    interface OnItemListClick {
        fun onSelectItem(position: Int, title: String)
        fun beforeDeleteItem(dataItem: DataItemK): Boolean
    }

    var onItemListClickListener: OnItemListClick? = null

    fun addItem(image: Int, title: String, subtext: String) {
        if (mList.add(DataItemK(image, title, subtext))) {
            notifyItemInserted(mList.size - 1)
            if (itemSeleted != -1) {
                val priorSelected = itemSeleted
                itemSeleted = -1
                notifyItemChanged(priorSelected)
            }
        }
    }

    /////////////////////
    inner class DataViewHolder(itemView: View, onItemListClick: OnItemListClick?) : RecyclerView.ViewHolder(itemView){
        val dvhImage: ImageView = itemView.findViewById(R.id.iv_Image)
        val dvhTitle: TextView = itemView.findViewById(R.id.tv_Title)
        val dvhSubText: TextView = itemView.findViewById(R.id.tv_SubText)

        init {
           itemView.setOnClickListener(object: OnDoubleClickListenerK(){
               override fun onSingleClick(view: View) {
                   val priorClick = itemSeleted
                   itemSeleted = adapterPosition
                   notifyItemChanged(priorClick)
                   notifyItemChanged(itemSeleted)

                   if (itemSeleted != RecyclerView.NO_POSITION)
                       onItemListClick?.onSelectItem(itemSeleted, mList[itemSeleted].title)
               }

               override fun onDoubleClick(view: View) {
                   if (onItemListClick != null) {
                       val position = adapterPosition
                       if (position != RecyclerView.NO_POSITION &&
                               onItemListClick.beforeDeleteItem(mList[position])) {
                           mList.removeAt(position)
                           itemSeleted = -1
                           notifyItemRemoved(position)
                       }
                   }
               }
           })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item,
                        parent,
                        false),
                onItemListClickListener
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataItem = mList[position]
        holder.dvhImage.setImageResource(dataItem.image)
        if (itemSeleted != position)
            holder.dvhTitle.text = dataItem.title
        else
            holder.dvhTitle.text = "SELECTED"
        holder.dvhSubText.text = dataItem.subText
    }

}