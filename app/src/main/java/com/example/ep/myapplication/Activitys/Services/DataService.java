package com.example.ep.myapplication.Activitys.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


import com.example.ep.myapplication.Activitys.Activitys.MainActivity;
import com.example.ep.myapplication.Activitys.Adapters.StateAdapter;
import com.example.ep.myapplication.Activitys.Model.State;
import com.example.ep.myapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by EP on 18/07/2017.
 */


//*****************************
//must add asyncTask! of thread UI
//*****************************

public class DataService
{

    public ArrayList<State> getArrState()
    {
        SendInBackground sendInBackground = new SendInBackground();
        sendInBackground.execute();
        ArrayList<State> statesArr=null;
        try {
            statesArr = sendInBackground.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return statesArr;
    }


    public State getBorder(String s)
    {
        SendInBackground2 sendInBackground2 = new SendInBackground2();
        sendInBackground2.execute(s);
        State state = null;
        try{
            state = sendInBackground2.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return state;
    }

    private class SendInBackground extends AsyncTask<Void,Void,ArrayList<State>>
    {
        @Override
        protected ArrayList<State> doInBackground(Void... voids)
        {
            ArrayList<State> arrState = new ArrayList<>();
            String sURL = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;flag"; // get all states
            // Connect to the URL using java's native library
            URL url = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                url = new URL(sURL);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                Log.d("result", "trying to connect");
                request.connect();
                Log.d("result", "connected");
                // Convert to a JSON object to print data
                JsonParser jp = new JsonParser(); //from gson
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

                JsonArray rootobj = root.getAsJsonArray(); //May be an array, may be an object.

                Log.d("result", rootobj.size() + "");

                for (JsonElement je : rootobj) {
                    JsonObject obj = je.getAsJsonObject(); //since you know it's a JsonObject
                    JsonElement entriesname = obj.get("name");//will return members of your object
                    JsonElement entriesnative = obj.get("nativeName");
                    JsonElement entriesborders = obj.get("borders");
                    JsonElement entriesflag = obj.get("flag");

                    String name = entriesname.toString().replace("\"", "");
                    String nativen = entriesnative.toString().replace("\"", "");
                    String flag = entriesflag.toString().replace("\"", "");

                    ArrayList<String> arrBorders = new ArrayList<String>();
                    JsonArray entriesbordersArray = entriesborders.getAsJsonArray();
                    Log.d("result", "yes");

                    for (JsonElement j : entriesbordersArray) {

                        String s = j.toString().replace("\"", "");
                        arrBorders.add(s);
                        Log.d("result", "no");
                    }

                    arrState.add(new State(name, arrBorders, nativen, flag));

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NetworkOnMainThreadException e) {
                e.printStackTrace();
            }

            return arrState;
        }
    }




     protected class SendInBackground2 extends AsyncTask<String,Void,State>
    {

        @Override
        protected State doInBackground(String... strings)
        {
            ArrayList<String> arrS = new ArrayList<>();
            String sURL = "https://restcountries.eu/rest/v2/alpha/" + strings[0]+"?fields=name;nativeName;flag"; // gets a state by code
            String name = null;
            State s = null;
            // Connect to the URL using java's native library
            URL url = null;

            try{
                url = new URL(sURL);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection request = (HttpURLConnection) url.openConnection();

                request.connect();


                JsonParser jp = new JsonParser(); //from gson
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

                JsonObject rootobj = root.getAsJsonObject();

                JsonElement entriesname = rootobj.get("name");
                JsonElement entriesnative = rootobj.get("nativeName");
                JsonElement entriesflag = rootobj.get("flag");


                String nameR = entriesname.toString().replace("\"",""); // convert to pure string
                String nativen = entriesnative.toString().replace("\"","");
                String flag = entriesflag.toString().replace("\"", "");

                s = new State(nameR,nativen,flag);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(State state)
        {
            super.onPostExecute(state);
        }
    }
}



