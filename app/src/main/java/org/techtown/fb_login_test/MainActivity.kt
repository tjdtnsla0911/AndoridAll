package org.techtown.fb_login_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.techtown.fb_login_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

//        val btn = findViewById<Button>(R.id.noEmailLoginBtn)

//        val resultText = findViewById<TextView>(R.id.resultText)
        //데이터 바인딩
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //회원가입버튼
        binding.joinBtn.setOnClickListener {
            Toast.makeText(this,"회원가입클릭!",Toast.LENGTH_SHORT).show()

            val email = binding.emailArea
            val passwrod = binding.passwordArea

            Log.e("#### email : ",email.text.toString())
            Log.e("#### passwrod : ",passwrod.text.toString())


            auth.createUserWithEmailAndPassword(email.text.toString(), passwrod.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("######", "createUserWithEmail:success")

                            Toast.makeText(this,"이메일성공",Toast.LENGTH_SHORT).show()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("#####", "createUserWithEmail:failure", task.exception)

                            Toast.makeText(baseContext, "실패",
                                    Toast.LENGTH_SHORT).show()

                        }
                    }
        }
        //로그아웃  처리
        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            binding.emailArea.text = null
            binding.passwordArea.text = null
            Toast.makeText(this,"로그아웃되었습니다",Toast.LENGTH_SHORT).show()
        }

        binding.loginBtn.setOnClickListener{

            auth.signInWithEmailAndPassword( binding.emailArea.text.toString(),binding.passwordArea.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(baseContext,"로그인성공",Toast.LENGTH_SHORT).show()
                        } else {

                            Toast.makeText(baseContext, "로그인 실패",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }

        //UID로 로그인하기
        binding.noEmailLoginBtn.setOnClickListener {
            Toast.makeText(this,"버튼클릭됬다",Toast.LENGTH_SHORT).show()

            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task -> //얘가성공하면은
                        if (task.isSuccessful) { //성공적일때 실행
                            Toast.makeText(this,"버튼클릭됬다",Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("#####", "성공했어! ")
                            Log.e("####","가져온 아이디 =>"+user!!.uid.toString())
                            binding.resultText.text = user.uid.toString()
//                            resultText.text = user.uid.toString()

                        } else { //실패했을때실행
                            // If sign in fails, display a message to the user.
                            Log.w("#######", "signInAnonymously:failure", task.exception)
                            Toast.makeText(this, "실패했어",
                                    Toast.LENGTH_SHORT).show()

                        }
                    }

        }
    }
}