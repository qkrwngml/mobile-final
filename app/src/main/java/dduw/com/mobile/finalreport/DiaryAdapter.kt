package dduw.com.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dduw.com.mobile.finalreport.data.DiaryDto
import dduw.com.mobile.finalreport.databinding.ListItemBinding

class DiaryAdapter (val diarys : ArrayList<DiaryDto>)
    : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    val TAG = "DiaryAdapter"

    override fun getItemCount(): Int = diarys.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(itemBinding, listener, lcListener)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        var idx = diarys[position].id
        var idxImg = R.mipmap.icons01
        when (idx%6) {
            1 -> idxImg = R.mipmap.icons01
            2 -> idxImg = R.mipmap.icons02
            3-> idxImg = R.mipmap.icons03
            4 -> idxImg = R.mipmap.icons04
            5 -> idxImg = R.mipmap.icons05
        }
        holder.itemBinding.ivPhoto.setImageResource(idxImg)
        holder.itemBinding.tvText.text = diarys[position].title
        holder.itemBinding.tvDate.text = diarys[position].date
        holder.itemBinding.tvEmotion.text = diarys[position].emotion


    }

    class DiaryViewHolder(val itemBinding: ListItemBinding,
                          listener: OnItemClickListener?,
                          lcListener: OnItemLongClickListener?)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener{
                listener?.onItemClick(it, adapterPosition)
            }
            itemBinding.root.setOnLongClickListener{
                lcListener?.onItemLongClick(it, adapterPosition)
                true
            }

        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    var lcListener : OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.lcListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view : View, position : Int)
    }

    var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}