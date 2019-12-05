package edu.cs371m.silverscreen.ui.Showtime

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.TheatrePost
import java.time.LocalDateTime

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
        var time = LocalDateTime.now()
        date = time.toString().substring(0,10)
        val days_of_week = mutableListOf<String>()
        days_of_week.add(time.toString())

        var i :Long = 1
        while (i < 7) {
            days_of_week.add(time.plusDays(i).toString())
            i++
        }
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
        var day_date1 = root.findViewById<TextView>(R.id.date1)
        var day_date2 = root.findViewById<TextView>(R.id.date2)
        var day_date3 = root.findViewById<TextView>(R.id.date3)
        var day_date4 = root.findViewById<TextView>(R.id.date4)
        var day_date5 = root.findViewById<TextView>(R.id.date5)
        var day_date6 = root.findViewById<TextView>(R.id.date6)
        var day_date7 = root.findViewById<TextView>(R.id.date7)
//
        var day_month1 = root.findViewById<TextView>(R.id.month1)
        var day_month2 = root.findViewById<TextView>(R.id.month2)
        var day_month3 = root.findViewById<TextView>(R.id.month3)
        var day_month4 = root.findViewById<TextView>(R.id.month4)
        var day_month5 = root.findViewById<TextView>(R.id.month5)
        var day_month6 = root.findViewById<TextView>(R.id.month6)
        var day_month7 = root.findViewById<TextView>(R.id.month7)
        val months = listOf<String>("January", "February","March","April", "May", "June",
            "July","August","September","October", "November", "December")
//        //today
        day_date1.text = days_of_week[0].substring(8,10)
        day_month1.text = months[days_of_week[0].substring(5,7).toInt() -1]
//
        day_date2.text = days_of_week[1].substring(8,10)
        day_month2.text = months[days_of_week[1].substring(5,7).toInt()-1]
//
        day_date3.text = days_of_week[2].substring(8,10)
        day_month3.text = months[days_of_week[2].substring(5,7).toInt()-1]
//

//
        day_date4.text = days_of_week[3].substring(8,10)
        day_month4.text = months[days_of_week[3].substring(5,7).toInt()-1]
//
        day_date5.text = days_of_week[4].substring(8,10)
        day_month5.text = months[days_of_week[4].substring(5,7).toInt()-1]
//
        day_date6.text = days_of_week[5].substring(8,10)
        day_month6.text = months[days_of_week[5].substring(5,7).toInt() -1]
        day_date7.text = days_of_week[6].substring(8,10)
        day_month7.text = months[days_of_week[6].substring(5,7).toInt()- 1]


        day1.setOnClickListener{
            date = time.toString().substring(0,10)
            adapter.changeDate(date)
        }
        day2.setOnClickListener{
            date = time.plusDays(1).toString().substring(0,10)
            adapter.changeDate(date)

        }
        day3.setOnClickListener{
            date = time.plusDays(2).toString().substring(0,10)
            adapter.changeDate(date)

        }
        day4.setOnClickListener{
            date = time.plusDays(3).toString().substring(0,10)
            adapter.changeDate(date)

        }
        day5.setOnClickListener{
            date = time.plusDays(4).toString().substring(0,10)
            adapter.changeDate(date)

        }
        day6.setOnClickListener{
            date = time.plusDays(5).toString().substring(0,10)
            adapter.changeDate(date)

        }
        day7.setOnClickListener{
            date = time.plusDays(6).toString().substring(0,10)
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
