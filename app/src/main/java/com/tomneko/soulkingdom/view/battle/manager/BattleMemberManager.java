package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.BattleStageController;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.battle.model.FlashStatus;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleActionType;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.moving.model.enums.MovingType;
import com.tomneko.soulkingdom.view.moving.service.MovingProcessor;

import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_ACTION;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_ACTION_NOSA;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_AFTER_DODGE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_AFTER_GUARD;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_APPEAR_IN;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_BEFORE_DODGE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_BLINK_DEFEATED;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_CHANGE_BACK;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_CHANGE_GO;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_DEFEATED;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_DODGE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_GUARD;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_RESERVE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_SHAKE;
import static com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType.ON_WAIT;

/**
 * 敵と味方の共通のマネージャ
 * <p/>
 * Created by toyama on 2017/09/09.
 */
@Service
public class BattleMemberManager {

	@Inject
	private EffectManager effectManager;

	@Inject
	private MovingProcessor movingProcessor;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private PartyManager partyManager;

	@Inject
	private BattleStageController battleStageController;

	/**
	 * 戦闘ステータスを設定
	 *
	 * @param battleMember
	 */
	private void setBattleStateType(BattleMember battleMember) {
		MovingObject mo = battleMember.getMovingObject();
		MovingCombination mc = mo.getMovingCombination();

		// 移動中の場合
		if (mc != null) {
			MovingType mct = mc.getCurrentMovingType();
			switch (mct) {
				case ACTION_MOVE:
					battleMember.setBattleMovingType(ON_ACTION);
					break;
				case ACTION_NOSA_MOVE:
					battleMember.setBattleMovingType(ON_ACTION_NOSA);
					break;
				case GUARD_MOVE:
					battleMember.setBattleMovingType(ON_GUARD);
					break;
				case GUARD_AFTER_MOVE:
					battleMember.setBattleMovingType(ON_AFTER_GUARD);
					break;
				case DODGE_BEFORE_MOVE:
					battleMember.setBattleMovingType(ON_BEFORE_DODGE);
					break;
				case DODGE_MOVE:
					battleMember.setBattleMovingType(ON_DODGE);
					break;
				case DODGE_AFTER_MOVE:
					battleMember.setBattleMovingType(ON_AFTER_DODGE);
					break;
				case SHAKE_MOVE:
					battleMember.setBattleMovingType(ON_SHAKE);
					break;
				case CHANGE_GO_MOVE:
					battleMember.setBattleMovingType(ON_CHANGE_GO);
					break;
				case CHANGE_BACK_MOVE:
					battleMember.setBattleMovingType(ON_CHANGE_BACK);
					break;
				case APPEAR_IN_MOVE:
					battleMember.setBattleMovingType(ON_APPEAR_IN);
					break;
//				case APPEAR_MOVE:
//					battleMember.setBattleMovingType(ON_APPEAR_MOVE);
//					break;
				case BLINK_DEFEATED_MOVE:
					battleMember.setBattleMovingType(ON_BLINK_DEFEATED);
					break;
				case EFFECT_MOVE:
				default:
					// あり得ない
					throw new RuntimeException("invalid MovingType.EFFECT:" + mct.name());
			}
		}

		// 移動中でない
		else {

			// 点滅終わりは行動不能に
			if (battleMember.getBattleMovingType() == ON_BLINK_DEFEATED) {
				battleMember.setBattleMovingType(ON_DEFEATED);

				if (battleMember instanceof BattleChara) {
					battleStageController.defeatedChara(battleMember.getId());
				} else {
					battleStageController.defeatedEnemy(battleMember.getId());
				}
				// battleMember.setActive(false);
			}

			// 交代戻る終わりは控え
			else if (battleMember.getBattleMovingType() == ON_CHANGE_BACK) {
				battleMember.setBattleMovingType(ON_RESERVE);
			}

			// 戦闘不能、控えの場合はそのまま
			else if (battleMember.getBattleMovingType() == ON_DEFEATED
					|| battleMember.getBattleMovingType() == ON_RESERVE) {
			}

			// それ以外は待ち
			else {
				battleMember.setBattleMovingType(ON_WAIT);
			}

//			// それ以外でアクティブなら待ち
//			else if (battleMember.isOnBattle()) {
//				battleMember.setBattleMovingType(ON_WAIT);
//			}
//
//			// アクティブでなければ控え
//			else {
//				battleMember.setBattleMovingType(ON_RESERVE);
//			}
		}
	}

