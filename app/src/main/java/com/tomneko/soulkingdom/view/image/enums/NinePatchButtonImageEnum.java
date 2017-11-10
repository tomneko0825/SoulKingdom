package com.tomneko.soulkingdom.view.image.enums;

import com.tomneko.soulkingdom.R;

/**
 * 9-patchボタンのイメージ
 *
 * Created by toyama on 2017/09/10.
 */
public enum NinePatchButtonImageEnum {

	// 青、円、左
	BLUE_CIRCLE_L(R.drawable.button_blue_c_l),

	// 青、円、右
	BLUE_CIRCLE_R(R.drawable.button_blue_c_r),

	// ステータス、青
	STATUS_BLUE(R.drawable.button_status_blue),

	// ステータス、オレンジ
	STATUS_ORANGE(R.drawable.button_status_orange),

	// ステータス、シルバー
	STATUS_SILVER(R.drawable.button_status_silver),

	// ステータス、黄
	STATUS_YELLOW(R.drawable.button_status_yellow),

	// ステータス、シアン
	STATUS_CYAN(R.drawable.button_status_cyan),;

	private int id;

	private NinePatchButtonImageEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
