package com.example.luke.classname;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    static String test="Luke ";
    int sum=26;
    int classTag=0;
    public Button btn[] = new Button[sum];
    static public String stuName[] = new String[]{
            "施信含", "曹焰", "邱若晨", "吕玉辉",
            "陈玉琪", "胡悦", "刘振环", "谢若天",
            "颜松", "叶加博", "刘航", "熊雨晨",
            "黄磊","詹国强","胡元华","江玉川",
            "甘海林","赵裕霖","毛嘉豪","梁倩",
            "黄仲英","刘中江","陈启凡","于媛芳",
            "曹志雄","刘强"};
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(test,"My activity Pause");
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(test,"My activity Start");
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(test,"My activity Restart");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(test,"My activity Stop");
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(test,"My activity Resume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(test,"My activity Create");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.bodyLayout1);
        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.bodyLayout2);

        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(1,10,100,1);

        initClassSelector();
        /*
        动态加载名单按钮,并设置onClick监听器
         */
        initNameBtn(linearLayout1,linearLayout2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(R.id.abc),"保存ing",Snackbar.LENGTH_LONG).show();

                if(saveDataFunc())
                    Toast.makeText(getApplicationContext(),"保存完毕",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_LONG).show();

            }
        });
        NavigationView naviView = (NavigationView)findViewById(R.id.navigation);
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                boolean flag=true;
                DrawerLayout dl = (DrawerLayout)findViewById(R.id.drawer_layout_nav);

                switch (id)
                {
                    case R.id.show_list:
                        Intent showNameIntent = new Intent();
                        showNameIntent.setClass(MainActivity.this,NameListActivity.class);
                        startActivity(showNameIntent);
                        break;
                }
                return flag;
            }
        });
    }
    /*
    Creating Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
    /*
    数据库保存当前名单
     */
    @Override
    //设置监听器
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Log.v("12","123");
        switch (id)
        {
            case R.id.show_list:
            {
                Intent showNameIntent = new Intent();
                showNameIntent.setClass(MainActivity.this,NameListActivity.class);
                startActivity(showNameIntent);
            }
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //保存当前名单状态
    public boolean saveDataFunc()
    {
        SQLiteDatabase dbs = openOrCreateDatabase("stuINFO.db", MODE_PRIVATE,null);
        if(dbs==null)
            return false;
        Calendar cal = Calendar.getInstance();
        String nowTimeStr;

        String section[] = new String[]{"ID","name","tag"};
        nowTimeStr="add"+String.valueOf(cal.get(Calendar.YEAR))+"_"+String.valueOf(cal.get(Calendar.MONTH)+1)+"_"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String tabName = "classTag"+classTag+nowTimeStr;
        //Log.v("str",tabName);
        String creatCom = "create table "+tabName+" (ID INT PRIMARY KEY NOT NULL,name text,tag int)";
        if(!tabExist(dbs,tabName))
            dbs.execSQL(creatCom);

        for(int i=0;i<sum;i++)
        {
            if(i==sum-2)
                i=sum-2;
            ContentValues tempStu = new ContentValues();
            //String test ="insert into "+nowTimeStr+"(name,tag) values('"+stuName[i]+"',"+btn[i].getTag().toString()+")";
            //dbs.execSQL("insert into "+nowTimeStr+"(name,tag) values(?,?)",new Object[]{nowTimeStr,stuName[i],(int)btn[i].getTag()});
            tempStu.put("ID",i);
            tempStu.put("name",stuName[i]);
            tempStu.put("tag",(int)btn[i].getTag());
            dbs.insert(tabName,null,tempStu);
        }

        dbs.close();
        return true;
    }
    boolean tabExist(SQLiteDatabase dbs,String tabName)
    {
        boolean rec=false;
        if(tabName==null)
            return false;
        Cursor cursor = null;
        try
        {                 //select count(*) from sqlite_master where type='table' and name='%1
            String sql = "select count(*) from sqlite_master where type = 'table' and name = '"+tabName.trim()+"'";
            cursor = dbs.rawQuery(sql,null);
            if(cursor.moveToNext())
            {
                int cnt = cursor.getInt(0);
                if(cnt>0)
                    rec=true;
            }
        }
        catch (Exception e)
        {
            return rec;
        }
        return rec;
    }
    void initClassSelector()
    {
        Spinner mSpinner = (Spinner) findViewById(R.id.classSpinner);
        final String[] mClassItem = getResources().getStringArray(R.array.class_name);
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mClassItem);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(stringAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classTag=position;
                Toast.makeText(getApplicationContext(),mClassItem[position],Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
    void initNameBtn(final LinearLayout linearLayout1,final LinearLayout linearLayout2)
    {
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

                        linearLayout1.addView(v);
                    }
                }
            });
        }
    }
}



