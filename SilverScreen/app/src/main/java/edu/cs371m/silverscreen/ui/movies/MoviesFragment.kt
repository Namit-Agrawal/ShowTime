package edu.cs371m.silverscreen.ui.movies

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
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
import edu.cs371m.silverscreen.comingsoon.ComingSoon
import edu.cs371m.silverscreen.ui.myFavorites.Favorites
import kotlinx.android.synthetic.main.fragment_movies.*
import org.w3c.dom.Text


class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var geocoder: Geocoder
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
        geocoder = Geocoder(activity)
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

                val myMovies = customView.findViewById<TextView>(R.id.favorites_upper)
                myMovies.setOnClickListener {
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, Favorites())
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


                viewModel.observeZip().observe(this, Observer {
                    Log.d("****************8", "Inside the obeserve   "+ it)
                    //viewModel.netSubRefresh()
                    location.text = it
                })

            }
        }
        val permission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)


        Log.d("HHHHHHHHHHHHHHHHHHHHHHHHEEEEEEEEEEEEEERRRRRREEEE", "*")

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    Log.d("latitude", location.latitude.toString())
                    Log.d("longitude", location.longitude.toString())
                    val addr = geocoder.getFromLocation(location.latitude, location.longitude,1)
                    val loca = addr.get(0).postalCode.toString()
                    //viewModel.updateZip(loca)
                    Log.d("PPPPPPPPPPPPPPPPPPPOOOOOOOOOOOOOOOOOSSSSSSSSSSSSSTTTTTTAAAAAAAL", loca)


                }
            }
        }
        else
        {
           requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
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
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }
}


