package com.tomneko.soulkingdom.view.battle.manager;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyGroup;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyTeam;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleStageStateType;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.battle.service.DrawingObjectListCreator;
import com.tomneko.soulkingdom.view.battle.stage.enemy.BattleEnemyGroup001Factory;
import com.tomneko.soulkingdom.view.battle.strategy.BattleEnemyFactory;
import com.tomneko.soulkingdom.view.battle.tactics.service.TacticsExecutor;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonObjectType;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.moving.factory.enemy.AppearFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;

/**
 * 敵の管理
 * <p/>
 * Created by toyama on 2017/09/03.
 */
@Service
public class EnemyManager {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattleMemberManager battleMemberManager;

	@Inject
	private ImageHolder imageHolder;

	@Inject
	private DrawingObjectListCreator drawingObjectListCreator;

	@Inject
	private BattleConstantsHolder bch;

	@Inject
	private TacticsExecutor tacticsExecutor;

	@Inject
	private BattleEnemyFactory battleEnemyFactory;

	@Inject
	private AppearFactory appearFactory;

	@Inject
	private BattleStageManager battleStageManager;

	private BattleEnemyGroup battleEnemyGroup;

	private List<BattleMemberDrawingObject> drawingObjectList = new ArrayList();

	public List<BattleMemberDrawingObject> getDrawingObjectList() {
		return drawingObjectList;
	}

	public BattleEnemyGroup getBattleEnemyGroup() {
		return battleEnemyGroup;
	}

	public void setBattleEnemyGroup(BattleEnemyGroup battleEnemyGroup) {
		this.battleEnemyGroup = battleEnemyGroup;
	}

	/**
	 * 現在ターゲットとなっている敵
	 *
	 * @return
	 */
	public BattleEnemy getTargetBattleEnemy() {

		if (getCurrentBattleEnemyList().size() == 0) {
			return null;
		}

		// TODO とりあえず
		return getCurrentBattleEnemyList().get(0);
	}

	/**
	 * 現在表示されている敵のリスト
	 *
	 * @return
	 */
	public List<BattleEnemy> getCurrentBattleEnemyList() {

		List<BattleEnemy> list = new ArrayList();
		for (BattleMemberDrawingObject bmdo : this.drawingObjectList) {
			if (bmdo.getBattleMember() != null) {
				list.add((BattleEnemy) bmdo.getBattleMember());
			}
		}

		return list;
	}

//	/**
//	 * 敵チームの表示部分を作成
//	 */
//	public void createEnemyButton() {
//
//		float y = sc.getY(20);
//		float height = sc.getY(STATUS_HEIGHT);
//
//		this.drawingObjectList = drawingObjectListCreator.createDrawingObjectList(3, y, height, "enemy");
//
//		// とりあえず非表示
//		for (BattleMemberDrawingObject bmdo : this.drawingObjectList) {
//			bmdo.getButtonObject().setVisible(false);
//			bmdo.getButtonObject().setEnabled(false);
//		}
//
//	}

	@Inject
	private BattleEnemyGroup001Factory battleEnemyGroup001Factory;

	/**
	 * 敵を作成
	 */
	public void createEnemyGroup() {

		// TODO とりあえず
		this.battleEnemyGroup = battleEnemyGroup001Factory.create();
	}

