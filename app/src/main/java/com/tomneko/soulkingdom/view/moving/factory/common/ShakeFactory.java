package com.tomneko.soulkingdom.view.moving.factory.common;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageConstatns;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.moving.model.enums.MovingType;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.SHAKE_MOVE;

/**
 * シェイク
 * <p/>
 * Created by toyama on 2017/09/09.
 */
@Service
public class ShakeFactory {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattleConstantsHolder bch;

	/**
	 * キャラ用シェイク
	 *
	 * @param mo
	 * @return
	 */
	public MovingCombination createShakeForChara(MovingObject mo) {
		return createShake(mo, BLOCK_WIDHT, -BLOCK_WIDHT, true);
	}

	/**
	 * 現在地点のキャラ用シェイク＆基本ポジションに戻す
	 *
	 * @param mo
	 * @return
	 */
	public MovingCombination createShakeForCharaCurrentAndReturn(MovingObject mo) {
		MovingCombination mc = createShake(mo, BLOCK_WIDHT, -BLOCK_WIDHT, mo.getX(), mo.getY(), true);

		MovingTarget mt = MovingTargetFactory.createStraight(mo.getX(), mo.getY(), mo.getBaseX(), mo.getBaseY(), 2);
		mt.setMovingType(MovingType.SHAKE_MOVE);
		mc.addMovingTarget(mt);

		return mc;
	}

	/**
	 * 敵用シェイク
	 *
	 * @param mo
	 * @return
	 */
	public MovingCombination createShakeForEnemy(MovingObject mo) {
		return createShake(mo, -BLOCK_WIDHT, BLOCK_WIDHT, false);
	}

	/**
	 * 指定量のシェイク
	 *
	 * @param mo
	 * @param x1
	 * @param x2
	 * @param imageChange
	 * @return
	 */
	public MovingCombination createShake(MovingObject mo, float x1, float x2, boolean imageChange) {
		return createShake(mo, x1, x2, mo.getBaseX(), mo.getBaseY(), imageChange);
	}

	/**
	 * 指定量のシェイク
	 *
	 * @param mo
	 * @param x1
	 * @param x2
	 * @param baseX
	 * @param baseY
	 * @param imageChange
	 * @return
	 */
	public MovingCombination createShake(MovingObject mo, float x1, float x2, float baseX, float baseY, boolean imageChange) {
		x1 = baseX + sc.getX(x1);
		x2 = baseX + sc.getX(x2);

		MovingCombination mc = new MovingCombination("shake");

		int shakeStep = bch.getStep(4);

		// 後
		MovingTarget mt1 = MovingTargetFactory.createStraight(baseX, baseY, x1, baseY, shakeStep);
		mt1.setMovingType(SHAKE_MOVE);
		mc.addMovingTarget(mt1);

		if (imageChange) {
			MovingImageChanger changerShake = new MovingImageChanger(3, 1);
			mt1.putMovingImageChangerLast(changerShake);
		}

		// 前
		MovingTarget mt2 = MovingTargetFactory.createStraight(x1, baseY, x2, baseY, shakeStep);
		mt2.setMovingType(SHAKE_MOVE);
		mc.addMovingTarget(mt2);

		// 戻す
		MovingTarget mt3 = MovingTargetFactory.createStraight(x2, baseY, baseX, baseY, shakeStep);
		mt3.setMovingType(SHAKE_MOVE);
		mc.addMovingTarget(mt3);

		if (imageChange) {
			// シェイク後に画像を戻す
			MovingImageChanger changerDefault = new MovingImageChanger();
			changerDefault.setDefault();
			mt3.putMovingImageChangerLast(changerDefault);
		}

		return mc;
	}

}
