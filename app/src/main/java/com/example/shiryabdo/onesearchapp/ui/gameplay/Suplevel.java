package com.example.shiryabdo.onesearchapp.ui.gameplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shiryabdo.onesearchapp.DataModel;
import com.example.shiryabdo.onesearchapp.R;
import com.example.shiryabdo.onesearchapp.adapters.RecyclerViewAdapter;
import com.example.shiryabdo.onesearchapp.base.BaseActivity;

import java.util.ArrayList;

public class Suplevel extends BaseActivity implements RecyclerViewAdapter.ItemListener {
//     int numberQution_level_one = 5;
//    int numberQution_level_two = 10;
//    int numberQution_level_three =30;
    RecyclerView recyclerView;
    ArrayList arrayList ,newScore ;
    Bundle bundle ;
    RecyclerViewAdapter adapter;
    int qutionmuberLevel ;
    Long timeAfterup;
    Intent i;
    int timMin  ;
    String score ,StringShERED  ;
    int imagLoc ,NUMERoflevel ,level ;
    SharedPreferences sharedPreferences ;

    boolean checkPss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seve_lavels_recycleview);
        sharedPreferences= getSharedPreferences("not", Context.MODE_PRIVATE);
        arrayList = new ArrayList();

        newScore=new ArrayList();
        bundle=new Bundle();
        bundle =getIntent().getExtras();
         if (bundle!=null) {
            qutionmuberLevel= bundle.getInt("qutionNumper");
             timeAfterup=bundle.getLong("timeAfter");
             score= bundle.getString("scoreView");
             NUMERoflevel=bundle.getInt("numerOflevels");
             level=bundle.getInt("level");


         }
//        StringShERED=sharedPreferences.getString("1", "");
//        String two=sharedPreferences.getString("2", "");
//        Toast.makeText(getApplicationContext(), StringShERED + "heree "+two+"/", Toast.LENGTH_SHORT).show();
        newScore.add(0);
        for(int i = 1; i <=sharedPreferences.getAll().size();){
            newScore.add(sharedPreferences.getString(Integer.toString(i), ""));
            i++;

        }
//          for(int n = 1; n<  5;){
//              newScore.add(n);
//              ++n;
//        }


        newScore.size();


         imagLoc= R.drawable.lock_icon;



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        if(StringShERED!= "" ) {
            for (int k=1; k <newScore.size(); ) {
                imagLoc = R.drawable.lock;
                arrayList.add(new DataModel(k, imagLoc, "#09A9FF"));
                k++;
            }
             for(int i = newScore.size(); i < 20;i++)
            {


                    imagLoc = R.drawable.lock_icon;
                    arrayList.add(new DataModel(i, imagLoc, "#09A9FF"));





            }


        }else{
            for(int i = 1; i <  1;)
            {

                imagLoc = R.drawable.lock_icon;
                arrayList.add(new DataModel(i, imagLoc, "#09A9FF"));

                i++;
            }

        }





        adapter= new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onItemClick(DataModel item) {
        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
         i= new Intent(Suplevel.this, WordSearchActivity.class);

        if(item.text==1){
            i.putExtra("qutionNumper",  5 );
            i.putExtra("timMin" ,  0);
            i.putExtra("numerOflevels", item.text );
            checkPss=true;
        }
        else{
             if (item.text>=newScore.size()){
                checkPss= false;
            }else {
                 checkPss= true;
             }
            i.putExtra("numerOflevels", item.text );
//            i.putExtra("timeAfter", timeAfterup );
            if(qutionmuberLevel==0){
                i.putExtra("qutionNumper",  item.text );
            }else {
                i.putExtra("qutionNumper", qutionmuberLevel );

            }
            i.putExtra("timMin" , 5000);
        }


        if (checkPss!=false) {
            startActivity(i);
        }else{

            Toast.makeText(getApplicationContext(), item.text -1+ "you sould pass  the level", Toast.LENGTH_SHORT).show();


        }

    }
}



