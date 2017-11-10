package com.tomneko.soulkingdom.view.moving.factory.common;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.moving.model.MovingVisibleChanger;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.BLINK_DEFEATED_MOVE;

/**
 * 点滅のファクトリ
 * <p/>
 * Created by toyama on 2017/10/01.
 */
@Service
public class BlinkFactory {

	/**
	 * 点滅を作成
	 *
	 * @param mo
	 * @param step
	 * @return
	 */
	public MovingCombination createBlink(MovingObject mo, int step) {

		float x = mo.getX();
		float y = mo.getY();

		MovingCombination mc = new MovingCombination("blink");

		// 固定
		MovingTarget mt = MovingTargetFactory.createStay(x, y, step);
		mt.setMovingType(BLINK_DEFEATED_MOVE);

		MovingVisibleChanger mic1 = new MovingVisibleChanger(false);
		mt.putMovingImageChangerFirst(mic1);

		MovingVisibleChanger mic2 = new MovingVisibleChanger(true);
		mt.putMovingImageChanger(step / 4 * 1, mic2);

		MovingVisibleChanger mic3 = new MovingVisibleChanger(false);
		mt.putMovingImageChanger(step / 4 * 2, mic3);

		MovingVisibleChanger mic4 = new MovingVisibleChanger(true);
		mt.putMovingImageChanger(step / 4 * 3, mic4);

		MovingVisibleChanger mic5 = new MovingVisibleChanger(false);
		mt.putMovingImageChangerLast(mic5);

		mc.addMovingTarget(mt);

		return mc;
	}


}
