package com.tomneko.soulkingdom.view.drawer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.NinePatchDrawable;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.List;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * ボタンの補助
 * <p/>
 * Created by toyama on 2017/09/02.
 */
@Service
public class ButtonDrawer {

	@Inject
	private ScaleCalculator sc;

	/**
	 * 描画
	 *
	 * @param canvas
	 * @param bo
	 */
	public void draw(Canvas canvas, ButtonObject bo) {

		switch (bo.getButtonObjectType()) {
			case ALPHA_LABEL:
				drawAlphaLabelButton(canvas, bo);
				break;
			case IMAGE_NINE:
				drawNinePatchButton(canvas, bo);
				break;
			case IMAGE:
				break;
		}
	}

	/**
	 * 半透明ラベルのボタン
	 *
	 * @param canvas
	 * @param bo
	 */
	private void drawAlphaLabelButton(Canvas canvas, ButtonObject bo) {

		// float corner = sc.getX(8);

		// ボタン作成時にスケール計算してるので注意
		int left = (int) bo.getX();
		int top = (int) bo.getY();
		int right = (int) (bo.getX() + bo.getWidth());
		int bottom = (int) (bo.getY() + bo.getHeight());

		// 矩形を作成
		RectF rect = new RectF(left, top, right, bottom);

		Paint paint = new Paint(ANTI_ALIAS_FLAG);

		// グラデーションの作成
		// int[] colors = {Color.DKGRAY, Color.LTGRAY, Color.DKGRAY};
		int[] colors = {Color.BLACK, Color.DKGRAY, Color.BLACK};
		LinearGradient lig = new LinearGradient(left, 0, right, 0, colors, null, Shader.TileMode.CLAMP);
		paint.setShader(lig);

		// 半透明
		paint.setAlpha(32);

		// 角丸作成
		// canvas.drawRoundRect(rect, corner, corner, paint);
		canvas.drawRect(rect, paint);
	}

	/**
	 * 9-patchのボタン
	 *
	 * @param canvas
	 * @param bo
	 */
	public void drawNinePatchButton(Canvas canvas, ButtonObject bo) {

		// ボタン作成時にスケール計算してるので注意
		int left = (int) bo.getX();
		int top = (int) bo.getY();
		int right = (int) (bo.getX() + bo.getWidth());
		int bottom = (int) (bo.getY() + bo.getHeight());

		NinePatchDrawable npd = bo.getCurrentNpd();

		npd.setBounds(left, top, right, bottom);
		npd.draw(canvas);
	}

	/**
	 * 指定された位置に含まれるオブジェクトを取得
	 *
	 * @param buttonObjectList
	 * @param posX
	 * @param posY
	 * @return
	 */
	public ButtonObject contains(List<ButtonObject> buttonObjectList, float posX, float posY) {
		for (ButtonObject bo : buttonObjectList) {
			if (bo.contains(posX, posY)) {
				return bo;
			}
		}
		return null;
	}


}
