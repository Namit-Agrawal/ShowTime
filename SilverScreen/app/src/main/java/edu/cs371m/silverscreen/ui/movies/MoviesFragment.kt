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
                val filterBut = customView.findViewById<ImageButton>(R.id.filter)
                location.setOnClickListener{
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, Location(), "location")
                        ?.addToBackStack(null)
                        ?.commit()
                }
                filterBut.setOnClickListener{
                    if(theListView.visibility == View.INVISIBLE) {
                        recycler_view_movies.visibility = View.INVISIBLE
                        theListView.visibility = View.VISIBLE
                        val theAdapter = ArrayAdapter<String>(
                            activity!!,
                            android.R.layout.simple_list_item_multiple_choice,
                            android.R.id.text1,
                            ratings
                        )

                        // ListViews display data in a scrollable list
                        // Tells the ListView what data to use
                        theListView.adapter = theAdapter

                        theListView.setOnItemClickListener { parent, view, position, id ->
                            val albumPicked =
                                String.format(
                                    "%s %d %s",
                                    "You selected item",
                                    position,
                                    parent.getItemAtPosition(position)
                                )
                        }
                    }
                    else {

                        theListView.visibility = View.INVISIBLE
                        recycler_view_movies.visibility = View.VISIBLE
                    }

                }
                viewModel.observeZip().observe(this, Observer {
                    Log.d("****************8", "Inside the obeserve   "+ it)
                    viewModel.netSubRefresh()
                    location.text = it
                })
            }
        }


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