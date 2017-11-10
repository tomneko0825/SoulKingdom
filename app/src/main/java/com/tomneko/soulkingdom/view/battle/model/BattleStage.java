package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.model.enums.BattleStageStateType;

/**
 * 戦場
 *
 * Created by toyama on 2017/09/07.
 */
public class BattleStage {

	// 戦場の状態
	// TODO 戦闘開始処理未作成
	// private BattleStageStateType battleStageStateType = BattleStageStateType.BEFORE;
	private BattleStageStateType battleStageStateType = BattleStageStateType.BATTLE;

	// ウェーブ数
	private int maxWave = 5;

	// 現在のウェーブ
	private int currentWave = 1;

	public int getMaxWave() {
		return maxWave;
	}

	public void setMaxWave(int maxWave) {
		this.maxWave = maxWave;
	}

	public int getCurrentWave() {
		return currentWave;
	}

	public void nextWave() {
		this.currentWave++;;
	}

	public BattleStageStateType getBattleStageStateType() {
		return battleStageStateType;
	}

	public void setBattleStageStateType(BattleStageStateType battleStageStateType) {
		this.battleStageStateType = battleStageStateType;
	}

	/**
	 * 最後かどうか
	 */
	public boolean isLast() {
		if (maxWave == currentWave) {
			return true;
		}
		return false;
	}
}
