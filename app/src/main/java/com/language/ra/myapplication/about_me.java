package com.language.ra.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class about_me extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        SetDisplayOrientation(); // صدا زدن تابع تنظیم کردن چرخش صفحه نمایش

        // تعریف تولبار
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);
        // فعال کردن گزینه برگشت به صفحه قبلی
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // غیر فعال کردن متن پیش فرض تولبار
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // تنظیم کردن فونت
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // تابع ارسال ایمیل
    public void SendingEmail(View v) {
        Intent SendEmail = new Intent(Intent.ACTION_SEND);
        SendEmail.setType("plain/text");
        SendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"rezaab1375s@gmail.com"});
        SendEmail.putExtra(Intent.EXTRA_SUBJECT, "برنامه تبدیل متن به رمز");
        startActivity(SendEmail);
    }

    // تابع باز کردن صفحه برنامه در مایکت
    public void OpenPageinMyket(View v) {
        Intent OpenMyket = new Intent(Intent.ACTION_VIEW);

        if(getPackageManager().getLaunchIntentForPackage("ir.mservices.market") == null){
            OpenMyket.setData(Uri.parse("http://myket.ir/app/com.language.ra"));
            startActivity(OpenMyket);
        }
        else {
            OpenMyket.setPackage("ir.mservices.market");
            OpenMyket.setData(Uri.parse("http://myket.ir/app/com.language.ra"));
            startActivity(OpenMyket);
        }

    }


    // تابع باز کردن وبسایت
    public void OpenWebsite(View v){
        Intent openWebsit = new Intent(Intent.ACTION_VIEW);

        openWebsit.setData(Uri.parse("http://seyedabdollahi.ir"));
        startActivity(openWebsit);
    }

    // تابع باز کردن صفحه برنامه در بازار
    public void OpenPageinBazaar(View v){
        Intent OpenBazaar = new Intent(Intent.ACTION_VIEW);
        OpenBazaar.setData(Uri.parse("bazaar://details?id=" + "com.language.ra2"));
        OpenBazaar.setPackage("com.farsitel.bazaar");
        startActivity(OpenBazaar);
    }

    // تابع ارتباط با برنامه نویس در تلگرام
    public void OpenTelegramDeveloper(View v){
        Intent OpenTelegram = new Intent(Intent.ACTION_VIEW);

        OpenTelegram.setData(Uri.parse("https://telegram.me/Bayad_raaft"));
        startActivity(OpenTelegram);
    }

    //تابع نظر مستقیم در بازار
    public void AddCommentBazaar(View v){

        Intent AddComment = new Intent(Intent.ACTION_EDIT);
        AddComment.setData(Uri.parse("bazaar://details?id=" + "com.language.ra2"));
        AddComment.setPackage("com.farsitel.bazaar");
        startActivity(AddComment);
    }

    // تابع نظر مستقیم در مایکت
    public void AddCommentMyket(View v){

        if(getPackageManager().getLaunchIntentForPackage("ir.mservices.market") == null){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("خطا")
                    .setMessage("برنامه مایکت در دستگاه شما نصب نمی باشد. مایل به دانلود مایکت هستید؟")
                    .setPositiveButton("بله" , new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent installmyket = new Intent(Intent.ACTION_VIEW);

                            if(getPackageManager().getLaunchIntentForPackage("com.android.chrome") == null){
                                installmyket.setData(Uri.parse("https://www.myket.ir/download"));
                                startActivity(installmyket);
                            }
                            else {
                                installmyket.setData(Uri.parse("https://www.myket.ir/download"));
                                installmyket.setPackage("com.android.chrome");
                                startActivity(installmyket);
                            }

                        }
                    })
                    .setNegativeButton("بی خیال", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            Intent AddComment = new Intent(Intent.ACTION_VIEW);
            AddComment.setData(Uri.parse("myket://comment?id=" + "com.language.ra"));
            AddComment.setPackage("ir.mservices.market");
            startActivity(AddComment);
        }
    }

    // تابع تنظیم کردن چرخش صفحه نمایش
    public void SetDisplayOrientation() {

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

        if (widthdp >= heightdp) {
            if (heightdp >= 768) {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else if (widthdp < heightdp) {
            if (widthdp >= 768) {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    // تابع برگشت به صفحه قبل
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

