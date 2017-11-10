package com.tomneko.soulkingdom.view.moving;

import com.tomneko.soulkingdom.view.moving.model.MovingPattern;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;

/**
 * MovingTargetのFactory
 * <p/>
 * Created by toyama on 2017/08/25.
 */
public class MovingTargetFactory {

	/**
	 * 待機を作成
	 *
	 * @param currentX
	 * @param currentY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createStay(float currentX, float currentY, int steps) {
		MovingTarget mt = new MovingTarget();
		mt.setFromX(currentX);
		mt.setFromY(currentY);
		mt.setToX(currentX);
		mt.setToY(currentY);
		mt.setSteps(steps);

		float[][] points = new float[steps + 1][2];

		for (int i = 0; i < points.length; i++) {
			points[i][0] = currentX;
			points[i][1] = currentY;
		}

		mt.setPoints(points);
		mt.setMovingPattern(MovingPattern.STAY);

		return mt;
	}

	/**
	 * 直線を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createStraight(float fromX, float fromY, float toX, float toY, int steps) {
		MovingTarget mt = new MovingTarget();
		mt.setFromX(fromX);
		mt.setFromY(fromY);
		mt.setToX(toX);
		mt.setToY(toY);
		mt.setSteps(steps);

		float[][] points = createStraight(toX - fromX, toY - fromY, steps);

		for (int i = 0; i < points.length; i++) {
			points[i][0] = points[i][0] + fromX;
			points[i][1] = points[i][1] + fromY;
		}

		mt.setPoints(points);
		mt.setMovingPattern(MovingPattern.STRAIGHT);

		return mt;
	}

	/**
	 * 放物線を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createCurve(float fromX, float fromY, float toX, float topY, int steps) {
		return createCurve(fromX, fromY, toX, topY, steps, MovingPattern.CURVE);
	}

	/**
	 * 上昇放物線を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createCurveUp(float fromX, float fromY, float toX, float topY, int steps) {
		return createCurve(fromX, fromY, toX, topY, steps, MovingPattern.CURVE_UP);
	}

	/**
	 * 上昇放物線の逆を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createCurveUpReverse(float fromX, float fromY, float toX, float topY, int steps) {
		return createCurve(fromX, fromY, toX, topY, steps, MovingPattern.CURVE_UP_REVERSE);
	}

	/**
	 * 下降放物線を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createCurveDown(float fromX, float fromY, float toX, float topY, int steps) {
		return createCurve(fromX, fromY, toX, topY, steps, MovingPattern.CURVE_DOWN);
	}

	/**
	 * 下降放物線の逆を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @return
	 */
	public static MovingTarget createCurveDownReverse(float fromX, float fromY, float toX, float topY, int steps) {
		return createCurve(fromX, fromY, toX, topY, steps, MovingPattern.CURVE_DOWN_REVERSE);
	}

	/**
	 * 各種放物線を作成
	 *
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param topY
	 * @param steps
	 * @param pattern
	 * @return
	 */
	private static MovingTarget createCurve(float fromX, float fromY, float toX, float topY, int steps, MovingPattern pattern) {
		MovingTarget mt = new MovingTarget();
		mt.setFromX(fromX);
		mt.setFromY(fromY);
		mt.setToX(toX);

		if (pattern == MovingPattern.CURVE) {
			mt.setToY(fromY);
		} else {
			mt.setToY(topY);
		}
		mt.setSteps(steps);

		float[][] points = null;

		switch (pattern) {
			case CURVE:
				points = createCurve(toX - fromX, -topY, steps);
				break;
			case CURVE_UP:
				points = createCurveUp(toX - fromX, -topY, steps);
				break;
			case CURVE_UP_REVERSE:
				points = createCurveUpReverse(toX - fromX, -topY, steps);
				break;
			case CURVE_DOWN:
				points = createCurveDown(toX - fromX, -topY, steps);
				break;
			case CURVE_DOWN_REVERSE:
				points = createCurveDownReverse(toX - fromX, -topY, steps);
				break;
		}

		for (int i = 0; i < points.length; i++) {
			points[i][0] = points[i][0] + fromX;
			points[i][1] = points[i][1] + fromY;
		}

		mt.setPoints(points);

		mt.setMovingPattern(pattern);

		return mt;
	}

	/**
	 * 直線を作成
	 *
	 * @param x
	 * @param y
	 * @param steps
	 * @return
	 */
	private static float[][] createStraight(float x, float y, int steps) {

		float scaleX = calculateScale(x, steps);
		float scaleY = calculateScale(y, steps);

		float[][] points = new float[steps + 1][2];

		for (int i = 1; i < steps + 1; i++) {
			points[i][0] = scaleX * i;
			points[i][1] = scaleY * i;
		}

		// 最後にx、yを詰める
		points[steps][0] = x;
		points[steps][1] = y;

		return points;
	}

	/**
	 * １ステップあたりの移動距離を計算
	 *
	 * @param x
	 * @param steps
	 * @return
	 */
	private static float calculateScale(float x, int steps) {
		return x / steps;
	}

	/**
	 * 下降放物線を作る
	 *
	 * @param x     進む距離
	 * @param topY  Yの最大値
	 * @param steps Xに進む回数
	 * @return
	 */
	private static float[][] createCurveDown(float x, float topY, int steps) {

		// 上昇放物線を作ってから変換
		float[][] upPoints = createCurveUp(x, topY, steps);

		return createReverse(upPoints);
	}

