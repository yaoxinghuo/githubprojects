/*
 * Copyright (c) 2017.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.terrynow.vpnshortcut;

import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date May 22, 2014 10:33:00 AM
 * @description
 */
public class ShortcutCreateActivity extends Activity {
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent shortcutIntent = new Intent("android.net.vpn.SETTINGS");
		final ShortcutIconResource iconResource = ShortcutIconResource
				.fromContext(this, R.drawable.ic_vpn);
		final Intent intent = new Intent();
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getString(R.string.app_name));
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
		setResult(RESULT_OK, intent);
		// Toast.makeText(this, "shortcut created", Toast.LENGTH_SHORT).show();
		finish();
	}
}
