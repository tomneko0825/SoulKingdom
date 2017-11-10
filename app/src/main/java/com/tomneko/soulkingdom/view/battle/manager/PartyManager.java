package com.tomneko.soulkingdom.view.battle.manager;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.tomneko.soulkingdom.framework.Inject;
import com.tomneko.soulkingdom.framework.Service;
import com.tomneko.soulkingdom.view.battle.listener.PartyButtonListenerCreator;
import com.tomneko.soulkingdom.view.battle.model.BattleChara;
import com.tomneko.soulkingdom.view.battle.model.BattleMember;
import com.tomneko.soulkingdom.view.battle.model.BattleMemberDrawingObject;
import com.tomneko.soulkingdom.view.battle.model.BattleParty;
import com.tomneko.soulkingdom.view.battle.model.FlashStatusCounter;
import com.tomneko.soulkingdom.view.battle.model.enums.BattleMovingType;
import com.tomneko.soulkingdom.view.battle.model.enums.FlashStatusType;
import com.tomneko.soulkingdom.view.battle.service.BattleConstantsHolder;
import com.tomneko.soulkingdom.view.battle.service.DrawingObjectListCreator;
import com.tomneko.soulkingdom.view.button.ButtonObject;
import com.tomneko.soulkingdom.view.button.enums.ButtonStateType;
import com.tomneko.soulkingdom.view.drawer.ButtonDrawer;
import com.tomneko.soulkingdom.view.image.ImageHolder;
import com.tomneko.soulkingdom.view.image.enums.CharaImageEnum;
import com.tomneko.soulkingdom.view.moving.factory.chara.ChangeFactory;
import com.tomneko.soulkingdom.view.moving.model.MovingCombination;
import com.tomneko.soulkingdom.view.moving.model.MovingObject;
import com.tomneko.soulkingdom.view.service.ScaleCalculator;

import java.util.ArrayList;
import java.util.List;

import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_COUNT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.BLOCK_WIDHT;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_X;
import static com.tomneko.soulkingdom.view.battle.BattleStageConstatns.CHARA_BASE_Y;

/**
 * パーティの管理
 * <p/>
 * Created by toyama on 2017/09/02.
 */
@Service
public class PartyManager {

	public static final float STATUS_HEIGHT = 62;

	@Inject
	private ButtonDrawer buttonDrawer;

	@Inject
	private ScaleCalculator sc;

	@Inject
	private ImageHolder imageHolder;

	@Inject
	private DrawingObjectListCreator drawingObjectListCreator;

	@Inject
	private PartyButtonListenerCreator partyButtonListenerCreator;

	@Inject
	private ChangeFactory changeFactory;

	@Inject
	private BattleConstantsHolder bch;

	private BattleParty battleParty;

	private List<BattleMemberDrawingObject> drawingObjectList = new ArrayList();

	public List<BattleMemberDrawingObject> getDrawingObjectList() {
		return drawingObjectList;
	}

	public BattleParty getBattleParty() {
		return battleParty;
	}

	public void setBattleParty(BattleParty battleParty) {
		this.battleParty = battleParty;
	}

	/**
	 * パーティの表示部分を作成
	 */
	public void createParty() {

		float y = sc.getY(BLOCK_WIDHT * 2) * 7.5f;
		float height = sc.getY(STATUS_HEIGHT);

		List<BattleMember> list = new ArrayList();
		for (BattleMember battleMember : battleParty.getBattleCharaList()) {
			list.add(battleMember);
		}

		this.drawingObjectList = drawingObjectListCreator.createDrawingObjectList(list.size(), y, height, "party");

		// メンバーを設定
		for (int i = 0; i < this.drawingObjectList.size(); i++) {
			BattleMemberDrawingObject bmdo = this.drawingObjectList.get(i);
			bmdo.setBattleMember(list.get(i));
		}

		// リスナーを作成、登録
		partyButtonListenerCreator.createListener(drawingObjectList);
	}

