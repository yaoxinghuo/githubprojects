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
import android.os.Bundle;

import java.net.URISyntaxException;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date 2017-8-4 08:19
 * @description
 */
public class WechatScanShortcutCreateActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String uri = "#Intent;action=com.tencent.mm.action.BIZSHORTCUT;launchFlags=0x4000000;" +
                "B.LauncherUI.From.Scaner.Shortcut=true;end";
        try {
            final Intent shortcutIntent = Intent.parseUri(uri, 0);
            shortcutIntent.addFlags(270532608);

            final Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource
                    .fromContext(this, R.drawable.ic_wechat_scan);
            final Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.wechat_scan_name));
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
            setResult(RESULT_OK, intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // Toast.makeText(this, "shortcut created", Toast.LENGTH_SHORT).show();
        finish();
    }
}