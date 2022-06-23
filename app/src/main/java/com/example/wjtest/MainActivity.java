package com.example.wjtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner activity_Spinner, activity_min;
    private Button btn_add_rewards;
    private ImageView btn_back_left;
    String activityCategory = "";
    String activityMin = "";
    String userID;
    int c, p;
    String calories;
    String points;
    FirebaseAuth mAuth;
    private DatabaseReference activityRef, pointRef;
    private ProgressDialog progressDialog;
    String recordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        recordID = UUID.randomUUID().toString();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        toolbar = (Toolbar) findViewById(R.id.add_record_toolbar);
        activity_Spinner = (Spinner) findViewById(R.id.aar_spiActivity);
        activity_min = (Spinner) findViewById(R.id.aar_spiMinutes);
        btn_add_rewards = (Button) findViewById(R.id.add_record_button);
        btn_back_left = (ImageView) findViewById(R.id.addrecord_back);
        progressDialog = new ProgressDialog(MainActivity.this);

        btn_back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> activity_category = new ArrayList<String>();
        activity_category.add("Please Select");
        activity_category.add("Running");
        activity_category.add("Biking");
        activity_category.add("Jogging");
        activity_category.add("Cycling");

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activity_category);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        activity_Spinner.setAdapter(ad);
        activity_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityCategory = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> activiy_Min = new ArrayList<String>();
        activiy_Min.add("Please Select");
        activiy_Min.add("01");
        activiy_Min.add("02");
        activiy_Min.add("03");
        activiy_Min.add("04");
        activiy_Min.add("05");
        activiy_Min.add("06");
        activiy_Min.add("07");
        activiy_Min.add("08");
        activiy_Min.add("09");
        activiy_Min.add("10");
        activiy_Min.add("11");
        activiy_Min.add("12");
        activiy_Min.add("13");
        activiy_Min.add("14");
        activiy_Min.add("15");
        activiy_Min.add("16");
        activiy_Min.add("17");
        activiy_Min.add("18");
        activiy_Min.add("19");
        activiy_Min.add("20");
        activiy_Min.add("21");
        activiy_Min.add("22");
        activiy_Min.add("23");
        activiy_Min.add("24");
        activiy_Min.add("25");
        activiy_Min.add("26");
        activiy_Min.add("27");
        activiy_Min.add("28");
        activiy_Min.add("29");
        activiy_Min.add("30");
        activiy_Min.add("31");
        activiy_Min.add("32");
        activiy_Min.add("33");
        activiy_Min.add("34");
        activiy_Min.add("35");
        activiy_Min.add("36");
        activiy_Min.add("37");
        activiy_Min.add("38");
        activiy_Min.add("39");
        activiy_Min.add("40");
        activiy_Min.add("41");
        activiy_Min.add("42");
        activiy_Min.add("43");
        activiy_Min.add("44");
        activiy_Min.add("45");
        activiy_Min.add("46");
        activiy_Min.add("47");
        activiy_Min.add("48");
        activiy_Min.add("49");
        activiy_Min.add("50");
        activiy_Min.add("51");
        activiy_Min.add("52");
        activiy_Min.add("53");
        activiy_Min.add("54");
        activiy_Min.add("55");
        activiy_Min.add("56");
        activiy_Min.add("57");
        activiy_Min.add("58");
        activiy_Min.add("59");
        activiy_Min.add("60");

        ArrayAdapter<String> min = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activiy_Min);
        min.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_min.setAdapter(min);
        activity_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityMin = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_add_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("SYSTEM");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if (activityCategory.equals("Please Select")) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Please Select Activity", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (activityMin.equals("Please Select")) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Please Select Minutes", Toast.LENGTH_SHORT).show();
                    return;

                } else {

                    int min = Integer.parseInt(activityMin);

                    if (activityCategory.equals("Running")) {
                        if (min <= 20) {
                            c = 300;
                            calories = String.valueOf(c);
                            p = 100;
                            points = String.valueOf(p);

                        } else if (min <= 40) {
                            c = 400;
                            calories = String.valueOf(c);
                            p = 200;
                            points = String.valueOf(p);

                        } else {
                            c = 500;
                            calories = String.valueOf(c);
                            p = 300;
                            points = String.valueOf(p);

                        }
                    } else if (activityCategory.equals("Biking")) {
                        if (min <= 20) {
                            c = 300;
                            calories = String.valueOf(c);
                            p = 100;
                            points = String.valueOf(p);

                        } else if (min <= 40) {
                            c = 400;
                            calories = String.valueOf(c);
                            p = 200;
                            points = String.valueOf(p);

                        } else {
                            c = 500;
                            calories = String.valueOf(c);
                            p = 300;
                            points = String.valueOf(p);

                        }
                    } else if (activityCategory.equals("Cycling")) {
                        if (min <= 20) {
                            c = 300;
                            calories = String.valueOf(c);
                            p = 100;
                            points = String.valueOf(p);

                        } else if (min <= 40) {
                            c = 400;
                            calories = String.valueOf(c);
                            p = 200;
                            points = String.valueOf(p);

                        } else {
                            c = 500;
                            calories = String.valueOf(c);
                            p = 300;
                            points = String.valueOf(p);

                        }
                    } else if (activityCategory.equals("Jogging")) {
                        if (min <= 20) {
                            c = 300;
                            calories = String.valueOf(c);
                            p = 100;
                            points = String.valueOf(p);

                        } else if (min <= 40) {
                            c = 400;
                            calories = String.valueOf(c);
                            p = 200;
                            points = String.valueOf(p);

                        } else {
                            c = 500;
                            calories = String.valueOf(c);
                            p = 300;
                            points = String.valueOf(p);

                        }
                    }

                    activityRef = FirebaseDatabase.getInstance().getReference();
                    activityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!(snapshot.child("Record").child(recordID).exists())) {
                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("userID", userID);
                                userdataMap.put("activity", activityCategory);
                                userdataMap.put("minutes", activityMin);
                                userdataMap.put("date", timeStamp);
                                userdataMap.put("calories", calories);
                                userdataMap.put("points", points);

                                activityRef.child("Record").child(recordID).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            pointRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                            pointRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    progressDialog.dismiss();
                                                    String balance_points = snapshot.child("points").getValue().toString();
                                                    int balance = Integer. parseInt(balance_points);
                                                    int add_points = Integer. parseInt(points);
                                                    int total_balance = balance + add_points;
                                                    String total_PointBalance = String.valueOf(total_balance);

                                                    HashMap<String, Object> fromupdates = new HashMap<>();
                                                    fromupdates.put("points", total_PointBalance);
                                                    pointRef.updateChildren(fromupdates);

                                                    Toast.makeText(getApplicationContext(), "Add Record Complete", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Network Error. Please Try Again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Network Error. Please try Again", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
