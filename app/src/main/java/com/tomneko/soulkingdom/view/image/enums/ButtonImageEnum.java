package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * ボタンのイメージ
 *
 * Created by toyama on 2017/09/10.
 */
public enum ButtonImageEnum {

	// TODO 廃止
	BLUE_CIRCLE_L(R.drawable.button_blue_c_l),

	// TODO 廃止
	BLUE_CIRCLE_R(R.drawable.button_blue_c_r);

	private int id;

	private ButtonImageEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
