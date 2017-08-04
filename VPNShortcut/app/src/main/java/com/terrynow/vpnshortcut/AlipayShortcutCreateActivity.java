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
import android.net.Uri;
import android.os.Bundle;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date 2017-8-4 08:38
 * @description
 */
public class AlipayShortcutCreateActivity extends Activity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String uri = "alipayss://platformapi/startapp?appId=20000056&source=shortcut#Intent;" +
                "component=com.eg.android.AlipayGphone/.FastStartActivity;B.directly=true;B.fromDesktop=true;end";
        Uri uri1 = Uri.parse(uri);
        final Intent shortcutIntent = new Intent("android.intent.action.VIEW", uri1);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//268435456

        final Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource
                .fromContext(this, R.drawable.ic_alipay);
        final Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.alipay_pay));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        setResult(RESULT_OK, intent);

        // Toast.makeText(this, "shortcut created", Toast.LENGTH_SHORT).show();
        finish();
    }
}