package edu.cs371m.silverscreen.ui.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import edu.cs371m.silverscreen.R
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.*

class AccountFragment : Fragment() {

    private lateinit var dashboardViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(false)
                it?.setDisplayShowCustomEnabled(true)
                val customView: View =
                    layoutInflater.inflate(R.layout.actionbar_multiple, null)
                // Apply the custom view
                it?.customView = customView
            }
        }

        val sign_up = root.findViewById<Button>(R.id.account_signin)
        sign_up.setOnClickListener{
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                1)
        }
        val facebook = root.findViewById<Button>(R.id.facebook)
        facebook.setOnClickListener{


            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                var res = ""
                res = getStuff(user!!)
                userTV.setText(res)
                setDisplayName.setOnClickListener{
                    var name = displayNameET.text.toString()
                    userTV.setText("User: "+name+"\n"+"Email: "+user!!.email)
                }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    fun getStuff(user: FirebaseUser? ): String{

        var email = ""
        var res = ""
        if (user != null) {
            email = user.email!!
            res = "User: "+user.displayName+"\n"+"Email: "+email
        }
        return res
    }
}