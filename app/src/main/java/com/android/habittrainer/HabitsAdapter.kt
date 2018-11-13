package com.android.habittrainer

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.habittrainer.db.HabitDbTable
import com.android.habittrainer.db.HabitTrainerDb
import kotlinx.android.synthetic.main.single_card.view.*

class HabitsAdapter(val habits: MutableList<Habit>) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>(){

    private var title = ""
    lateinit var hb: HabitsAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)


        view.setOnLongClickListener( object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {


                HabitDbTable(parent.context).delete(title)
                notifyItemRemoved(viewType)
                Log.i("CLICKED","CLICKED");
                return true;
            }

        })

        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.iv.tv_title.text = habits[position].title
        title = habits[position].title
        holder.iv.tv_description.text = habits[position].description
        holder.iv.iv_icon.setImageBitmap( habits[position].image )
        holder.iv.setOnLongClickListener( object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {


                HabitDbTable(holder.iv.context).delete(title)
                habits.removeAt(position)
                notifyItemRemoved(position)
                Log.i("CLICKED",position.toString());
                return true;
            }
        })
    }

    class HabitViewHolder(val iv: View) : RecyclerView.ViewHolder(iv)


    }


