package com.tomneko.soulkingdom.view.battle.tactics.service;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.ObjectContainer;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.tactics.FactoryClassAndSteps;
import com.tomneko.soulkingdom.view.battle.tactics.Tactics;
import com.tomneko.soulkingdom.view.battle.tactics.TacticsPattern;
import com.tomneko.soulkingdom.view.moving.factory.enemy.EnemySkillFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * タクティクス実行のためのクラス
 * <p/>
 * Created by toyama on 2017/10/06.
 */
@Service
public class TacticsExecutor {

	@Inject
	private ObjectContainer objectContainer;

	private static Random random = null;

	/**
	 * タクティクスを実行する
	 *
	 * @param battleEnemy
	 */
	public void execute(BattleEnemy battleEnemy) {

		// 戦術がない。デバッグ用
		Tactics tactics = battleEnemy.getTactics();
		if (tactics == null) {
			return;
		}

		// 予約されている場合はそれを実行
		MovingCombination reserveMc = tactics.getReserveMovingCombinationList().poll();
		if (reserveMc != null) {
			battleEnemy.getMovingObject().setMovingCombination(reserveMc);
			return;
		}

		// 実行するかどうか
		boolean isExecute = isExecute(tactics);

		if (isExecute == false) {

			// インターバルを追加
			tactics.setInterval(tactics.getInterval() + 1);
			return;
		}

		// インターバルを０にする
		tactics.setInterval(0);

		// tacticsPatternのどれかを実行
		TacticsPattern executePattern = getExecutePattern(tactics);

		// パターンのインターバルを進める
		tactics.proceedInterval(executePattern);

		// スキルを実行
		executePattern(battleEnemy.getMovingObject(), tactics, executePattern);

	}

	/**
	 * パターンを実行
	 *
	 * @param mo
	 * @param executePattern
	 */
	private void executePattern(MovingObject mo, Tactics tactics, TacticsPattern executePattern) {

		for (FactoryClassAndSteps fcas : executePattern.getFactoryClassAndStepsList()) {

			Class clazz = fcas.getEnemySkillFactoryClass();
			EnemySkillFactory enemySkillFactory = (EnemySkillFactory) objectContainer.getObject(clazz);

			int[] steps = fcas.getSteps();

			MovingCombination mc = null;

			if (steps != null) {
				mc = enemySkillFactory.create(mo, steps);
			} else {
				mc = enemySkillFactory.create(mo);
			}

			tactics.addReserveMovingCombination(mc);
		}

		mo.setMovingCombination(tactics.getReserveMovingCombinationList().poll());
	}

	/**
	 * 実行するパータンを取得
	 *
	 * @param tactics
	 * @return
	 */
	private TacticsPattern getExecutePattern(Tactics tactics) {

		TacticsPattern executePattern = null;

		int max = 0;

		// インターバルがmaxになっているものを取得
		for (TacticsPattern pattern : tactics.getTacticsPatternList()) {

			int counter = tactics.getCounter(pattern);
			if (pattern.getExecuteMaxInterval() <= counter) {
				executePattern = pattern;
				break;
			}

			max += pattern.getExecutePercentage();
		}

		if (executePattern != null) {
			return executePattern;
		}

		int r = nextInt(max);

		// 実行すべきパターンを選択
		for (TacticsPattern pattern : tactics.getTacticsPatternList()) {

			int per = pattern.getExecutePercentage();

			if (r < per) {
				executePattern = pattern;
				break;
			}

			r = r - per;
		}

		// ありえないはず
		if (executePattern == null) {
			throw new RuntimeException("pattern should execute.");
		}

		return executePattern;
	}


	/**
	 * ランダムな0～maxの数値を取得
	 *
	 * @return
	 */
	private int nextInt(int max) {

		if (random == null) {
			random = new Random(System.currentTimeMillis());
		}

		return random.nextInt(max);
	}

	/**
	 * 実行すべきかどうか
	 *
	 * @param tactics
	 * @return
	 */
	private boolean isExecute(Tactics tactics) {

		// タクティクスを実行するかどうか
		int interval = tactics.getInterval();

		// 最小以下なら実行しない
		if (interval < tactics.getIntervalMin()) {
			return false;
		}

		// 最大以上なら実行
		if (tactics.getIntervalMax() <= interval) {
			return true;
		}

		float diff = tactics.getIntervalMax() - tactics.getIntervalMin();
		float perDiff = 100 - tactics.getIntervalMinPer();

		float point = perDiff / diff;
		float per = point * (float) (interval - tactics.getIntervalMin());
		per = per + (float) tactics.getIntervalMinPer();

		int r = nextInt(100);

		if ((float) r <= per) {
			return true;
		}

		return false;
	}
}
