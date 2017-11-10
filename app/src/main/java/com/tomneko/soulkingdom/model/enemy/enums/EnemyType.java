package com.tomneko.soulkingdom.model.enemy.enums;

import com.tomneko.soulkingdom.view.image.enums.EnemyImageEnum;

import static com.tomneko.soulkingdom.view.image.enums.EnemyImageEnum.WILD_WOLF_IMG;

/**
 * 敵の種類
 *
 * Created by toyama on 2017/10/17.
 */
public enum EnemyType {

	WILD_WOLF("ワイルドウルフ", WILD_WOLF_IMG, 150, 40, 40),

	;

	EnemyType(String name, EnemyImageEnum enemyImage, int hp, int atk, int def) {
		this.name = name;
		this.enemyImage = enemyImage;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
	}

	private String name;

	private EnemyImageEnum enemyImage;

	private int hp;

	private int atk;

	private int def;

	public String getName() {
		return name;
	}

	public EnemyImageEnum getEnemyImage() {
		return enemyImage;
	}

	public int getHp() {
		return hp;
	}

	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}
}
