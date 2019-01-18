package com.language.ra.myapplication;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import abdollahi.reza.ir.languagera.LanguageRA;
import co.ronash.pushe.Pushe;
import ir.adad.client.Adad;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    CoordinatorLayout CoordiLayout; // در استنکبار استفاده می شود

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pushe.initialize(this,false);

        //***** Adad initialize should get called before setContent if you have bannerAd in this activity.
        Adad.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        SetDisplayOrientation(); // صدا زدن تابع تنظیم کردن چرخش صفحه نمایش

        // تابع ست کردن تولبار
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        // غیر فعال کردن متن پیش فرض تولبار
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("Debug_Volley" , "Start Class CheckUpdate");
        CheckUpdate();
    }

    // تنظیم کردن فونت
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // تابع تنظیم کردن چرخش صفحه نمایش
        public void SetDisplayOrientation(){

            // تنظیم کردن textview اعلام نتیجه که scroll داشته باشد
            TextView ResulText = (TextView)findViewById(R.id.ResultText);
            ResulText.setMovementMethod(new ScrollingMovementMethod());

            // بدست آوردن dp های صفحه نمایش و تنظیم آن با توجه به اندازه صفحه نمایش
            DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
            int widthdp;
            int heightdp;
            int dp;
            int widthpx;
            int heightpx;

            dp = metrics.densityDpi;
            widthpx = metrics.widthPixels;
            heightpx = metrics.heightPixels;
            widthdp = widthpx * 160 / dp;
            heightdp = heightpx * 160 / dp;

            if(widthdp >= heightdp){
                if(heightdp >= 768)
                {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else
                {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
            else if(widthdp < heightdp){
                if(widthdp >= 768)
                {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else
                {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // تابع ساخت منو
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            String Text = "آیا می دانید مطالبی که می خواهید با ایمیل یا تلگرام یا اس ام اس برای کسی بفرستی ولی نگران لو رفتن یا انتشار آنها هستی را میشه رمزی فرستاد تا کسی نتونه بخونه؟\n" +
                    "آیا می دانید که  نوشتن اطلاعات مهم خود نظیر شماره رمز کارت بانکی ، پسورد ایمیل و فایل های مهم می تواند خطرساز و باعث لو رفتن و سواستفاده شود؟ پس رمزی بنویس که هیچ کس نتونه بخونه!\n" +
                    "دوس داری مطلبی رو تو یه گروه بفرستی ولی فقط برخی افراد مورد نظر شما متوجه آن بشوند؟\n" +
                    "دوس داری برای کسی پیام رمزی بفرستی؟\n" +
                    "\n" +
                    "با استفاده از این برنامه شما میتوانید تمامی این کارها رو به راحتی انجام بدین\n" +
                    "فقط کافیه برای رمز گذاری متن گزینه 'رمزنگاری' را زده و متن را به رمز تبدیل کنید و رمز دریافتی را در دفترچه یادداشت موبایل ذخیره  و یا از طریق ایمیل یا تلگرام یا... برای دیگران ارسال کنید.\n" +
                    "هر موقع به متن احتیاج داشتین توسط همین برنامه رمزگشایی کنید و یا اگر برای کسی ارسال کردین اون شخص هم با داشتن این برنامه میتونه متن شمارو رمز گشایی کنه و پیام رو ببینه\n" +
                    "\n" +
                    "تبدیل متن به رمز را در مایکت ببین:\n" +
                    "http://myket.ir/app/com.language.ra";

            Intent Share = new Intent(Intent.ACTION_SEND);
            Share.setType("text/plain");
            Share.putExtra(Intent.EXTRA_TEXT , Text);
            startActivity(Share);


        } else if (id == R.id.nav_send) {

            Intent OpenAbout_Me_Activity = new Intent(this , about_me.class);
            startActivity(OpenAbout_Me_Activity);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // تابع رمزنگاری
    public void ConvertENtoRA(View v){

        EditText EearlyText = (EditText)findViewById(R.id.EarlyText);
        TextView ResulText = (TextView)findViewById(R.id.ResultText);

        LanguageRA languageRA = new LanguageRA();

        ResulText.setText(languageRA.convertEntoRA(EearlyText.getText().toString()));
    }

    // تابع رمزگشایی
    public void ConvertRAtoEN(View v){

        EditText EearlyText = (EditText)findViewById(R.id.EarlyText);
        TextView ResulText = (TextView)findViewById(R.id.ResultText);

        LanguageRA languageRA = new LanguageRA();

        ResulText.setText(languageRA.convertRAtoEN(EearlyText.getText().toString()));
    }

    // تابع کپی متن
    public void CopyData(View v){
        TextView ResulText = (TextView)findViewById(R.id.ResultText);

        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text" , ResulText.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    // تابع اشتراک گذاری متن
    public void ShareResultText(View v){
        TextView ResulText = (TextView)findViewById(R.id.ResultText);

        Intent Share = new Intent(Intent.ACTION_SEND);
        Share.setType("text/plain");
        Share.putExtra(Intent.EXTRA_TEXT , ResulText.getText().toString());
        startActivity(Share);
    }

    // بررسی به روزرسانی برنامه
    public void CheckUpdate(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://quarkbackend.com/getfile/rezaab1375/checkupdateapp";

        StringRequest stringRequest = new StringRequest(Request.Method.GET , url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String LastVersionCode;
                        String LastVersionName;

                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONObject checkupdate = jo.getJSONObject("ConvertTexttoCipher");
                            LastVersionCode = checkupdate.getString("LastVersionCode");
                            LastVersionName = checkupdate.getString("LastVersionName");

                            if(BuildConfig.VERSION_CODE < Integer.parseInt(LastVersionCode)){
                                ShowSnackbar("نسخه " + LastVersionName + " منتشر شده است" , 4000 , 2);                         }
                            Log.d("Debug_Volley" , LastVersionCode);
                            Log.d("Debug_Volley" , LastVersionName);
                        }
                        catch (JSONException e){
                            Log.d("Debug_Volley" , e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Debug_Volley" , "Error");
                    }
                });

        Log.d("Debug_Volley" , "Start Volley");
        requestQueue.add(stringRequest);
    }

    // نمایش اسنک بار
    public void ShowSnackbar(String txt , int time , int color){

        // value 0 is: White
        // value 1 is; Yellow
        // value 2 is: Green

        CoordiLayout = findViewById(R.id.coordi);
        Snackbar snkbr = Snackbar.make(CoordiLayout, txt , Snackbar.LENGTH_SHORT);
        snkbr.setDuration(time); // مشخص کردن مدت زمان نمایش
        View snkView = snkbr.getView();
        TextView snkText = snkView.findViewById(android.support.design.R.id.snackbar_text);

        // مشخص کردن رنگ
        switch (color){
            case 0:
                snkText.setTextColor(Color.WHITE);
                break;
            case 1:
                snkText.setTextColor(Color.YELLOW);
                break;
            case 2:
                snkText.setTextColor(Color.GREEN);
                break;
        }

        snkbr.show();
    }

}
