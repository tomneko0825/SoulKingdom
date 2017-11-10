package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.battle.manager.BattleButtonManager;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;

/**
 * 戦闘用ボタンの描画
 *
 * Created by toyama on 2017/09/08.
 */
@Service
public class BattleButtonDrawer {

	@Inject
	private BattleButtonManager battleButtonManager;

	@Inject
	private ButtonDrawer buttonDrawer;

	/**
	 * 描画
	 *
	 * @param canvas
	 */
	public void drawButton(Canvas canvas) {
		for (ButtonObject bo : battleButtonManager.getButtonObjectList()) {

			// ボタン作成時にスケール計算してるので注意
			int left = (int) bo.getX();
			int top = (int) bo.getY();
			int right = (int) (bo.getX() + bo.getWidth());
			int bottom = (int) (bo.getY() + bo.getHeight());

			NinePatchDrawable npd = bo.getCurrentNpd();
			npd.setBounds(left, top, right, bottom);
			npd.draw(canvas);
		}
	}
}
