package com.example.cointwo.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cointwo.App
import com.example.cointwo.R
import com.example.cointwo.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityLoginBinding
    private val binding get() = _binding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    //        private var auth = FirebaseAuth.getInstance(FirebaseApp.getInstance())
    private lateinit var auth: FirebaseAuth

    private val googleSignLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("dodo55 ", "googleSignLauncher111111 result.resultCod : ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            var account: GoogleSignInAccount? = null
            kotlin.runCatching {
                Log.d("dodo55 ", "googleSignLauncher222222")
                account = task.getResult(ApiException::class.java)
                Log.d("dodo55 ", "account : ${account}")
                firebaseAuthWithGoogle(account!!.idToken)
            }.onFailure {
                Log.d("dodo55 ", "e : $it")
            }

        } else {
            Log.d("dodo55 ", "googleSignLauncher33333333")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setGoogleLogin()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            var account: GoogleSignInAccount? = null
//            try {
//                account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account!!.idToken)
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Failed Google Login", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        Log.d("dodo55 ", "firebaseAuthWithGoogle idToken : ${idToken}")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Log.d("dodo55 ", "credential : ${credential}")
        Log.d("dodo55 ", "auth : ${auth}")
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        Log.d("dodo55 ", "인증성공 ??? : ${auth!!.currentUser?.email}")
                        Log.d("dodo55 ", "?? ??? : ${auth!!.currentUser?.metadata}")
                        // 인증에 성공한 후, 현재 로그인된 유저의 정보를 가져올 수 있습니다.
//                        val email = auth!!.currentUser?.email
                        Toast.makeText(this, "구글 로그인 성공", Toast.LENGTH_SHORT).show()
                        // 다음 화면
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun firebaseGoogleLogout() {
        mGoogleSignInClient.signOut()
    }


    private fun setGoogleLogin() {

        auth = FirebaseAuth.getInstance(App.FIRE_INIT)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, options)


        binding.btnGoogleLogin.setOnClickListener {
            Log.d("dodo55 ", "로긴~")
            googleSignLauncher.launch(mGoogleSignInClient.signInIntent)
        }
    }

    companion object {
        const val SIGN_CODE = 1
    }
}