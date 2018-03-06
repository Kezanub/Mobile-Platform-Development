package labstuff.gcu.owencannon.org.exercise9;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText length = (EditText) findViewById(R.id.txtLength);
        EditText width = (EditText) findViewById(R.id.txtWidth);
        TextView result = (TextView) findViewById(R.id.lblResult);
        Button calculate = (Button) findViewById(R.id.btnCalculate);

        calculate.setOnClickListener(this);
    }

    public void onClick(View v){

        EditText length = (EditText) findViewById(R.id.txtLength);
        EditText width = (EditText) findViewById(R.id.txtWidth);
        calculateArea(length.getText().toString(), width.getText().toString());
    }

    private void calculateArea(String clength, String cwidth){

        TextView result = (TextView) findViewById(R.id.lblResult);

        int area = Integer.parseInt(clength)*Integer.parseInt(cwidth);

        result.setText(String.valueOf(area));
    }}
