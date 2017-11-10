package com.tomneko.soulkingdom.view.battle;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.MainView;
import com.tomneko.soulkingdom.view.battle.drawer.BattleButtonDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.BattleStageDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.CharaDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.DebugDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.EffectDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.EnemyDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.EnemyTeamDrawer;
import com.tomneko.soulkingdom.view.battle.drawer.PartyDrawer;
import com.tomneko.soulkingdom.view.battle.manager.BattleActionManager;
import com.tomneko.soulkingdom.view.battle.manager.BattleButtonManager;
import com.tomneko.soulkingdom.view.battle.manager.BattleMemberManager;
import com.tomneko.soulkingdom.view.battle.manager.BattleStageManager;
import com.tomneko.soulkingdom.view.battle.manager.CharaManager;
import com.tomneko.soulkingdom.view.battle.manager.DebugManager;
import com.tomneko.soulkingdom.view.battle.manager.EffectManager;
import com.tomneko.soulkingdom.view.battle.manager.EnemyManager;
import com.tomneko.soulkingdom.view.battle.manager.PartyManager;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleStageStateType;
import com.tomneko.soulkingdom.view.hander.TouchEventHandler;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.moving.factory.chara.DodgeFactory;
import com.tomneko.soulkingdom.view.moving.factory.chara.GuardFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.ShakeFactory;
import com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory;
import com.tomneko.soulkingdom.view.moving.factory.enemy.SimpleAssultFactory;
import com.tomneko.soulkingdom.view.moving.service.MovingProcessor;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.service.FpsManager;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.concurrent.TimeUnit;

/**
 * 戦闘画面のコントローラ
 * <p/>
 * Created by toyama on 2017/09/08.
 */
@Service
public class BattleStageController implements Runnable {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private BattleStageManager battleStageManager;

	@Inject
	private BattleStageDrawer battleStageDrawer;

	@Inject
	private BattleButtonManager battleButtonManager;

	@Inject
	private BattleButtonDrawer battleButtonDrawer;

	@Inject
	private PartyManager partyManager;

	@Inject
	private PartyDrawer partyDrawer;

	@Inject
	private CharaManager charaManager;

	@Inject
	private CharaDrawer charaDrawer;

	@Inject
	private EnemyManager enemyManager;

	@Inject
	private EnemyDrawer enemyDrawer;

	@Inject
	private EffectManager effectManager;

	@Inject
	private EffectDrawer effectDrawer;

	@Inject
	private BattleActionManager battleActionManager;

	@Inject
	private DebugManager debugManager;

	@Inject
	private DebugDrawer debugDrawer;

	@Inject
	private BattleMemberManager battleMemberManager;


	@Inject
	private MovingProcessor movingProcessor;

	@Inject
	private FpsManager fpsManager;

	@Inject
	private ImageHolder imageHolder;

	private MainView mainView;

	private Thread thread;


	// TODO 動きのファクトリはここじゃない
	@Inject
	private SimpleAssultFactory simpleAssultFactory;

	@Inject
	private SimpleEffectFactory simpleEffectFactory;

	@Inject
	private ShakeFactory shakeFactory;

	@Inject
	private GuardFactory guardFactory;

	@Inject
	private DodgeFactory dodgeFactory;

	@Inject
	private TouchEventHandler touchEventHandler;

	@Inject
	private EnemyTeamDrawer enemyTeamDrawer;

	/**
	 * 初期化処理
	 *
	 * @param mainView
	 */
	public void initialize(MainView mainView) {
		this.mainView = mainView;
	}

	public void surfaceCreated(SurfaceHolder holder) {

		imageHolder.load(this.mainView.getResources());

		// ステージを作成
		battleStageManager.createBattleStage();

		// デバッグ関連各種
		debugManager.createDebugButtons();

		// 戦闘用ボタンを作成
		battleButtonManager.createButtons();

		// パーティを作成
		partyManager.createChara();
		partyManager.createParty();

		// 敵を作成
		enemyManager.createEnemyGroup();
		// enemyManager.createEnemyButton();

		// スレッドの開始
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {

			while (thread != null) {

				while (true) {

					// ストップの間は止まる
					if (mainView.isStop() == false) {
						break;
					}

					Thread.sleep(1000);
				}

				onTick();

				TimeUnit.NANOSECONDS.sleep(fpsManager.state());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 停止状態を変更する
	 */
	public void changeStop() {
		this.mainView.setStop(!this.mainView.isStop());
	}

	/**
	 * 時を刻む
	 */
	public void onTick() {

		// 進める
		proceed();

		// 描画
		draw();
	}

	/**
	 * 状態を勧める
	 */
	private void proceed() {

		// キャラ
		charaManager.proceedChara();

		// 敵を進めるのは戦闘中のみ
		if (battleStageManager.getBattleStageStateType() == BattleStageStateType.BATTLE) {
			// 敵
			enemyManager.proceedEnemy();
		}

		// エフェクト
		effectManager.proceedEffect();

		// 戦闘処理
		battleActionManager.proceedBattleAction();

	}

	/**
	 * 描画処理
	 */
	private void draw() {

		// ダブルバッファリング
		Canvas canvas = mainView.getSurfaceHolder().lockCanvas();
		if (canvas == null) {
			return;
		}

		// 背景の描画
		battleStageDrawer.drawBackground(canvas);

		// グリッドの表示
		debugDrawer.drawGrid(canvas);

		// デバッグボタンの表示
		debugDrawer.drawButton(canvas);

		// ボタンの描画
		battleButtonDrawer.drawButton(canvas);

		// パーティの描画
		partyDrawer.drawParty(canvas);

		// キャラの描画
		charaDrawer.drawChara(canvas);

		// 敵の描画
		enemyDrawer.drawEnemy(canvas);

		// 敵チームの描画
		enemyTeamDrawer.drawEnemyTeam(canvas);

		// エフェクトの描画
		effectDrawer.drawEffect(canvas);

		// デバッグメッセージの描画
		debugDrawer.drawDebugMessage(canvas);

		mainView.getSurfaceHolder().unlockCanvasAndPost(canvas);
	}


	/**
	 * タッチイベント
	 *
	 * @param event
	 * @return
	 */
	public boolean onTouchEvent(MotionEvent event) {

		// ハンドラに処理を委譲
		touchEventHandler.onTouchEvent(event);

		return true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
	}

	/**
	 * キャラが戦闘不能
	 *
	 * @param id
	 */
	public void defeatedChara(String id) {
		partyManager.defeated(id);
	}

	/**
	 * 敵が戦闘不能
	 *
	 * @param id
	 */
	public void defeatedEnemy(String id) {

		enemyManager.defeated(id);

		// 現在のチームが終了してたら
		if (enemyManager.getBattleEnemyGroup().getCurrentBattleEnemyTeam().isFinish()) {

			// 最後なら終了処理
			if (battleStageManager.getBattleStage().isLast()) {
				battleStageManager.finish();
			}

			// それ以外は次のウェーブへ
			else {
				enemyManager.getBattleEnemyGroup().nextTeam();
				battleStageManager.nextWave();
			}
		}
	}
}

