package me.uwu.autophoto.data;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataManager {
    public static boolean isEnabled(Context context) {
        boolean authorized = false;
        File f = new File(context.getFilesDir().getAbsolutePath() + "/enabled");
        if (!f.exists()) {
            setup(context);
            return true;
        }

        try {
            Log.d("AAAAAAAAAAAAAA", "f5e45fes87");
            InputStream inputStream = context.openFileInput("enabled");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = br.readLine();
            Log.d("AAAAAAAAAAAAAA", line);
            authorized = Boolean.parseBoolean(line);
        }
        catch (IOException e) {
            Log.e("Ex", e.getMessage());
        }
        return authorized;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void setup(Context context) {
        String directory = context.getFilesDir().getAbsolutePath();
        File enabled = new File(directory + "/enabled");
        File setup = new File(directory + "/setup");

        if (!enabled.exists()) {
            try {
                enabled.createNewFile();
                disable(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!setup.exists()) {
            try {
                setup.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Log.i("Info", "Setup finished.");
    }

    public static boolean hasSetup(Context context) {
        String directory = context.getFilesDir().getAbsolutePath();
        return new File(directory + "/setup").exists();
    }

    public static void disable(Context context) {
        Log.d("eee", "disabling");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("enabled", Context.MODE_PRIVATE));
            outputStreamWriter.write("false");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static void enable(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("enabled", Context.MODE_PRIVATE));
            outputStreamWriter.write("true");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static void toggle(Context context) {
        if (isEnabled(context))
            disable(context);
        else enable(context);
    }
}
