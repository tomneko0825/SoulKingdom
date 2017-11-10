package com.tomneko.soulkingdom.view.moving.factory.chara;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.*;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.*;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.*;

/**
 * ガード
 *
 * Created by toyama on 2017/09/10.
 */
@Service
public class GuardFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	/**
	 * ガードを作成
	 *
	 * @param mo
	 * @param step
	 * @return
	 */
	public MovingCombination createGuard(MovingObject mo, int step) {

		float x1 = mo.getBaseX() - sc.getX(BLOCK_WIDHT);

		MovingCombination mc = new MovingCombination("guard");

		// 前
		MovingTarget mt1 = MovingTargetFactory.createStraight(mo.getBaseX(), mo.getBaseY(), x1, mo.getBaseY(), step / 2);
		mt1.setMovingType(GUARD_MOVE);
		mc.addMovingTarget(mt1);

		// 画像の切り替え
		MovingImageChanger changer1 = new MovingImageChanger(1, 1);
		mt1.putMovingImageChangerFirst(changer1);

		// ガードエフェクト
		float rootX = bpc.getCharaRootPositionX() - sc.getX(BLOCK_WIDHT);
		float rootY = bpc.getCharaRootPositionY();
		MovingEffect movingEffect = simpleEffectFactory.createStopEffect(mo.getInvokerId(), rootX, rootY, step / 2, C_GUARD_S);

		mt1.putMovingEffectFirst(movingEffect);

		// 戻す
		MovingTarget mt2 = MovingTargetFactory.createStraight(x1, mo.getBaseY(), mo.getBaseX(), mo.getBaseY(), step / 2);
		mt2.setMovingType(GUARD_AFTER_MOVE);
		mc.addMovingTarget(mt2);

		// 画像をデフォルトに戻す
		MovingImageChanger changer2 = new MovingImageChanger();
		changer2.setDefault();
		mt2.putMovingImageChangerLast(changer2);

		return mc;
	}
}

