package com.example.themodist1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();

    List<CustomObject> items = new ArrayList<CustomObject>();

    public class CustomObject {

        String topText;

        public CustomObject(String topText) {
            this.topText = topText;
        }

    }

    CustomPagerAdapter customPagerAdapter;

    //

    ImageView imageBannerTop;

    ArrayList<String> sampleTitles = new ArrayList<String>();
    ArrayList<String> sampleNetworkImageURLs = new ArrayList<String>();
    ArrayList<String> sampleDescription = new ArrayList<String>();

    CarousalPagerAdapter carousalPagerAdapter;
    ViewPager viewPagerBanner;

    private RecyclerView mHorizontalRecyclerView;
    private HorizontalRecyclerViewAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayoutManager;

    ArrayList<ImageModel> arrayListGrid1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_view);
        getSupportActionBar().setElevation(0);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        customPagerAdapter = new CustomPagerAdapter(this, items);
        viewPager.setAdapter(customPagerAdapter);

        //

        imageBannerTop = (ImageView)findViewById(R.id.imageBannerTop);


         viewPagerBanner = (ViewPager) findViewById(R.id.viewPagerBanner);


        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);


        new HttpGetRequest().execute("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomPagerAdapter
            extends PagerAdapter {

        List<CustomObject> items;
        LayoutInflater inflater;

        public CustomPagerAdapter(Context context, List<CustomObject> items) {
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.items = items;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View itemView;
            itemView = inflater.inflate(R.layout.hotelmap, container, false);

            TextView topTextItem = (TextView) itemView.findViewById(R.id.topText);

            CustomObject customObject = items.get(position);

            topTextItem.setText(customObject.topText);

            ((ViewPager) container).addView(itemView);

            return itemView;
        }
    }


    private class HttpGetRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params){

            String result = "";
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(Urls.homeUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

                Log.e("Response","Result: "+result);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);

            if(result != null)
            {
                try {

                    JSONObject jsonObjectResult = new JSONObject(result);
                    JSONObject jsonObjectDefault = jsonObjectResult.getJSONObject("default");
                    JSONArray jsonArray = new JSONArray(jsonObjectDefault.getString("promo_strip"));
                    Log.e(TAG,"Size : " + jsonArray.length());

                    for(int i = 0 ; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObjectPromoObject = (JSONObject)jsonArray.get(i);
                        JSONObject jsonObjectMessage = jsonObjectPromoObject.getJSONObject("message");

                        items.add(new CustomObject(jsonObjectMessage.getString("link_title")));
                    }

                    customPagerAdapter.notifyDataSetChanged();


                    // banner top

                    JSONArray jsonArrayBannerTop = new JSONArray(jsonObjectDefault.getString("banner_top"));

                    JSONObject jsonObjectBannerTop = (JSONObject)jsonArrayBannerTop.get(0);

                    Glide.with(MainActivity.this).load(jsonObjectBannerTop.getString("img_src"))
                            .into(imageBannerTop);

                    // carousel_content

                    JSONArray jsonArrayCarouselContent = new JSONArray(jsonObjectDefault.getString("carousel_content"));
                    Log.e(TAG,"CarouselContent Size : " + jsonArrayCarouselContent.length());
                    for(int i = 0 ; i < jsonArrayCarouselContent.length(); i++)
                    {
                        JSONObject jsonObjectCarouselContent = (JSONObject)jsonArrayCarouselContent.get(i);

                        sampleTitles.add(jsonObjectCarouselContent.getString("link_title"));

                        if(jsonObjectCarouselContent.getString("img_src").length() > 0)
                        {
                            sampleNetworkImageURLs.add(jsonObjectCarouselContent.getString("img_src"));
                        }
                        else
                        {
                            JSONObject jsonObjectVideo = new JSONObject(jsonObjectCarouselContent.getString("video"));
                            sampleNetworkImageURLs.add(jsonObjectVideo.getString("video_poster"));

                        }

                        JSONObject jsonObjectDescription = new JSONObject(jsonObjectCarouselContent.getString("slideDescr"));

                        sampleDescription.add( jsonObjectDescription.getString("text"));
                    }

                    carousalPagerAdapter = new CarousalPagerAdapter(MainActivity.this,sampleTitles,sampleNetworkImageURLs,sampleDescription);
                    viewPagerBanner.setAdapter(carousalPagerAdapter);

                    carousalPagerAdapter.notifyDataSetChanged();

                    // grid_1

                    JSONObject jsonObjectCarouselProduct = new JSONObject(jsonObjectDefault.getString("carousel_product"));
                    JSONArray jsonArrayGrid1 = new JSONArray(jsonObjectCarouselProduct.getString("products"));


                    Log.e(TAG,"jsonArrayGrid1 Size : " + jsonArrayGrid1.length());

                    for(int i = 0 ; i < jsonArrayGrid1.length();i++)
                    {
                        JSONObject jsonObjectGrid1 = (JSONObject)jsonArrayGrid1.get(i);

                        JSONObject jsonObjectImage = new JSONObject(jsonObjectGrid1.getString("c_image"));

                        ImageModel imageModel0 = new ImageModel();
                        imageModel0.setId(System.currentTimeMillis());
                        imageModel0.setImageName(jsonObjectGrid1.getString("c_brand"));
                        imageModel0.setImagePath(jsonObjectImage.getString("link"));
                        arrayListGrid1.add(imageModel0);
                    }

                    horizontalAdapter = new HorizontalRecyclerViewAdapter(arrayListGrid1, getApplication());
                    horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
                    mHorizontalRecyclerView.setAdapter(horizontalAdapter);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
}
