package edu.cs371m.silverscreen.ui

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

class Request : Fragment() {

    companion object {
        fun newInstance() = Request()
    }

    private lateinit var viewModel: RequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.let { ViewModelProviders.of(it).get(RequestViewModel::class.java) }!!

        var root = inflater.inflate(R.layout.request_fragment, container, false)
        val adapter = initRecyclerView(root)

        viewModel.observeRequests().observe(this, Observer {
            Log.d("snaity", it.size.toString())
            adapter.submitList(it)
        })

        return root
    }



    private fun initRecyclerView(root: View?): RequestAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_request)

        val adapter =RequestAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }

}
