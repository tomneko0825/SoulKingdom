package com.tomneko.soulkingdom.view.moving.model;

/**
 * MovingObjectの移動タイプ
 *
 * Created by toyama on 2017/08/18.
 */
public enum MovingPattern {

	// 待機
	STAY,

	// 直線
	STRAIGHT,

	// 放物線
	CURVE,

	// 上昇放物線
	CURVE_UP,

	// 上昇放物線の逆
	CURVE_UP_REVERSE,

	// 下降放物線
	CURVE_DOWN,

	// 下降放物線の逆
	CURVE_DOWN_REVERSE,

}
