package com.tomneko.soulkingdom.view.moving.model;

/**
 * 移動しながらイメージを変える
 * <p/>
 * Created by toyama on 2017/09/11.
 */
public class MovingImageChanger {

	// タイルを変更
	protected boolean tileChange = true;

	// 非表示、この変更はMovingVisibleChangerにてすること。
	protected boolean invisible = false;

	private int tileX = 0;

	private int tileY = 0;

	// デフォルトに設定する
	private boolean setDefault = false;

	public MovingImageChanger(int tileX, int tileY) {
		this.tileX = tileX;
		this.tileY = tileY;
	}

	public MovingImageChanger() {
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public boolean isSetDefault() {
		return setDefault;
	}

	public void setDefault() {
		this.setDefault = true;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isTileChange() {
		return tileChange;
	}
}
