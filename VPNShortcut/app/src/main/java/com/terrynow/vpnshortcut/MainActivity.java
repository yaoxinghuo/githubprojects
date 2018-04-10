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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("android.net.vpn.SETTINGS");
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        startService(new Intent(this, NotificationListener.class));
        finish();
    }
}
