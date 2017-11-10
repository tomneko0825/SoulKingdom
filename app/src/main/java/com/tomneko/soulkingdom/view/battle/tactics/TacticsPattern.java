package com.tomneko.soulkingdom.view.battle.tactics;

import com.tomneko.soulkingdom.view.battle.tactics.enums.TacticsPatternExecuteType;

import java.util.ArrayList;
import java.util.List;

/**
 * 戦術パターン
 * <p/>
 * Created by toyama on 2017/10/04.
 */
public class TacticsPattern {

	private TacticsPatternExecuteType executeType = TacticsPatternExecuteType.NORMAL;

	// 実行する確率
	private int executePercentage = 30;

	// 何回に一度は必ず実行するか
	private int executeMaxInterval = 5;

	private List<FactoryClassAndSteps> factoryClassAndStepsList = new ArrayList();

	public TacticsPattern(Class enemySkillFactoryClass) {
		FactoryClassAndSteps fcas = new FactoryClassAndSteps(enemySkillFactoryClass);
		this.factoryClassAndStepsList.add(fcas);
	}

	public TacticsPattern(Class enemySkillFactoryClass, int[] steps) {
		FactoryClassAndSteps fcas = new FactoryClassAndSteps(enemySkillFactoryClass, steps);
		this.factoryClassAndStepsList.add(fcas);
	}

	public TacticsPatternExecuteType getExecuteType() {
		return executeType;
	}

	public int getExecutePercentage() {
		return executePercentage;
	}

	public void setExecutePercentage(int executePercentage) {
		this.executePercentage = executePercentage;
	}

	public int getExecuteMaxInterval() {
		return executeMaxInterval;
	}

	public void setExecuteMaxInterval(int executeMaxInterval) {
		this.executeMaxInterval = executeMaxInterval;
	}

	public void addFactoryClassAndSteps(Class enemySkillFactoryClass) {
		FactoryClassAndSteps fcas = new FactoryClassAndSteps(enemySkillFactoryClass);
		this.factoryClassAndStepsList.add(fcas);
	}

	public void addFactoryClassAndSteps(Class enemySkillFactoryClass, int[] steps) {
		FactoryClassAndSteps fcas = new FactoryClassAndSteps(enemySkillFactoryClass, steps);
		this.factoryClassAndStepsList.add(fcas);
	}

	public List<FactoryClassAndSteps> getFactoryClassAndStepsList() {
		return factoryClassAndStepsList;
	}
}
