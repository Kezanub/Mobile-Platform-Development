//Owen Cannon S1628221

package labstuff.gcu.owencannon.org.assignment2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity implements View.OnClickListener {
    private String url1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url2 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String url3 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private TextView urlInput;
    private View mainView;
    private Button incidentButton;
    private Button roadButton;
    private Button plannedButton;
    private Button buttonclear;
    private Button buttonexit;
    private Button mapbutton;
    private EditText inputSearch;
    private ListView lv;
    private String result = "";
    private ArrayList<HashMap<String, String>> RSSList = new ArrayList<>();
    private HashMap<String,String> rss = new HashMap<>();
    private SimpleAdapter adapter;
    private Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = (View) findViewById(R.id.mainView);
        urlInput = (TextView) findViewById(R.id.urlInput);
        incidentButton = (Button) findViewById(R.id.incidentButton);
        incidentButton.setOnClickListener(this);
        roadButton = (Button) findViewById(R.id.roadButton);
        roadButton.setOnClickListener(this);
        plannedButton = (Button) findViewById(R.id.plannedButton);
        plannedButton.setOnClickListener(this);
        buttonclear = (Button) findViewById(R.id.button);
        buttonclear.setOnClickListener(this);
        buttonexit = (Button) findViewById(R.id.button2);
        buttonexit.setOnClickListener(this);
        mapbutton = (Button) findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(this);
        buttonclear.setEnabled(false);
    }

    public void onClick(View aview) {
        if (aview == incidentButton) {
            result = "";
            RSSList.clear();
            urlInput.setText("Viewing Current Incidents");
            startIncidentProgress();
            buttonclear.setEnabled(true);
        }
        if (aview == roadButton) {
            result = "";
            RSSList.clear();
            urlInput.setText("Viewing Current Roadworks");
            startRoadworksProgress();
            buttonclear.setEnabled(true);
        }
        if (aview == plannedButton) {
            result = "";
            RSSList.clear();
            urlInput.setText("Viewing Planned Roadworks");
            startPlannedProgress();
            buttonclear.setEnabled(true);
        }
        if (aview == buttonclear) {
            buttonclear.setEnabled(false);
            result = "";
            lv.setAdapter(null);
            inputSearch.setText("");
            urlInput.setText("Welcome to Traffic Scotland RSS feeds");
        }
        if (aview == buttonexit) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        if (aview == mapbutton) {
            Intent i = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void startIncidentProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(url1)).start();
    }

    public void startRoadworksProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(url2)).start();
    }

    public void startPlannedProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(url3)).start();
    } //


    class Task implements Runnable {
        private String url;


        public Task(String aurl) {
            url = aurl;
        }
        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);
                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    handler.postDelayed(this, 600000);
                    Log.d("UI thread", "I am the UI thread");
                    try{
                        lv = (ListView) findViewById(R.id.listView1);
                        inputSearch = (EditText) findViewById(R.id.editText);
                        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                        SAXParser parser = parserFactory.newSAXParser();
                        DefaultHandler handler = new DefaultHandler(){
                            String currentValue = "";
                            boolean currentElement = false;
                            public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
                                currentElement = true;
                                currentValue = "";
                                if(localName.equals("item")){
                                    rss = new HashMap<>();
                                }
                            }
                            public void endElement(String uri, String localName, String qName) throws SAXException {
                                currentElement = false;
                                if (localName.equalsIgnoreCase("title"))
                                    rss.put("title", currentValue);
                                else if (localName.equalsIgnoreCase("description"))
                                    rss.put("description", currentValue);
                                else if (localName.equalsIgnoreCase("link"))
                                    rss.put("link", currentValue);
                                else if (localName.equalsIgnoreCase("pubDate"))
                                    rss.put("pubDate", currentValue);
                                else if (localName.equalsIgnoreCase("item"))
                                    RSSList.add(rss);
                            }
                            @Override
                            public void characters(char[] ch, int start, int length) throws SAXException {
                                if (currentElement) {
                                    currentValue = currentValue +  new String(ch, start, length);
                                }
                            }
                        };
                        parser.parse(new InputSource(new StringReader(result)), handler);
                        adapter = new SimpleAdapter(MainActivity.this, RSSList, R.layout.list_row,new String[]{"title","description","link","pubDate"},
                                new int[]{R.id.title, R.id.description, R.id.link, R.id.pubdate});
                        lv.setAdapter(adapter);
                        inputSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                // When user changed the Text
                                MainActivity.this.adapter.getFilter().filter(cs);
                            }

                            @Override
                            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                          int arg3) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void afterTextChanged(Editable arg0) {
                                // TODO Auto-generated method stub
                            }
                        });
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

