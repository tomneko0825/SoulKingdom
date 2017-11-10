package com.tomneko.soulkingdom.view.battle.strategy;

import android.graphics.Bitmap;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.ObjectContainer;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.framework.utils.StringUtils;
import com.tomneko.soulkingdom.model.enemy.enums.EnemyType;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemy;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberStatus;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleEnemyPositionType;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.battle.tactics.Tactics;
import com.tomneko.soulkingdom.view.battle.tactics.factory.TacticsFactory;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.ENEMY_BASE_X;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.ENEMY_BASE_Y_1;

/**
 * 敵のファクトリ
 * <p/>
 * Created by toyama on 2017/10/18.
 */
@Service
public class BattleEnemyFactory {

	private static final String TACTICS_FACTORY_PACKAGE = "com.tomneko.soulkingdom.view.battle.tactics.factory";
	private static final String TACTICS_FACTORY_SUFFIX = "TacticsFactory";

	@Inject
	private ImageHolder imageHolder;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ObjectContainer objectContainer;

	// ID用のカウンタ
	private int counter;

	/**
	 * カウンターを取得
	 *
	 * @return
	 */
	private String getCounter() {
		if (10000 <= counter) {
			counter = 0;
		}
		return String.format("%04d", counter);
	}

	/**
	 * 敵を作成
	 *
	 * @param enemyType
	 * @param level
	 * @param positionType
	 * @return
	 */
	public BattleEnemy create(EnemyType enemyType, int level, BattleEnemyPositionType positionType) {

		// MovingObjectを作成
		MovingObject enemy = createMovingObject(enemyType);

		BattleEnemy battleEnemy = new BattleEnemy();
		battleEnemy.setId(enemy.getInvokerId());
		battleEnemy.setMovingObject(enemy);
		battleEnemy.setBattleMovingType(BattleMovingType.ON_APPEAR_IN);

		BattleMemberStatus status = new BattleMemberStatus(enemyType.getHp(), enemyType.getAtk(), enemyType.getDef());
		battleEnemy.setBattleMemberStatus(status);

		Tactics tactics = createTactics(enemyType, level);
		battleEnemy.setTactics(tactics);

		return battleEnemy;

	}

	/**
	 * MovingObjectを作成
	 *
	 * @param enemyType
	 * @return
	 */
	private MovingObject createMovingObject(EnemyType enemyType) {
		MovingObject enemy = new MovingObject(enemyType.name() + "_" + getCounter());

		Bitmap bmp = imageHolder.getEnemyImage(enemyType.getEnemyImage());
		enemy.setImage(bmp);

		float viewWidth = sc.getViewWidth();

		// 基準となる位置
		float rootX = viewWidth / BLOCK_COUNT * ENEMY_BASE_X;
		float rootY = sc.getY(BLOCK_WIDHT) * ENEMY_BASE_Y_1;

		float x = rootX - (bmp.getWidth() / 2);
		float y = rootY - bmp.getHeight();

		enemy.setBaseX(x);
		enemy.setX(0 - enemy.getTileWidth());
		enemy.setBaseY(y);
		enemy.setY(y);
		return enemy;
	}

	/**
	 * タクティクスを作成
	 *
	 * @param enemyType
	 * @param level
	 * @return
	 */
	private Tactics createTactics(EnemyType enemyType, int level) {

		String name = StringUtils.toCamelCase(enemyType.name());

		String factoryName = TACTICS_FACTORY_PACKAGE + "." + name + TACTICS_FACTORY_SUFFIX;

		Class clazz = null;

		try {
			clazz = Class.forName(factoryName);
		} catch (Exception e) {
			throw new RuntimeException("tactics class not found. [" + factoryName + "]");
		}

		TacticsFactory tacticsFactory = (TacticsFactory) objectContainer.getObject(clazz);
		return tacticsFactory.create(level);
	}
}
