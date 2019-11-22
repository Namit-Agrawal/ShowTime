package edu.cs371m.silverscreen.ui.theaters

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.TheatrePost
import kotlinx.android.synthetic.main.theaters_row.view.*

class TheaterRowAdapter(private val viewModel: TheatersViewModel):RecyclerView.Adapter<TheaterRowAdapter.VH>() {
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

        }


    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
