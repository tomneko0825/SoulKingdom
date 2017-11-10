package com.tomneko.soulkingdom.view.battle.model;

import com.tomneko.soulkingdom.view.battle.drawer.enums.EnemyDrawingType;
import com.tomneko.soulkingdom.view.battle.strategy.BattleEnemyFactory;
import com.tomneko.soulkingdom.view.battle.strategy.BattleEnemyTeamStrategy;

import java.util.List;

/**
 * １度に出てくる敵のチーム
 * <p/>
 * Created by toyama on 2017/09/08.
 */
public class BattleEnemyTeam {

	public BattleEnemyTeam(BattleEnemyTeamStrategy strategy) {
		this.battleEnemyTeamStrategy = strategy;
	}

	/**
	 * invokerIdから取得
	 *
	 * @param id
	 * @return
	 */
	public BattleEnemy getById(String id) {

		if (battleEnemy1 != null) {
			if (battleEnemy1.getId().equals(id)) {
				return battleEnemy1;
			}
		}

		if (battleEnemy2 != null) {
			if (battleEnemy2.getId().equals(id)) {
				return battleEnemy2;
			}
		}
		return null;
	}

	// レベル
	private int level;

	// チームの戦略
	private BattleEnemyTeamStrategy battleEnemyTeamStrategy;

	private BattleEnemy battleEnemy1;

	private BattleEnemy battleEnemy2;

	public void setBattleEnemyTeamStrategy(BattleEnemyTeamStrategy battleEnemyTeamStrategy) {
		this.battleEnemyTeamStrategy = battleEnemyTeamStrategy;
	}

	public BattleEnemy getBattleEnemy1() {
		return battleEnemy1;
	}

	public void setBattleEnemy1(BattleEnemy battleEnemy1) {
		this.battleEnemy1 = battleEnemy1;
	}

	public BattleEnemy getBattleEnemy2() {
		return battleEnemy2;
	}

	public void setBattleEnemy2(BattleEnemy battleEnemy2) {
		this.battleEnemy2 = battleEnemy2;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 敵の描画方法を取得
	 *
	 * @return
	 */
	public EnemyDrawingType getEnemyDrawingType() {
		return battleEnemyTeamStrategy.getEnemyDrawingType();
	}

	/**
	 * 戦略を実行する
	 */
	public List<BattleEnemy> doStrategy(BattleEnemyFactory battleEnemyFactory) {

		// 戦略を実行する
		return battleEnemyTeamStrategy.doStrategy(this, battleEnemyFactory);
	}

	/**
	 * 終わりかどうか
	 *
	 * @return
	 */
	public boolean isFinish() {

		// １、２がいない、かつ、戦略が終了
		if (battleEnemy1 == null
				&& battleEnemy2 == null
				&& battleEnemyTeamStrategy.isFinish()) {

			return true;
		}

		return false;
	}

	/**
	 * 対象idの戦闘不能処理
	 *
	 * @param id
	 */
	public void defeated(String id) {

		if (battleEnemy1 != null) {
			if (battleEnemy1.getId().equals(id)) {
				battleEnemy1 = null;
			}
		}

		if (battleEnemy2 != null) {
			if (battleEnemy2.getId().equals(id)) {
				battleEnemy2 = null;
			}
		}
	}

}
