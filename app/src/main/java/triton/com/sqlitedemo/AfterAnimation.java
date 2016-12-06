package triton.com.sqlitedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_animation);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(nextActivity);
                //push from bottom to top
                //overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
                overridePendingTransition(R.anim.zoom_exit, R.anim.zoom_enter);

            }
        });
    }
}
