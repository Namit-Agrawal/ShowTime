package edu.cs371m.silverscreen.ui.movie_times

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.Times
import edu.cs371m.silverscreen.ui.MovieTimes.MovieTimesAdapter

class MovieTimes : Fragment() {

    companion object {
        fun newInstance(moviePost: MoviePost) : MovieTimes {
            val times = MovieTimes()
            val b = Bundle()
            b.putParcelable("moviepost", moviePost)
            times.arguments = b
            return times
        }    }

    private lateinit var viewModel: MovieTimesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(MovieTimesViewModel::class.java)

        val root = inflater.inflate(R.layout.movie_times_fragment, container, false)
       // viewModel.netSubRefresh()

        val adapter = initRecyclerView(root)
        val post = this.arguments?.getParcelable<MoviePost>("moviepost")
        val times = post!!.showtimes
//        [ (a, 10:30) , (a, 12:00), (b, 1:00) ]
//
//        [ (a, [10:30, 12:00]) , (b, [1:00])  ]

        var correct = organizeShowtimes(times)
        var i = 0
        while(i < correct.size)
        {
//            Log.d("Theatre Name", correct[i].theatreName)
//            Log.d("List of times", correct[i].times.toString())
            i++
        }
        adapter.submitList(correct)
        return root
    }

    fun organizeShowtimes(times: List<Times>): List<TheatreTimes> {
        val correct_times = mutableListOf<TheatreTimes>()

        var currentList = mutableListOf<String>()
        var currentName= "blah"

        var index = 0
        var dummy = 0


        while (index < times.size) {
            if (times[index].theatre.theatreId?.equals(currentName)) {
                currentList.add(times[index].time)

            } else {
                currentName = times[index].theatre.theatreId
                //make the object and add to correct times
                var current = TheatreTimes()
                var nameIndex = 0
                if (index != 0) {
                    nameIndex = index
                    nameIndex--
                }
                current.theatreName =  times[nameIndex].theatre.name
                current.times = currentList
                
                if(!currentName.equals("blah"))
                    correct_times.add(current)

                if(times[nameIndex].theatre.theatreId.equals("9489")) {
                    Log.d("Theatre name:", times[nameIndex].theatre.name)
                    Log.d("List of times:", currentList.toString())
                }
                currentList.clear()

            }
            index++
        }
        return correct_times
    }

    private fun initRecyclerView(root: View?): MovieTimesAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)

        val adapter = MovieTimesAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }



}

class TheatreTimes {
     lateinit var theatreName :String
     lateinit var times: List<String>

}
