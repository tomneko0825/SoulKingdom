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
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.INEVITABLE_ATTACK;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.E_CRAW;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_CHARA;

/**
 * 直線強襲のファクトリ
 * <p/>
 * Created by toyama on 2017/09/06.
 */
@Service
public class StraightAssultFactory implements EnemySkillFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattleConstantsHolder bch;

	@Override
	public MovingCombination create(MovingObject mo) {

		int baseStep = bch.getStep();

		int[] steps = {baseStep, baseStep, baseStep, baseStep, baseStep};

		return create(mo, steps);
	}

	@Override
	public MovingCombination create(MovingObject mo, int[] steps) {

		float backX1 = sc.getViewWidth() / BLOCK_COUNT * 4;
		float backX2 = sc.getViewWidth() / BLOCK_COUNT * 3;
		float frontX = sc.getViewWidth() / BLOCK_COUNT * 13;
		float topY1 = sc.getY(80);
		float topY2 = sc.getY(120);
		float topY3 = sc.getY(120);

		float x1 = backX1;
		float x2 = backX2;
		float x3 = frontX - (mo.getTileWidth());
		float x4 = mo.getBaseX();

		float y1 = mo.getBaseY();
		float y2 = y1 - topY2;

		MovingCombination mc = new MovingCombination("straightAssult");

		// バックステップ１
		MovingTarget mt1 = MovingTargetFactory.createCurve(
				mo.getBaseX(), y1, x1, topY1, steps[0]);
		mc.addMovingTarget(mt1);

		// バックステップ２
		MovingTarget mt2 = MovingTargetFactory.createCurveUp(
				x1, y1, x2, topY2, steps[1]);
		mc.addMovingTarget(mt2);

		// 停止
		MovingTarget mtStop = MovingTargetFactory.createStay(x2, y2, steps[2]);
		mc.addMovingTarget(mtStop);

		// ストレート
		MovingTarget mt3 = MovingTargetFactory.createStraight(x2, y2, x3, y1, steps[3]);
		mc.addMovingTarget(mt3);

		// 攻撃エフェクト
		MovingEffect movingEffect = simpleEffectFactory.createStopEffectToTarget(mo.getInvokerId(), E_CRAW, ET_CHARA);
		mt3.putMovingEffect(steps[3] - 1, movingEffect);

		// アクション
		BattleAction action = new BattleAction(INEVITABLE_ATTACK, mo.getInvokerId());
		action.setFrom(TARGET_TYPE_ENEMY);
		action.setTo(TARGET_TYPE_CHARA);
		action.setMessage("action");
		movingEffect.setBattleAction(action);

		// 戻る
		MovingTarget mt4 = MovingTargetFactory.createCurve(
				x3, y1, x4, topY3, steps[4]);
		mc.addMovingTarget(mt4);

		return mc;
	}

}
