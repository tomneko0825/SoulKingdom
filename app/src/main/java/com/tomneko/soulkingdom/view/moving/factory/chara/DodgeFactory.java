package com.tomneko.soulkingdom.view.moving.factory.chara;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator;
import com.tomneko.soulkingdom.view.image.enums.EffectImageEnum;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.*;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.DODGE_AFTER_MOVE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.DODGE_BEFORE_MOVE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.DODGE_MOVE;

/**
 * 回避
 * <p/>
 * Created by toyama on 2017/09/13.
 */
@Service
public class DodgeFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	/**
	 * 回避を作成
	 *
	 * @param mo
	 * @param dodgeBeforeStep
	 * @param dodgeStep
	 * @param dodgeAfterStep
	 * @return
	 */
	public MovingCombination createDodge(MovingObject mo, int dodgeBeforeStep, int dodgeStep, int dodgeAfterStep) {

		float x1 = mo.getBaseX() + sc.getX(BLOCK_WIDHT) * 2;

		MovingCombination mc = new MovingCombination("dodge");

		// 後ろ
		MovingTarget mt1 = MovingTargetFactory.createStraight(mo.getBaseX(), mo.getBaseY(), x1, mo.getBaseY(), dodgeBeforeStep);
		mt1.setMovingType(DODGE_BEFORE_MOVE);
		mc.addMovingTarget(mt1);

		// 画像の切り替え
		// 後ろむき
		MovingImageChanger changerBack = new MovingImageChanger(1, 2);
		mt1.putMovingImageChangerFirst(changerBack);

		// 回避エフェクト
		// 回避途中に描画
		float rootX = bpc.getCharaRootPositionX() + sc.getX(BLOCK_WIDHT);
		float rootY = bpc.getCharaRootPositionY();
		MovingEffect movingEffect = simpleEffectFactory.createStopEffect(mo.getInvokerId(), rootX, rootY, dodgeBeforeStep, C_DODGE_S);

		mt1.putMovingEffectFirst(movingEffect);

		// 回避中
		MovingTarget mt2 = MovingTargetFactory.createStay(x1, mo.getBaseY(), dodgeStep);
		mt2.setMovingType(DODGE_MOVE);
		mc.addMovingTarget(mt2);

		MovingImageChanger changerStay = new MovingImageChanger(1, 1);
		mt2.putMovingImageChangerFirst(changerStay);

		// 戻す
		MovingTarget mt3 = MovingTargetFactory.createStraight(x1, mo.getBaseY(), mo.getBaseX(), mo.getBaseY(), dodgeAfterStep);
		mt3.setMovingType(DODGE_AFTER_MOVE);
		mc.addMovingTarget(mt3);

		// 後ろむき
		mt3.putMovingImageChangerFirst(changerBack);

		// 画像をデフォルトに戻す
		MovingImageChanger changerDefault = new MovingImageChanger();
		changerDefault.setDefault();
		mt3.putMovingImageChangerLast(changerDefault);

		return mc;
	}
}

