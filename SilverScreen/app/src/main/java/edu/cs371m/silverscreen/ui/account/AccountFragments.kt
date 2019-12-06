package edu.cs371m.silverscreen.ui.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.share.model.SharePhoto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.TheatrePost
import edu.cs371m.silverscreen.ui.ListFriends
import edu.cs371m.silverscreen.ui.ListFriendsViewModel
import edu.cs371m.silverscreen.ui.Request
import edu.cs371m.silverscreen.ui.RequestViewModel
import edu.cs371m.silverscreen.ui.movie_times.MovieTimesViewModel
import edu.cs371m.silverscreen.ui.movies.MoviesFragment
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import edu.cs371m.silverscreen.ui.theaters.OneTheatrePost
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.*

class AccountFragment : Fragment() {

    private lateinit var dashboardViewModel: AccountViewModel
    private lateinit var callbackManager: CallbackManager
    private lateinit var database: FirebaseFirestore
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseFirestore.getInstance()


        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!


        val root = inflater.inflate(R.layout.fragment_account, container, false)
        callbackManager = CallbackManager.Factory.create()
        val EMAIL = "email"
        val loginButton = root.findViewById<LoginButton>(R.id.login_button)
        loginButton.setPermissions(Arrays.asList(EMAIL))
        loginButton.setFragment(this)
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
            }

            override fun onCancel() {
                Log.d("On Cancel", "***************************************************************88")

            }

            override fun onError(error: FacebookException) {
                Log.d("On Error", "***************************************************************88")

            }
        })


        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(true)
                it?.setDisplayShowCustomEnabled(true)
                it?.title="Account"
            }
        }


        val sign_up = root.findViewById<Button>(R.id.account_signin)
        sign_up.text = "Sign in/Sign out"

        sign_up.setOnClickListener {
            if (viewModel.observeUser().value == null) {
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build()
                )
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                    1
                )

            } else {
                viewModel.setUser(null)
                FirebaseAuth.getInstance().signOut()
                viewModel.favorites.value = mutableListOf()
                viewModel.favPostID.value = mutableListOf()

                //userTV.isVisible = false
            }

        }
//        val facebook = root.findViewById<Button>(R.id.facebook)
//        facebook.setOnClickListener {
//
//
//            LoginManager.getInstance()
//                .logInWithReadPermissions(this, Arrays.asList("public_profile"));
//        }
        val accessToken = AccessToken.getCurrentAccessToken()


        val friends_but = root.findViewById<LinearLayout>(R.id.friends)
        friends_but.setOnClickListener{
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, ListFriends())
                ?.addToBackStack(null)
                // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }

        val new_friend_request = root.findViewById<LinearLayout>(R.id.new_r)
        new_friend_request.setOnClickListener {
            //fragment
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, Request())
                ?.addToBackStack(null)
                // TRANSIT_FRAGMENT_FADE calls for the Fragment to fade away
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
        }


        return root




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val auth = FirebaseAuth.getInstance()
                auth.currentUser
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                var res = ""
          //      res = getStuff(user!!)
//                userTV.setText(res)
//                setDisplayName.setOnClickListener {
//                    var name = displayNameET.text.toString()
//                    userTV.setText("User: " + name + "\n" + "Email: " + user!!.email)
//
//                }
                userTV.setText("Welcome\n"+"User: " + user!!.displayName + "\n" + "Email: " + user.email)
                viewModel.favorites.value = mutableListOf()
                viewModel.favPostID.value = mutableListOf()
                val docRef =database.collection("Users").document(user.uid)
                docRef.get()
                    .addOnSuccessListener { document ->

                        if (document.exists()) {
                            //returning doc
                            val x = document.toObject(MoviesViewModel.User::class.java)
                            Log.d("Inside document", x?.favs.toString())
                            if(x!!.entMoviePost!!.size>0)
                                viewModel.favorites.value = x.entMoviePost
                            if(x.favs!!.size>0)
                                viewModel.favPostID.value = x.favs

                        } else {
                            //new user logging in for the first time in forever
                            val currentUser = MoviesViewModel.User(
                                user.displayName!!,
                                user.email!!,
                                "78705",
                                listOf<String>(),
                                listOf<MoviePost>()

                            )
                            val friends = MoviesViewModel.Friends(listOf<String>())
                            var requests = MoviesViewModel.IncomingRequests(listOf<String>())
                            database.collection("Users").document(user.uid).set(currentUser)
                            database.collection("Users").document(user.uid).collection("Other data")
                                .document("friend").set(friends)
                            database.collection("Users").document(user.uid).collection("Other data")
                                .document("new requests").set(requests)

                        }

                    }
//                database.collection("Users")
//                    .whereEqualTo("email", user.email)
//                    .get()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            Log.d("help", "${document.id} => ${document.data}")
//                        }
//                    }
                //set up snapshot listener for incoming requests
                var model =    activity?.let { ViewModelProviders.of(it).get(RequestViewModel::class.java) }!!

                val requestDoc = database.collection("Users").document(user.uid).collection("Other data")
                    .document("new requests")
                requestDoc.addSnapshotListener{snapshot,e ->
                    if(e!=null) {
                        Log.d("fail", "listen")

                    } else if (snapshot != null && snapshot.exists()) {
                        val x = snapshot.toObject(MoviesViewModel.IncomingRequests::class.java)
                        //requests.text = x!!.list!!.size.toString()
                        model.updateRequests(x!!.list!!)

                        Log.d("updateeeddd friends list!!!", "here" + snapshot.data)
                    }

                }
                var friendmodel =    activity?.let { ViewModelProviders.of(it).get(ListFriendsViewModel::class.java) }!!

                val friendDoc = database.collection("Users").document(user.uid).collection("Other data")
                    .document("friend")
                friendDoc.addSnapshotListener{snapshot, e ->
                    if (e != null) {
                        Log.d("fail", "listen")
                    } else if (snapshot != null && snapshot.exists()) {
                        val x = snapshot.toObject(MoviesViewModel.Friends::class.java)
                        //requests.text = x!!.list!!.size.toString()
                        friendmodel.updateFriends(x!!.list!!)

                        Log.d("updateeeddd friends list!!!", "here" + snapshot.data)
                    }

                }

                //TODO set the user obj data AKA, fetch their zipcode, fetch favs, fetch username
                viewModel.setUser(user)
                // ...
            } else {

            }
        }
    }

    fun getStuff(user: FirebaseUser?): String {

        var res = ""
        if (user != null) {
            var email = user.email!!
            res = "User: " + user.displayName + "\n" + "Email: " + email
        }
        return res
    }




}



