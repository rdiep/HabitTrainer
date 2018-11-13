package com.android.habittrainer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.android.habittrainer.db.HabitDbTable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rv.adapter = HabitsAdapter(HabitDbTable(this).readAllHabits())

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_habit){
            switchTo(CreateHabitActivity::class.java)
        }
        return true
    }

    private fun switchTo(c: Class<*>) {
        val intent = Intent(this,c)
        startActivity(intent)
    }
}
