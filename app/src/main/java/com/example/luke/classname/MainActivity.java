package com.example.luke.classname;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.green;

public class MainActivity extends AppCompatActivity{
    int sum=26;
    public Button btn[] = new Button[sum];
    public String stuName[] = new String[]{
            "施信含", "曹焰", "邱若晨", "吕玉辉",
            "陈玉琪", "胡悦", "刘振环", "谢若天",
            "颜松", "叶加博", "刘航", "熊雨晨",
            "黄磊","詹国强","胡元华","江玉川",
            "甘海林","赵裕霖","毛嘉豪","梁倩",
            "黄仲英","刘中江","陈启凡","于媛芳",
            "曹志雄","刘强"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.bodyLayout1);
        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.bodyLayout2);

        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(1,10,100,1);

        for(int i=0;i<sum;i++)
        {
            btn[i]=new Button(this);

            btn[i].setId(View.generateViewId());
            btn[i].setText(stuName[i]);
            //final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btn[i].getLayoutParams();
            //lp.height=LinearLayout.LayoutParams.WRAP_CONTENT;
            //lp.width=LinearLayout.LayoutParams.FILL_PARENT;
            //btn[i].setLayoutParams(lp);
            btn[i].setTag(0);
            linearLayout1.addView(btn[i]);
            btn[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if((int)v.getTag()==0)
                    {
                        linearLayout1.removeView(v);
                        v.setTag(1);
                        linearLayout2.addView(v);

                    }
                    else
                    {
                        linearLayout2.removeView(v);
                        v.setTag(0);
                        //Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_LONG).show();
                        linearLayout1.addView(v);

                    }
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
        //Log.v("12","123");
        Toast.makeText(getApplicationContext(),"保存ing",Toast.LENGTH_SHORT).show();
        switch (id)
        {
            case R.id.save_info:
            {
                if(saveDataFunc())
                    Toast.makeText(getApplicationContext(),"保存完毕",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();
            }
            break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean saveDataFunc()
    {

        SQLiteDatabase dbs = openOrCreateDatabase("stuINFO.db", MODE_WORLD_READABLE,null);
        if(dbs==null)
            return false;
        Calendar cal = Calendar.getInstance();
        String nowTimeStr = new String();

        String section[] = new String[]{"name","tag"};
        nowTimeStr="add"+String.valueOf(cal.get(Calendar.YEAR))+"_"+String.valueOf(cal.get(Calendar.MONTH))+"_"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        String creatCom = "create table if not exists "+nowTimeStr+"(ID INT PRIMARY KEY NOT NULL,name text,tag int)";
        dbs.execSQL(creatCom);
        ContentValues tempStu = new ContentValues();
        for(int i=0;i<sum;i++)
        {
            //String test ="insert into "+nowTimeStr+"(name,tag) values('"+stuName[i]+"',"+btn[i].getTag().toString()+")";
            //dbs.execSQL("insert into "+nowTimeStr+"(name,tag) values(?,?)",new Object[]{nowTimeStr,stuName[i],(int)btn[i].getTag()});
            tempStu.put("name",stuName[i]);
            tempStu.put("tag",(int)btn[i].getTag());
            dbs.insert(nowTimeStr,null,tempStu);
        }
        //dbs.insert(nowTimeStr,null,tempStu);

        TextView tx = (TextView)findViewById(R.id.testText);
        Cursor readTemp;
        //dbs = openOrCreateDatabase("stuINFO.db",MODE_PRIVATE,null);
        readTemp= dbs.query(nowTimeStr,section,null,null,null,null,null);
        //readTemp = dbs.rawQuery("select * from "+nowTimeStr,null);
        String sss = new String("123");
        int test=readTemp.getCount();
        for(int i=0;i<readTemp.getCount();i++)
        {
            readTemp.move(i);
            sss+=readTemp.getString(0);
            sss+=" "+String.valueOf(readTemp.getInt(1));
        }
        tx.setText(sss);
        readTemp.close();
        dbs.close();
        return true;
    }
    /*判断表存不存在，写不来
    public  boolean SqlCreated(SQLiteDatabase dbs,String nowTimeStr)
    {
        Cursor readTemp;
        readTemp = dbs.rawQuery("select count(*) as c from sqlite_master+ where type ='table' and name ='"+nowTimeStr+"' ",null);

    }
    */
}


