package irmc.esprit.tn.irmcmobile;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddActivity extends AppCompatActivity
       {


           private String name;
           private String sigle;
           private String address;
           private String description;
           private String codePostale;
           private String website;
           private Float longitude;
           private Float latitude;
           private String typeAcces;
           private String type;






           private EditText editname;
           private EditText editsigle;
           private EditText editaddress;
           private EditText editdescription;
           private EditText editcodePostale;
           private EditText editlongitude;
           private EditText editlatitude;
           private EditText editwebsite;

           private RadioButton radioButton;
           private RadioGroup radioGroup;

           private RadioButton radioButton1;
           private RadioGroup radioGroup1;



           private Button add;

           String url ="http://1b44f4fb.ngrok.io/IRMCJEE-web/IRMC/institute/";


           @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

               editname= (EditText) findViewById(R.id.name);
               editsigle= (EditText) findViewById(R.id.sigle);
               editaddress= (EditText) findViewById(R.id.address);
               editcodePostale= (EditText) findViewById(R.id.codePostal);
               editdescription= (EditText) findViewById(R.id.description);
               editlatitude= (EditText) findViewById(R.id.latitude);
               editlongitude= (EditText) findViewById(R.id.longitude);
               editwebsite= (EditText) findViewById(R.id.website);
               radioGroup = (RadioGroup) findViewById(R.id.radio);
               radioGroup1 = (RadioGroup) findViewById(R.id.radio1);


               add = (Button) findViewById(R.id.add_inst) ;
               RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
               requestQueue.start();
               add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Intent intent = new Intent(AddActivity.this, MainActivity.class);
                       startActivity(intent);
                       final Institute inst = new Institute();

                       inst.setName(editname.getText().toString());
                       inst.setSigle(editsigle.getText().toString());
                      inst.setAddress(editaddress.getText().toString());
                       inst.setDescription(editdescription.getText().toString());
                       inst.setCodePostale(editcodePostale.getText().toString());
                      String lat=editlatitude.getText().toString();
                       inst.setLatitude(Double.parseDouble(lat));
                       String lon=editlongitude.getText().toString();
                       inst.setLongitude(Double.parseDouble(lon));
                       inst.setWebsite(editwebsite.getText().toString());
                       // get selected radio button from radioGroup
                       int selectedId = radioGroup.getCheckedRadioButtonId();

                       // find the radiobutton by returned id
                       radioButton = (RadioButton) findViewById(selectedId);
                       inst.setTypeAcces(radioButton.getText().toString());


                       // get selected radio button from radioGroup
                       int selectedId1 = radioGroup1.getCheckedRadioButtonId();

                       // find the radiobutton by returned id
                       radioButton1 = (RadioButton) findViewById(selectedId1);
                       inst.setType(radioButton1.getText().toString());




                       sendPost(inst);

                   }
               });


    }


           public void sendPost(final Institute inst) {
               Thread thread = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           String urljson = "http://1b44f4fb.ngrok.io/IRMCJEE-web/IRMC/institute/";
                           URL url = new URL(urljson);
                           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                           conn.setRequestMethod("POST");
                           conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                           conn.setRequestProperty("Accept","application/json");
                           conn.setDoOutput(true);
                           conn.setDoInput(true);
                           JSONObject jsonParam = new JSONObject();
                           jsonParam.put("name", inst.getName());
                           jsonParam.put("sigle",inst.getSigle());
                           jsonParam.put("address",inst.getAddress());
                           jsonParam.put("description",inst.getDescription());
                           jsonParam.put("code_postale",inst.getCodePostale());
                           jsonParam.put("longitude",inst.getLongitude());
                           jsonParam.put("latitude",inst.getLatitude());
                           jsonParam.put("website",inst.getWebsite());
                           jsonParam.put("type_acces",inst.getTypeAcces());
                           jsonParam.put("type",inst.getType());


                           Log.i("JSON", jsonParam.toString());
                           DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                           os.writeBytes(jsonParam.toString());

                           os.flush();
                           os.close();

                           Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                           Log.i("MSG" , conn.getResponseMessage());

                           conn.disconnect();
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                   }
               });

               thread.start();
           }


       }
