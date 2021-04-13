package com.example.ep.myapplication.Activitys.Activitys;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ep.myapplication.Activitys.Fragments.SecondFragment;
import com.example.ep.myapplication.Activitys.Fragments.mainFirstFragment;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.R;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements mainFirstFragment.OnFirstFragmentInteractionListener, SecondFragment.OnSecondFragmentInteractionListener {


    private  int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // fregment_container = all the fragments will be on him

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fregment_container);

        if(fragment == null ) // if it the first time to call the first fragment
        {
            fragment = new mainFirstFragment();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.fregment_container, fragment,"0").commit();

        }

    }

    public void LoadSecFragment(State s) // replace the first fragment with the second fragment
    {

        SecondFragment secondFregment = new SecondFragment();

        getIntent().putExtra("StateOb",  s);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fregment_container, secondFregment,(getSupportFragmentManager().getBackStackEntryCount()-1)+"").addToBackStack(null).commit();
        flag =1;
    }

    public void GoBack() { // return to first fragment

        Fragment f;
        getSupportFragmentManager().popBackStack();
        int num = getSupportFragmentManager().getBackStackEntryCount();
        f = getSupportFragmentManager().findFragmentByTag("0");
        getSupportFragmentManager().beginTransaction().replace(R.id.fregment_container, f,(getSupportFragmentManager().getBackStackEntryCount()-1)+"").commit();
        flag=0;
    }


    @Override
    public void onBackPressed() { // override the back button on android phones

        if( flag == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }


    @Override
    public void onSecondFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFirstFragmentInteraction(Uri uri) {

    }


}
