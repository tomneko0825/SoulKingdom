package com.tomneko.soulkingdom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.tomneko.soulkingdom.view.MainView;

/**
 * Created by toyama on 2017/08/31.
 */
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new MainView(this));
	}
}
