package com.decode.nextjob.helpers.firebase

import android.app.Activity
import android.content.Intent

import android.util.Log
import android.widget.Toast
import com.decode.nextjob.R
import com.decode.nextjob.ui.LoginActivity
import com.decode.nextjob.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase

object TestarLogin{

    private  lateinit var googleSignInClient: GoogleSignInClient
    public val RC_SIGN_IN= 846411171


    fun testIfUserIsSignedIn(activity: LoginActivity, auth: FirebaseAuth) {
        val currentUser = auth.currentUser
        if (currentUser != null) {

            var intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("photoUrl",currentUser.photoUrl)
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    fun signIn(activity: LoginActivity, googleSignInClient: GoogleSignInClient){

        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun ConfigureGoogleSignIn(activity: LoginActivity): GoogleSignInClient{

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(activity,gso)
        return googleSignInClient
    }

    fun firebaseAuthWithGoogle(idToken:String,auth : FirebaseAuth,activity: Activity){

        val  credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity){
                    if (it.isSuccessful){

                        Log.d("FirebaseInfo","signInWithCredential: sucess")
                        val user= auth.currentUser
                        var intent = Intent(activity, MainActivity::class.java)
                        activity.startActivity(intent)

                    }else{

                        Log.w("FirebaseInfo", "signInWithCredential: failure",it.exception)
                        Toast.makeText(
                                activity.applicationContext,
                                "Login Failed: "+it.exception,
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                }
    }







}