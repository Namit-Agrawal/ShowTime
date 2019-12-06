package edu.cs371m.silverscreen.ui

import FriendProfileAdapter
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import edu.cs371m.silverscreen.R


class FriendProfile : Fragment() {

    companion object {
        fun newInstance() = FriendProfile()
    }

    private lateinit var viewModel: FriendProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("hegkjehrg","gelrjglk")
    //    return inflater.inflate(R.layout.fragment_movies, container, false)
        viewModel = activity?.let { ViewModelProviders.of(it).get(FriendProfileViewModel::class.java) }!!

        var root = inflater.inflate(R.layout.friend_profile_fragment, container, false)
        //val adapter = initRecyclerView(root)

        viewModel.observeFriendProfile().observe(this, Observer {
            Log.d("snaity", it.size.toString())
            //adapter.submitList(it)
        })

    return root
    }

//
//    private fun initRecyclerView(root: View?): FriendProfileAdapter {
//        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)
//
//        //val adapter =FriendProfileAdapter(viewModel)
//        rv.adapter = adapter
//        rv.layoutManager = LinearLayoutManager(context)
//        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
//        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
//        rv.addItemDecoration(itemDecor)
//        return adapter
//    }

}
