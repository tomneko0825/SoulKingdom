package com.tomneko.soulkingdom.view.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.service.PaintHolder;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;
import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

/**
 * 各種文字の描画
 * <p/>
 * Created by toyama on 2017/09/12.
 */
@Service
public class TextDrawer {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private PaintHolder paintHolder;

	/**
	 * 飾り文字を描画
	 *
	 * @param canvas
	 * @param text
	 * @param textX
	 * @param textY
	 * @param type
	 * @param shadowType
	 */
	public void drawDecorationText(Canvas canvas, String text, float textX, float textY, PaintHolderType type, PaintHolderType shadowType) {
		Paint nameShadowPaint = paintHolder.getPaint(shadowType);
		canvas.drawText(text, textX, textY, nameShadowPaint);
		Paint namePaint = paintHolder.getPaint(type);
		canvas.drawText(text, textX, textY, namePaint);
	}

}
