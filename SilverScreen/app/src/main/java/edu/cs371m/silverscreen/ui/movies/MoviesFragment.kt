package edu.cs371m.silverscreen.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.Location
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.ui.comingsoon.ComingSoon
import edu.cs371m.silverscreen.ui.myFavorites.Favorites
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    companion object {
        val ratings = listOf(
            "G",
            "PG",
            "PG-13",
            "R",
            "NR"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     //   viewModel =
       //         ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!

        val root = inflater.inflate(R.layout.fragment_movies, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(false)
                it?.setDisplayShowCustomEnabled(true)
                val customView: View =
                    layoutInflater.inflate(R.layout.actionbar_movies, null)
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
                val coming = customView.findViewById<TextView>(R.id.coming_soon)
                coming.setOnClickListener {
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, ComingSoon())
                        ?.addToBackStack(null)
                        ?.commit()
                }



                val myMovies = customView.findViewById<TextView>(R.id.favorites_upper)
                myMovies.setOnClickListener {
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, Favorites())
                        ?.addToBackStack(null)
                        ?.commit()
                }
                viewModel.observeZip().observe(this, Observer {
                    Log.d("****************8", "Inside the obeserve   "+ it)
                    //viewModel.netSubRefresh()
                    location.text = it
                })

            }
        }
       viewModel.netSubRefresh()

        val adapter = initRecyclerView(root)
        viewModel.observeMovies().observe(this, Observer {
            adapter.submitList(it)
        })
        return root
    }


    private fun initRecyclerView(root: View?): MovieRowAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)

        val adapter =MovieRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }}