package com.tomneko.soulkingdom.view.battle.strategy;

import com.tomneko.soulkingdom.model.enemy.enums.EnemyType;
import com.tomneko.soulkingdom.view.battle.drawer.enums.EnemyDrawingType;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyTeam;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleEnemyPositionType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 一列に順番に出現
 * <p/>
 * Created by toyama on 2017/10/14.
 */
public class SerialStrategy implements BattleEnemyTeamStrategy {

	private LinkedList<EnemyType> enemyList = new LinkedList();

	public void addEnemy(EnemyType enemyType) {
		this.enemyList.add(enemyType);
	}

	@Override
	public EnemyDrawingType getEnemyDrawingType() {
		return EnemyDrawingType.SINGLE;
	}

	@Override
	public List<BattleEnemy> doStrategy(BattleEnemyTeam battleEnemyTeam, BattleEnemyFactory battleEnemyFactory) {

		List<BattleEnemy> list = new ArrayList();

		// 終わっていたら実行しない
		if (isFinish()) {
			return list;
		}

		// １にいなかったら追加
		if (battleEnemyTeam.getBattleEnemy1() == null) {

			EnemyType enemyType = enemyList.poll();

			BattleEnemy battleEnemy = battleEnemyFactory.create(enemyType, battleEnemyTeam.getLevel(), BattleEnemyPositionType.SINGLE_POSITION);

			battleEnemyTeam.setBattleEnemy1(battleEnemy);

			list.add(battleEnemy);
		}

		return list;
	}

	@Override
	public boolean isFinish() {
		if (enemyList.size() == 0) {
			return true;
		}
		return false;
	}
}
