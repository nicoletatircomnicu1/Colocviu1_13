package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Colocviu1_13MainActivity extends AppCompatActivity {

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

    private Button northButton, eastButton, southButton, westButton;
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

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.NumPushes))
                numPushes = savedInstanceState.getInt(Constants.NumPushes);
            else
                numPushes = 0;
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
}
