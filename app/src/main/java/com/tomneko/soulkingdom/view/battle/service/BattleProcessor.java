package com.tomneko.soulkingdom.view.battle.service;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.manager.EffectManager;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.moving.factory.common.BlinkFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.DamageTextFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.ShakeFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;

import java.util.List;

import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_WAIT;

/**
 * 戦闘処理
 * <p/>
 * Created by toyama on 2017/09/29.
 */
@Service
public class BattleProcessor {

	@Inject
	private DamageCalculator damageCalculator;

	@Inject
	private ShakeFactory shakeFactory;

	@Inject
	private DamageTextFactory damageTextFactory;

	@Inject
	private EffectManager effectManager;

	@Inject
	private BlinkFactory blinkFactory;

	@Inject
	private BattleConstantsHolder bch;

	/**
	 * 戦闘処理
	 *
	 * @param attacker
	 * @param targetList
	 * @param battleAction
	 */
	public void processBattle(BattleMember attacker, List<BattleMember> targetList, BattleAction battleAction) {
		for (BattleMember target : targetList) {
			processBattle(attacker, target, battleAction);
		}

	}

	/**
	 * 戦闘処理
	 *
	 * @param attacker
	 * @param target
	 * @param battleAction
	 */
	public void processBattle(BattleMember attacker, BattleMember target, BattleAction battleAction) {

		int damage = damageCalculator.damage(attacker, target, battleAction);

		// ターゲットのHPを減算
		target.getBattleMemberStatus().damage(damage);

		// 数字を表示
		MovingObject damageText = damageTextFactory.createDamageText(target.getMovingObject(), "" + damage, 8);
		effectManager.addMovingText(damageText);

		// HPが０なら戦闘不能
		if (target.getBattleMemberStatus().getHp() == 0) {

			// 点滅のコンビネーションを設定
			// 点滅以降の処理はBttleMemberManagerにて
			MovingCombination mc = blinkFactory.createBlink(target.getMovingObject(), bch.getStepMulti(2));
			target.getMovingObject().setMovingCombination(mc);
		}

		// ０でないならシェイク
		else {
			if (target instanceof BattleChara) {
				shakeForChara(target);
			} else {
				shakeForEnemy(target);
			}
		}
	}

	/**
	 * 敵のためのシェイク
	 *
	 * @param target
	 */
	private void shakeForEnemy(BattleMember target) {
		// 待ち状態のみシェイク
		if (target.getBattleMovingType() == ON_WAIT) {
			MovingObject mo = target.getMovingObject();
			MovingCombination mc = shakeFactory.createShakeForEnemy(mo);
			mo.setMovingCombination(mc);
		}
	}

	/**
	 * キャラのためのシェイク
	 *
	 * @param target
	 */
	private void shakeForChara(BattleMember target) {
		switch (target.getBattleMovingType()) {
			// SAがついてないアクション
			case ON_ACTION_NOSA:
				// 現地点でシェイクして元に戻す
				MovingObject mo1 = target.getMovingObject();
				MovingCombination mc1 = shakeFactory.createShakeForCharaCurrentAndReturn(mo1);
				mo1.setMovingCombination(mc1);
				break;

			// 待ち状態ならシェイク
			case ON_WAIT:

				// 基本ポジションでシェイク
				MovingObject mo2 = target.getMovingObject();
				MovingCombination mc2 = shakeFactory.createShakeForChara(mo2);
				mo2.setMovingCombination(mc2);
				break;

			default:
				break;
		}
	}


}
