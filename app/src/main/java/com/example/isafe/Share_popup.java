package com.example.isafe;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;

public class Share_popup extends Activity {

    TextView what, text, copy;

    ImageView cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.share_codepop);

        what = (TextView) findViewById(R.id.sharewhatsapp);
        text = (TextView) findViewById(R.id.sharetext);
        copy = (TextView) findViewById(R.id.copy);

        cross = (ImageView) findViewById(R.id.cross);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.3));
        getWindow().setBackgroundDrawableResource(R.drawable.share_code);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().closeAllPanels();
            }
        });

        what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, CodeGenerator.codeg);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(Share_popup.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();

                }
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", CodeGenerator.codeg);
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setClipboard(getApplicationContext(),CodeGenerator.codeg );

            }
        });
    }

    private void setClipboard(Context context, String text) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copied to Clipboard!", Toast.LENGTH_SHORT).show();

    }
}
