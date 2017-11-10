package com.tomneko.soulkingdom.view.moving.factory.common;

import android.graphics.Bitmap;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.service.BattlePositionCalculator;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.image.enums.EffectImageEnum;
import com.tomneko.soulkingdom.view.moving.MovingTargetFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingEffect;
import com.tomneko.soulkingdom.view.moving.model.MovingImageChanger;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.moving.model.MovingPattern;
import com.tomneko.soulkingdom.view.moving.model.MovingTarget;
import com.tomneko.soulkingdom.view.moving.model.enums.MovingEffectTargetType;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import static com.tomneko.soulkingdom.view.moving.model.enums.MovingType.EFFECT_MOVE;

/**
 * シンプルなエフェクトのファクトリ
 * <p>
 * Created by toyama on 2017/09/06.
 */
@Service
public class SimpleEffectFactory {

	@Inject
	private ScaleCalculator sc;

	@Inject
	private BattlePositionCalculator bpc;

	@Inject
	private ImageHolder imageHolder;

	/**
	 * MovingEffectTargetType依存のエフェクトを作成<br/>
	 * BY_IDは想定外
	 *
	 * @param invokerId
	 * @param eie
	 * @return
	 */
	public MovingEffect createStopEffectToTarget(String invokerId, EffectImageEnum eie, MovingEffectTargetType targetType) {
		return createStopEffectToTarget(invokerId, eie.getTileCount(), eie, targetType, null);
	}

	/**
	 * 指定されたIDの地点にエフェクトを作成
	 *
	 * @param invokerId
	 * @param step
	 * @param eie
	 * @return
	 */
	public MovingEffect createStopEffectToTarget(String invokerId, int step, EffectImageEnum eie, String targetId) {
		return createStopEffectToTarget(invokerId, step, eie, MovingEffectTargetType.ET_BY_ID, targetId);
	}

	/**
	 * MovingEffectTargetType依存のエフェクトを作成
	 *
	 * @param invokerId
	 * @param step
	 * @param eie
	 * @return
	 */
	private MovingEffect createStopEffectToTarget(String invokerId, int step, EffectImageEnum eie, MovingEffectTargetType targetType, String targetId) {

		// キャラの場合はデフォルト位置を設定する必要があるため
		if (targetType == MovingEffectTargetType.ET_CHARA) {
			return createStopEffectToChara(invokerId, step, eie);
		}

		Bitmap image = imageHolder.getEffectImage(eie);

		MovingObject mo = new MovingObject(invokerId);
		mo.setImage(image);

		mo.setTileCountX(eie.getTileCountX());
		mo.setTileCountY(eie.getTileCountY());

		MovingCombination mc = new MovingCombination("stopEffect");

		MovingTarget mt = new MovingTarget();
		mt.setSteps(step);
		mt.setPoints(new float[step + 1][2]);
		mt.setMovingPattern(MovingPattern.STAY);
		mt.setMovingType(EFFECT_MOVE);
		mc.addMovingTarget(mt);

		// １ステップごとのチェンジャー
		if (1 < mo.getTileCountX() || 1 < mo.getTileCountY()) {
			List<MovingImageChanger> changerList = createChangerList(eie);
			for (int i = 1; i <= changerList.size(); i++) {
				mt.putMovingImageChanger(i, changerList.get(i - 1));
			}
		}

		mo.setMovingCombination(mc);

		MovingEffect movingEffect = new MovingEffect(eie);
		movingEffect.setMovingObject(mo);
		movingEffect.setMovingEffectTargetType(targetType);
		if (targetType == MovingEffectTargetType.ET_BY_ID) {
			movingEffect.setMovingEffectTargetId(targetId);
		}

		return movingEffect;
	}

	/**
	 * 指定された地点の中央に表示
	 *
	 * @param invokerId
	 * @param rootX
	 * @param rootY
	 * @param step
	 * @param eie
	 * @return
	 */
	public MovingEffect createStopEffect(String invokerId, float rootX, float rootY, int step, EffectImageEnum eie) {

		Bitmap image = imageHolder.getEffectImage(eie);

		MovingObject mo = new MovingObject(invokerId);
		mo.setImage(image);

		mo.setTileCountX(eie.getTileCountX());
		mo.setTileCountY(eie.getTileCountY());

		float x = rootX - (mo.getTileWidth() / 2);
		float y = rootY - (mo.getTileHeight() / 2);

		mo.setBaseX(x);
		mo.setX(x);
		mo.setBaseY(y);
		mo.setY(y);

		MovingCombination mc = new MovingCombination("stopEffect");
		MovingTarget mt = MovingTargetFactory.createStay(x, y, step);
		mt.setMovingType(EFFECT_MOVE);
		mc.addMovingTarget(mt);

		// １ステップごとのチェンジャー
		if (1 < mo.getTileCountX() || 1 < mo.getTileCountY()) {
			List<MovingImageChanger> changerList = createChangerList(eie);
			for (int i = 1; i <= changerList.size(); i++) {
				mt.putMovingImageChanger(i, changerList.get(i - 1));
			}
		}

		mo.setMovingCombination(mc);

		MovingEffect movingEffect = new MovingEffect(eie);
		movingEffect.setMovingObject(mo);

		return movingEffect;
	}

	/**
	 * チェンジャーのリストを作成
	 *
	 * @param eie
	 * @return
	 */
	private List<MovingImageChanger> createChangerList(EffectImageEnum eie) {

		int tileCountX = eie.getTileCountX();
		int tileCountY = eie.getTileCountY();

		List<MovingImageChanger> changerList = new ArrayList();

		for (int y = 0; y < tileCountY; y++) {
			for (int x = 0; x < tileCountX; x++) {
				MovingImageChanger changer = new MovingImageChanger(x, y);
				changerList.add(changer);
			}
		}

		return changerList;
	}

	/**
	 * 指定されたMovingObjectの中央に表示
	 *
	 * @param targetMo
	 * @param step
	 * @param eie
	 * @return
	 */
	public MovingEffect createStopEffect(MovingObject targetMo, int step, EffectImageEnum eie) {

		// 基準となる位置
		float rootX = targetMo.getX() + targetMo.getTileWidth() / 2;
		float rootY = targetMo.getY() + targetMo.getTileHeight() / 2;

		MovingEffect effect = createStopEffect(targetMo.getInvokerId(), rootX, rootY, step, eie);

		return effect;
	}

	/**
	 * キャラの規定ポジションの中央に表示
	 *
	 * @param invokerId
	 * @param step
	 * @param eie
	 * @return
	 */
	private MovingEffect createStopEffectToChara(String invokerId, int step, EffectImageEnum eie) {

		// 基準となる位置
		float rootX = bpc.getCharaRootPositionX();
		float rootY = bpc.getCharaRootPositionY();

		MovingEffect effect = createStopEffect(invokerId, rootX, rootY, step, eie);
		effect.setMovingEffectTargetType(MovingEffectTargetType.ET_CHARA);
		return effect;
	}

}
