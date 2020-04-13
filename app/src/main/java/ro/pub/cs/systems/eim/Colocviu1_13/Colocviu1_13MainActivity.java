package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_13MainActivity extends AppCompatActivity {

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private TextView textView;
    private int numPushes = 0;
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            textView.setText(textView.getText().toString() + ((Button)view).getText().toString());
            numPushes ++;
        }
    }
    private ButtonSecondClickListener buttonSecondClickListener = new ButtonSecondClickListener();
    private class ButtonSecondClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
            intent.putExtra(Constants.RegisterIntent, textView.getText().toString());
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
            textView.setText("");
            numPushes = 0;

            if (numPushes > 4) {
                Intent serviceintent = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                serviceintent.putExtra(Constants.TEXTSERVICE, textView.getText().toString());
                getApplicationContext().startService(serviceintent);
            }
        }
    }

    private Button northButton, eastButton, southButton, westButton, secondaryActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_main);
        northButton = findViewById(R.id.northButton);
        northButton.setOnClickListener(buttonClickListener);
        eastButton = findViewById(R.id.eastButton);
        eastButton.setOnClickListener(buttonClickListener);
        westButton = findViewById(R.id.westButton);
        westButton.setOnClickListener(buttonClickListener);
        southButton = findViewById(R.id.southButton);
        southButton.setOnClickListener(buttonClickListener);
        textView = findViewById(R.id.directii);
        secondaryActivity = findViewById(R.id.secondaryActivity);
        secondaryActivity.setOnClickListener(buttonSecondClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.NumPushes))
                numPushes = savedInstanceState.getInt(Constants.NumPushes);
            else
                numPushes = 0;
        }
        intentFilter.addAction(Constants.actionTypes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.NumPushes, numPushes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.NumPushes)) {
            numPushes = savedInstanceState.getInt(Constants.NumPushes);
        } else {
            numPushes = 0;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_13Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
