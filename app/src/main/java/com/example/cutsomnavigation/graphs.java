package com.example.cutsomnavigation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Vector;

public class graphs extends AppCompatActivity {
GraphHelper GH;
    Spinner date_spinner;
int p=0;
int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphs_activity);
        GH=new GraphHelper(this);
        final String[] selected_date = {""};
        date_spinner = findViewById(R.id.date_spinner);

        Vector<String> datearray=new Vector<>();
        Vector<String> dateattendance=new Vector<>();
        PieChart pieChart = findViewById(R.id.pieChart);
        Cursor x = GH.getgraph();
        if(x!=null ) {
            if(x.getCount()!=0){
                while (x.moveToNext()) {
                    datearray.addElement(x.getString(0));
                    dateattendance.addElement(x.getString(4));
                }}
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(graphs.this,android.R.layout.simple_spinner_dropdown_item,datearray);
        date_spinner.setAdapter(adapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                selected_date[0] =""+parent.getItemAtPosition(position).toString();
                Toast.makeText(graphs.this, selected_date[0],Toast.LENGTH_SHORT).show();
                int l = datearray.indexOf(selected_date[0]);
                String s = ""+dateattendance.elementAt(l);
                a=0;
                p=0;
                for(int i=0;i<s.length();i++)
                {
                    char temp=s.charAt(i);
                    if("0".compareTo(""+temp)==0)
                    {
                        a=a+1;
                    }
                    if("0".compareTo(""+temp)!=0)
                    {
                        p=p+1;
                    }
                }
                ArrayList<PieEntry> attendance = new ArrayList<>();
                attendance.clear();
                attendance.add(new PieEntry(p*20,"PRESENT %"));
                attendance.add(new PieEntry(a*20,"ABSENT %"));
                PieDataSet pieDataSet = new PieDataSet(attendance,"Attendance");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Student\nAttendance");
                pieChart.animate();
                pieChart.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}