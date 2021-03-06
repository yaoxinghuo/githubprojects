/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.s17fabu.vip.gwt.showcase.client.content.panels;

import net.s17fabu.vip.gwt.showcase.client.ContentWidget;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example file.
 */
public class CwVerticalSplitPanel extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants,
      ContentWidget.CwConstants {
    String cwVerticalSplitPanelDescription();

    String cwVerticalSplitPanelName();

    String cwVerticalSplitPanelText();
  }

  /**
   * An instance of the constants.
   */
  private CwConstants constants;

  /**
   * Constructor.
   * 
   * @param constants the constants
   */
  public CwVerticalSplitPanel(CwConstants constants) {
    super(constants);
    this.constants = constants;
  }

  @Override
  public String getDescription() {
    return constants.cwVerticalSplitPanelDescription();
  }

  @Override
  public String getName() {
    return constants.cwVerticalSplitPanelName();
  }

  /**
   * Initialize this example.
   */
  @Override
  public Widget onInitialize() {
    // Create a Vertical Split Panel
    VerticalSplitPanel vSplit = new VerticalSplitPanel();
    vSplit.ensureDebugId("cwVerticalSplitPanel");
    vSplit.setSize("500px", "350px");
    vSplit.setSplitPosition("30%");

    // Add some content
    String randomText = constants.cwVerticalSplitPanelText() + " ";
    for (int i = 0; i < 2; i++) {
      randomText += randomText;
    }
    vSplit.setTopWidget(new HTML(randomText));
    vSplit.setBottomWidget(new HTML(randomText));

    // Wrap the split panel in a decorator panel
    DecoratorPanel decPanel = new DecoratorPanel();
    decPanel.setWidget(vSplit);

    // Return the content
    return decPanel;
  }
}
