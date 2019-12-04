package edu.cs371m.silverscreen.ui.trailer


import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.TheatrePost
import edu.cs371m.silverscreen.api.api.videoPost
import edu.cs371m.silverscreen.ui.MovieTimes.MovieTimesAdapter
import edu.cs371m.silverscreen.ui.movie_times.MovieTimesViewModel
import edu.cs371m.silverscreen.ui.movie_times.TheatreTimes
import edu.cs371m.silverscreen.ui.movies.OneMoviePost
import edu.cs371m.silverscreen.ui.theaters.OneTheatrePost

class TrailersAdapter(private val viewModel: TrailersViewModel, private val poster_url: String) :
    RecyclerView.Adapter<TrailersAdapter.VH>() {

    private  var videos  = listOf<videoPost>()

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var video = itemView.findViewById<TextView>(R.id.video_name)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var wholePost = itemView.findViewById<LinearLayout>(R.id.post)

        init{
            wholePost.setOnClickListener {

                val position = adapterPosition
                TrailersViewModel.doVideoPost(itemView.context, videos[position].video_key)

            }
        }

        fun bind(item: videoPost?)
        {
            if(item == null)
                return;
            video.text = item.name
            viewModel.netFetchImage("http://demo.tmsimg.com/" + poster_url, thumbnail, false)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.trailer_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       // Log.d("site checking", videos[position].site)
        if (videos[position].site.equals("YouTube")) {
            holder.bind(videos[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        if (videos == null) {
            return 0
        }
        return videos.size
    }
    fun submitList(it: List<videoPost>) {
        videos = it
        notifyDataSetChanged()
    }
}