	/**
	 * 曲線の上昇下降を反転させる
	 *
	 * @return
	 */
	private static float[][] createReverse(float[][] points) {

		float topY = points[points.length - 1][1];

		// 配列を作る
		float[][] revPoints = new float[points.length][2];

		int reverse = points.length - 1;
		for (int i = 1; i < points.length; i++) {
			revPoints[i][0] = points[i][0];

			// 最後は終点
			if (i == points.length - 1) {
				revPoints[i][1] = -topY;
			} else {
				revPoints[i][1] = points[reverse - 1][1] - topY;
			}
			reverse--;
		}

		return revPoints;

	}

	/**
	 * 反下降放物線を作る
	 *
	 * @param x     進む距離
	 * @param topY  Yの最大値
	 * @param steps Xに進む回数
	 * @return
	 */
	private static float[][] createCurveDownReverse(float x, float topY, int steps) {

		// 上昇放物線を作ってから変換
		float[][] upPoints = createCurveUpReverse(x, topY, steps);

		return createReverse(upPoints);
	}

	/**
	 * 上昇放物線を作る
	 *
	 * @param x     進む距離
	 * @param topY  Yの最大値
	 * @param steps Xに進む回数
	 * @return
	 */
	private static float[][] createCurveUp(float x, float topY, int steps) {

		float scaleX = calculateScale(x, steps);

		// 曲線係数を求める
		float a = -(float) (topY / Math.pow(steps, 2));

		// 配列を作る
		float[][] upPoints = new float[steps + 1][2];

		for (int i = 1; i < steps + 1; i++) {

			// 真ん中を０として計算
			float tmpX = i - steps;
			float tmpY = (float) (a * Math.pow(tmpX, 2));

			// 補正
			upPoints[i][0] = (tmpX + steps) * scaleX;
			upPoints[i][1] = tmpY + topY;

		}

		return upPoints;
	}

	/**
	 * 反上昇放物線を作る
	 *
	 * @param x     進む距離
	 * @param topY  Yの最大値
	 * @param steps Xに進む回数
	 * @return
	 */
	private static float[][] createCurveUpReverse(float x, float topY, int steps) {

		float scaleX = calculateScale(x, steps);

		// 曲線係数を求める
		float a = (float) (topY / Math.pow(steps, 2));

		// 配列を作る
		float[][] upPoints = new float[steps + 1][2];

		for (int i = 1; i < steps + 1; i++) {

			// 真ん中を０として計算
			float tmpX = i;
			float tmpY = (float) (a * Math.pow(tmpX, 2));

			// 補正
			upPoints[i][0] = tmpX * scaleX;
			upPoints[i][1] = tmpY;

		}

		return upPoints;
	}

	/**
	 * 放物線を作る
	 *
	 * @param x     進む距離
	 * @param topY  Yの最大値
	 * @param steps Xに進む回数
	 * @return
	 */
	private static float[][] createCurve(float x, float topY, int steps) {

		float scaleX = calculateScale(x, steps);

		// 曲線係数を求める
		int center = steps / 2;
		float a = -(float) (topY / Math.pow(center, 2));

		// 配列を作る
		float[][] points = new float[steps + 1][2];

		for (int i = 1; i < steps + 1; i++) {

			// 真ん中を０として計算
			float tmpX = i - center;
			float tmpY = (float) (a * Math.pow(tmpX, 2));

			// 補正
			points[i][0] = (tmpX + center) * scaleX;
			points[i][1] = tmpY + topY;

		}
		return points;
	}

	public static void main(String[] args) {

		System.out.println("hello world");

		// 偶数でないとだめだな
		int steps = 6;

		float toX = -200;
		float toY = 100;

		float topY = 120;

		// 直線を作る
		float[][] straights = createStraight(toX, toY, steps);

		// 放物線を作る
		float[][] points = createCurve(toX, topY, steps);

		// 上りを作る
		float[][] upPoints = createCurveUp(toX, topY, steps);

		// 反上りを作る
		float[][] upRevPoints = createCurveUpReverse(toX, topY, steps);

		// 下りを作る
		float[][] downPoints = createCurveDown(toX, topY, steps);

		// 反下りを作る
		float[][] downRevPoints = createCurveDownReverse(toX, topY, steps);

		System.out.println("-----");

		for (int i = 0; i < straights.length; i++) {
			System.out.println("straight xy:" + straights[i][0] + "," + straights[i][1]);
		}

		System.out.println("-----");

		for (int i = 0; i < points.length; i++) {
			System.out.println("normal xy:" + points[i][0] + "," + points[i][1]);
		}

		System.out.println("-----");

		for (int i = 0; i < upPoints.length; i++) {
			System.out.println("up xy:" + upPoints[i][0] + "," + upPoints[i][1]);
		}

		System.out.println("-----");

		for (int i = 0; i < upRevPoints.length; i++) {
			System.out.println("upRev xy:" + upRevPoints[i][0] + "," + upRevPoints[i][1]);
		}

		System.out.println("-----");

		for (int i = 0; i < downPoints.length; i++) {
			System.out.println("down xy:" + downPoints[i][0] + "," + downPoints[i][1]);
		}

		System.out.println("-----");

		for (int i = 0; i < downPoints.length; i++) {
			System.out.println("downRev xy:" + downRevPoints[i][0] + "," + downRevPoints[i][1]);
		}

		System.out.println("-----");

	}

}
