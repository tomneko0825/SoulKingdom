package com.tomneko.soulkingdom.view.moving.factory.chara;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.ATTACK;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.*;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.C_SLASHS;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_ENEMY_TARGET;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.*;

/**
 * スラッシュ縦のファクトリ
 *
 * Created by toyama on 2017/09/15.
 */
@Service
public class SlashVFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	/**
	 * スラッシュ縦を作成
	 *
	 * @param mo
	 * @return
	 */
	public MovingCombination createSlashV(MovingObject mo, int baseStep) {

		float x1 = mo.getBaseX() + (sc.getX(BLOCK_WIDHT) * 1);
		float x2 = mo.getBaseX() - (sc.getX(BLOCK_WIDHT) * 2);

		float topY1 = sc.getY(40);
		float topY2 = sc.getY(160);

		MovingCombination mc = new MovingCombination("slashV");

		// 後ろ
		MovingTarget mt1 = MovingTargetFactory.createCurve(mo.getBaseX(), mo.getBaseY(), x1, topY1, baseStep / 2);
		mt1.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt1);

		// 画像の切り替え
		MovingImageChanger changer1 = new MovingImageChanger(1, 1);
		mt1.putMovingImageChangerFirst(changer1);

		// 停止
		MovingTarget mt2 = MovingTargetFactory.createStay(x1, mo.getBaseY(), baseStep);
		mt2.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt2);

		// 前スラッシュ
		MovingTarget mt3 = MovingTargetFactory.createCurve(x1, mo.getBaseY(), x2, topY2, baseStep);
		mt3.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt3);

		// 画像の切り替え
		MovingImageChanger changer3 = new MovingImageChanger(3, 1);
		mt3.putMovingImageChangerFirst(changer3);

		// エフェクト
		MovingEffect movingEffect = simpleEffectFactory.createStopEffectToTarget(mo.getInvokerId(), C_SLASHV, ET_ENEMY_TARGET);
		mt3.putMovingEffectLast(movingEffect);

		// アクション
		BattleAction action = new BattleAction(ATTACK, mo.getInvokerId());
		action.setFrom(TARGET_TYPE_CHARA);
		action.setTo(TARGET_TYPE_ENEMY);
		action.setMessage("action");

		movingEffect.setBattleAction(action);

		// ジャンプ戻り
		MovingTarget mt4 = MovingTargetFactory.createCurve(x2, mo.getBaseY(), mo.getBaseX(), topY1, baseStep);
		mt4.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt4);

		// 画像の切り替え
		MovingImageChanger changer4 = new MovingImageChanger();
		changer4.setDefault();
		mt4.putMovingImageChangerLast(changer4);

		return mc;
	}
}


