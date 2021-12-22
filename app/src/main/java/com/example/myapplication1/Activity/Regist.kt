package com.example.myapplication1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject

class Regist : AppCompatActivity() {
    private lateinit var app: MyApp
    private lateinit var nameText: EditText
    private lateinit var familiaText: EditText
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var secPasswordText: EditText
    private lateinit var regist: Button
    private lateinit var akk: Button
    private lateinit var math: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        nameText = findViewById(R.id.nameText)
        app=applicationContext as MyApp
        familiaText = findViewById(R.id.familiaText)
        emailText = findViewById(R.id.editTextTextEmailAddress2)
        passwordText = findViewById(R.id.editTextTextPassword)
        secPasswordText = findViewById(R.id.editTextTextPassword2)
        regist = findViewById(R.id.regButton)
        akk = findViewById(R.id.akkButton)
        val re = Regex("""[a-z0-9]+@[a-z0-9]+\.[a-z]{1,3}$""")
         math = re.find(emailText.toString()).toString()
    }

    fun regClick(view: android.view.View) {
        if (nameText.text.isNotEmpty() && familiaText.text.isNotEmpty() && emailText.text.isNotEmpty() && passwordText.text.isNotEmpty() && secPasswordText.text.isNotEmpty() &&
        math!=null && passwordText == secPasswordText ){
            app.name=nameText.text.toString()
            app.familia=familiaText.text.toString()
            app.email=emailText.text.toString()
            app.password=passwordText.text.toString()
            app.secPassword = passwordText.text.toString()

            HTTP.requestPOST("http://s4a.kolei.ru/login",
            JSONObject().put("firstName", app.name).put("lastName", app.familia).put("email", app.email).put("password", app.password),
            mapOf(
                "Content-Type" to "application/json"
            )
                ){result, error, code ->
                runOnUiThread {
                    if (code==201) {
                        startActivity(Intent(this, AuthActivity::class.java) )
                    }
                   else{
                       AlertDialog.Builder(this)
                           .setTitle("Ошибка")
                           .setMessage("Регестрация не удалась")
                           .setPositiveButton("ОК", null)
                           .create()
                           .show()
                    }
                }
            }

        }
        else {
            AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Поля пустые, либо заполнены не верно")
                .setPositiveButton("ОК", null)
                .create()
                .show()
        }

    }
    fun akkClick(view: android.view.View) {
        startActivity(Intent(this, AuthActivity::class.java ))
    }
}