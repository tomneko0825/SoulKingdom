package com.tomneko.soulkingdom.view.moving.factory.enemy;

import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;

/**
 * 敵スキルのインタフェース
 *
 * Created by toyama on 2017/10/04.
 */
public interface EnemySkillFactory {

	MovingCombination create(MovingObject mo);

	MovingCombination create(MovingObject mo, int[] steps);

}
