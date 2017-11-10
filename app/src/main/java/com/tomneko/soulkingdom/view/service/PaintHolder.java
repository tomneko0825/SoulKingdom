package com.tomneko.soulkingdom.view.service;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

import java.util.HashMap;
import java.util.Map;

/**
 * ペインターの再利用のためのホルダー
 * <p/>
 * Created by toyama on 2017/09/21.
 */
@Service
public class PaintHolder {

	@Inject
	private ScaleCalculator sc;

	private AssetManager assetManager;

	private Map<PaintHolderType, Paint> paintMap = new HashMap();

	private Map<String, Typeface> typefaceMap = new HashMap();

	public void initialize(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	/**
	 * Paintを取得
	 *
	 * @param type
	 * @return
	 */
	public Paint getPaint(PaintHolderType type) {

		Paint paint = paintMap.get(type);
		if (paint != null) {
			return paint;
		}

		switch (type.getPaintHolderFontType()) {
			case NORMAL:
				paint = createNormalFontPaint(type);
				break;
			case SHADOW:
				paint = createShadowFontPaint(type);
				break;
		}

		paintMap.put(type, paint);
		return paint;
	}

	/**
	 * Typefaceを取得
	 *
	 * @param fontFileName
	 * @return
	 */
	private Typeface getTypeface(String fontFileName) {
		Typeface typeface = typefaceMap.get(fontFileName);
		if (typeface == null) {
			typeface = Typeface.createFromAsset(assetManager, fontFileName);
		}
		return typeface;
	}

	private Paint createNormalFontPaint(PaintHolderType type) {
		// 文字の設定
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(0);
		paint.setColor(type.getColor());
		paint.setTextSize(sc.getX(type.getTextSize()));
		paint.setTextAlign(type.getAlign());
		paint.setStyle(Paint.Style.FILL);
		paint.setTypeface(getTypeface(type.getTypefaceName()));
		return paint;
	}

	private Paint createShadowFontPaint(PaintHolderType type) {
		// 縁取りの設定
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(sc.getX(type.getTextSize() / 4));
		paint.setColor(type.getColor());
		paint.setAlpha(128);
		paint.setTextSize(sc.getX(type.getTextSize()));
		paint.setTextAlign(type.getAlign());
		paint.setStyle(Paint.Style.STROKE);
		paint.setTypeface(getTypeface(type.getTypefaceName()));
		return paint;
	}




}
