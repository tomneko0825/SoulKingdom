package com.tomneko.soulkingdom.view.battle.manager;

import android.graphics.Bitmap;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleStage;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleStageStateType;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.image.enums.BackBattleImageEnum;
import com.tomneko.soulkingdom.view.moving.factory.common.EyeCatchFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingFinishExecutor;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

/**
 * 戦場のマネージャ
 * <p/>
 * Created by toyama on 2017/09/08.
 */
@Service
public class BattleStageManager {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ImageHolder imageHolder;

	@Inject
	private EyeCatchFactory eyeCatchFactory;

	@Inject
	private EffectManager effectManager;

	private BattleStage battleStage;

	public BattleStage getBattleStage() {
		return battleStage;
	}

	/**
	 * 背景を取得
	 *
	 * @return
	 */
	public Bitmap getBackground() {
		return imageHolder.getBackBattleImage(BackBattleImageEnum.FOREST);
	}

	/**
	 * 戦場を作成
	 */
	public void createBattleStage() {

		// TODO とりあえず
		battleStage = new BattleStage();
	}

	/**
	 * 戦場の状態を取得
	 *
	 * @return
	 */
	public BattleStageStateType getBattleStageStateType() {
		return battleStage.getBattleStageStateType();
	}

	/**
	 * 次のウェーブへ
	 */
	public void nextWave() {

		battleStage.nextWave();
		battleStage.setBattleStageStateType(BattleStageStateType.EYE_CATCH);

		// アイキャッチを実行
		String preWave = "Wave " + (battleStage.getCurrentWave() - 1);
		String newWave = "Wave " + battleStage.getCurrentWave();

		// クリアのWave
		MovingObject clearWaveMo = eyeCatchFactory.createClearWaveText(preWave, 48, 8);
		effectManager.addMovingText(clearWaveMo);

		// クリア
		MovingObject clearMo = eyeCatchFactory.createClearText("Clear", 48, 8);
		effectManager.addMovingText(clearMo);

		// スタートのWave
		MovingObject startWaveMo = eyeCatchFactory.createStartWaveText(newWave, 48, 8, 64);
		effectManager.addMovingText(startWaveMo);

		// スタート
		MovingObject startMo = eyeCatchFactory.createStartText("Start", 104, 16);
		effectManager.addMovingText(startMo);

		// スタートの終了時に実行
		MovingFinishExecutor mfe = new MovingFinishExecutor() {
			@Override
			public void execute() {
				finishEyeCatch();
			}
		};

		startMo.setMovingFinishExecutor(mfe);
	}

	/**
	 * アイキャッチの終了
	 */
	public void finishEyeCatch() {
		battleStage.setBattleStageStateType(BattleStageStateType.BATTLE);
	}

	/**
	 * 戦闘の終了処理
	 */
	public void finish() {
		battleStage.setBattleStageStateType(BattleStageStateType.FINISH);
	}
}
