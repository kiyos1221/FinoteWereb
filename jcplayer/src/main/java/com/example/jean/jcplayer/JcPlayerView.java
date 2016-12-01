package com.example.jean.jcplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jean.jcplayer.JCPlayerExceptions.AudioUrlInvalidException;
import com.example.jean.jcplayer.JCPlayerExceptions.AudioListNullPointerException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class JcPlayerView extends LinearLayout implements
        JcPlayerService.JCPlayerServiceListener,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private TextView txtCurrentMusic;
    private Typeface typeface;
    private ImageView playing;
    private ImageButton btnPrev;
    private ImageButton btnPlay;
    private List<JcAudio> playlist;
    private ProgressBar progressBarPlayer;
    private JcAudioPlayer jcAudioPlayer;
    private TextView txtDuration;
    private ImageButton btnNext;
    private SeekBar seekBar;
    private TextView txtCurrentDuration;


    public JcPlayerView(Context context){
        super(context);
        init();
    }

    public JcPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public JcPlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){
        inflate(getContext(), R.layout.view_jcplayer, this);
        playing = (ImageView) findViewById(R.id.gifImageView);
        this.progressBarPlayer = (ProgressBar) findViewById(R.id.progress_bar_player);
        this.btnNext = (ImageButton) findViewById(R.id.btn_next);
        this.btnPrev = (ImageButton) findViewById(R.id.btn_prev);
        this.btnPlay = (ImageButton) findViewById(R.id.btn_play);
        this.txtDuration = (TextView) findViewById(R.id.txt_total_duration);
        this.txtCurrentDuration = (TextView) findViewById(R.id.txt_current_duration);
        this.txtCurrentMusic = (TextView) findViewById(R.id.txt_current_music);
        this.txtCurrentMusic.setTypeface(typeface);
        this.seekBar = (SeekBar) findViewById(R.id.seek_bar);
        this.btnPlay.setTag(R.drawable.ic_play_black);

        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }


    /**
     * Initialize the playlist and controls.
     * @param mPlaylist List of JcAudio objects that you want play
     */
    public void initPlaylist(List<JcAudio> mPlaylist){
        if(playlist == null)
            playlist = new ArrayList<>();

        for(JcAudio audio : mPlaylist){
             this.playlist.add(audio);
        }

        jcAudioPlayer = new JcAudioPlayer(getContext(), playlist, JcPlayerView.this);
    }


    /**
     * Initialize an anonymous playlist with a default title for all
     * @param urls List of urls strings
     */
    public void initAnonPlaylist(ArrayList<String> urls){
        JcAudio jcAudio;

        if(playlist == null)
            playlist = new ArrayList<>();

        for(int i = 0; i < urls.size(); i++){
                jcAudio = new JcAudio();
                jcAudio.setId(i);
                jcAudio.setPosition(i);
                jcAudio.setUrl(urls.get(i));
                playlist.add(jcAudio);

                generateTitleAudio("Track " + String.valueOf(i+1), i);
        }

        jcAudioPlayer = new JcAudioPlayer(getContext(), playlist, JcPlayerView.this);
    }


    /**
     * Initialize an anonymous playlist, but with a custom title for all
     * @param urls List of urls strings
     * @param title Default title
     */
    public void initWithTitlePlaylist(ArrayList<String> urls, String title){
        JcAudio jcAudio;

        if(playlist == null)
            playlist = new ArrayList<>();

        for(int i = 0; i < urls.size(); i++){
                jcAudio = new JcAudio();
                jcAudio.setId(i);
                jcAudio.setPosition(i);
                jcAudio.setUrl(urls.get(i));
                playlist.add(jcAudio);
                if (i==0)
                    generateTitleAudio("ቅኝት", i);
                if (i==1)
                    generateTitleAudio("ከ እጅ ሥራ ጋር", i);
                if (i==2)
                    generateTitleAudio("ሲቀራረብ", i);


        }

        jcAudioPlayer = new JcAudioPlayer(getContext(), playlist, JcPlayerView.this);
    }


    /**
     * Initialize an anonymous playlist, but with a custom title for all
     * @param title Audio title
     * @param url List of urls strings
     */
    public void addAudio(String title, String url){
            if (playlist == null)
                playlist = new ArrayList<>();

            int lastPosition = playlist.size();
            playlist.add(lastPosition,
                    new JcAudio(url, title, /* id */ lastPosition + 1, /* position */ lastPosition + 1));

            if (jcAudioPlayer == null)
                jcAudioPlayer = new JcAudioPlayer(getContext(), playlist, JcPlayerView.this);
    }



    private void generateTitleAudio(String title, int position){
        playlist.get(position).setTitle(title);
    }


    @Override
    public void onClick(View view) {
        if(playlist != null)
            if(view.getId() ==  R.id.btn_play) {
                if (btnPlay.getTag().equals(R.drawable.ic_pause_black)){
                    pause();
                    Glide.with(playing.getContext()).load(R.drawable.playing).asBitmap().into(playing);
                }
                else{
                    continueAudio();
                    Glide.with(playing.getContext()).load(R.drawable.playing).asGif().into(playing);
                }
            }

            if(view.getId() == R.id.btn_next) {

                next();
            }

            if(view.getId() == R.id.btn_prev) {

                previous();
            }
    }

    public void playAudio(JcAudio JcAudio){
        showProgressBar();
        try {
            jcAudioPlayer.playAudio(JcAudio);
        }catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    public void playAudio(String url, String title){
        showProgressBar();

        JcAudio jcAudio = new JcAudio(url, title);
        if (playlist == null)
            playlist = new ArrayList<>();
        playlist.add(jcAudio);

        if(jcAudioPlayer == null)
            jcAudioPlayer = new JcAudioPlayer(getContext(), playlist, JcPlayerView.this);

        try {
            jcAudioPlayer.playAudio(jcAudio);
        }catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    private void next(){
        resetPlayerInfo();
        showProgressBar();

        try {
            jcAudioPlayer.nextAudio();
        }catch (AudioListNullPointerException e){
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    private void continueAudio(){
        showProgressBar();

        try {
            jcAudioPlayer.continueAudio();
        } catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    private void pause() {
        jcAudioPlayer.pauseAudio();
    }

    private void previous(){
        resetPlayerInfo();
        showProgressBar();

        try {
            jcAudioPlayer.previousAudio();
        } catch (AudioListNullPointerException e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

    @Override
    public void onPreparedAudio(String audioName, int duration) {
        dismissProgressBar();
        resetPlayerInfo();

        long aux = duration / 1000;
        int minute = (int) (aux / 60);
        int second = (int) (aux % 60);

        final String sDuration =
                // Minutes
                (minute < 10 ? "0"+minute : minute+"")
                        + ":" +
                 // Seconds
                 (second < 10 ? "0"+second : second+"");

        seekBar.setMax(duration);

        txtDuration.post(new Runnable() {
            @Override
            public void run() {
                txtDuration.setText(sDuration);
            }
        });
    }

    @Override
    public void onCompletedAudio() {
        resetPlayerInfo();

        try {
            jcAudioPlayer.nextAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void resetPlayerInfo(){
        seekBar.setProgress(0);
        txtCurrentMusic.setText("");
        txtCurrentDuration.setText("00:00");
        txtDuration.setText("00:00");
    }

    public void showProgressBar(){
        progressBarPlayer.setVisibility(ProgressBar.VISIBLE);
        btnPlay.setVisibility(Button.GONE);
        btnNext.setClickable(false);
        btnPrev.setClickable(false);

    }

    public void dismissProgressBar(){
        progressBarPlayer.setVisibility(ProgressBar.GONE);
        btnPlay.setVisibility(Button.VISIBLE);
        btnNext.setClickable(true);
        btnPrev.setClickable(true);
    }


    @Override
    public void onPaused() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play_black, null));
        else
            btnPlay.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play_black, null));
        btnPlay.setTag(R.drawable.ic_play_black);
    }

    @Override
    public void onContinueAudio() {
        dismissProgressBar();
    }

    @Override
    public void onPlaying() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause_black, null));
        else
            btnPlay.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause_black, null));

        btnPlay.setTag(R.drawable.ic_pause_black);
    }

    @Override
    public void onTimeChanged(long currentPosition) {
        long aux = currentPosition / 1000;
        int minutes = (int) (aux / 60);
        int seconds = (int) (aux % 60);
        final String sMinutes = minutes < 10 ? "0"+minutes : minutes+"";
        final String sSeconds = seconds < 10 ? "0"+seconds : seconds+"";

        seekBar.setProgress((int) currentPosition);
        txtCurrentDuration.post(new Runnable() {
            @Override
            public void run() {
                txtCurrentDuration.setText(String.valueOf(sMinutes + ":" + sSeconds));
            }
        });
    }


    @Override
    public void updateTitle(String title) {
        final String mTitle = title;

        txtCurrentMusic.post(new Runnable() {
            @Override
            public void run() {
                txtCurrentMusic.setText(mTitle);
            }
        });
    }


    /**
     * Create a notification player with same playlist with a custom icon.
     * @param iconResource icon path.
     */
    public void createNotification(int iconResource){
        if(jcAudioPlayer != null)
            jcAudioPlayer.createNewNotification(iconResource);
    }

    /**
     * Create a notification player with same playlist with a default icon
     */
    public void createNotification(){
        int iconResource;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            // For light theme
            iconResource = R.drawable.ic_notification_default_black;
        else
            // For dark theme
            iconResource = R.drawable.ic_notification_default_white;

        if(jcAudioPlayer != null)
            jcAudioPlayer.createNewNotification(iconResource);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        if(fromUser)
            jcAudioPlayer.seekTo(i);
    }

    public void kill() {
        if(jcAudioPlayer != null)
            jcAudioPlayer.kill();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        showProgressBar();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        dismissProgressBar();
    }

    public List<JcAudio> getMyPlaylist(){
        return playlist;
    }
}
