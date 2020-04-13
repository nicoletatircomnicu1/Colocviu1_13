package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {

    private TextView numberOfClicksTextView;
    private Button okButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.registerButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_secondary);

        numberOfClicksTextView = (TextView)findViewById(R.id.textView);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.RegisterIntent)) {
            String numberOfClicks = intent.getStringExtra(Constants.RegisterIntent);
            numberOfClicksTextView.setText(numberOfClicks.toString());
        }

        okButton = (Button)findViewById(R.id.registerButton);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
