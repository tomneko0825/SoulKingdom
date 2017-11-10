package com.tomneko.soulkingdom.view.moving.factory.enemy;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.moving.model.enums.MovingType;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_X;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.ATTACK;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.E_BITE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_CHARA;

/**
 * 出現のファクトリ
 *
 * Created by toyama on 2017/09/26.
 */
@Service
public class AppearFactory {

	@Inject
	private ScaleCalculator sc;

	public MovingCombination createAppearIn(MovingObject mo, int baseStep) {

		float backX = 0 - mo.getTileWidth();
		float y = mo.getBaseY();
		float frontX = mo.getBaseX();
		float topY = sc.getY(80);

		MovingCombination mc = new MovingCombination("appearIn");

		// ジャンプ
		MovingTarget mt = MovingTargetFactory.createCurve(
				backX, y, frontX, topY, baseStep);
		mt.setMovingType(MovingType.APPEAR_IN_MOVE);
		mc.addMovingTarget(mt);

		return mc;
	}

}
