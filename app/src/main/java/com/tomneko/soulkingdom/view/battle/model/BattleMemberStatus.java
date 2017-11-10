package com.tomneko.soulkingdom.view.battle.model;

/**
 * 戦闘員のステータス
 * <p/>
 * Created by toyama on 2017/09/20.
 */
public class BattleMemberStatus {

	private int hp;

	private int maxHp;

	private int originalAtk;

	private int atk;

	public BattleMemberStatus() {
		// TODO とりあえず
		this.maxHp = 150;
		this.hp = 150;
		this.originalAtk = 40;
		this.atk = 40;
	}

	public BattleMemberStatus(int hp, int atk, int def) {
		this.maxHp = hp;
		this.hp = hp;
		this.originalAtk = atk;
		this.atk = atk;
	}

	public int getOriginalAtk() {
		return originalAtk;
	}

	public void setOriginalAtk(int originalAtk) {
		this.originalAtk = originalAtk;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	/**
	 * 指定されたHPのダメージ
	 */
	public void damage(int damageHp) {

		hp -= damageHp;

		if (hp < 0) {
			hp = 0;
		}
	}

	/**
	 * HPの割合
	 *
	 * @return
	 */
	public float getHpPercentage() {
		return (float) hp / (float) maxHp;
	}

}
