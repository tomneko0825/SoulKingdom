package com.tomneko.soulkingdom.view.moving.service;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;

/**
 * Created by toyama on 2017/09/07.
 */
@Service
public class MovingProcessor {


	/**
	 * MovingCombinationに従って移動させる
	 *
	 * @param mo
	 */
	public void move(MovingObject mo) {

		MovingCombination mc = mo.getMovingCombination();

		MovingTarget mt = mc.getMovingTargetList().getFirst();

		// 移動させる
		float[] p = mt.next();

		// チェンジャーを取得
		MovingImageChanger changer = mt.getCurrentMovingImageChanger();
		if (changer != null) {

			// タイル変更
			if (changer.isTileChange()) {
				// デフォルトに設定する場合
				if (changer.isSetDefault()) {
					mo.setTileX(mo.getTileBaseX());
					mo.setTileY(mo.getTileBaseY());
				} else {
					mo.setTileX(changer.getTileX());
					mo.setTileY(changer.getTileY());
				}
			}

			// 非表示設定
			else {
				mo.setInvisible(changer.isInvisible());
			}
		}

		// 残りが０なら終了処理
		if (mt.isFinish()) {

			// 先頭を削除
			mc.getMovingTargetList().poll();

			// 移動の終了処理
			if (mc.getMovingTargetList().size() == 0) {
				mo.setMovingCombination(null);
			}
		}

		// 設定
		mo.setX(p[0]);
		mo.setY(p[1]);
	}

}
