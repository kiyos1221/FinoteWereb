package com.a360ground.finotewereb;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a360ground.finotewereb.Date.EthiopianCalendar;
import com.androidessence.pinchzoomtextview.PinchZoomTextView;
import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import garin.artemiy.quickaction.library.QuickAction;

/**
 * Created by Kiyos Solomon on 11/26/2016.
 */

public class PlayerActivity extends AppCompatActivity{
    JcPlayerView jcPlayerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        Typeface  typeface = Typeface.createFromAsset(getAssets(), "font/ebrima.ttf");
        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayer);
        TextView title = (TextView) findViewById(R.id.title);
        final GetWordTextView geez = (GetWordTextView) findViewById(R.id.geez_content);
        PinchZoomTextView tirgum = (PinchZoomTextView) findViewById(R.id.amharic_content);
        geez.setOnWordClickListener(new GetWordTextView.OnWordClickListener() {
            @Override
            public void onClick(String word) {
                if(word.equals("አፍላገ")){
                    Toast.makeText(PlayerActivity.this, "ወንዞች", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ሶበ")){
                    Toast.makeText(PlayerActivity.this, "ጊዜ", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ውስተ")){
                    Toast.makeText(PlayerActivity.this, "ውስጥ", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ተዘከርናሃ")){
                    Toast.makeText(PlayerActivity.this, "ስናስባት", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ወበከይነ")){
                    Toast.makeText(PlayerActivity.this, "አለቀስን", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ህየ")){
                    Toast.makeText(PlayerActivity.this, "በዚያ", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ነበርነ(2x)")){
                    Toast.makeText(PlayerActivity.this, "ኖርን", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ምእመናኒሃ")){
                    Toast.makeText(PlayerActivity.this, "ምእመኖቿ（አማኞቿ）", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ባቢሎን")){
                    Toast.makeText(PlayerActivity.this, "ባቢሎን", Toast.LENGTH_SHORT).show();
                }
                if(word.equals("ጽዮን")){
                    Toast.makeText(PlayerActivity.this, "አምባ ፥ መሸሸጊያ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button share = (Button) findViewById(R.id.share);
        geez.setTypeface(typeface);
        tirgum.setTypeface(typeface);
        title.setTypeface(typeface);
        final Intent intent = getIntent();
        geez.setText(intent.getStringExtra("TITLE"));
        tirgum.setText(intent.getStringExtra("TIRGUM"));
        Log.d("Path","file://"+Environment.getExternalStorageState()+ File.separator+"some.mp3");
        ArrayList<String> werebContents = new ArrayList<>();
        werebContents.add("file://"+Environment.getExternalStorageDirectory()+ File.separator+"weste_aflage.mp3");
        werebContents.add("file://"+Environment.getExternalStorageDirectory()+ File.separator+"some.mp3");
        werebContents.add("file://"+Environment.getExternalStorageDirectory()+ File.separator+"some1.mp3");
        jcPlayerView.initWithTitlePlaylist(werebContents,"ወረብ");

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("TITLE") +"\n\n"+ intent.getStringExtra("TIRGUM") +"\n\n"+ "ፍኖት ወረብ © 2008");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });
    }
    public void playAudio(JcAudio jcAudio){
        jcPlayerView.playAudio(jcAudio);
    }
    @Override
    protected void onPause() {
        super.onPause();
        jcPlayerView.createNotification();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jcPlayerView.kill();
    }
}
