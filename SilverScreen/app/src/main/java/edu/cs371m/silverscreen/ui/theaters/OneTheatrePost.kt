package edu.cs371m.silverscreen.ui.theaters

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.TheatrePost
import edu.cs371m.silverscreen.ui.Showtime.Showtimes
import kotlinx.android.synthetic.main.activity_one_theatre_post.*

class OneTheatrePost:AppCompatActivity() {
    private lateinit var post: TheatrePost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_theatre_post)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent

        val callingBundle = intent.extras
        callingBundle?.apply {
            post = intent.getParcelableExtra("theater_info")
            if (post == null) {
                return
            }
            theatre.text = post.name
            address.text = post.loc.address.st
            state_zipcode.text = post.loc.address.city

        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag, Showtimes.newInstance(post))
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
        {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
