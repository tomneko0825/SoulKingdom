package com.tomneko.soulkingdom.view.moving.factory.common;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingObjectText;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.TEXT_MOVE;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.EYE_CATCH_START_TEXT;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.EYE_CATCH_START_TEXT_SHADOW;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.EYE_CATCH_WAVE_TEXT;
import static com.tomneko.soulkingdom.view.service.enums.PaintHolderType.EYE_CATCH_WAVE_TEXT_SHADOW;

/**
 * アイキャッチのファクトリ
 * <p/>
 * Created by toyama on 2017/11/07.
 */
@Service
public class EyeCatchFactory {

	@Inject
	private ScaleCalculator sc;

	/**
	 * Startのテキストを作成
	 *
	 * @param text
	 * @param hideStep
	 * @param stayStep
	 * @return
	 */
	public MovingObject createStartText(String text, int hideStep, int stayStep) {

		float x = sc.getViewWidth() / 2;

		// 隠れるよう適当に
		float y1 = sc.getViewHeight() * 2;
		float y2 = sc.getY(BLOCK_WIDHT * 9);

		MovingObject mo = new MovingObject("eyeCatchStart");
		mo.setBaseX(x);
		mo.setX(x);
		mo.setBaseY(y1);
		mo.setY(y1);

		MovingObjectText mot = new MovingObjectText(text, EYE_CATCH_START_TEXT, EYE_CATCH_START_TEXT_SHADOW);
		mo.setMovingObjectText(mot);

		MovingCombination mc1 = new MovingCombination("stay");

		// 隠す
		MovingTarget mt1 = MovingTargetFactory.createStay(x, y1, hideStep);
		mt1.setMovingType(TEXT_MOVE);
		mc1.addMovingTarget(mt1);

		// 現れる
		MovingTarget mt2 = MovingTargetFactory.createStay(x, y2, stayStep);
		mt2.setMovingType(TEXT_MOVE);
		mc1.addMovingTarget(mt2);

		mo.setMovingCombination(mc1);

		return mo;
	}

	/**
	 * クリアのテキストを作成
	 *
	 * @param text
	 * @param stayStep
	 * @param moveStep
	 * @return
	 */
	public MovingObject createClearText(String text, int stayStep, int moveStep) {

		float y = sc.getY(BLOCK_WIDHT * 9);
		float x1 = sc.getViewWidth() / 2;
		float x2 = sc.getViewWidth() + sc.getY(BLOCK_WIDHT * 8);

		MovingObject mo = new MovingObject("eyeCatchClear");
		mo.setBaseX(x1);
		mo.setX(x1);
		mo.setBaseY(y);
		mo.setY(y);

		MovingObjectText mot = new MovingObjectText(text, EYE_CATCH_START_TEXT, EYE_CATCH_START_TEXT_SHADOW);
		mo.setMovingObjectText(mot);

		MovingCombination mc = new MovingCombination("stayAndMove");

		// 固定
		MovingTarget mt1 = MovingTargetFactory.createStay(x1, y, stayStep);
		mt1.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt1);

		// 移動
		MovingTarget mt2 = MovingTargetFactory.createStraight(x1, y, x2, y, moveStep);
		mt2.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt2);

		mo.setMovingCombination(mc);

		return mo;
	}

	/**
	 * クリアWaveのテキストを作成
	 *
	 * @param text
	 * @param stayStep
	 * @param moveStep
	 * @return
	 */
	public MovingObject createClearWaveText(String text, int stayStep, int moveStep) {

		float y = sc.getY(BLOCK_WIDHT * 6);
		float x1 = sc.getViewWidth() / 2;
		float x2 = sc.getViewWidth() + sc.getY(BLOCK_WIDHT * 8);

		MovingObject mo = new MovingObject("eyeCatchClearWave");
		mo.setBaseX(x1);
		mo.setX(x1);
		mo.setBaseY(y);
		mo.setY(y);

		MovingObjectText mot = new MovingObjectText(text, EYE_CATCH_WAVE_TEXT, EYE_CATCH_WAVE_TEXT_SHADOW);
		mo.setMovingObjectText(mot);

		MovingCombination mc = new MovingCombination("stayAndMove");

		// 固定
		MovingTarget mt1 = MovingTargetFactory.createStay(x1, y, stayStep);
		mt1.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt1);

		// 移動
		MovingTarget mt2 = MovingTargetFactory.createStraight(x1, y, x2, y, moveStep);
		mt2.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt2);

		mo.setMovingCombination(mc);

		return mo;
	}

	/**
	 * 開始Waveのテキストを作成
	 *
	 * @param text
	 * @param hideStep
	 * @param moveStep
	 * @param stayStep
	 * @return
	 */
	public MovingObject createStartWaveText(String text, int hideStep, int moveStep, int stayStep) {

		float y = sc.getY(BLOCK_WIDHT * 6);
		float x1 = -sc.getY(BLOCK_WIDHT * 8);
		float x2 = sc.getViewWidth() / 2;

		MovingObject mo = new MovingObject("eyeCatchStartWave");
		mo.setBaseX(x1);
		mo.setX(x1);
		mo.setBaseY(y);
		mo.setY(y);

		MovingObjectText mot = new MovingObjectText(text, EYE_CATCH_WAVE_TEXT, EYE_CATCH_WAVE_TEXT_SHADOW);
		mo.setMovingObjectText(mot);

		MovingCombination mc = new MovingCombination("moveAndStay");

		// 固定
		MovingTarget mt1 = MovingTargetFactory.createStay(x1, y, hideStep);
		mt1.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt1);

		// 移動
		MovingTarget mt2 = MovingTargetFactory.createStraight(x1, y, x2, y, moveStep);
		mt2.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt2);

		// 固定
		MovingTarget mt3 = MovingTargetFactory.createStay(x2, y, stayStep);
		mt3.setMovingType(TEXT_MOVE);
		mc.addMovingTarget(mt3);

		mo.setMovingCombination(mc);

		return mo;
	}
}
