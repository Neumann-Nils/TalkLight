package talklight.talklight;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import talklight.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nils = (Button) findViewById(R.id.nils);
        nils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("id", 0);
                startActivity(i);
                finish();
            }
        });

        Button hanna = (Button) findViewById(R.id.hanna);
        hanna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("id", 1);
                startActivity(i);
                finish();
            }
        });
    }
}
