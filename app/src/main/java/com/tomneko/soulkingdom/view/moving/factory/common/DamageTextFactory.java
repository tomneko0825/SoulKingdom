package com.tomneko.soulkingdom.view.moving.factory.common;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingObjectText;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.PaintHolder;
import com.tomneko.soulkingdom.view.service.enums.PaintHolderType;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.TEXT_MOVE;

/**
 * ダメージのテキストを表示
 * <p/>
 * Created by toyama on 2017/09/13.
 */
@Service
public class DamageTextFactory {

	/**
	 * ダメージテキストを作成
	 *
	 * @param target
	 * @param step
	 * @return
	 */
	public MovingObject createDamageText(MovingObject target, String text, int step) {

		MovingObject mo = new MovingObject(target.getInvokerId());
		float x = target.getX() + target.getTileWidth() / 2;
		float y = target.getY();
		mo.setBaseX(x);
		mo.setX(x);
		mo.setBaseY(y);
		mo.setY(y);

		MovingObjectText mot = new MovingObjectText(text, PaintHolderType.DAMAGE_HP, PaintHolderType.DAMAGE_HP_SHADOW);
		mo.setMovingObjectText(mot);

		MovingCombination mc = new MovingCombination("damageText");

		// 固定
		MovingTarget mt = MovingTargetFactory.createStay(x, y, step);
		mt.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt);

		mo.setMovingCombination(mc);

		return mo;
	}
}

