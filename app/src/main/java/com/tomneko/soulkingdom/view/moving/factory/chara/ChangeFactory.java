package com.tomneko.soulkingdom.view.moving.factory.chara;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.CHANGE_BACK_MOVE;
import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.CHANGE_GO_MOVE;

/**
 * チェンジのファクトリ
 *
 * Created by toyama on 2017/09/25.
 */
@Service
public class ChangeFactory {

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	/**
	 * チェンジ出陣を作成
	 *
	 * @param mo
	 * @param toX
	 * @param toY
	 * @param step
	 * @return
	 */
	public MovingCombination createChangeGo(MovingObject mo, float toX, float toY, int step) {

		MovingCombination mc = new MovingCombination("changeGo");

		float topY = -(toY - mo.getY());

		MovingTarget mt = MovingTargetFactory.createCurveUp(mo.getX(), mo.getY(), toX, topY, step);
		mt.setMovingType(CHANGE_GO_MOVE);
		mc.addMovingTarget(mt);

		// 画像の切り替え
		// 正面
		MovingImageChanger changerFront = new MovingImageChanger(1, 0);
		mt.putMovingImageChangerFirst(changerFront);

		// 右
		MovingImageChanger changerRight = new MovingImageChanger(1, 2);
		mt.putMovingImageChanger(step / 4, changerRight);

		// 背面
		MovingImageChanger changerBack = new MovingImageChanger(1, 3);
		mt.putMovingImageChanger(step / 2, changerBack);

		// 左
		// 画像をデフォルトに戻す
		MovingImageChanger changerDefault = new MovingImageChanger();
		changerDefault.setDefault();
		mt.putMovingImageChangerLast(changerDefault);

		return mc;
	}

	/**
	 * チェンジ戻るを作成
	 *
	 * @param mo
	 * @param toX
	 * @param toY
	 * @param step
	 * @return
	 */
	public MovingCombination createChangeBack(MovingObject mo, float toX, float toY, int step) {

		MovingCombination mc = new MovingCombination("changeGo");

		float topY = -(mo.getY() - toY);

		MovingTarget mt = MovingTargetFactory.createCurveDown(mo.getX(), mo.getY(), toX, topY, step);
		mt.setMovingType(CHANGE_BACK_MOVE);
		mc.addMovingTarget(mt);

		// 画像の切り替え
		// 左
		MovingImageChanger changerLeft = new MovingImageChanger(1, 1);
		mt.putMovingImageChanger(0, changerLeft);

		// 背面
		MovingImageChanger changerBack = new MovingImageChanger(1, 3);
		mt.putMovingImageChanger(2, changerBack);

		// 右
		MovingImageChanger changerRight = new MovingImageChanger(1, 2);
		mt.putMovingImageChanger(4, changerRight);

		// 正面
		MovingImageChanger changerFront = new MovingImageChanger(0, 0);
		mt.putMovingImageChangerLast(changerFront);

		return mc;
	}
}


