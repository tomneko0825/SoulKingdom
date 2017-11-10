package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;

/**
 * キャラの戦闘データ
 * <p/>
 * Created by toyama on 2017/09/07.
 */
public class BattleChara extends BattleMember {

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public boolean isOnBattle() {

		if (getBattleMovingType() == BattleMovingType.ON_RESERVE
				|| getBattleMovingType() == BattleMovingType.ON_DEFEATED
				|| getBattleMovingType() == BattleMovingType.ON_BLINK_DEFEATED
				|| getBattleMovingType() == BattleMovingType.ON_CHANGE_BACK) {
			return false;
		}

		return true;
	}
}
