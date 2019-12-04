package edu.cs371m.silverscreen.ui.trailer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.ui.movies.MovieRowAdapter

class Trailers : Fragment() {

    companion object {
        fun newInstance(moviePost: MoviePost): Trailers{
            val trailer = Trailers()
            val b = Bundle()
            b.putParcelable("moviepost", moviePost)
            trailer.arguments =b
            return trailer
        }
    }

    private lateinit var viewModel: TrailersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(TrailersViewModel::class.java)

        val root =inflater.inflate(R.layout.trailers_fragment, container, false)


        val post = this.arguments?.getParcelable<MoviePost>("moviepost")
        if (post != null) {
            val adapter = initRecyclerView(root, post!!.img.image_url)
            viewModel.DBQuery(post)
            viewModel.queryResult.observe(this, Observer {
                if (it != null) {
                    //find the movie with exact same name
//                    var i = 0
//                    var found = false
                    if (it.size > 0) {
                        viewModel.setMovieID(it[0].movie_id)
                        viewModel.DBVideoQuery()
                        viewModel.videoList.observe(this, Observer {
                            if (it != null) {
                                adapter.submitList(it)
                            }
                        })
                    }
//                    while (i < it.size && !found) {
//                        if (post!!.movieName.equals(it[i].movieName)) {
//                            //set the movie id
//                            viewModel.setMovieID(it[i].movie_id)
//                            found = true
//
//
//                            //pull the trailers
//                            viewModel.DBVideoQuery()
//                            viewModel.videoList.observe(this, Observer {
//                                if (it != null) {
//                                    adapter.submitList(it)
//
//                                    //only add stuff to adapter if youtube
//                                    //https://www.youtube.com/watch?v=Zi4LMpSDccc
//                                }
//                            })
//                        }
//                        i++
//
//                    }

                }


            })

        }

        return root
    }



    private fun initRecyclerView(root: View?, path: String): TrailersAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_trailers)

        val adapter = TrailersAdapter(viewModel, path)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }}


