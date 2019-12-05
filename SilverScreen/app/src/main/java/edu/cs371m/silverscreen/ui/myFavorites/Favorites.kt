package edu.cs371m.silverscreen.ui.myFavorites

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
import edu.cs371m.silverscreen.ui.movies.MovieRowAdapter
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*


class Favorites : Fragment() {

    private lateinit var viewModel: MoviesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   viewModel =
        //         ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!

        val root = inflater.inflate(R.layout.favorites_fragment, container, false)

        // viewModel.netSubRefresh()

        val adapter = initRecyclerView(root)
        //pull from the network, get favorites list, and update viewholder's favorites


        viewModel.observeFavorites().observe(this, Observer {
            Log.d("message", it.size.toString() + "inside favorites, size is")
            adapter.submitList(it)
        })
//        viewModel.observeUserFavList2().observe(this, Observer {
//            Log.d("message", it.size.toString() + "inside favorites, size is")
//            adapter.submitList(it)
//        })
        return root
    }


    private fun initRecyclerView(root: View?): MovieRowAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)

        val adapter = MovieRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }}


