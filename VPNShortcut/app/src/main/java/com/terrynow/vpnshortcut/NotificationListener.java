/*
 * Copyright (c) 2018.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.terrynow.vpnshortcut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date 2018-4-7 08:33
 * @description
 */
@SuppressLint("OverrideAbstract")
public class NotificationListener extends NotificationListenerService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        if ("com.samsung.android.gearoplugin".equals(packageName)) {
            this.cancelNotification(sbn.getKey());
            Log.w("VPNShortcut", "removed notification, package: " + packageName);
        }
        super.onNotificationPosted(sbn);
    }
}