	/**
	 * たぶんここじゃない
	 *
	 * @deprecated
	 */
	public void createChara() {

		// 戦闘パーティの作成
		battleParty = new BattleParty();
		List<BattleChara> battleCharaList = battleParty.getBattleCharaList();

		String id1 = "chara_001";
		String name1 = "イロハニホヘトチ";
		CharaImageEnum charaImageEnum1 = CharaImageEnum.CHARA_001;
		BattleChara battleChara1 = createBattleChara(id1, name1, charaImageEnum1);

		// 一人目をアクティブに
		// battleChara1.setActive(true);
		battleChara1.setBattleMovingType(BattleMovingType.ON_WAIT);
		battleCharaList.add(battleChara1);

		String id2 = "chara_002";
		String name2 = "イロハニホヘトチ";
		CharaImageEnum charaImageEnum2 = CharaImageEnum.CHARA_002;
		BattleChara battleChara2 = createBattleChara(id2, name2, charaImageEnum2);
		battleCharaList.add(battleChara2);

		String id3 = "chara_003";
		String name3 = "イロハニホヘトチ";
		CharaImageEnum charaImageEnum3 = CharaImageEnum.CHARA_003;
		BattleChara battleChara3 = createBattleChara(id3, name3, charaImageEnum3);
		battleCharaList.add(battleChara3);
	}

	/**
	 * 戦闘キャラの作成
	 *
	 * @param id
	 * @param name
	 * @param charaImageEnum
	 * @return
	 */
	@NonNull
	private BattleChara createBattleChara(String id, String name, CharaImageEnum charaImageEnum) {
		// TODO とりあえずのID
		MovingObject chara = new MovingObject(id);

		// 画像を読み込んでスケール計算してサイズ調整
		Bitmap bmp = imageHolder.getCharaImage(charaImageEnum);
		chara.setImage(bmp);

		float viewWidth = sc.getViewWidth();

		// 基準となる位置
		float rootX = viewWidth / BLOCK_COUNT * CHARA_BASE_X;
		float rootY = sc.getY(BLOCK_WIDHT) * CHARA_BASE_Y;

		float x = rootX - (bmp.getWidth() / 4 / 2);
		float y = rootY - bmp.getHeight() / 4;

		chara.setBaseX(x);
		chara.setX(x);
		chara.setBaseY(y);
		chara.setY(y);

		chara.setTileCountX(4);
		chara.setTileCountY(4);

		chara.setTileBaseX(0);
		chara.setTileX(0);
		chara.setTileBaseY(1);
		chara.setTileY(1);

		// 戦闘キャラの作成
		BattleChara battleChara = new BattleChara();
		battleChara.setId(chara.getInvokerId());
		battleChara.setName(name);
		battleChara.setMovingObject(chara);
		return battleChara;
	}

	/**
	 * 指定されたキャラにチェンジ
	 *
	 * @param nextBmdo
	 */
	public void changeChara(BattleMemberDrawingObject nextBmdo) {

		ButtonObject nexbBo = nextBmdo.getButtonObject();

		// 通常以外はなにもしない
		if (nexbBo.getButtonStateType() != ButtonStateType.NORMAL) {
			return;
		}

		// アクティブキャラのステータスによってはなにもしない
		BattleChara activeChara = battleParty.getCurrentBattleChara();
		BattleMovingType movingType = activeChara.getBattleMovingType();

		// 交代できるのは待ち状態と回避中
		if (movingType == BattleMovingType.ON_WAIT
				|| movingType == BattleMovingType.ON_DODGE) {
		} else {
			return;
		}

		BattleMemberDrawingObject activeBmdo = getBattleMemberDrawingObjectById(activeChara.getId());
		ButtonObject activeBo = activeBmdo.getButtonObject();
		activeBo.setButtonStateType(ButtonStateType.NORMAL);
		nexbBo.setButtonStateType(ButtonStateType.HIGHLIGHT);

		// 戦場から戻す
		backFromStage(activeChara, false);

		// 対象キャラを戦場へ
		BattleChara nextChara = (BattleChara) nextBmdo.getBattleMember();
		MovingObject nextMo = nextChara.getMovingObject();

		MovingCombination changeGo = changeFactory.createChangeGo(nextMo, nextMo.getBaseX(), nextMo.getBaseY(), bch.getStep());
		nextMo.setMovingCombination(changeGo);

		// フラッシュステータスの移動
		copyFlushStatus(activeChara, nextChara);
	}

