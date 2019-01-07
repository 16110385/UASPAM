package dev.selodev.mahasiswa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.selodev.mahasiswa.Util.AppController;
import dev.selodev.mahasiswa.Util.ServerAPI;

public class InsertActivity extends AppCompatActivity {

    EditText npm,nama,prodi,fakultas;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        /*get data from intent*/
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_npm = data.getStringExtra("npm");
        String intent_nama = data.getStringExtra("nama");
        String intent_prodi = data.getStringExtra("prodi");
        String intent_fakultas = data.getStringExtra("fakultas");
        /*end get data from intent*/

        npm = (EditText) findViewById(R.id.inp_npm);
        nama = (EditText) findViewById(R.id.inp_nama);
        prodi = (EditText) findViewById(R.id.inp_prodi);
        fakultas= (EditText) findViewById(R.id.inp_fakultas);
        btnbatal = (Button) findViewById(R.id.btn_cancel);
        btnsimpan = (Button) findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(InsertActivity.this);

        /*kondisi update / insert*/
        if(update == 1)
        {
            btnsimpan.setText("Update Data");
            npm.setText(intent_npm);
            npm.setVisibility(View.GONE);
            nama.setText(intent_nama);
            prodi.setText(intent_prodi);
            fakultas.setText(intent_fakultas);

        }


        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update == 1)
                {
                    Update_data();
                }else {
                    simpanData();
                }
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(InsertActivity.this,MainActivity.class);
                startActivity(main);
            }
        });
    }

    private void addNotification() {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.users)
                        .setContentTitle("Pemberitahuan")
                        .setContentText("Data Mahasiswa Berhasil Ditambahkan")
                        .setSound(soundUri)
                        .setAutoCancel(true);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void Update_data()
    {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(InsertActivity.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("npm",npm.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("prodi",prodi.getText().toString());
                map.put("fakultas",fakultas.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }



    private void simpanData()
    {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        addNotification();
//                        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                        Notification notify=new Notification.Builder
//                                (getApplicationContext()).setContentTitle("Pemberitahuan").setContentText("Data Mahasiswa Berhasil Ditambahkan").
//                                setContentTitle("Pemberitahuan").setSmallIcon(R.drawable.users).build();
//                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                        notif.notify(0, notify);

                        startActivity( new Intent(InsertActivity.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("npm",npm.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("prodi",prodi.getText().toString());
                map.put("fakultas",fakultas.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }
}
