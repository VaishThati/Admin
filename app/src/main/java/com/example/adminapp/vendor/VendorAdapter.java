package com.example.adminapp.vendor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminapp.R;
import com.loopj.android.http.LogHandler;


import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder> {

    private int vendorId;
    private Context context;
    private VendorGetSet[] data;
    public VendorAdapter(Context context, VendorGetSet[] data){
        this.context  = context;
        this.data = data;
    }

    @NonNull
    @Override
    public VendorAdapter.VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_vendor_list, parent, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorAdapter.VendorViewHolder holder, int position) {
        VendorGetSet user = data[position];
        holder.vendorName.setText(user.getEmail());
        Log.e("vendorName", holder.vendorName.getText().toString());
        vendorId = user.getVendorID();
        holder.vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });

        holder.vEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateVendor.class);
                intent.putExtra("vendorId", String.valueOf(user.getVendorID()));
                intent.putExtra("vendorName", user.getName().toString());
                Log.e("vendrname", user.getName().toString());
                intent.putExtra("vendorEmail", user.getEmail());
                intent.putExtra("vendorStore", user.getStore().toString());
                Log.e("vendorstore", user.getStore().toString());
                intent.putExtra("vendorSite", user.getWebsite());
                intent.putExtra("vendorgst", user.getGst().toString());
                Log.e("vendorgst", user.getGst().toString());
                intent.putExtra("vendorsc", user.getSupplierCode());
                Log.e("vendorId", String.valueOf(user.getVendorID()));
                Log.e("onclickedit", "onclickedit");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    //View Holder
    public class VendorViewHolder extends RecyclerView.ViewHolder{

        TextView vendorName, vEdit, vDelete;
        public VendorViewHolder(View itemView){
            super(itemView);
            vendorName = itemView.findViewById(R.id.vendor_name);
            vEdit = itemView.findViewById(R.id.editVendor);
            vDelete = itemView.findViewById(R.id.deleteVendor);
        }
    }

    private void Submit() {
        String URL = "http://103.150.187.59:54691/Vendor/Delete?id="+vendorId;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("delete", response);
                Intent i = new Intent(context, AddVendor.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("id", String.valueOf(vendorId));
                Log.e("headers", String.valueOf(headers));
                return headers;
            }
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        //requestQueue.add(stringRequest);
        int socketTimeout = 1000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}
