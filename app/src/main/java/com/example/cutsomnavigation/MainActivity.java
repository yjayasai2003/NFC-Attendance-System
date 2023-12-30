package com.example.cutsomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.transition.Slide;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView pass;
    int lock=0;
    public String text="";
    Button one,two,three,four,five,six,seven,eight,nine,zero,ok,backspace;
    public int passlength=5;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        three=findViewById(R.id.three);
        four=findViewById(R.id.four);
        five=findViewById(R.id.five);
        six=findViewById(R.id.six);
        seven=findViewById(R.id.seven);
        eight=findViewById(R.id.eight);
        nine=findViewById(R.id.nine);
        zero=findViewById(R.id.zero);
        ok=findViewById(R.id.ok);
        backspace=findViewById(R.id.backspace);
        pass=findViewById(R.id.pass);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        pass.setText("ENTER PIN");
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"1";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"2";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"3";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"4";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"5";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"6";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"7";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"8";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }checkpass();
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"9";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }
                checkpass();
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passlength!=(pass.getText()).length())
                {
                    text=text+"0";
                    pass.setText(text);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Password can be only 5 digits", Toast.LENGTH_SHORT).show();
                    pass.setText(text);
                }
                checkpass();
            }
        });
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.length()>=1)
                {
                    String temp = text.substring(0,text.length()-1);
                    text=""+temp;
                }
                if(text.length()==0)
                {
                    pass.setText("ENTER CODE");
                }
                else
                {
                    pass.setText(text);
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.length()!=5)
                {
                    Toast.makeText(MainActivity.this, "Password Must Be 5 Digits", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(text.compareTo("09123")==0)
                    {
                        pass.setText("PASSCODE");
                        Toast.makeText(MainActivity.this, "You Entered Correct Code", Toast.LENGTH_SHORT).show();
                        text="";
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Sorry! Try Again", Toast.LENGTH_SHORT).show();
                        text="";
                        pass.setText("PASSCODE");


                    }
                }
            }
        });
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId()) {
                    case R.id.menu_notice:
                        if (lock == 1) {
                            Intent intent = new Intent(getApplicationContext(), notice.class);
                            startActivity(intent);

                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            Toast.makeText(MainActivity.this, "Please Enter Pin!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.menu_reports:
                        if (lock == 1) {
                            Intent kntent = new Intent(getApplicationContext(), graphs.class);
                            startActivity(kntent);
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Please Enter Pin!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.menu_attendance:
                        if (lock == 1) {
                        Intent lntent = new Intent(getApplicationContext(), attendance.class);
                        startActivity(lntent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                          }
                        else
                         {
                              Toast.makeText(MainActivity.this, "Please Enter Pin!", Toast.LENGTH_SHORT).show();
                         }
                            break;

                case R.id.menu_studentreport:
                if (lock == 1) {
                    Intent lntent = new Intent(getApplicationContext(), studentReport.class);
                    startActivity(lntent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter Pin!", Toast.LENGTH_SHORT).show();
                }
                break;
                    case R.id.menu_aboutus:

                            Intent lntent = new Intent(getApplicationContext(), About.class);
                            startActivity(lntent);
                            drawerLayout.closeDrawer(GravityCompat.START);

                        break;
            }

                return true;
            }

        });

    }
    public void checkpass()
    {
        if(text.length()==5)

        {
            if(text.compareTo("09123")==0)
            {
                pass.setText("MENU UNLOCKED");
                lock=1;
                Toast.makeText(MainActivity.this, "Menu Unlocked", Toast.LENGTH_SHORT).show();
                text="";
            }
            else
            {
                Toast.makeText(MainActivity.this, "Sorry! Try Again", Toast.LENGTH_SHORT).show();
                text="";
                pass.setText("ENTER PIN");
            }
        }
    }
}
