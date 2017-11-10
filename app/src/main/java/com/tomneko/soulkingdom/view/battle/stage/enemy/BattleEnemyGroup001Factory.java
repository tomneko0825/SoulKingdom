package com.tomneko.soulkingdom.view.battle.stage.enemy;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.model.enemy.enums.EnemyType;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyGroup;
import com.tomneko.soulkingdom.view.battle.model.BattleEnemyTeam;
import com.tomneko.soulkingdom.view.battle.strategy.SerialStrategy;

/**
 * 敵グループ001のファクトリ
 * Created by toyama on 2017/10/20.
 */
@Service
public class BattleEnemyGroup001Factory implements BattleEnemyGroupFactory {

	@Override
	public BattleEnemyGroup create() {

		BattleEnemyGroup group = new BattleEnemyGroup();

		// １：狼１
		SerialStrategy strategy1 = new SerialStrategy();
		strategy1.addEnemy(EnemyType.WILD_WOLF);

		group.addBattleEnemyTeam(new BattleEnemyTeam(strategy1));

		// ２：狼１
		SerialStrategy strategy2 = new SerialStrategy();
		strategy2.addEnemy(EnemyType.WILD_WOLF);

		group.addBattleEnemyTeam(new BattleEnemyTeam(strategy2));

		// ３：狼２
		SerialStrategy strategy3 = new SerialStrategy();
		strategy3.addEnemy(EnemyType.WILD_WOLF);
		strategy3.addEnemy(EnemyType.WILD_WOLF);

		group.addBattleEnemyTeam(new BattleEnemyTeam(strategy3));

		// ４：狼１
		SerialStrategy strategy4 = new SerialStrategy();
		strategy4.addEnemy(EnemyType.WILD_WOLF);

		group.addBattleEnemyTeam(new BattleEnemyTeam(strategy4));

		// ５：狼２
		SerialStrategy strategy5 = new SerialStrategy();
		strategy5.addEnemy(EnemyType.WILD_WOLF);
		strategy5.addEnemy(EnemyType.WILD_WOLF);

		group.addBattleEnemyTeam(new BattleEnemyTeam(strategy5));


		return group;
	}
}
