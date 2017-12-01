package thanhcs.ghn.phunuvietnam;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.io.Reader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {

    Grating grate[];
    List<Grating> gratingList = new ArrayList<>();
    GHNAdapter mAdapter;
    int[] numberRating = new int[5];
    List<ArrayList<Grating>> arrayListsRateOne = new ArrayList<>();

    private PieChartView chart;
    private PieChartData data;

    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private TextView textDate, textTotal, textReport, textDraw, textWinter;
    String date = "";
    Calendar myCalendar = Calendar.getInstance();
    private  RecyclerView recyclerView;
    boolean isLoaded = false;
    String url = "";
    DatePickerDialog.OnDateSetListener datetime = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    private void updateLabel() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        date = format1.format(myCalendar.getTime());
        numberRating[0] = 0;
        numberRating[1] = 0;
        numberRating[2] = 0;
        numberRating[3] = 0;
        numberRating[4] = 0;

        for(int i = 0 ; i< numberRating.length ;i++){
            arrayListsRateOne.get(i).clear();
        }
        gratingList.clear();
        if(!isLoaded) {
            Log.wtf("load filter", "" + date + " size = " + gratingList.size());
            new JSONParse(url+"?date="+date).execute();
            isLoaded = true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDate = (TextView)findViewById(R.id.text_date);
        textTotal = (TextView)findViewById(R.id.text_total);
        textReport = (TextView)findViewById(R.id.text_report);
        textDraw = (TextView)findViewById(R.id.text_draw);
        textWinter = (TextView)findViewById(R.id.text_winter);
        textDraw.setVisibility(View.GONE);
        textWinter.setVisibility(View.GONE);

        for(int i = 0 ; i< numberRating.length ;i++){
            arrayListsRateOne.add(i, new ArrayList<Grating>());
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(cal.getTime());
        // Output "Wed Sep 26 14:23:28 EST 2012"
        date = format1.format(cal.getTime());
        // Output "2012-09-26"

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mAdapter = new GHNAdapter(gratingList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        chart = (PieChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        numberRating[0] = 0;
        numberRating[1] = 0;
        numberRating[2] = 0;
        numberRating[3] = 0;
        numberRating[4] = 0;

        new JSONParse(url+"?date="+date).execute();

        convertDatime();

    }

    private void convertDatime() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        System.out.println(sdf.format(calendar.getTime()));
//        Log.wtf("Date time convert : ", sdf.format(calendar.getTime()));
//
//        Log.wtf("Date time convert : ", sdf.format("2017-11-18 10:30:00"));
//
//        sdf.setTimeZone(TimeZone.getDefault());
//        System.out.println(sdf.format(calendar.getTime()));
//        Log.wtf("Date time convert : ", sdf.format(calendar.getTime()));


        System.out.println(new Date().getTime());
        System.out.println(convertDateTimeZone(new Date().getTime(), TimeZone
                .getDefault().getID(), "UTC+7"));

        Log.wtf("Date time convert : ", new Date().getTime()+"");
        Log.wtf("Date time convert : ", convertDateTimeZone(new Date().getTime(), TimeZone
                .getDefault().getID(), "UTC+7")+"");

    }


    public static long convertDateTimeZone(long lngDate, String fromTimeZone,
                                           String toTimeZone){
        TimeZone toTZ = TimeZone.getTimeZone(toTimeZone);
        Calendar toCal = Calendar.getInstance(toTZ);

        TimeZone fromTZ = TimeZone.getTimeZone(fromTimeZone);
        Calendar fromCal = Calendar.getInstance(fromTZ);
        fromCal.setTimeInMillis(lngDate);
        toCal.setTimeInMillis(fromCal.getTimeInMillis()
                + toTZ.getOffset(fromCal.getTimeInMillis())
                - TimeZone.getDefault().getOffset(fromCal.getTimeInMillis()));
        return toCal.getTimeInMillis();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_date:
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, datetime, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                return true;

            case R.id.menu_sort:
                 date = "ALL TIME";
                 new JSONParse(url).execute();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void generateData(int[] numberRating) {
        int numValues = 5;
        int colorRating[] = {
                Color.parseColor("#e82224"),
                Color.parseColor("#f87d0b"),
                Color.parseColor("#f3c717"),
                Color.parseColor("#69a126"),
                Color.parseColor("#427e2a")};
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue(numberRating[i], colorRating[i]);
            values.add(sliceValue);
            sliceValue.setLabel("" + (i + 1));

        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);
        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        hasCenterText1 = true;
        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

//            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        chart.setPieChartData(data);
    }


    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            ArrayList<Grating> arr =  arrayListsRateOne.get(arcIndex);
           // Toast.makeText(MainActivity.this, "Selected: " + arcIndex + " size " + arr.size() , Toast.LENGTH_SHORT).show();
            mAdapter.replaceNew(arr);
        }
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        String url = "";
        public JSONParse(String s) {
            this.url = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            textDate.setText(date);

            for(int i = 0 ; i< numberRating.length ;i++){
                arrayListsRateOne.get(i).clear();
            }
            gratingList.clear();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {

                //https://shiip.ghn.vn:8084/v2/rating/export?date=2017-11-08
                URL URL = new URL(this.url);
                Log.wtf("HISTORY ", URL + "--");
                Reader reader = Parse.getData(URL);
                if (reader != null) {
                    Gson gson = new Gson();
                    grate = gson.fromJson(reader, Grating[].class);
                    Log.wtf("DATA", "Success - " + grate[0].toString());
                    for (int i = 0; i < grate.length; i++) {
                        gratingList.add(grate[i]);
                    }
                } else {
                    grate = null;
                    Log.wtf("DATA", "Fail");
                }
            } catch (Exception e) {
                Log.wtf("DATA", e.toString() + "");
                System.err.println("Error data");
            }
            return null;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            mAdapter.replaceNew(gratingList);
            for (int i = 0; i < gratingList.size(); i++) {
                if (gratingList.get(i).getPoint() == 1) {
                    numberRating[0]++;
                    arrayListsRateOne.get(0).add(gratingList.get(i));
                } else if (gratingList.get(i).getPoint() == 2) {
                    numberRating[1]++;
                    arrayListsRateOne.get(1).add(gratingList.get(i));
                } else if (gratingList.get(i).getPoint() == 3) {
                    numberRating[2]++;
                    arrayListsRateOne.get(2).add(gratingList.get(i));
                } else if (gratingList.get(i).getPoint() == 4) {
                    numberRating[3]++;
                    arrayListsRateOne.get(3).add(gratingList.get(i));
                } else {
                    numberRating[4]++;
                    arrayListsRateOne.get(4).add(gratingList.get(i));
                }
            }
            textTotal.setText("Total rate : "+ gratingList.size());
            textReport.setText(
                    "1 sao : "+ numberRating[0]+"\n"+
                    "2 sao : "+ numberRating[1]+"\n"+
                    "3 sao : "+ numberRating[2]+"\n"+
                    "4 sao : "+ numberRating[3]+"\n"+
                    "5 sao : "+ numberRating[4]+"\n"

            );
            generateData(numberRating);
            isLoaded = false;
            if(gratingList.size()>0){
                textDraw.setVisibility(View.GONE);
                textWinter.setVisibility(View.GONE);
            }else{
                textDraw.setVisibility(View.VISIBLE);
                textWinter.setVisibility(View.VISIBLE);
            }
        }
    }


}