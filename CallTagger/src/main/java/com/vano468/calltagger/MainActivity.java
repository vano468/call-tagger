package com.vano468.calltagger;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToggleButton();
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            startService(new Intent(this, CallService.class));
        } else {
            stopService(new Intent(this, CallService.class));
        }
    }

    public void initToggleButton() {
        ToggleButton toggleService = (ToggleButton) findViewById(R.id.toggleService);
        toggleService.setChecked(isCallServiceRunning());
    }

    private boolean isCallServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (CallService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}