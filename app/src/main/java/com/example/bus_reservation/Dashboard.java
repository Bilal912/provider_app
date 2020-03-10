package com.example.bus_reservation;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Dashboard extends Fragment {
    private SliderLayout mDemoSlider;
    TextView booked_seat,booked,refund;
    ArrayList<String> banner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mDemoSlider = view.findViewById(R.id.slider);

        banner= new ArrayList<String>();
        booked=view.findViewById(R.id.bookoo);
        booked_seat=view.findViewById(R.id.booked_seat);
        refund=view.findViewById(R.id.refund_id);

        getData();

        return view;
    }

    private void getData() {
        final android.app.AlertDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading...");
        loading.show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Constant.Banner_url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Boolean status = null;

                try {
                        status = response.getBoolean("response");
                        if (status){
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String temp=object.getString("image");
                                banner.add(temp);
                            }
                            Slider_view(banner);
                            loading.dismiss();
                            }
                        else {
                            loading.dismiss();
                        }
                }
                catch (Exception e){
                    loading.dismiss();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                makeText(getContext(), "Connection Error", LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonRequest);
    }


    public void Slider_view(ArrayList<String> image){

        //from image link

        HashMap<String,String> url_maps = new HashMap<String, String>();

        for (int j = 0; j < image.size(); j++){
            url_maps.put(String.valueOf(j), Constant.Image_url.concat(image.get(j)));
        }
//        url_maps.put("", "https://image.freepik.com/free-vector/city-street-skyscraper-buildings-road-view-modern-cityscape-singapore-downtown_48369-12979.jpg");
//        url_maps.put("", "https://image.freepik.com/free-vector/city-street-skyscraper-buildings-road-view-modern-cityscape-singapore-downtown_48369-12983.jpg");
//        url_maps.put("", "https://image.freepik.com/free-vector/bus-inside_93732-2.jpg");
//        url_maps.put("", "https://image.freepik.com/free-vector/man-standing-inside-public-transport_107173-10516.jpg");

        //from drawable
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal",R.drawable.hannibal);

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
//                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4500);
    }
}
