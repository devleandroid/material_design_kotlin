package br.com.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.InjectView

class SignupActivity : AppCompatActivity() {

    private val TAG = "SignupActivity"

    @InjectView(R.id.input_name)
    var _nameText: EditText? = null

    @InjectView(R.id.input_address)
    var _addressText: EditText? = null

    @InjectView(R.id.input_email)
    var _emailText: EditText? = null

    @InjectView(R.id.input_mobile)
    var _mobileText: EditText? = null

    @InjectView(R.id.input_password)
    var _passwordText: EditText? = null

    @InjectView(R.id.input_reEnterPassword)
    var _reEnterPasswordText: EditText? = null

    @InjectView(R.id.btn_signup)
    var _signupButton: Button? = null

    @InjectView(R.id.link_login)
    var _loginLink: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        ButterKnife.inject(this)
        initComponent()

        _signupButton!!.setOnClickListener { signup() }

        _loginLink!!.setOnClickListener { // Finish the registration screen and return to the Login activity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    fun initComponent(){
        _nameText = findViewById<EditText>(R.id.input_name)
        _addressText = findViewById<EditText>(R.id.input_address)
        _emailText = findViewById<EditText>(R.id.input_email)
        _mobileText = findViewById<EditText>(R.id.input_mobile)
        _passwordText = findViewById<EditText>(R.id.input_password)
        _reEnterPasswordText = findViewById<EditText>(R.id.input_reEnterPassword)
        _signupButton = findViewById<Button>(R.id.btn_signup)
        _loginLink= findViewById<TextView>(R.id.link_login)
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        // moveTaskToBack(true)
        finish()
    }

    fun signup() {
        Log.d(TAG, "Signup")
        if (!validate()) {
            onSignupFailed()
            return
        }
        _signupButton!!.isEnabled = false
        val progressDialog = ProgressDialog(
            this@SignupActivity,
            R.style.AppTheme
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()
        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        // TODO: Implement your own signup logic here.
        Handler().postDelayed(
            { // On complete call either onSignupSuccess or onSignupFailed
                // depending on success
                onSignupSuccess()
                // onSignupFailed();
                progressDialog.dismiss()
            }, 3000
        )
    }


    fun onSignupSuccess() {
        _signupButton!!.isEnabled = true
        setResult(RESULT_OK, null)
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _signupButton!!.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true
        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val mobile = _mobileText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()
        if (name.isEmpty() || name.length < 3) {
            _nameText!!.error = "at least 3 characters"
            valid = false
        } else {
            _nameText!!.error = null
        }
        if (address.isEmpty()) {
            _addressText!!.error = "Enter Valid Address"
            valid = false
        } else {
            _addressText!!.error = null
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText!!.error = "enter a valid email address"
            valid = false
        } else {
            _emailText!!.error = null
        }
        if (mobile.isEmpty() || mobile.length != 10) {
            _mobileText!!.error = "Enter Valid Mobile Number"
            valid = false
        } else {
            _mobileText!!.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            _passwordText!!.error = null
        }
        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            _reEnterPasswordText!!.error = "Password Do not match"
            valid = false
        } else {
            _reEnterPasswordText!!.error = null
        }
        return valid
    }
}