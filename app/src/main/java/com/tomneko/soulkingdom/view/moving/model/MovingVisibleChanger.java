package com.tomneko.soulkingdom.view.moving.model;

/**
 * 移動しながら表示非表示を変える
 *
 * Created by toyama on 2017/10/03.
 */
public class MovingVisibleChanger extends MovingImageChanger{

	public MovingVisibleChanger(boolean invisible) {
		this.invisible = invisible;
		this.tileChange = false;
	}
}
