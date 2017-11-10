package com.tomneko.soulkingdom.view.moving.model;

import com.tomneko.soulkingdom.view.moving.model.enums.MovingType;

import java.util.HashMap;
import java.util.Map;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.ACTION_MOVE;

/**
 * DrawingObjectの移動目標
 * <p>
 * Created by toyama on 2017/08/17.
 */
public class MovingTarget {

	// 何を意味するか
	private MovingType type = ACTION_MOVE;

	// 移動元
	private float fromX;
	private float fromY;

	// 移動先
	private float toX;
	private float toY;

	// 放物線の高さ
	private float topY;

	// 移動パターン
	private MovingPattern movingPattern;

	// いくつでたどり着くか
	private int steps;

	// 現在の段階
	private int currentSteps;

	// 移動座標
	private float[][] points;

	// ステップをキーとしたエフェクト
	private Map<Integer, MovingEffect> movingEffectMap = new HashMap();

	// ステップをキーとした画像のチェンジャー
	private Map<Integer, MovingImageChanger> movingImageChangerMap = new HashMap();

	public void setMovingType(MovingType type) {
		this.type = type;
	}

	public MovingType getMovingType() {
		return type;
	}

	public float getFromX() {
		return fromX;
	}

	public void setFromX(float fromX) {
		this.fromX = fromX;
	}

	public float getFromY() {
		return fromY;
	}

	public void setFromY(float fromY) {
		this.fromY = fromY;
	}

	public float getToX() {
		return toX;
	}

	public void setToX(float toX) {
		this.toX = toX;
	}

	public float getToY() {
		return toY;
	}

	public void setToY(float toY) {
		this.toY = toY;
	}

	public MovingPattern getMovingPattern() {
		return movingPattern;
	}

	public void setMovingPattern(MovingPattern movingPattern) {
		this.movingPattern = movingPattern;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public float[][] getPoints() {
		return points;
	}

	public void setPoints(float[][] points) {
		this.points = points;
	}

	public float getTopY() {
		return topY;
	}

	public void setTopY(float topY) {
		this.topY = topY;
	}

	public int getCurrentSteps() {
		return currentSteps;
	}

	public void setCurrentSteps(int currentSteps) {
		this.currentSteps = currentSteps;
	}

	/**
	 * 指定されたステップにエフェクトを設定
	 *
	 * @param step
	 * @param movingEffect
	 */
	public void putMovingEffect(int step, MovingEffect movingEffect) {
		this.movingEffectMap.put(step, movingEffect);
	}

	/**
	 * 最初にエフェクトを設定
	 *
	 * @param movingEffect
	 */
	public void putMovingEffectFirst(MovingEffect movingEffect) {
		this.movingEffectMap.put(1, movingEffect);
	}

	/**
	 * 最後にエフェクトを設定
	 *
	 * @param movingEffect
	 */
	public void putMovingEffectLast(MovingEffect movingEffect) {
		this.movingEffectMap.put(steps - 1, movingEffect);
	}

	/**
	 * 現在のエフェクトを取得
	 *
	 * @return
	 */
	public MovingEffect getCurrentMovingEffect() {
		return this.movingEffectMap.get(currentSteps);
	}

	/**
	 * 指定されたステップにチェンジャーを設定
	 *
	 * @param targetStep
	 * @param movingImageChanger
	 */
	public void putMovingImageChanger(int targetStep, MovingImageChanger movingImageChanger) {
		this.movingImageChangerMap.put(targetStep, movingImageChanger);
	}

	/**
	 * 最初にチェンジャーを設定
	 *
	 * @param movingImageChanger
	 */
	public void putMovingImageChangerFirst(MovingImageChanger movingImageChanger) {
		this.movingImageChangerMap.put(1, movingImageChanger);
	}

	/**
	 * 最後にチェンジャーを設定
	 *
	 * @param movingImageChanger
	 */
	public void putMovingImageChangerLast(MovingImageChanger movingImageChanger) {
		this.movingImageChangerMap.put(steps, movingImageChanger);
	}

	/**
	 * 現在のチェンジャーを取得
	 *
	 * @return
	 */
	public MovingImageChanger getCurrentMovingImageChanger() {
		return this.movingImageChangerMap.get(currentSteps);
	}

	/**
	 * 次の動作へ
	 *
	 * @return
	 */
	public float[] next() {

		// 次のステップへ
		currentSteps++;

		if (points.length <= currentSteps) {
			currentSteps = points.length - 1;
		}

		return points[currentSteps];
	}

	/**
	 * 終わりかどうか
	 *
	 * @return
	 */
	public boolean isFinish() {
		return steps == currentSteps;
	}

	/**
	 * 強制終了させる
	 */
	public void forcedFinish() {
		this.currentSteps = steps;
	}
}
