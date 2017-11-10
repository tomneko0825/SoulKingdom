package com.tomneko.soulkingdom.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tomneko.soulkingdom.framework.ObjectContainer;
import com.tomneko.soulkingdom.view.battle.BattleStageController;
import com.tomneko.soulkingdom.view.service.PaintHolder;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

/**
 * Created by toyama on 2017/08/31.
 */
public class MainView extends SurfaceView
		implements SurfaceHolder.Callback {

	private BattleStageController battleStageController;

	private ObjectContainer objectContainer;

	private boolean stop = true;

	public MainView(Context context) {
		super(context);

		getHolder().addCallback(this);
	}

	public boolean isStop() {
		return stop;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		// 初期化処理
		objectContainer = new ObjectContainer();
		objectContainer.initialize();

		// コントローラーを取得し自身を渡す
		battleStageController = (BattleStageController)objectContainer.getObject(BattleStageController.class);
		battleStageController.initialize(this);

		// ScaleCalculatorを取得し自信を渡す
		ScaleCalculator sc = (ScaleCalculator)objectContainer.getObject(ScaleCalculator.class);
		sc .initialize(this);

		// ペイントホルダーの初期化
		PaintHolder paintHolder = (PaintHolder)objectContainer.getObject(PaintHolder.class);
		paintHolder.initialize(getContext().getAssets());

		// とりあえず戦闘ステージ
		battleStageController.surfaceCreated(holder);

		// 初期化終わり
		stop = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		battleStageController.surfaceDestroyed(holder);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return battleStageController.onTouchEvent(event);
	}

	public SurfaceHolder getSurfaceHolder() {
		return getHolder();
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
