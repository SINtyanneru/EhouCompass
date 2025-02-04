package su.rumishistem.android.ehou_compass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Year;
import java.util.HashMap;

import su.rumishistem.android.ehou_compass.TYPE.HOUGAKU;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//今年の西暦を取得
		int YEAR = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			YEAR = Year.now().getValue();

			System.out.println("今年は" + YEAR + "年だ！");
		} else {
			System.out.println("西暦の取得に失敗した");
		}

		//テキスト書き換え
		TextView NOW_TEXT = findViewById(R.id.NowTextView);
		NOW_TEXT.setText(YEAR + "年の恵方は");

		int SHIMO = Integer.parseInt(String.valueOf(YEAR).split("")[String.valueOf(YEAR).split("").length - 1]);
		System.out.println(YEAR + "の下桁は:" + SHIMO);

		//西暦の下桁と十干の対応表
		HashMap<Integer, HOUGAKU> TABLE = new HashMap<>();
		TABLE.put(4, new HOUGAKU(4, 67.5f, "東北東", "東北東微東", "甲"));
		TABLE.put(5, new HOUGAKU(5, 247.5f, "西南西", "西南西微西", "乙"));
		TABLE.put(6, new HOUGAKU(6, 157.5f, "南南東", "南南東微南", "丙"));
		TABLE.put(7, new HOUGAKU(7, 337.5f, "北北西", "北北西微北", "丁"));
		TABLE.put(8, new HOUGAKU(8, 157.5f, "南南東", "南南東微南", "戊"));
		TABLE.put(9, new HOUGAKU(9, 67.5f, "東北東", "東北東微東", "己"));
		TABLE.put(0, new HOUGAKU(0, 247.5f, "西南西", "西南西微西", "庚"));
		TABLE.put(1, new HOUGAKU(1, 157.5f, "南南東", "南南東微南", "辛"));
		TABLE.put(2, new HOUGAKU(2, 337.5f, "北北西", "北北西微北", "壬"));
		TABLE.put(3, new HOUGAKU(3, 157.5f, "南南東", "南南東微南", "癸"));

		HOUGAKU NOW_HOUGAKU = TABLE.get(SHIMO);

		System.out.println("今年は" + NOW_HOUGAKU.GetZHIKKAN() + "なので" + NOW_HOUGAKU.GetHOUGAKU() + "だ！");

		//テキスト書き換え
		TextView HOUGAKU_TEXT = findViewById(R.id.HougakuTextView);
		HOUGAKU_TEXT.setText(NOW_HOUGAKU.GetHOUGAKU());

		//画像表示
		ImageView COMPASS_IMAGEVIEW = findViewById(R.id.CompassImageView);
		COMPASS_IMAGEVIEW.post(new Runnable() {
			@Override
			public void run() {
				int WIDTH = COMPASS_IMAGEVIEW.getWidth();
				int HEIGHT = COMPASS_IMAGEVIEW.getHeight();
				System.out.println(WIDTH);
				System.out.println(HEIGHT);
				Bitmap BMP = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
				Canvas CANVAS = new Canvas(BMP);
				CANVAS.drawColor(Color.WHITE);

				//枠を生成
				float CX = WIDTH / 2f;
				float CY = HEIGHT / 2f;
				Paint CIRCLE_PAINT = new Paint();
				CIRCLE_PAINT.setColor(Color.rgb(255, 234, 128));
				CIRCLE_PAINT.setStyle(Paint.Style.FILL);
				float RADIUS = Math.min(WIDTH, HEIGHT) / 3f;
				CANVAS.drawCircle(CX, CY, RADIUS, CIRCLE_PAINT);

				//方角を書く
				Paint TEXT_PAINT = new Paint();
				TEXT_PAINT.setColor(Color.BLACK);
				TEXT_PAINT.setTextSize(RADIUS / 3); // 適当なサイズに調整
				TEXT_PAINT.setTextAlign(Paint.Align.CENTER);
				TEXT_PAINT.setTypeface(Typeface.DEFAULT_BOLD);
				float TEXT_OFFSET_Y = TEXT_PAINT.getTextSize() / 2; // 文字の高さ調整
				CANVAS.drawText("北", CX, CY - RADIUS - TEXT_OFFSET_Y, TEXT_PAINT);
				CANVAS.drawText("南", CX, CY + RADIUS + TEXT_OFFSET_Y * 2, TEXT_PAINT);
				CANVAS.drawText("東", CX + RADIUS + TEXT_OFFSET_Y, CY + TEXT_OFFSET_Y, TEXT_PAINT);
				CANVAS.drawText("西", CX - RADIUS - TEXT_OFFSET_Y, CY + TEXT_OFFSET_Y, TEXT_PAINT);

				//恵方の方角に向けて線を引く
				Paint HOUGAKU_PAINT = new Paint();
				HOUGAKU_PAINT.setColor(Color.BLACK);
				HOUGAKU_PAINT.setStrokeWidth(5);				//↓何故か東スタート且つ上向きに進む
				float ANGLE = (float)Math.toRadians((NOW_HOUGAKU.GetDO() - 90) * -1);//←度からラジアンに変換する必要が有る
				float EX = CX + ((float) (RADIUS * Math.cos(ANGLE)));
				float EY = CY - ((float) (RADIUS * Math.sin(ANGLE)));
				CANVAS.drawLine(CX, CY, EX , EY, HOUGAKU_PAINT);

				COMPASS_IMAGEVIEW.setImageBitmap(BMP);
			}
		});
	}
}