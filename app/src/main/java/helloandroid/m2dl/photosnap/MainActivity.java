package helloandroid.m2dl.photosnap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.photosnap.delegate.DataChange;
import helloandroid.m2dl.photosnap.delegates.GameDelegate;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.helpers.GameContextBuilder;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity  implements DataChange, GameDelegate {

    private SensorManager sensorManager;
    private  TextView dir;
    private int width;
    private int height;
    private GameView gameView;

    private Handler mHandler = new Handler();

    private boolean running = false;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (running) {
                gameView.invalidate();
                mHandler.postDelayed(this, 10);
            }
        }
    };

    private Rect gameViewRect;
    private PopupWindow popWindow;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private static List<Integer[]> obstaclePicture;

    private GameContext gameContext;

    private ImageButton btnPicture;
    private ImageButton btnRebuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        setContentView(R.layout.activity_main);

        ConstraintLayout mainView = findViewById(R.id.main_layout);

        gameView = findViewById(R.id.game_view);

        gameView.setDelegate(this);

        gameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                gameViewRect = new Rect(gameView.getLeft(), gameView.getTop(), gameView.getRight(), gameView.getBottom());
                gameContext = GameContextBuilder.buildGameContext(gameViewRect, 0,0);
                gameView.setGameContext(gameContext);
                gameView.invalidate();
            }
        });

        showPopupMenu(R.layout.menu_popup);

        imageView = findViewById(R.id.imageView);

        btnPicture = findViewById(R.id.btn_pic);
        btnRebuild = findViewById(R.id.btn_rebuild);

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnRebuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameContext = GameContextBuilder.buildGameContext(gameViewRect, gameContext.getDifficulty(), gameContext.getDifficulty());
                gameView.setGameContext(gameContext);
                gameView.invalidate();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorAcceleration capt = new SensorAcceleration(sensorManager,this);
        sensorManager.registerListener(capt,capt.getSensor(), SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (popWindow != null)
                popWindow.dismiss();
            popWindow = null;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            convertBitmap(imageBitmap);
            imageView.setImageBitmap(imageBitmap);
            running = true;
            mHandler.postDelayed(mUpdateTimeTask,500);
        }
    }

    public static Bitmap convertBitmap(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        obstaclePicture = new ArrayList<>();
        // color information
        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                // use 128 as threshold, above -> white, below -> black
                if (gray > 115) {
                    Integer[] tmp = new Integer[2];
                    tmp[0] = x;
                    tmp[1] = y;
                    obstaclePicture.add(tmp);
                }
                else{
                    gray = 0;
                }
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
            }
        }
        return bmOut;
    }

    public void onClickListener(View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }
        dispatchTakePictureIntent();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && popWindow != null) {
            popWindow.showAtLocation(findViewById(R.id.game_view), Gravity.CENTER, 0,90);
        }
    }

    public void showPopupMenu(int resource) {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(resource, null,false);

        popWindow = new PopupWindow(inflatedView, width - 256, height - 480, true);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
    }

    @Override
    public void onWin(GameContext gameContext) {
        running = false;
        gameContext.getBall().setMoving(false);
        gameContext.getBall().setDir(Direction.NONE);
        imageView.setImageBitmap(null);
        int nextDifficulty = gameContext.getDifficulty() + 1;
        this.gameContext = GameContextBuilder.buildGameContext(gameViewRect, nextDifficulty, nextDifficulty);
        gameView.setGameContext(this.gameContext);
        gameView.invalidate();

        showPopupMenu(R.layout.win_popup);
    }

    @Override
    public void onLose(GameContext gameContext) {
        running = false;
        gameContext.getBall().setMoving(false);
        gameContext.getBall().setDir(Direction.NONE);
        gameView.setGameContext(gameContext);
        gameView.invalidate();

        showPopupMenu(R.layout.lose_popup);
    }

    @Override
    public void dataDidChange(SensorAcceleration capteur, Direction direction) {
        if ( gameView.getGameContext() != null ) {
            Ball ball = gameView.getGameContext().getBall();
            //Si la balle ne bouge pas on l'a fait avancer dans la direction du capteur
           // if (Direction.NONE.equals(ball.getDir())) {
                ball.setMoving(true);
                ball.setDir(direction);
            //}
        }

    }

}
