package helloandroid.m2dl.photosnap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.helpers.Square;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private PopupWindow popWindow;

    private int width;
    private int height;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Bitmap bitmap;

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

        Ball ball = new Ball(128, 128, 64);
        Exit exit = new Exit(new Square(128, gameView.getTop(), 800));
        GameContext gameContext = new GameContext(ball, exit);

        gameView.setGameContext(gameContext);
        gameView.invalidate();

        onShowPopupMenu(mainView);

        imageView = findViewById(R.id.imageView);
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
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = convertBitmap(imageBitmap);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public static Bitmap convertBitmap(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
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
                    gray = 255;
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
        } else {
            popWindow.dismiss();
            popWindow = null;
            dispatchTakePictureIntent();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && popWindow != null) {
            popWindow.showAtLocation(findViewById(R.id.game_view), Gravity.CENTER, 0,90);
        }
    }

    public void onShowPopupMenu(View v) {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.menu_popup, null,false);

        popWindow = new PopupWindow(inflatedView, width - 256, height - 480, true);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
    }

}
