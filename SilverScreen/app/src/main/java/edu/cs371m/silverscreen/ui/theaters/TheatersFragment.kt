package edu.cs371m.silverscreen.ui.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.Location
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel

class TheatersFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel =
//                ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!
        val root = inflater.inflate(R.layout.fragment_theaters, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(false)
                it?.setDisplayShowCustomEnabled(true)
                val customView: View =
                    layoutInflater.inflate(R.layout.actionbar_multiple, null)
                // Apply the custom view
                it?.customView = customView

                val location = customView.findViewById<TextView>(R.id.location)
                location.setOnClickListener{
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, Location(), "location")
                        ?.addToBackStack(null)
                        ?.commit()
                }
                viewModel.observeZip().observe(this, Observer {
                    viewModel.netSubTheatreRefresh()
                    location.text = it
                })


            }
        }

//
        val adapter = initRecyclerView(root)
        viewModel.observeTheaters().observe(this, Observer {
                        adapter.submitList(it)
        })


        return root
    }


    private fun initRecyclerView(root: View?): TheaterRowAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_theater)

        val adapter =TheaterRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }

}



