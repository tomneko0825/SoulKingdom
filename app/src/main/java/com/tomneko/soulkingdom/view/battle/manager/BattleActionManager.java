package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.battle.service.BattleProcessor;
import com.tomneko.soulkingdom.view.moving.factory.common.ShakeFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;

import java.util.ArrayList;
import java.util.List;

import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_CHARA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionTargetType.TARGET_TYPE_ENEMY_ALL;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType.INEVITABLE_ATTACK;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_BEFORE_DODGE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_DODGE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_GUARD;
import static com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType.DODGE_FLUSH;
import static com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType.GUARD_FLUSH;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.C_DODGE_FLASH;
import static com.tomneko.soulkingdom.view.image.enums.EffectImageEnum.C_GUARD_FLASH;

/**
 * 戦闘の処理
 * <p/>
 * Created by toyama on 2017/09/09.
 */
@Service
public class BattleActionManager {

	@Inject
	private DebugManager debugManager;

	@Inject
	private PartyManager partyManager;

	@Inject
	private EffectManager effectmanager;

	@Inject
	private ShakeFactory shakeFactory;

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private BattleProcessor battleProcessor;

	@Inject
	private BattleConstantsHolder bch;

	private List<BattleAction> battleActionList = new ArrayList();

	public List<BattleAction> getBattleActionList() {
		return battleActionList;
	}

	public void addBattleAction(BattleAction battleAction) {
		this.battleActionList.add(battleAction);
	}

	/**
	 * 戦闘処理
	 */
	public void proceedBattleAction() {

		for (BattleAction ba : battleActionList) {
			String message = ba.getMessage();
			debugManager.addMessage(message);

			switch (ba.getType()) {
				case ATTACK:
				case INEVITABLE_ATTACK:

					// キャラへの攻撃
					if (ba.getTo() == TARGET_TYPE_CHARA) {
						attackToChara(ba);
					}

					// 敵への攻撃
					else if (ba.getTo() == TARGET_TYPE_ENEMY
							|| ba.getTo() == TARGET_TYPE_ENEMY_ALL) {
						attackToEnemy(ba);
					}
					break;
			}
		}

		// 削除
		battleActionList.clear();
	}

	/**
	 * 敵への攻撃
	 *
	 * @param ba
	 */
	private void attackToEnemy(BattleAction ba) {

		// TODO とりあえず
		BattleEnemy battleEnemy = enemyManager.getTargetBattleEnemy();

		if (battleEnemy == null) {
			return;
		}

		// 戦闘処理
		BattleChara battleChara = partyManager.getBattleParty().getById(ba.getInvorkerId());
		battleProcessor.processBattle(battleChara, battleEnemy, ba);
	}

	/**
	 * キャラへの攻撃
	 *
	 * @param ba
	 */
	private void attackToChara(BattleAction ba) {

		BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

		if (battleChara == null) {
			return;
		}

		// ガード中
		if (battleChara.getBattleMovingType() == ON_GUARD) {

			// キャラにフラッシュガードを追加
			// TODO ガード期間
			int guard = bch.getStepMulti(4);
			battleChara.getFlashStatus().putFlashStatus(GUARD_FLUSH, guard);

			// エフェクト発生
			MovingEffect effect = simpleEffectFactory.createStopEffect(battleChara.getMovingObject(), bch.getStep(2), C_GUARD_FLASH);
			effectmanager.addMovingEffect(effect);
		}

		// 回避前
		else if (battleChara.getBattleMovingType() == ON_BEFORE_DODGE) {
			// キャラにフラッシュ回避を追加
			// TODO 回避期間
			int dodge = bch.getStepMulti(6);
			battleChara.getFlashStatus().putFlashStatus(DODGE_FLUSH, dodge);

			// エフェクト発生
			MovingEffect effect = simpleEffectFactory.createStopEffect(battleChara.getMovingObject(), bch.getStep(2), C_DODGE_FLASH);
			effectmanager.addMovingEffect(effect);
		}

		// 回避中は回避不能攻撃以外はなにもしない
		else if (battleChara.getBattleMovingType() == ON_DODGE
				&& ba.getType() != INEVITABLE_ATTACK) {
		}

		// それ以外は攻撃判定
		else {
			BattleEnemy battleEnemy = enemyManager.getBattleEnemyGroup().getById(ba.getInvorkerId());
			battleProcessor.processBattle(battleEnemy, battleChara, ba);
		}
	}
}
