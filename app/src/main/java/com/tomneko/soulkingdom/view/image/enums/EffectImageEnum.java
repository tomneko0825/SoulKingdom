package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * エフェクトのイメージ
 * <p/>
 * Created by toyama on 2017/09/10.
 */
public enum EffectImageEnum {

	// 噛みつき
	E_BITE(R.drawable.effect_e_001, 2, 1),

	// 爪
	E_CRAW(R.drawable.effect_e_002, 2, 1),

	// ダッシュ
	E_DASH(R.drawable.effect_e_003, 4, 1),

	// ガード小
	C_GUARD_S(R.drawable.effect_c_001),

	// ガード成功
	C_GUARD_FLASH(R.drawable.effect_c_002),

	// 回避小
	C_DODGE_S(R.drawable.effect_c_003, 2, 1),

	// 回避成功
	C_DODGE_FLASH(R.drawable.effect_c_004, 2, 1),

	// スラッシュ
	C_SLASH(R.drawable.effect_c_007, 4, 1),

	// スラッシュ強
	C_SLASHS(R.drawable.effect_c_005, 4, 1),

	// スラッシュ縦
	C_SLASHV(R.drawable.effect_c_006, 4, 1),

	// 終端用dummy
	DUMMY(R.drawable.effect_e_001);

	private int id;

	private int tileCountX = 1;

	private int tileCountY = 1;

	private EffectImageEnum(int id) {
		this.id = id;
	}

	private EffectImageEnum(int id, int tileCountX, int tileCountY) {
		this.id = id;
		this.tileCountX = tileCountX;
		this.tileCountY = tileCountY;
	}

	public int getId() {
		return id;
	}

	public int getTileCountX() {
		return tileCountX;
	}

	public int getTileCountY() {
		return tileCountY;
	}

	public int getTileCount() {
		return tileCountX * tileCountY;
	}
}
