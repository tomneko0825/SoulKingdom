package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleAction;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.service.MovingProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * エフェクトの管理
 * <p/>
 * Created by toyama on 2017/09/06.
 */
@Service
public class EffectManager {

	@Inject
	private BattleActionManager battleActionManager;

	@Inject
	private MovingProcessor movingProcessor;


	// 表示するエフェクトのリスト
	private List<MovingEffect> movingEffectList = new ArrayList();

	// 削除対象のエフェクトのリスト
	private List<MovingEffect> deleteEffectList = new ArrayList();

	// 表示するテキストのリスト
	private List<MovingObject> movingTextList = new ArrayList();

	// 削除対象のテキストのリスト
	private List<MovingObject> deleteTextList = new ArrayList();

	public List<MovingEffect> getMovingEffectList() {
		return movingEffectList;
	}

	public void addMovingEffect(MovingEffect movingEffect) {
		this.movingEffectList.add(movingEffect);
	}

	public List<MovingObject> getMovingTextList() {
		return movingTextList;
	}

	public void addMovingText(MovingObject mo) {
		this.movingTextList.add(mo);
	}

	/**
	 * エフェクトを進める
	 */
	public void proceedEffect() {

		// エフェクトを進める
		proceedMovingEffect();

		// 文字を進める
		proceedMovingText();
	}

	/**
	 * テキストを進める
	 */
	private void proceedMovingText() {

		// 前回の処理で削除対象となったエフェクトを消す
		for (MovingObject delete : deleteTextList) {

			// 終了処理を実行
			if (delete.getMovingFinishExecutor() != null) {
				delete.getMovingFinishExecutor().execute();
			}

			movingTextList.remove(delete);
		}

		// 削除対象をクリア
		deleteTextList.clear();

		for (MovingObject mo : movingTextList) {

			movingProcessor.move(mo);

			// 終了したら消す
			MovingCombination mc = mo.getMovingCombination();

			if (mc == null) {

				// リストに追加しておいて次回削除
				deleteTextList.add(mo);
			}
		}
	}


	/**
	 * エフェクトを進める
	 */
	private void proceedMovingEffect() {

		// 前回の処理で削除対象となったエフェクトを消す
		for (MovingEffect delete : deleteEffectList) {
			movingEffectList.remove(delete);
		}

		// 削除対象をクリア
		deleteEffectList.clear();

		for (MovingEffect me : movingEffectList) {

			// アクションがあったら詰め替え
			BattleAction ba = me.getBattleAction();
			if (ba != null) {
				battleActionManager.addBattleAction(ba);
				me.clearBattleAction();
			}

			MovingObject mo = me.getMovingObject();

			movingProcessor.move(mo);

			// エフェクトが終了したら消す
			MovingCombination mc = mo.getMovingCombination();

			if (mc == null) {

				// リストに追加しておいて次回削除
				deleteEffectList.add(me);
			}
		}
	}

}
