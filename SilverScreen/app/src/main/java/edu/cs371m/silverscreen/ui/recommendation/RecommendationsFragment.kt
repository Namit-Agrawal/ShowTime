package edu.cs371m.silverscreen.ui.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.cs371m.silverscreen.R

class RecommendationsFragmentFragment : Fragment() {

    private lateinit var dashboardViewModel: RecommendationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(RecommendationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recommendations, container, false)
        val textView: TextView = root.findViewById(R.id.text_recommendations)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}