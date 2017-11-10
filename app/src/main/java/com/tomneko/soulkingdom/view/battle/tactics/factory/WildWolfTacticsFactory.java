package com.tomneko.soulkingdom.view.battle.tactics.factory;

import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.tactics.Tactics;
import com.tomneko.soulkingdom.view.battle.tactics.TacticsPattern;
import com.tomneko.soulkingdom.view.moving.factory.enemy.SimpleAssultFactory;
import com.tomneko.soulkingdom.view.moving.factory.enemy.StraightAssultFactory;

/**
 * ワイルドウルフの戦術のファクトリ
 * <p/>
 * Created by toyama on 2017/10/05.
 */
@Service
public class WildWolfTacticsFactory implements TacticsFactory {

	@Override
	public Tactics create(int level) {

		Tactics tactics = new Tactics();

		// アサルト
		TacticsPattern p1 = new TacticsPattern(SimpleAssultFactory.class);
		tactics.addTacticsPattern(p1);

		// 突撃
		TacticsPattern p2 = new TacticsPattern(StraightAssultFactory.class);
		tactics.addTacticsPattern(p2);

		// アサルト×２
		TacticsPattern p3 = new TacticsPattern(SimpleAssultFactory.class);
		p3.addFactoryClassAndSteps(SimpleAssultFactory.class);
		tactics.addTacticsPattern(p3);

		return tactics;
	}

}
