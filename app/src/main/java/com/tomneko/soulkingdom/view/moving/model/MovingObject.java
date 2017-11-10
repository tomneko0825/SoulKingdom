package com.tomneko.soulkingdom.view.moving.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * 描画されるオブジェクト
 * <p/>
 * Created by toyama on 2017/08/16.
 */
public class MovingObject {

	// 起動者のID
	private String invokerId;

	// 基準となる地点x
	private float baseX;

	// 基準となる地点y
	private float baseY;

	// 現在位置x
	private float x;

	// 現在位置y
	private float y;

	// 画像の代わりに表示するテキスト。画像よりも優先される
	private MovingObjectText movingObjectText;

	// 画像
	private Bitmap image;

	// タイルの枚数x
	private int tileCountX = 1;

	// タイルの枚数y
	private int tileCountY = 1;

	// 基本のタイルx
	private int tileBaseX = 0;

	// 現在のタイルx
	private int tileX = 0;

	// 基本のタイルy
	private int tileBaseY = 1;

	// 現在のタイルy
	private int tileY = 0;

	// 非表示
	private boolean invisible = false;

	private MovingCombination movingCombination;

	// 終了時の実行
	private MovingFinishExecutor movingFinishExecutor;

	public MovingObject(String invokerId) {
		this.invokerId = invokerId;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Bitmap getImage() {
		return image;
	}

	public MovingFinishExecutor getMovingFinishExecutor() {
		return movingFinishExecutor;
	}

	public void setMovingFinishExecutor(MovingFinishExecutor movingFinishExecutor) {
		this.movingFinishExecutor = movingFinishExecutor;
	}

	public float getBaseX() {
		return baseX;
	}

	public void setBaseX(float baseX) {
		this.baseX = baseX;
	}

	public float getBaseY() {
		return baseY;
	}

	public void setBaseY(float baseY) {
		this.baseY = baseY;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getInvokerId() {
		return invokerId;
	}

	public int getTileCountX() {
		return tileCountX;
	}

	public void setTileCountX(int tileCountX) {
		this.tileCountX = tileCountX;
	}

	public int getTileCountY() {
		return tileCountY;
	}

	public void setTileCountY(int tileCountY) {
		this.tileCountY = tileCountY;
	}

	public int getTileBaseX() {
		return tileBaseX;
	}

	public void setTileBaseX(int tileBaseX) {
		this.tileBaseX = tileBaseX;
	}

	public int getTileBaseY() {
		return tileBaseY;
	}

	public void setTileBaseY(int tileBaseY) {
		this.tileBaseY = tileBaseY;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/**
	 * タイルの範囲を取得
	 *
	 * @return
	 */
	public Rect getTileSrc() {
		return getTileSrc(this.tileX, this.tileY);
	}

	/**
	 * タイルの範囲を取得
	 *
	 * @param tileX
	 * @param tileY
	 * @return
	 */
	public Rect getTileSrc(int tileX, int tileY) {

		float tileWidth = getTileWidth();
		float tileHeight = getTileHeight();

		int left = (int) tileWidth * tileX;
		int right = left + (int) tileWidth;
		int top = (int) tileHeight * tileY;
		int bottom = top + (int) tileHeight;

		Rect r = new Rect(left, top, right, bottom);
		return r;
	}

	public float getTileWidth() {
		return image.getWidth() / tileCountX;
	}

	public float getTileHeight() {
		return image.getHeight() / tileCountY;
	}

	public MovingCombination getMovingCombination() {
		return movingCombination;
	}

	public void setMovingCombination(MovingCombination movingCombination) {
		this.movingCombination = movingCombination;
	}

	public MovingObjectText getMovingObjectText() {
		return movingObjectText;
	}

	public void setMovingObjectText(MovingObjectText movingObjectText) {
		this.movingObjectText = movingObjectText;
	}

	@Override
	public String toString() {
		String str = "mo{";
		str += "invokerId='" + invokerId + '\'';
		if (movingCombination != null) {
			str += ", " + movingCombination.toString();
		}
		str += '}';
		return str;
	}
}
