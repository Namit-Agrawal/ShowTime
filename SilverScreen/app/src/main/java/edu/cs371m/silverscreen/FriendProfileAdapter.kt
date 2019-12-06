package edu.cs371m.silverscreen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import edu.cs371m.silverscreen.Glide.Glide
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost

class FriendProfileAdapter(private val viewModel: FriendProfileViewModel): RecyclerView.Adapter<FriendProfileAdapter.VH>() {
    private var favs = listOf<MoviePost>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieTitleText = itemView.findViewById<TextView>(R.id.movie_name)
        var durationText = itemView.findViewById<TextView>(R.id.duration)
        var actors = itemView.findViewById<TextView>(R.id.pop_actors)
        var date = itemView.findViewById<TextView>(R.id.date)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var fav = itemView.findViewById<ImageButton>(R.id.favBut)


        fun bind(item: MoviePost?) {
            if (item == null) {
                return;
            }
            fav.visibility = View.INVISIBLE
            movieTitleText.text = item.movieName
            if(item.duration!=null)
                durationText.text = item.duration.substring(3,4)+" hr "+item.duration.substring(5,7)+" min"

            if(item.cast!=null) {
                actors.text = item.getMovieList(item.cast)
            }
            else
            {
                actors.isInvisible = true
            }
            date.isInvisible=true
            viewModel.netFetchImage("http://demo.tmsimg.com/"+item.img?.image_url, thumbnail, false)




            //get the dipslay name instead of the uid



        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bind(favs[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return VH(itemView)
    }
    override fun getItemCount(): Int {
        return favs.size
    }
    fun submitList(items: List<MoviePost>) {
        favs = items
        notifyDataSetChanged()
    }
}