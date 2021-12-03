package valenzuela.carlos.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import valenzuela.carlos.firebaselogin.databinding.ActivitySignInBinding
import kotlin.math.sign

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        var mEmail = binding.emailEditText.text.toString()
        var mPassword = binding.passwordEditText.text.toString()

        val button = findViewById<Button>(R.id.signInAppCompatButton)

        val signUp = binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        when{
            mEmail.isEmpty() || mPassword.isEmpty()->{
                Toast.makeText(baseContext, "Correo o Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
            }
        }

        button.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailEditText)
            val password = findViewById<EditText>(R.id.passwordEditText)

            mEmail = email.text.toString()
            mPassword = password.text.toString()

            if(mEmail.isNotEmpty() && mPassword.isNotEmpty()){
                SignIn(mEmail, mPassword)
            } else{
                if(mEmail.isBlank()){
                    Toast.makeText(baseContext, "Correo en blanco.", Toast.LENGTH_SHORT).show()
                } else if(mPassword.isBlank()){
                    Toast.makeText(baseContext, "Contraseña en blanco.", Toast.LENGTH_SHORT).show()
                } else if(mEmail.isEmpty()){
                    Toast.makeText(baseContext, "Correo vacio.", Toast.LENGTH_SHORT).show()
                } else if (mPassword.isEmpty()){
                    Toast.makeText(baseContext, "Contraseña vacia.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun SignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                task -> if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    //val user = auth.currentUser
                    reload()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    private fun reload(){
        //val intent = Intent(this, SignInActivity::class.java)
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }
}