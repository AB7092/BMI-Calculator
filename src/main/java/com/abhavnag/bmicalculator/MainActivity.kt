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
import android.widget.SeekBar
import android.widget.TextView
import org.w3c.dom.Text

private const val TAG = "MainActivity"
private const val INITIAL_INCH_VALUE = 0
class MainActivity : AppCompatActivity() {
    private lateinit var etFeet: EditText
    private lateinit var etWeight: EditText
    private lateinit var seekBarInches: SeekBar
    private lateinit var tvInches: TextView
    private lateinit var tvBMIAmount: TextView
    private lateinit var tvStatusAns: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etFeet = findViewById(R.id.etFeet)
        etWeight = findViewById(R.id.etWeight)
        seekBarInches = findViewById(R.id.seekBarInches)
        tvInches = findViewById(R.id.tvInches)
        tvBMIAmount = findViewById(R.id.tvBMIAmount)
        tvStatusAns = findViewById(R.id.tvStatusAns)

        seekBarInches.progress = INITIAL_INCH_VALUE
        tvInches.text = "$INITIAL_INCH_VALUE"+" in"
        seekBarInches.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "OnProgressChanged $p1")
                tvInches.text = "$p1"+" in"
                computeBMI()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        etFeet.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"afterTextChanged $p0")
                computeBMI()
            }

        })
        etWeight.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeBMI()
            }

        })
    }

    private fun computeBMI() {
        if (etFeet.text.isEmpty()||etWeight.text.isEmpty()){
            tvBMIAmount.text = ""
            tvStatusAns.text = ""
            return
        }
        //Get value of height, inches, and weight
        val feet = etFeet.text.toString().toInt()
        val weight = etWeight.text.toString().toDouble()
        val inches = seekBarInches.progress
        //Compute BMI and Status
        val totalInches = (feet*12) + inches
        val heightMeters = totalInches * 0.0254
        val weightKg = weight * 0.45359237
        if (heightMeters==0.0){
            tvBMIAmount.text = ""
            tvStatusAns.text = ""
            return
        }
        val bmi = weightKg / (heightMeters * heightMeters)
        //Update UI
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

                true
            }
            R.id.Metric -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
//                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
                finish()
                overridePendingTransition(R.anim.no_anim, R.anim.no_anim)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}