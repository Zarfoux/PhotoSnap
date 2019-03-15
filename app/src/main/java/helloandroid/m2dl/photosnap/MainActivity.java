package helloandroid.m2dl.photosnap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.helpers.Square;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Button button;
    private Bitmap bitmap;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.game_view);

        System.out.println(gameView);

        Ball ball = new Ball(128, 128, 64);
        Exit exit = new Exit(new Square(128, 800, 800));
        GameContext gameContext = new GameContext(ball, exit);

        gameView.setGameContext(gameContext);
        gameView.invalidate();

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
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
}
