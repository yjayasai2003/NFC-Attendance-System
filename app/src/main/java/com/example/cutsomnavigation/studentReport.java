package com.example.cutsomnavigation;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Vector;
public class studentReport extends AppCompatActivity {
    GraphHelper GH;
    TextView days;
    int totaldays=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GH=new GraphHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentreport_layout);
        days=findViewById(R.id.days);
        Cursor x = GH.getgraph();
        Vector<String> attendancelist = new Vector<>();
        Vector<Integer> counts = new Vector<>();
        totaldays=x.getCount();
        days.setText("Total Working Hours = "+totaldays);
        for(int i =0;i<5;i++)
        {
            counts.addElement(0);
        }
        while(x.moveToNext()) {
        attendancelist.addElement(x.getString(4));
        }
        for(int i=0;i<attendancelist.size();i++)
        {
            String temp = attendancelist.elementAt(i);
            for(int j=0;j<temp.length();j++)
            {
                String t=""+""+temp.charAt(j);
                if(t.compareTo("1")==0) {
                    int num = counts.elementAt(j) + 1;
                    counts.set(j,num);
                }
            }
        }
        BarChart barChart = findViewById(R.id.barChart);

        ArrayList <BarEntry> studentreport = new ArrayList<>();
        for(int i =0;i<counts.size();i++)
        {
            studentreport.add(new BarEntry(i,counts.elementAt(i)));
        }
        final String[] labels = new String[] {"U1", "U2", "U3", "U4", "U5"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarDataSet barDataSet = new BarDataSet(studentreport,"StudentS Report");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Student Attendance Report");
        barChart.animateY(2000);
    }
}