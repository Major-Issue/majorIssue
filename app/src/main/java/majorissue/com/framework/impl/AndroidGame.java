package majorissue.com.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.majorissue.game.R;

import majorissue.com.framework.Audio;
import majorissue.com.framework.FileIO;
import majorissue.com.framework.Game;
import majorissue.com.framework.Graphics;
import majorissue.com.framework.Input;
import majorissue.com.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    InterstitialAd interstitialAd;

    public final static float defaultWidth = 1794f;
    public final static float defaultHeight = 1080f;
    float scaleX = 1f;
    float scaleY = 1f;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        scaleX = (float)displayWidth / defaultWidth;
        scaleY = (float)displayHeight / defaultHeight;

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? displayWidth : displayHeight;
        int frameBufferHeight = isLandscape ? displayHeight : displayWidth;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth / displayWidth;
        float scaleY = (float) frameBufferHeight / displayHeight;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();

        setContentView(R.layout.container);
        FrameLayout fl = (FrameLayout) findViewById(R.id.container_game);
        fl.addView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");

        loadAd();
    }

    private void loadAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // requestNewInterstitial(); // remove if exit
            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("07429FC17B6F7C0A242414934F91CB26") // my nexus 5
                .build();

        interstitialAd.loadAd(adRequest);
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing()) {
            if(interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
            screen.dispose();
        }
    }

    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }
}