	/**
	 * 敵キャラのボタンを作成
	 *
	 * @param battleEnemy
	 * @return
	 */
	private void createEnemyButton(BattleEnemy battleEnemy) {

		MovingObject mo = battleEnemy.getMovingObject();

		// ボタンを作る
		float left = sc.getX(BLOCK_WIDHT) * 6;
		float top = mo.getBaseY();

		ButtonObject bo = new ButtonObject();
		bo.setX(left);
		bo.setWidth(sc.getX(BLOCK_WIDHT) * 5);
		bo.setY(top);
		bo.setHeight(sc.getY(60));
		bo.setButtonObjectType(ButtonObjectType.ALPHA_LABEL);

		// ボタンと敵の結びつけ
		BattleMemberDrawingObject bmdo = new BattleMemberDrawingObject();
		bmdo.setBattleMember(battleEnemy);
		bmdo.setButtonObject(bo);

		// ボタンを登録
		this.drawingObjectList.add(bmdo);
	}

//	/**
//	 * 敵とボタンの組み合わせを作成
//	 */
//	public void createEnemy() {
//
//		// TODO とりあえず
//		// 敵を作る
//		BattleEnemy battleEnemy = createBattleEnemy();
//		ButtonObject bo = createEnemyButton(battleEnemy);
//
//		BattleMemberDrawingObject bmdo = new BattleMemberDrawingObject();
//		bmdo.setBattleMember(battleEnemy);
//		bmdo.setButtonObject(bo);
//
//		// TODO 敵グループに追加、最初に作成されて登録されているべき？
//		battleEnemyGroup.getBattleEnemyTeamList().get(0).getBattleEnemyList().add(battleEnemy);
//
//		// 移動を設定
//		MovingObject mo = battleEnemy.getMovingObject();
//		MovingCombination mc = appearFactory.createAppearIn(mo, bch.getStep());
//		mo.setMovingCombination(mc);
//	}
//
//	/**
//	 * 敵を作成
//	 */
//	private BattleEnemy createBattleEnemy() {
//
//		// TODO とりあえず
//		MovingObject enemy = new MovingObject("enemy_001");
//
//		Bitmap bmp = imageHolder.getEnemyImage(EnemyImageEnum.WILD_WOLF_IMG);
//		enemy.setImage(bmp);
//
//		float viewWidth = sc.getViewWidth();
//
//		// 基準となる位置
//		float rootX = viewWidth / BLOCK_COUNT * ENEMY_BASE_X;
//		float rootY = sc.getY(BLOCK_WIDHT) * ENEMY_BASE_Y_1;
//
//		float x = rootX - (bmp.getWidth() / 2);
//		float y = rootY - bmp.getHeight();
//
//		enemy.setBaseX(x);
//		enemy.setX(0 - enemy.getTileWidth());
//		enemy.setBaseY(y);
//		enemy.setY(y);
//
//		BattleEnemy battleEnemy = new BattleEnemy();
//		battleEnemy.setId(enemy.getInvokerId());
//		battleEnemy.setMovingObject(enemy);
//		battleEnemy.setBattleMovingType(BattleMovingType.ON_APPEAR_IN);
//		// battleEnemy.setActive(true);
//
//		Tactics tactics = wildWolfTacticsFactory.create();
//		battleEnemy.setTactics(tactics);
//
//		return battleEnemy;
//	}

	/**
	 * 敵を進める
	 */
	public void proceedEnemy() {

		// チームの戦略を実行
		BattleEnemyTeam team = battleEnemyGroup.getCurrentBattleEnemyTeam();
		List<BattleEnemy> newEnemyList = team.doStrategy(battleEnemyFactory);

		// 新規作成された敵の処理
		for (BattleEnemy battleEnemy : newEnemyList) {

			// 画面の敵ボタンを作成
			createEnemyButton(battleEnemy);

			// 移動を設定
			MovingObject mo = battleEnemy.getMovingObject();
			MovingCombination mc = appearFactory.createAppearIn(mo, bch.getStep());
			mo.setMovingCombination(mc);
		}

		for (BattleEnemy battleEnemy : getCurrentBattleEnemyList()) {

			// 戦術の実行
			if (battleEnemy.getBattleMovingType() == BattleMovingType.ON_WAIT) {
				tacticsExecutor.execute(battleEnemy);
			}

			battleMemberManager.proceedBattleMember(battleEnemy);
		}
	}

	/**
	 * 戦闘不能処理
	 *
	 * @param id
	 */
	public void defeated(String id) {

		// 画面表示ボタンの削除処理
		deleteFromDrawingObjectList(id);

		// チームから削除
		battleEnemyGroup.getCurrentBattleEnemyTeam().defeated(id);
	}

	/**
	 * 画面表示ボタンの削除
	 *
	 * @param id
	 */
	private void deleteFromDrawingObjectList(String id) {
		BattleMemberDrawingObject deleteBmdo = null;

		for (BattleMemberDrawingObject bmdo : drawingObjectList) {
			BattleMember battleMember = bmdo.getBattleMember();
			if (battleMember.getId().equals(id)) {
				deleteBmdo = bmdo;
				break;
			}
		}

		if (deleteBmdo == null) {
			throw new RuntimeException("enemy is not found:" + id);
		}

		drawingObjectList.remove(deleteBmdo);
	}


}
