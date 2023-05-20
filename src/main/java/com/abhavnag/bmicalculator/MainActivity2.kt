package com.abhavnag.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
private const val TAG = "MainActivity2"

class MainActivity2 : AppCompatActivity() {
    private lateinit var etHeightCm: EditText
    private lateinit var etWeightKg: EditText
    private lateinit var tvBMIAmount: TextView
    private lateinit var tvStatusAns: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        etHeightCm = findViewById(R.id.etHeightCm)
        etWeightKg = findViewById(R.id.etWeightKg)
        tvBMIAmount = findViewById(R.id.tvBMIAmount)
        tvStatusAns = findViewById(R.id.tvStatusAns)

        etHeightCm.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged $p0")
                computeBMI()
            }

        })

        etWeightKg.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeBMI()
            }

        })
    }

    private fun computeBMI() {
        if (etHeightCm.text.isEmpty()||etWeightKg.text.isEmpty()){
            tvBMIAmount.text = ""
            tvStatusAns.text = ""
            return
        }
        //Get values
        val cm = etHeightCm.text.toString().toInt()
        val weight = etWeightKg.text.toString().toDouble()
        //Compute BMI and Status
        val meters = cm/100.0
        if (meters==0.0){
            tvBMIAmount.text = ""
            tvStatusAns.text = ""
            return
        }
        val bmi = weight / (meters * meters)
        tvBMIAmount.text = "%.2f".format(bmi)
        if (bmi<18.5){
            tvStatusAns.text = "Underweight"
        } else if (bmi>=18.5 && bmi<25){
            tvStatusAns.text = "Normal weight"
        } else if (bmi>=25 && bmi < 30){
            tvStatusAns.text = "Overweight"
        } else if (bmi>=30){
            tvStatusAns.text = "Obese"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.Standard -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
//                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
                finish()
                overridePendingTransition(R.anim.no_anim, R.anim.no_anim)
                true
            }
            R.id.Metric -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}