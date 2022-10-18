package com.fatiheminkarahan.firebasedatabase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.fatiheminkarahan.firebasedatabase.R
import com.fatiheminkarahan.firebasedatabase.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpCountry: EditText
    private lateinit var btnSaveData: Button
    private lateinit var backButton: ImageButton

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpCountry = findViewById(R.id.etEmpCountry)
        btnSaveData = findViewById(R.id.btnSave)
        backButton = findViewById(R.id.backButton)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData(){
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empCountry = etEmpCountry.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empCountry.isEmpty()) {
            etEmpCountry.error = "Please enter country"
        }


        val empId = dbRef.push().key!!


        val user = UserModel(empId, empName, empAge, empCountry)

        dbRef.child(empId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpCountry.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }




    }
}