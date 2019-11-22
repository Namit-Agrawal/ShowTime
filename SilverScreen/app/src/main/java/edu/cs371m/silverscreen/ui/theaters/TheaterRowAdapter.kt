package edu.cs371m.silverscreen.ui.theaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.TheatrePost
import kotlinx.android.synthetic.main.theaters_row.view.*

class TheaterRowAdapter(private val viewModel: TheatersViewModel):RecyclerView.Adapter<TheaterRowAdapter.VH>() {
    private var theaters = listOf<TheatrePost>()
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var theatre = itemView.findViewById<TextView>(R.id.theatre_name)
        var address = itemView.findViewById<TextView>(R.id.address)
        var distance = itemView.findViewById<TextView>(R.id.distance)
        var post = itemView.findViewById<LinearLayout>(R.id.post)

        init{
            post.setOnClickListener {
                val position = adapterPosition
            }
        }
        fun bind(item: TheatrePost?) {
            if (item == null) {
                return;
            }
            theatre.text = item.name
            address.text = item.loc.address.st
            distance.text = "%.2f".format(item.loc.distance) +" mi"

        }


    }

    override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(theaters[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.theaters_row, parent, false)
        return VH(itemView)
    }
    override fun getItemCount(): Int {
        return theaters.size
    }
    fun submitList(items: List<TheatrePost>) {
        theaters = items
        notifyDataSetChanged()
    }
}
