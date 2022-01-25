package com.decode.nextjob.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.decode.nextjob.R
import com.decode.nextjob.helpers.firebase.TestarLogin
import com.decode.nextjob.helpers.firebase.TestarLogin.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.SignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private  lateinit var googleSignInClient: GoogleSignInClient
    private  lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleSignInClient = TestarLogin.ConfigureGoogleSignIn(this)

        auth= Firebase.auth

        btnLogin.setOnClickListener {
            TestarLogin.signIn(this,googleSignInClient)
        }
    }

    override fun onStart() {
        super.onStart()
        TestarLogin.testIfUserIsSignedIn(this, auth)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==RC_SIGN_IN){

            val task =GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)!!
                Log.d("FirebaseInfo","firebaseAuth: "+account.email)
                TestarLogin.firebaseAuthWithGoogle(account.idToken!!,auth,this)

            } catch (e: ApiException){
                Log.w("firebaseException","Google  sign in failed " + e.message.toString())
            }
        }
    }
}