	/**
	 * フラッシュステータスのコピー
	 *
	 * @param activeChara
	 * @param nextChara
	 */
	private void copyFlushStatus(BattleChara activeChara, BattleChara nextChara) {
		FlashStatusCounter guardCounter = activeChara.getFlashStatus().getCounter(FlashStatusType.GUARD_FLUSH);
		if (guardCounter != null) {
			nextChara.getFlashStatus().setFlashStatus(FlashStatusType.GUARD_FLUSH, guardCounter);
		}

		FlashStatusCounter dodgeCounter = activeChara.getFlashStatus().getCounter(FlashStatusType.DODGE_FLUSH);
		if (dodgeCounter != null) {
			nextChara.getFlashStatus().setFlashStatus(FlashStatusType.DODGE_FLUSH, dodgeCounter);
		}
	}

	/**
	 * 戦場から戻す
	 *
	 * @param battleChara
	 */
	private void backFromStage(BattleChara battleChara, boolean defeated) {

		MovingObject mo = battleChara.getMovingObject();
		BattleMemberDrawingObject bmdo = getBattleMemberDrawingObjectById(battleChara.getId());
		ButtonObject buttonObject = bmdo.getButtonObject();
		float left = buttonObject.getX();
		float top = buttonObject.getY() - sc.getY(45);

		// 戦闘不能の場合
		if (defeated) {
			// 裏にする
			mo.setTileX(0);
			mo.setTileY(3);
			mo.setX(left);
			mo.setY(top);
		}

		// 交代の場合
		else {
			MovingCombination changeBack = changeFactory.createChangeBack(mo, left, top, bch.getStep());
			mo.setMovingCombination(changeBack);
		}
	}

	/**
	 * 対象のキャラのIDを持つBattleMemberDrawingObjectを取得
	 *
	 * @param id
	 * @return
	 */
	private BattleMemberDrawingObject getBattleMemberDrawingObjectById(String id) {

		for (BattleMemberDrawingObject bmdo : drawingObjectList) {
			if (id.equals(bmdo.getBattleMember().getId())) {
				return bmdo;
			}
		}

		return null;
	}

	/**
	 * 戦闘不能処理
	 *
	 * @param id
	 */
	public void defeated(String id) {

		// TODO 全滅処理

		// 対象のボタンを取得
		BattleMemberDrawingObject targetBmdo = null;
		int targetIndex = -1;
		for (int i = 0; i < drawingObjectList.size(); i++) {
			BattleMemberDrawingObject bmdo = drawingObjectList.get(i);
			BattleMember bm = bmdo.getBattleMember();
			if (bm.getId().equals(id)) {
				targetIndex = i;
				targetBmdo = bmdo;
			}
		}

		if (targetBmdo == null) {
			throw new RuntimeException("invalid id:" + id);
		}

		BattleMemberDrawingObject nextBmdo = getNextBattleMemberDrawingObject(targetIndex, targetIndex);

		if (nextBmdo == null) {
			// TODO とりえあず
			throw new RuntimeException("全滅！");
		}

		// ボタンの選択状態を変更
		targetBmdo.getButtonObject().setButtonStateType(ButtonStateType.DISABLED);
		targetBmdo.getButtonObject().setEnabled(false);
		nextBmdo.getButtonObject().setButtonStateType(ButtonStateType.HIGHLIGHT);

		// 戦場から戻す
		BattleChara targetChara = (BattleChara) targetBmdo.getBattleMember();
		backFromStage(targetChara, true);

		// 対象キャラを戦場へ
		BattleChara battleChara = (BattleChara) nextBmdo.getBattleMember();
		MovingObject battleMo = battleChara.getMovingObject();

		MovingCombination changeGo = changeFactory.createChangeGo(battleMo, battleMo.getBaseX(), battleMo.getBaseY(), bch.getStep());
		battleMo.setMovingCombination(changeGo);
	}

	/**
	 * 指定されたインデックスの次のBattleMemberDrawingObjectを取得
	 *
	 * @param targetIndex
	 * @return
	 */
	private BattleMemberDrawingObject getNextBattleMemberDrawingObject(int targetIndex, int originalIndex) {

		// 次のインデックスを取得
		int nextIndex = targetIndex + 1;
		if (3 <= nextIndex) {
			nextIndex = 0;
		}

		// 次が最初のインデックスと同じなら全滅
		if (nextIndex == originalIndex) {
			return null;
		}

		BattleMemberDrawingObject nextBmdo = drawingObjectList.get(nextIndex);

		// ボタンが通常状態なら
		if (nextBmdo.getButtonObject().getButtonStateType() != ButtonStateType.DISABLED) {
			return nextBmdo;
		}

		return getNextBattleMemberDrawingObject(nextIndex, originalIndex);
	}
}
