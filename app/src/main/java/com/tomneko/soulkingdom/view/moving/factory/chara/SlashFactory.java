package com.tomneko.soulkingdom.view.moving.factory.chara;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
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
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.ATTACK;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.*;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType.ET_ENEMY_TARGET;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.ACTION_NOSA_MOVE;

/**
 * スラッシュのファクトリ
 * <p/>
 * Created by toyama on 2017/09/15.
 */
@Service
public class SlashFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	/**
	 * スラッシュを作成
	 *
	 * @param mo
	 * @return
	 */
	public MovingCombination create(MovingObject mo) {

		int attackStep = 4;
		int backStep = 4;

		float x = mo.getBaseX() - (sc.getX(BLOCK_WIDHT) * 2);
		float y = mo.getBaseY();

		MovingCombination mc = new MovingCombination("slash");

		// 前スラッシュ
		MovingTarget mt1 = MovingTargetFactory.createStraight(mo.getBaseX(), y, x, y, attackStep);
		mt1.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt1);

		// 画像の切り替え
		MovingImageChanger changer1 = new MovingImageChanger(3, 1);
		mt1.putMovingImageChangerFirst(changer1);

		// エフェクト
		MovingEffect movingEffect = simpleEffectFactory.createStopEffectToTarget(mo.getInvokerId(), C_SLASH, ET_ENEMY_TARGET);
		mt1.putMovingEffectLast(movingEffect);

		// アクション
		BattleAction action = new BattleAction(ATTACK, mo.getInvokerId());
		action.setFrom(TARGET_TYPE_CHARA);
		action.setTo(TARGET_TYPE_ENEMY);
		action.setMessage("action");

		movingEffect.setBattleAction(action);

		// 戻る
		MovingTarget mt2 = MovingTargetFactory.createStraight(x, y, mo.getBaseX(), y, backStep);
		mt2.setMovingType(ACTION_NOSA_MOVE);
		mc.addMovingTarget(mt2);

		// 画像の切り替え
		MovingImageChanger changer2 = new MovingImageChanger();
		changer2.setDefault();
		mt2.putMovingImageChangerLast(changer2);

		return mc;
	}
}


