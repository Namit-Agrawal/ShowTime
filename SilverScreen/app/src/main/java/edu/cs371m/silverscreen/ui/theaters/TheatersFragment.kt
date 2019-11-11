package edu.cs371m.silverscreen.ui.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.cs371m.silverscreen.R

class TheatersFragment : Fragment() {

    private lateinit var notificationsViewModel: TheatersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(TheatersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_theaters, container, false)
        val textView: TextView = root.findViewById(R.id.text_theaters)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}