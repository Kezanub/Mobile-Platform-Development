package labstuff.gcu.owencannon.org.exercise3;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button exitButton;
    private EditText nameEntry;
    private String name;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitButton = (Button)findViewById(R.id.exitButton);
        nameEntry = (EditText)findViewById(R.id.nameEntry);
        nameEntry.setWidth(120);
        exitButton.setOnClickListener(this);
        nameEntry.setFocusable(true);
    }//End of onCreate

    @Override
    public void onClick(View v)
    {
        //Check for exit button. Pop up dialogue if found
        if (v == exitButton)
        {
            name = nameEntry.getText().toString();
            showtbDialog(name);
        }
    }//End of onClick

    private void showtbDialog(String salutationName)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(salutationName + ",Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), name + " You Pressed Yes", Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}// End of Activity class