	/**
	 * 移動処理
	 *
	 * @param battleMember
	 */
	private void move(BattleMember battleMember) {

		// 先頭のターゲットを取得
		MovingObject mo = battleMember.getMovingObject();
		MovingCombination mc = mo.getMovingCombination();
		MovingTarget mt = mc.getMovingTargetList().getFirst();

		// エフェクトが発生してる場合はエフェクトマネージャに渡す
		MovingEffect me = mt.getCurrentMovingEffect();
		if (me != null) {

			// エフェクト位置の調整
			adjustEffectPosition(me);

			// ターゲット不明などで無効になっている場合がある
			if (me.isInvalid() == false) {
				effectManager.addMovingEffect(me);
			}
		}

		// 移動処理
		movingProcessor.move(mo);
	}

	/**
	 * エフェクト位置の調整
	 */
	private void adjustEffectPosition(MovingEffect me) {

		MovingObject mo = me.getMovingObject();

		switch (me.getMovingEffectTargetType()) {
			case ET_ASIS:
				// そのままなのでなにもしない
				break;
			case ET_BY_ID:
				String id = me.getMovingEffectTargetId();

				// 敵から探す
				BattleMember battleMember = enemyManager.getBattleEnemyGroup().getById(id);
				if (battleMember == null) {
					// キャラから探す
					battleMember = partyManager.getBattleParty().getById(id);
				}

				// みつからなかったら無効にする
				if (battleMember == null) {
					me.setInvalid(true);
					break;
				}

				MovingObject memberMo = battleMember.getMovingObject();
				copyPosition(memberMo, mo);

				break;
			case ET_ENEMY_TARGET:
				BattleEnemy battleEnemy = enemyManager.getTargetBattleEnemy();

				// ターゲットがいなかったら無効
				if (battleEnemy == null) {
					me.setInvalid(true);
					break;
				}

				MovingObject enemyMo = battleEnemy.getMovingObject();
				copyPosition(enemyMo, mo);
				break;
			case ET_ENEMY_ALL:
				// TODO ENEMY_ALL未実装
				break;
			case ET_CHARA:
				BattleChara battleChara = partyManager.getBattleParty().getCurrentBattleChara();

				// ターゲットがいなかったら無効
				if (battleChara == null) {
					me.setInvalid(true);
					break;
				}

				BattleAction ba = me.getBattleAction();

				// 回避不能攻撃はコピー
				if (ba != null && ba.getType() == BattleActionType.INEVITABLE_ATTACK) {
					copyPosition(battleChara.getMovingObject(), mo);
				}
				// 回避成功時はコピーしない
				else if (battleChara.getBattleMovingType() == ON_BEFORE_DODGE
						|| battleChara.getBattleMovingType() == ON_DODGE) {
				}
				// それ以外はコピー
				else {
					copyPosition(battleChara.getMovingObject(), mo);
				}
				break;
		}
	}


	/**
	 * 座標をコピーする
	 *
	 * @param src
	 * @param dst
	 */
	private void copyPosition(MovingObject src, MovingObject dst) {

		// 基本となる位置
		float rootDstX = dst.getX() + (dst.getTileWidth() / 2);
		float rootDstY = dst.getY() + (dst.getTileHeight() / 2);

		float baseDstX = rootDstX - (src.getTileWidth() / 2);
		float baseDstY = rootDstY - (src.getTileHeight() / 2);

		// 位置を更新
		dst.setX(baseDstX);
		dst.setY(baseDstY);

		for (MovingTarget mt : dst.getMovingCombination().getMovingTargetList()) {
			for (float[] points : mt.getPoints()) {
				// 基本位置との差を加算して更新
				float diffX = points[0] - baseDstX;
				float diffY = points[1] - baseDstY;

				points[0] = src.getX() + diffX;
				points[1] = src.getY() + diffY;
			}
		}
	}

	/**
	 * 進める
	 */
	public void proceedBattleMember(BattleMember battleMember) {

		// ステータスを設定
		setBattleStateType(battleMember);

		MovingObject mo = battleMember.getMovingObject();
		MovingCombination mc = mo.getMovingCombination();

		// 移動中の場合
		if (mc != null) {
			move(battleMember);
		}

		// ステータスのカウントアップと終了処理
		FlashStatus flashStatus = battleMember.getFlashStatus();
		flashStatus.countUp();
		flashStatus.finish();

	}
}
