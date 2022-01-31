package com.hello.socialmediaapp

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hello.socialmediaapp.databinding.ActivitySignInBinding
import com.hello.util.FullScreen
import kotlin.math.absoluteValue

class SignInActivity : AppCompatActivity() {
    private lateinit  var binding: ActivitySignInBinding
    val TAG="TAG"
    private lateinit var signInIntent:Intent
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        auth=Firebase.auth
        if (auth.currentUser!=null){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
        FullScreen.hideSystemBars(window)
        supportActionBar?.hide()
        fun configGoogleSignInOptions():GoogleSignInClient{
            val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            var googleSignInClient=GoogleSignIn.getClient(this,gso)
            return googleSignInClient
        }
        var resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
          val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account=task.getResult(ApiException::class.java)
                fireBaseAuthWithGoogle(account.idToken!!)

            }catch (e:ApiException){
                Log.d(TAG, "SignIn failed: "+e)
            }
        }
        binding.btnGoogleSignIn.setOnClickListener{
            signInIntent=configGoogleSignInOptions().signInIntent
           resultLauncher.launch(signInIntent)
        }
    }

    private fun fireBaseAuthWithGoogle(idToken: String) {
val credential=GoogleAuthProvider.getCredential(idToken,null)
  auth.signInWithCredential(credential)
      .addOnCompleteListener { task->
          if (task.isSuccessful){
              Toast.makeText(this,"Sign in successfull",Toast.LENGTH_SHORT).show()
var intent=Intent(this,MainActivity::class.java)
              startActivity(intent)
          }
      }
    }

}

