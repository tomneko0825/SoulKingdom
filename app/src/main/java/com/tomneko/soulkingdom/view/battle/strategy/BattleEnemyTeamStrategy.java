package com.tomneko.soulkingdom.view.battle.strategy;

import com.tomneko.soulkingdom.view.battle.drawer.enums.EnemyDrawingType;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyTeam;

import java.util.List;

/**
 * 敵チームの戦略
 * <p/>
 * Created by toyama on 2017/10/12.
 */
public interface BattleEnemyTeamStrategy {

	/**
	 * 敵の描画方法
	 *
	 * @return
	 */
	EnemyDrawingType getEnemyDrawingType();

	/**
	 * 戦略を実行する
	 *
	 * @param battleEnemyTeam
	 * @param battleEnemyFactory
	 */
	List<BattleEnemy> doStrategy(BattleEnemyTeam battleEnemyTeam, BattleEnemyFactory battleEnemyFactory);

	/**
	 * 終了判定
	 *
	 * @return
	 */
	boolean isFinish();

}
