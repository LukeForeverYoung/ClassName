package com.example.luke.classname;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.green;

public class MainActivity extends AppCompatActivity{
    int sum=26;
    int ord=sum-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String stuName[] = new String[]{
                "施信含", "曹焰", "邱若晨", "吕玉辉",
                "陈玉琪", "胡悦", "刘振环", "谢若天",
                "颜松", "叶加博", "刘航", "熊雨晨",
                "黄磊","詹国强","胡元华","江玉川",
                "甘海林","赵裕霖","毛嘉豪","梁倩",
                "黄仲英","刘中江","陈启凡","于媛芳",
                "曹志雄","刘强"};
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bodyLayout);
        final Button btn[] = new Button[sum];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(1,10,100,1);

        for(int i=0;i<sum;i++)
        {
            btn[i]=new Button(this);
            btn[i].setId(View.generateViewId());
            btn[i].setText(stuName[i]);
            //final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btn[i].getLayoutParams();
            //lp.height=LinearLayout.LayoutParams.WRAP_CONTENT;
            //lp.width=LinearLayout.LayoutParams.FILL_PARENT;
            btn[i].setLayoutParams(lp);
            linearLayout.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    linearLayout.removeView(v);
                    v.setBackgroundColor(GREEN);
                    //v.setLayoutParams(lp);
                    linearLayout.addView(v,ord);
                    ord--;
                }
            });

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


