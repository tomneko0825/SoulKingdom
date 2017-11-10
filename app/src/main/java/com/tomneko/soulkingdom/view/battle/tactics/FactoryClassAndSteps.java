package com.tomneko.soulkingdom.view.battle.tactics;

/**
 * ファクトリとステップの組み合わせ
 *
 * Created by toyama on 2017/10/07.
 */
public class FactoryClassAndSteps {

	private Class enemySkillFactoryClass;

	private int[] steps;

	public FactoryClassAndSteps(Class enemySkillFactoryClass) {
		this.enemySkillFactoryClass = enemySkillFactoryClass;
	}

	public FactoryClassAndSteps(Class enemySkillFactoryClass, int[] steps) {
		this.enemySkillFactoryClass = enemySkillFactoryClass;
		this.steps = steps;
	}

	public Class getEnemySkillFactoryClass() {
		return enemySkillFactoryClass;
	}

	public int[] getSteps() {
		return steps;
	}
}
