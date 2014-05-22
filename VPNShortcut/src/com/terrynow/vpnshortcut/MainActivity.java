package com.terrynow.vpnshortcut;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date May 22, 2014 10:12:21 AM
 * @description
 */
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent("android.net.vpn.SETTINGS");
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}
}
