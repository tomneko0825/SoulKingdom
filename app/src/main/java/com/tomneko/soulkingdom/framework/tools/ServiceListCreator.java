package com.tomneko.soulkingdom.framework.tools;

import com.tomneko.soulkingdom.framework.Service;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * サービスの一覧を作成するためのクラス
 * <p>
 * Created by toyama on 2017/09/10.
 */
public class ServiceListCreator {

	private static final String PK_NAME = "com.tomneko.soulkingdom";

	public static void main(String... args) throws Exception {
		String packageName = PK_NAME;
		List<Class> classes = getClasses(packageName);
		for (Class clazz : classes) {
			System.out.println(clazz.getName());
		}

		System.out.println("----------------");

		List<Class> serviceClasses = getServiceClasses(classes);
		for (Class clazz : serviceClasses) {
			System.out.println(clazz.getName());
		}

		System.out.println("----------------");
		System.out.println("出力");
		System.out.println("----------------");

		printServiceClasses(serviceClasses);

	}

	/**
	 * サービスクラスを出力する
	 *
	 * @param serviceClasses
	 */
	private static void printServiceClasses(List<Class> serviceClasses) {

		String prefix = "set.add(\"";
		String postfix = "\");";

		String prePackageName = "";

		for (Class serviceClass : serviceClasses) {

			String packageName = serviceClass.getPackage().getName();
			String className = serviceClass.getName();

			// 前回のと違っていたら改行を入れる
			if (!prePackageName.equals("") && !prePackageName.equals(packageName)) {
				System.out.println("");
			}

			System.out.println(prefix + className + postfix);

			prePackageName = packageName;
		}
	}

	/**
	 * Serviceアノテーションがあるクラスの一覧を取得
	 *
	 * @return
	 */
	private static List<Class> getServiceClasses(List<Class> classes) {

		List<Class> retList = new ArrayList();

		for (Class clazz : classes) {

			Annotation serviceAnnnoataion = clazz.getAnnotation(Service.class);

			// サービスアノテーションがある
			if (serviceAnnnoataion != null) {
				retList.add(clazz);
			}
		}

		return retList;
	}

	/**
	 * パッケージ配下のクラスを取得
	 *
	 * @param packageName
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClassNotFoundException
	 */
	private static List<Class> getClasses(String packageName)
			throws IOException, URISyntaxException, ClassNotFoundException {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		// パッケージ名のファイルを取得
		Enumeration<URL> enumeration = cl.getResources(packageName.replace(".", "/"));

		List<Class> retList = new ArrayList<>();

		// 指定パッケージ名のファイルが複数ある可能性あり
		for (; enumeration.hasMoreElements(); ) {

			URL url = enumeration.nextElement();
			File dir = new File(url.getPath());

			// ディレクトリを走査
			for (File file : dir.listFiles()) {

				// ディレクトリの場合は再帰処理
				if (file.isDirectory()) {
					String dirPath = packageName + "." + file.getName();
					retList.addAll(getClasses(dirPath));
				}

				// ファイルの場合
				else {

					String path = file.getName();

					String name = path.substring(0, path.length() - 6);

					// $ファイルは除外
					if (path.endsWith(".class") && !path.contains("$")) {
						retList.add(Class.forName(packageName + "." + name));
					}
				}
			}
		}

		return retList;
	}
}
