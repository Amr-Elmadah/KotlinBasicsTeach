package com.amr.kotlinteach.catfacts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amr.kotlinteach.R
import kotlinx.android.synthetic.main.activity_cat_facts.*

class CatFactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_facts)

        setSupportActionBar(toolbar)
    }
}
