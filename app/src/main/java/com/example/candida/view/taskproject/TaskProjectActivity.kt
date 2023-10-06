package com.example.candida.view.taskproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.candida.R
import com.example.candida.model.modelClass.modelUsers
import com.example.candida.viewmodel.SharedViewModel


class TaskProjectActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navDrawer: ListView

    private lateinit var viewModelShare: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val login = intent.getStringExtra("user")
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        if(login!=null){
            viewModelShare = ViewModelProviders.of(this).get(SharedViewModel::class.java)
            modelUsers().getUserByLogin(login){
                if(it!=null){
                    val selectedItem = it
                    viewModelShare.selectedItemUsers(selectedItem)
                }

            }


        }

        setContentView(R.layout.activity_task_projec)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ic_menu_drawer,menu)
        return super.onCreateOptionsMenu(menu)
    }


}