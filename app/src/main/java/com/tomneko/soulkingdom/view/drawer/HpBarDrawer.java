package com.tomneko.soulkingdom.view.drawer;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * HPバーを描画
 * <p/>
 * Created by toyama on 2017/09/23.
 */
@Service
public class HpBarDrawer {

	@Inject
	private ScaleCalculator sc;

	/**
	 * バーを作成
	 *
	 * @param canvas
	 * @param rect
	 * @param colors
	 * @param isFrame
	 */
	public void drawBar(Canvas canvas, RectF rect, int[] colors, boolean isFrame) {

		Paint paint = new Paint(ANTI_ALIAS_FLAG);

		float gradientTop = rect.top;
		float gradientBottom = rect.bottom;

		LinearGradient lig = new LinearGradient(0, gradientTop, 0, gradientBottom, colors, null, Shader.TileMode.CLAMP);
		paint.setShader(lig);

		// 半透明
		paint.setAlpha(192);

		// 枠
		if (isFrame) {
			paint.setStrokeWidth(sc.getX(4));
			paint.setStyle(Paint.Style.STROKE);
		}

		canvas.drawRect(rect, paint);
	}


}
