package com.tomneko.soulkingdom.view.battle.drawer;

import android.graphics.Canvas;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.EffectManager;
import com.tomneko.soulkingdom.view.battle.manager.EnemyManager;
import com.tomneko.soulkingdom.view.battle.manager.PartyManager;
import com.tomneko.soulkingdom.view.drawer.TextDrawer;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.drawer.MovingObjectDrawer;

/**
 * エフェクトの描画
 *
 * Created by toyama on 2017/09/08.
 */
@Service
public class EffectDrawer {

	@Inject
	private EffectManager effectManager;

	@Inject
	private MovingObjectDrawer movingObjectDrawer;

	/**
	 * エフェクトを描画
	 *
	 * @param canvas
	 */
	public void drawEffect(Canvas canvas) {

		for (MovingEffect me : effectManager.getMovingEffectList()) {
			MovingObject mo = me.getMovingObject();
			movingObjectDrawer.drawMovingObject(canvas, mo);
		}

		for (MovingObject mo : effectManager.getMovingTextList()) {
			movingObjectDrawer.drawMovingObjectText(canvas, mo);
		}
	}
}
