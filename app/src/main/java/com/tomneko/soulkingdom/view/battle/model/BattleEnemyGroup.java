package com.tomneko.soulkingdom.view.battle.model;

import java.util.LinkedList;

/**
 * １戦闘ステージの敵の集団
 * <p/>
 * Created by toyama on 2017/09/08.
 */
public class BattleEnemyGroup {

	private LinkedList<BattleEnemyTeam> battleEnemyTeamList = new LinkedList();

	public void addBattleEnemyTeam(BattleEnemyTeam battleEnemyTeam) {
		this.battleEnemyTeamList.add(battleEnemyTeam);
	}

	/**
	 * 現在のチームを取得
	 *
	 * @return
	 */
	public BattleEnemyTeam getCurrentBattleEnemyTeam() {
		return this.battleEnemyTeamList.getFirst();
	}

	/**
	 * invokerIdから取得
	 *
	 * @param id
	 * @return
	 */
	public BattleEnemy getById(String id) {

		BattleEnemyTeam enemyTeam = getCurrentBattleEnemyTeam();
		BattleEnemy battleEnemy = enemyTeam.getById(id);
		if (battleEnemy != null) {
			return battleEnemy;
		}

		return null;
	}

	/**
	 * 次のチームへ
	 */
	public void nextTeam() {

		if (0 < battleEnemyTeamList.size()) {
			battleEnemyTeamList.poll();
		}

		return;
	}

}
