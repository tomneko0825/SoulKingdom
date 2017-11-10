package com.tomneko.soulkingdom.view.moving.factory.enemy;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_X;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.ATTACK;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.E_BITE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_CHARA;

/**
 * 強襲のファクトリ
 * <p/>
 * Created by toyama on 2017/09/06.
 */
@Service
public class SimpleAssultFactory implements EnemySkillFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattleConstantsHolder bch;

	@Override
	public MovingCombination create(MovingObject mo) {

		int baseStep = bch.getStep();

		int[] steps = {baseStep, baseStep, baseStep, baseStep};

		return create(mo, steps);
	}

	@Override
	public MovingCombination create(MovingObject mo, int[] steps) {

		float backX = sc.getViewWidth() / BLOCK_COUNT * 3;
		float frontX = sc.getViewWidth() / BLOCK_COUNT * CHARA_BASE_X - mo.getImage().getWidth();
		float topY1 = sc.getY(80);
		float topY2 = sc.getY(160);
		float topY3 = sc.getY(120);

		float x1 = backX;
		float x2 = frontX;
		float x3 = mo.getBaseX();

		float y = mo.getBaseY();

		MovingCombination mc = new MovingCombination("simpleAssult");

		// バックステップ
		MovingTarget mt1 = MovingTargetFactory.createCurve(
				mo.getBaseX(), y, x1, topY1, steps[0]);
		mc.addMovingTarget(mt1);

		// 停止
		MovingTarget mt2 = MovingTargetFactory.createStay(x1, y, steps[1]);
		mc.addMovingTarget(mt2);

		// ジャンプ
		MovingTarget mt3 = MovingTargetFactory.createCurve(
				x1, y, x2, topY2, steps[2]);
		mc.addMovingTarget(mt3);

		// 攻撃エフェクト
		MovingEffect movingEffect = simpleEffectFactory.createStopEffectToTarget(mo.getInvokerId(), E_BITE, ET_CHARA);
		mt3.putMovingEffectLast(movingEffect);

		// アクション
		BattleAction action = new BattleAction(ATTACK, mo.getInvokerId());
		action.setFrom(TARGET_TYPE_ENEMY);
		action.setTo(TARGET_TYPE_CHARA);
		action.setMessage("action");

		movingEffect.setBattleAction(action);

		// 戻る
		MovingTarget mt5 = MovingTargetFactory.createCurve(
				x2, y, x3, topY3, steps[3]);
		mc.addMovingTarget(mt5);

		return mc;
	}

}
