package labstuff.gcu.owencannon.org.exercise9;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {
    CheckBox stuffed,thin,mush,sweet,onion,pepper;
    Button buttonOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonClick();
    }
    public void addListenerOnButtonClick(){
        //Getting instance of CheckBoxes and Button from the activty_main.xml file
        stuffed=(CheckBox)findViewById(R.id.checkBox1);
        thin=(CheckBox)findViewById(R.id.checkBox2);
        mush=(CheckBox)findViewById(R.id.checkBox3);
        sweet=(CheckBox)findViewById(R.id.checkBox4);
        onion=(CheckBox)findViewById(R.id.checkBox5);
        pepper=(CheckBox)findViewById(R.id.checkBox6);
        buttonOrder=(Button)findViewById(R.id.button1);

        //Applying the Listener on the Button click
        buttonOrder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int totalamount=0;
                StringBuilder result=new StringBuilder();
                result.append("Selected Items:");
                if(stuffed.isChecked()){
                    result.append("\nStuffed Crust £1");
                    totalamount+=1;
                }
                if(thin.isChecked()){
                    result.append("\nThin and Crispy £1");
                    totalamount+=1;
                }
                if(mush.isChecked()){
                    result.append("\nMushroom £1");
                    totalamount+=1;
                }
                if(sweet.isChecked()){
                    result.append("\nSweetcorn £1");
                    totalamount+=1;
                }
                if(onion.isChecked()){
                    result.append("\nOnion £1");
                    totalamount+=1;
                }
                if(pepper.isChecked()){
                    result.append("\nPepper £1");
                    totalamount+=1;
                }
                result.append("\nTotal: "+"£"+totalamount);
                //Displaying the message on the toast
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
