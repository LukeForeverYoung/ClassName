package com.example.luke.classname;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Luke on 2017/1/15.
 */

public class NameListActivity extends AppCompatActivity {
    class everyClass
    {
        int classTag;
        String Time;
        int year;
        int mon;
        int day;
        String[] state = new String[]{"缺勤","迟到"};
        public ArrayList<Stu> OutOfClassStu;
        public ArrayList<Stu> LateStu;
        public void getClassInfo(String tabName)
        {
            boolean f=true;
            int tag=0;
            for(int i=0;i<tabName.length();i++)
            {
                char temp=tabName.charAt(i);
                if(f)
                {
                    if(Character.isDigit(temp)) {
                        tag *= 10;
                        tag += temp - '0';
                    }
                    else if(i>=1&&Character.isDigit(tabName.charAt(i-1)))
                        f=false;
                }
                else
                {
                    Time=tabName.substring(i+2);
                    break;
                }
            }
            String[] temp=Time.split("_");
            year=Integer.parseInt(temp[0]);
            mon=Integer.parseInt(temp[1]);
            day=Integer.parseInt(temp[2]);
            classTag=tag;
        }
        public everyClass()
        {
            OutOfClassStu = new ArrayList<Stu>();
            LateStu = new ArrayList<Stu>();
        }
    }
    public class Stu
    {
        String name;
        int state;
    }
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase dbs = openOrCreateDatabase("stuINFO.db", MODE_PRIVATE,null);
        String sql = "select name from sqlite_master where type = 'table' order by name";
        Cursor findTab,readTemp;
        findTab=dbs.rawQuery(sql,null);
        String section[] = new String[]{"ID","name","tag"};
        final List<everyClass> classList = new ArrayList<>();//存放所有表
        findTab.moveToNext();//跳过第一个android_metadata
        while(findTab.moveToNext())
        {
            String tabName = findTab.getString(0);
            readTemp= dbs.query(tabName,section,null,null,null,null,null);
            everyClass nowClass = new everyClass();
            nowClass.getClassInfo(tabName);
            while(readTemp.moveToNext())
            {
                Stu student = new Stu();
                student.name=readTemp.getString(1);
                student.state=readTemp.getInt(2);
                if(student.state==0)
                    nowClass.OutOfClassStu.add(student);
                else if(student.state==2)
                    nowClass.LateStu.add(student);
            }
            readTemp.close();
            classList.add(nowClass);
        }
        findTab.close();
        dbs.close();
        Collections.sort(classList,new sortByTimeAndClass());
        String[] mClassName = getResources().getStringArray(R.array.class_name);
        Spinner mSpinner = (Spinner)findViewById(R.id.classSpinner);
        final String[] mTabList = new String[classList.size()];
        for(int i=0;i<classList.size();i++)
            mTabList[i]=classList.get(i).Time+mClassName[classList.get(i).classTag];
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mTabList);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(stringAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                everyClass now = classList.get(position);
                addTextView(now);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void addTextView(everyClass now)
    {
        LinearLayout lp = (LinearLayout) findViewById(R.id.nameList);
        lp.removeAllViews();
        boolean txFlag=true;

        if(now.OutOfClassStu.size()!=0)
        {
            TextView OutOfClassHead = new TextView(this);
            OutOfClassHead.setText("下列同学缺勤");
            OutOfClassHead.setTextSize(26);
            OutOfClassHead.setGravity(Gravity.CENTER);
            OutOfClassHead.setTextColor(getColor(R.color.redName));
            lp.addView(OutOfClassHead);
        }
        for(Stu nowStu:now.OutOfClassStu)
        {
                TextView tx = new TextView(this);
                tx.setText(nowStu.name);
                tx.setTextSize(26);
                tx.setGravity(Gravity.CENTER);
                tx.setTextColor(getColor(R.color.redName));
                lp.addView(tx);
                txFlag=false;
        }
        if(now.LateStu.size()!=0)
        {
            TextView LateHead = new TextView(this);
            LateHead.setText("下列同学迟到");
            LateHead.setTextSize(26);
            LateHead.setGravity(Gravity.CENTER);
            LateHead.setTextColor(getColor(R.color.blueName));
            lp.addView(LateHead);
        }
        for(Stu nowStu:now.LateStu)
        {
                TextView tx = new TextView(this);
                tx.setText(nowStu.name);
                tx.setTextSize(26);
                tx.setGravity(Gravity.CENTER);
                tx.setTextColor(getColor(R.color.blueName));
                lp.addView(tx);
                txFlag=false;
        }
        if(txFlag)
        {
            TextView tx = new TextView(this);
            tx.setText("本节课无人缺勤！");
            tx.setTextSize(26);
            tx.setGravity(Gravity.CENTER);
            tx.setTextColor(getColor(R.color.colorGreen));
            lp.addView(tx);
        }

    }
    class sortByTimeAndClass implements Comparator<everyClass>
    {
        public int compare(everyClass a,everyClass b)
        {
            if(a.year==b.year)
            {
                if (a.mon == b.mon)
                    return b.day-a.day;
                else
                    return b.mon-a.mon;
            }
            else
                return b.year-a.year;
        }
    }
}
