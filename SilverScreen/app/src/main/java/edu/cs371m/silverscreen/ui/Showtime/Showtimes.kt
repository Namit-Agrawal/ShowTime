package edu.cs371m.silverscreen.ui.Showtime

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.TheatrePost

class Showtimes : Fragment() {

    companion object {
        fun newInstance(theaterPost: TheatrePost) : Showtimes {
            val times = Showtimes()
            val b = Bundle()
            b.putParcelable("post", theaterPost)
            times.arguments = b
            return times
        }
    }

    private lateinit var viewModel: ShowtimesViewModel
    private lateinit var date: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        date = "2019-12-02"
        viewModel =
            ViewModelProviders.of(this).get(ShowtimesViewModel::class.java)
        val root = inflater.inflate(R.layout.showtimes_fragment, container, false)
        val adapter = initRecyclerView(root)

        val post = this.arguments?.getParcelable<TheatrePost>("post")

        viewModel.netSubRefresh(post!!.id)
        Log.d("message", "id is " + post.id.toString())

        viewModel.observeTimes().observe(this, Observer {
            //it needs to be organized correctly
            Log.d("message", it.size.toString() + "&&&&&&&&&&")
            adapter.submitList(it)
        })


        //dates
        var day1 = root.findViewById<LinearLayout>(R.id.day1)
        var day2 = root.findViewById<LinearLayout>(R.id.day2)
        var day3 = root.findViewById<LinearLayout>(R.id.day3)
        var day4 = root.findViewById<LinearLayout>(R.id.day4)
        var day5 = root.findViewById<LinearLayout>(R.id.day5)
        var day6 = root.findViewById<LinearLayout>(R.id.day6)
        var day7 = root.findViewById<LinearLayout>(R.id.day7)

        day1.setOnClickListener{
            date = "2019-12-01"
            adapter.changeDate(date)
        }
        day2.setOnClickListener{
            date = "2019-12-02"
            adapter.changeDate(date)

        }
        day3.setOnClickListener{
            date = "2019-12-03"
            adapter.changeDate(date)

        }
        day4.setOnClickListener{
            date = "2019-12-04"
            adapter.changeDate(date)

        }
        day5.setOnClickListener{
            date = "2019-12-05"
            adapter.changeDate(date)

        }
        day6.setOnClickListener{
            date = "2019-12-06"
            adapter.changeDate(date)

        }
        day7.setOnClickListener{
            date = "2019-12-07"
            adapter.changeDate(date)
        }
        return root
    }

    private fun initRecyclerView(root: View?): ShowtimesAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_showtimes)

        val adapter = ShowtimesAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }


}
