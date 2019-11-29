package edu.cs371m.silverscreen.ui.MovieTimes

import android.view.LayoutInflater
import android.view.View
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.Times
import edu.cs371m.silverscreen.ui.movie_times.MovieTimesViewModel
import edu.cs371m.silverscreen.ui.movie_times.TheatreTimes


class MovieTimesAdapter(private val MoveTimesViewMode: MovieTimesViewModel) :
    RecyclerView.Adapter<MovieTimesAdapter.VH>() {
    private lateinit var showtimes  : List<TheatreTimes?>

    inner class VH(itemView: View)  : RecyclerView.ViewHolder(itemView) {
        var theatreName = itemView.findViewById<TextView>(R.id.theatre_name)
        var r0 = itemView.findViewById<TableRow>(R.id.r0)
        var r1 = itemView.findViewById<TableRow>(R.id.r1)
        var r2 = itemView.findViewById<TableRow>(R.id.r2)
        var r3 = itemView.findViewById<TableRow>(R.id.r3)
        var r4 = itemView.findViewById<TableRow>(R.id.r4)
        var r5 = itemView.findViewById<TableRow>(R.id.r5)


        fun bind(item :TheatreTimes?) {
            if (item == null) return
            var rowList = mutableListOf<TableRow>()
            rowList.add(r0)
            rowList.add(r1)
            rowList.add(r2)
            rowList.add(r3)
            rowList.add(r4)
            rowList.add(r5)

            theatreName.text = item.theatreName
            var times = item.times

            var size = times.size
            var buttonsList = mutableListOf<Button>()
            var i = 0
            var rowNum=0
            while (i < size) {
                var button = Button(itemView.context)
                button.text= times[i].substring(11)
                //TODO: what is index????? how to insure no overlapping?
                buttonsList.add(button)
                rowList[rowNum].addView(button, i%4)
                i++
                if (i % 4 == 0) {
                    rowNum++
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (showtimes == null) {
            return 0
        }
        return showtimes.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(showtimes[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_times_row, parent, false)
        return VH(itemView)
    }

    fun submitList(times: List<TheatreTimes?>) {
        showtimes = times
        notifyDataSetChanged()

    }
}
