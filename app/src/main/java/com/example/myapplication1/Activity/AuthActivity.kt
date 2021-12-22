package com.example.myapplication1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject

class AuthActivity : AppCompatActivity() {
    private lateinit var app: MyApp
    private lateinit var email2: EditText
    private lateinit var password2: EditText
    private lateinit var login: Button
    private lateinit var reg2: Button
    private lateinit var math: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        email2 = findViewById(R.id.email2)
        password2 = findViewById(R.id.password2)
        login = findViewById(R.id.loginButton)
        reg2 = findViewById(R.id.reg2Button)
        val re = Regex ("""[a-z0-9]+@[a-z0-9]+\.[a-z]{1,3}$""")
        math = re.find(email2.toString()).toString()
    }

    fun reg2Click(view: android.view.View) {
        if(email2.text.isNotEmpty()&&password2.text.isNotEmpty()&&login.text.isNotEmpty()&&reg2.text.isNotEmpty()&&
                math!=null){
            app.email = email2.text.toString()
            app.password = password2.text.toString()

            HTTP.requestPOST("http://s4a.kolei.ru/login",
            JSONObject().put("email2", app.email).put("password2", app.password),
                mapOf(
                    "Content-Type" to "application/json"
                )
            ){result, error, code ->
                runOnUiThread {
                    if (code==200) {
                        if (result != null) {
                            try {
                                val json = JSONObject(result)
                                app.token = json.getJSONObject(result).getString("token")
                                    runOnUiThread {
                                        Toast.makeText(
                                            this,
                                            "success get token: ${app.token}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }


                             catch (e: Exception) {
                                 runOnUiThread {
                                     AlertDialog.Builder(this)
                                         .setTitle("Не работает")
                                         .setMessage(e.message)
                                         .setPositiveButton("Cancel", null)
                                         .create()
                                         .show()
                                 }
                             }
                            }
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }

            }  }
        }
    }
    fun loginClick(view: android.view.View) {}
