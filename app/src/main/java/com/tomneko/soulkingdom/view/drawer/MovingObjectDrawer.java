package com.tomneko.soulkingdom.view.drawer;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingObjectText;

/**
 * MovingObjectの描画
 * <p/>
 * Created by toyama on 2017/09/14.
 */
@Service
public class MovingObjectDrawer {

	@Inject
	private TextDrawer textDrawer;

	/**
	 * MovingObjectTextを描画
	 *
	 * @param canvas
	 * @param mo
	 */
	public void drawMovingObjectText(Canvas canvas, MovingObject mo) {

		MovingObjectText mot = mo.getMovingObjectText();

		textDrawer.drawDecorationText(canvas, mot.getText(), mo.getX(), mo.getY(), mot.getPaintTypeNormal(), mot.getPaintTypeShadow());
	}

	/**
	 * MovingObjectをタイル計算して描画
	 *
	 * @param canvas
	 * @param mo
	 */
	public void drawMovingObject(Canvas canvas, MovingObject mo) {

		// 非表示は何もしない
		if (mo.isInvisible()) {
			return;
		}

		// TODO 下のメソッドと統合
		Rect src = mo.getTileSrc();

		float left = mo.getX();
		float right = mo.getX() + mo.getTileWidth();
		float top = mo.getY();
		float bottom = mo.getY() + mo.getTileHeight();

		RectF dst = new RectF(left, top, right, bottom);

		canvas.drawBitmap(mo.getImage(), src, dst, null);
	}

	/**
	 * MovingObjectを指定された位置とタイルでタイル計算して描画
	 *
	 * @param canvas
	 * @param mo
	 * @param tileX
	 * @param tileY
	 * @param left
	 * @param top
	 */
	public void drawMovingObject(Canvas canvas, MovingObject mo, int tileX, int tileY, float left, float top) {
		Rect src = mo.getTileSrc(tileX, tileY);

		float right = left + mo.getTileWidth();
		float bottom = top + mo.getTileHeight();

		RectF dst = new RectF(left, top, right, bottom);

		canvas.drawBitmap(mo.getImage(), src, dst, null);
	}

}
