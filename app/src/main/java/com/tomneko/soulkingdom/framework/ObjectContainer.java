package com.tomneko.soulkingdom.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * DIのためのクラス
 * <p/>
 * Created by toyama on 2017/09/09.
 */
public class ObjectContainer {

	private Map<Class, Object> objectMap = new HashMap();

	/**
	 * コンテナ上にあるクラスを取得
	 *
	 * @param key
	 * @return
	 */
	public Object getObject(Class key) {
		return objectMap.get(key);
	}

	/**
	 * 初期化
	 */
	public void initialize() {

		try {

			// オブジェクトのマップを作製
			createObjectMap();

			// 依存性を注入
			inject();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 依存性を注入
	 *
	 * @throws IllegalAccessException
	 */
	private void inject() throws IllegalAccessException {
		for (Class key : objectMap.keySet()) {

			Object object = objectMap.get(key);

			// フィールドを取得
			for (Field field : object.getClass().getDeclaredFields()) {

				field.setAccessible(true);

				// Injectアノテーションがあるかどうか
				Annotation injectAnnotation = field.getAnnotation(Inject.class);
				if (injectAnnotation != null) {

					// あったら型のオブジェクトを注入
					Class targetType = field.getType();
					Object target = objectMap.get(targetType);

					// 対象がなかったらエラー
					if (target == null) {
						throw new RuntimeException("target not found. class [" + key + "] field [" + field + "]");
					}

					field.set(object, target);
				}
			}
		}
	}

	/**
	 * クラスとオブジェクトのマップを作成
	 *
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void createObjectMap() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Set<String> classNameSet = ServiceClassSet.createServiceNameSet();

		for (String className : classNameSet) {
			Class c = Class.forName(className);
			Object o = c.newInstance();

			objectMap.put(c, o);
		}

		// 自分も追加
		objectMap.put(ObjectContainer.class, this);
	}
}

