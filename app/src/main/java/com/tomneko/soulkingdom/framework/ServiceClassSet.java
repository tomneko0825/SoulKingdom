package com.tomneko.soulkingdom.framework;

import java.util.HashSet;
import java.util.Set;

/**
 * サービスのクラスのセット
 * <p/>
 * Created by toyama on 2017/09/10.
 */
public class ServiceClassSet {

	/**
	 * クラスとオブジェクトのセットを作成
	 *
	 * @return
	 */
	public static Set<String> createServiceNameSet() {
		Set<String> set = new HashSet();

		set.add("com.tomneko.soulkingdom.view.battle.BattleStageController");

		set.add("com.tomneko.soulkingdom.view.battle.drawer.BattleButtonDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.BattleStageDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.CharaDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.DebugDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.EffectDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.EnemyDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.EnemyTeamDrawer");
		set.add("com.tomneko.soulkingdom.view.battle.drawer.PartyDrawer");

		set.add("com.tomneko.soulkingdom.view.battle.listener.CharaButtonListenerCreator");
		set.add("com.tomneko.soulkingdom.view.battle.listener.DebugButtonListenerCreator");
		set.add("com.tomneko.soulkingdom.view.battle.listener.PartyButtonListenerCreator");

		set.add("com.tomneko.soulkingdom.view.battle.manager.BattleActionManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.BattleButtonManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.BattleMemberManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.BattleStageManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.CharaManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.DebugManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.EffectManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.EnemyManager");
		set.add("com.tomneko.soulkingdom.view.battle.manager.PartyManager");

		set.add("com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder");
		set.add("com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator");
		set.add("com.tomneko.soulkingdom.view.battle.service.BattleProcessor");
		set.add("com.tomneko.soulkingdom.view.battle.service.DamageCalculator");
		set.add("com.tomneko.soulkingdom.view.battle.service.DrawingObjectListCreator");

		set.add("com.tomneko.soulkingdom.view.battle.stage.enemy.BattleEnemyGroup001Factory");

		set.add("com.tomneko.soulkingdom.view.battle.strategy.BattleEnemyFactory");

		set.add("com.tomneko.soulkingdom.view.battle.tactics.factory.WildWolfTacticsFactory");

		set.add("com.tomneko.soulkingdom.view.battle.tactics.service.TacticsExecutor");

		set.add("com.tomneko.soulkingdom.view.drawer.ButtonDrawer");
		set.add("com.tomneko.soulkingdom.view.drawer.HpBarDrawer");
		set.add("com.tomneko.soulkingdom.view.drawer.MovingObjectDrawer");
		set.add("com.tomneko.soulkingdom.view.drawer.TextDrawer");

		set.add("com.tomneko.soulkingdom.view.hander.TouchEventHandler");

		set.add("com.tomneko.soulkingdom.view.image.ImageHolder");

		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.ChangeFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.DodgeFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.GuardFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.SlashFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.SlashSFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.chara.SlashVFactory");

		set.add("com.tomneko.soulkingdom.view.moving.factory.common.BlinkFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.common.DamageTextFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.common.EyeCatchFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.common.ShakeFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.common.SimpleEffectFactory");

		set.add("com.tomneko.soulkingdom.view.moving.factory.enemy.AppearFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.enemy.SimpleAssultFactory");
		set.add("com.tomneko.soulkingdom.view.moving.factory.enemy.StraightAssultFactory");

		set.add("com.tomneko.soulkingdom.view.moving.factory.EnemySkillFactoryHolder");

		set.add("com.tomneko.soulkingdom.view.moving.service.MovingProcessor");

		set.add("com.tomneko.soulkingdom.view.service.ButtonDrawer");
		set.add("com.tomneko.soulkingdom.view.service.FpsManager");
		set.add("com.tomneko.soulkingdom.view.service.PaintHolder");
		set.add("com.tomneko.soulkingdom.view.service.ScaleCalculator");

		return set;
	}
}
