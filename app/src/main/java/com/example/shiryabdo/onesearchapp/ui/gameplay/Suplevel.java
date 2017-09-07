package com.example.shiryabdo.onesearchapp.ui.gameplay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.shiryabdo.onesearchapp.DataModel;
import com.example.shiryabdo.onesearchapp.R;
import com.example.shiryabdo.onesearchapp.adapters.RecyclerViewAdapter;
import com.example.shiryabdo.onesearchapp.base.BaseActivity;

import java.util.ArrayList;

public class Suplevel extends BaseActivity implements RecyclerViewAdapter.ItemListener {

    RecyclerView recyclerView;
    ArrayList arrayList ,newScore ;
    Bundle bundle ;
    RecyclerViewAdapter adapter;
    int qutionmuberLevel ;
    Long timeAfterup;
    Intent i;
    int timMin  ;
    String score ,StringShERED  ;
    int imagLoc ,NUMERoflevel  ;
    SharedPreferences sharedPreferences ;
String backGroundColor,level;
    private final static long ROUND_TIME_IN_MS = 90000;
    TextDrawable drawable;
    boolean checkPss= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seve_lavels_recycleview);
         arrayList = new ArrayList();

        newScore=new ArrayList();
        bundle=new Bundle();
        bundle =getIntent().getExtras();
         if (bundle!=null) {
            qutionmuberLevel= bundle.getInt("qutionNumper");
             timeAfterup=bundle.getLong("timeAfter");
             score= bundle.getString("scoreView");
             NUMERoflevel=bundle.getInt("numerOflevels");
             level=bundle.getString("level");


         }

        sharedPreferences= getSharedPreferences(level, Context.MODE_PRIVATE);

        newScore.add(0);
        for(int i = 1; i <=sharedPreferences.getAll().size();){
            newScore.add(sharedPreferences.getString(Integer.toString(i), ""));
            i++;

        }






        newScore.size();


         imagLoc= R.drawable.lock_icon;
        backGroundColor="#787878";



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        if(StringShERED!= "" ) {
            for (int k=1; k <newScore.size(); ) {

                backGroundColor="#1ACA85";
                  drawable= TextDrawable.builder()
                       .beginConfig()
                         .textColor(Color.BLACK)
                         .useFont(Typeface.DEFAULT)
                         .width(60)
                         .height(60)
                         .fontSize(30)
                         .bold()
                         .toUpperCase()
                         .endConfig()
                        .buildRect(Integer.toString(k), Color.parseColor(backGroundColor));

                arrayList.add(new DataModel(k, drawable, backGroundColor));
                k++;
            }
             for(int i = newScore.size(); i < 500;i++)
            {
                            backGroundColor="#787878";
                 drawable= TextDrawable.builder()
                         .beginConfig()
                         .useFont(Typeface.DEFAULT)
                         .textColor(Color.BLACK)
                         .width(60)
                         .height(60)
                          .fontSize(30)
                         .bold()
                         .toUpperCase()
                          .endConfig()
                        .buildRect(Integer.toString(i), Color.parseColor(backGroundColor));

                    imagLoc = R.drawable.lock_icon;
                    arrayList.add(new DataModel(i, drawable,backGroundColor));





            }


        }else{
            for(int i = 1; i <  1;)
            {
                 drawable= TextDrawable.builder()
                        .buildRect(Integer.toString(i), Color.RED);
                imagLoc = R.drawable.lock_icon;
                arrayList.add(new DataModel(i, drawable, backGroundColor));

                i++;
            }

        }





        adapter= new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 6));
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onItemClick(DataModel item) {
          i= new Intent(Suplevel.this, WordSearchActivity.class);
        timing(item.text) ;
         i.putExtra("timeAfter", timing(item.text) );
         i.putExtra("numerOflevels", item.text );
        i.putExtra("level", level );



        if(item.text==1){
            i.putExtra("qutionNumper",  5 );
             checkPss=true;

        }
        else{
            if(item.text==newScore.size()){ item.drawable=TextDrawable.builder()
                    .beginConfig()
                    .textColor(Color.BLACK)
                    .useFont(Typeface.DEFAULT)
                    .width(60)
                    .height(60)
                    .fontSize(30)
                    .bold()
                    .toUpperCase()
                    .endConfig()
                    .buildRect(Integer.toString(1), Color.parseColor(backGroundColor));

            }
             if (item.text>newScore.size()){
                checkPss=    false;
                     }else {
                 checkPss=     true;
                     }

               if(item.text<=10) {
                i.putExtra("qutionNumper",  5 );
                 }else if (10<item.text&&item.text<=20){
                 i.putExtra("qutionNumper",  7 );
                }else if (20<item.text&&item.text<=30){
                       i.putExtra("qutionNumper",  8 );
               }else if (30<item.text&&item.text<=40){
                    i.putExtra("qutionNumper",  9 );

               }else if (40<item.text&&item.text<=50){
                    i.putExtra("qutionNumper",  10 );
               } else if (50<item.text&&item.text<=60){
                   i.putExtra("qutionNumper",  11 );
               }




            }




        if (checkPss!=false) {

            startActivity(i);
            onDestroy();


        }else{



        }



    }
     long timing (int number){
         int  numberMedual =  number % 10 ;
         int  afterMin =  numberMedual -1 ;
         long mxNumb=  afterMin *5000;
         long timmi= ROUND_TIME_IN_MS - mxNumb ;

        return    timmi;
    }
}



