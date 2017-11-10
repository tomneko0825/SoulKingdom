package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.battle.tactics.Tactics;

/**
 * 敵の戦闘データ
 * <p/>
 * Created by toyama on 2017/09/07.
 */
public class BattleEnemy extends BattleMember {

	private Tactics tactics;

	public Tactics getTactics() {
		return tactics;
	}

	public void setTactics(Tactics tactics) {
		this.tactics = tactics;
	}

	@Override
	public boolean isVisible() {

		if (getBattleMovingType() == BattleMovingType.ON_RESERVE
				|| getBattleMovingType() == BattleMovingType.ON_DEFEATED) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isOnBattle() {

		if (getBattleMovingType() == BattleMovingType.ON_RESERVE
				|| getBattleMovingType() == BattleMovingType.ON_DEFEATED
				|| getBattleMovingType() == BattleMovingType.ON_BLINK_DEFEATED
				|| getBattleMovingType() == BattleMovingType.ON_APPEAR_IN) {
			return false;
		}

		return true;
	}
}
