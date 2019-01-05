package pl.nsair.krzysztofko.nowaslaircondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView txtShowTextResult;
    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://195.181.209.174:8009/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtShowTextResult = findViewById(R.id.txtDisplay);
        btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                              sendAndRequestResponse();
                                          }
                                      }
        );

    }

    private void sendAndRequestResponse(){
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject Odpowiedz = new JSONObject(response);
                    JSONObject Dane = Odpowiedz.getJSONObject("data");
                    String Tytul = Dane.getString("nazwa")+"\n"+Dane.getString("temperatura");
                    txtShowTextResult.setText(Tytul);
                } catch (JSONException x){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }) {
        };
        mRequestQueue.add(mStringRequest);
    }
}