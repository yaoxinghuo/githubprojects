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
package net.s17fabu.vip.gwt.showcase.client;

import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwConstantsExample;
import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwConstantsWithLookupExample;
import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwDateTimeFormat;
import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwDictionaryExample;
import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwMessagesExample;
import net.s17fabu.vip.gwt.showcase.client.content.i18n.CwNumberFormat;
import net.s17fabu.vip.gwt.showcase.client.content.lists.CwListBox;
import net.s17fabu.vip.gwt.showcase.client.content.lists.CwMenuBar;
import net.s17fabu.vip.gwt.showcase.client.content.lists.CwStackPanel;
import net.s17fabu.vip.gwt.showcase.client.content.lists.CwSuggestBox;
import net.s17fabu.vip.gwt.showcase.client.content.lists.CwTree;
import net.s17fabu.vip.gwt.showcase.client.content.other.CwAnimation;
import net.s17fabu.vip.gwt.showcase.client.content.other.CwCookies;
import net.s17fabu.vip.gwt.showcase.client.content.other.CwFrame;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwAbsolutePanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwDecoratorPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwDisclosurePanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwDockPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwFlowPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwHorizontalPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwHorizontalSplitPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwTabPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwVerticalPanel;
import net.s17fabu.vip.gwt.showcase.client.content.panels.CwVerticalSplitPanel;
import net.s17fabu.vip.gwt.showcase.client.content.popups.CwBasicPopup;
import net.s17fabu.vip.gwt.showcase.client.content.popups.CwDialogBox;
import net.s17fabu.vip.gwt.showcase.client.content.tables.CwFlexTable;
import net.s17fabu.vip.gwt.showcase.client.content.tables.CwGrid;
import net.s17fabu.vip.gwt.showcase.client.content.text.CwBasicText;
import net.s17fabu.vip.gwt.showcase.client.content.text.CwRichText;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwBasicButton;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwCheckBox;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwCustomButton;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwDatePicker;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwFileUpload;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwHyperlink;
import net.s17fabu.vip.gwt.showcase.client.content.widgets.CwRadioButton;

import com.google.gwt.i18n.client.Constants;

/**
 * Constants used throughout the showcase.
 */
public interface ShowcaseConstants extends Constants,
    ContentWidget.CwConstants, CwCheckBox.CwConstants,
    CwRadioButton.CwConstants, CwBasicButton.CwConstants,
    CwCustomButton.CwConstants, CwListBox.CwConstants,
    CwSuggestBox.CwConstants, CwTree.CwConstants, CwMenuBar.CwConstants,
    CwFlowPanel.CwConstants, CwDisclosurePanel.CwConstants,
    CwTabPanel.CwConstants, CwDockPanel.CwConstants,
    CwHorizontalPanel.CwConstants, CwHorizontalSplitPanel.CwConstants,
    CwVerticalPanel.CwConstants, CwVerticalSplitPanel.CwConstants,
    CwBasicPopup.CwConstants, CwDialogBox.CwConstants, CwGrid.CwConstants,
    CwFlexTable.CwConstants, CwBasicText.CwConstants, CwRichText.CwConstants,
    CwFileUpload.CwConstants, CwAbsolutePanel.CwConstants,
    CwHyperlink.CwConstants, CwFrame.CwConstants, CwStackPanel.CwConstants,
    CwCookies.CwConstants, CwNumberFormat.CwConstants,
    CwDateTimeFormat.CwConstants, CwMessagesExample.CwConstants,
    CwConstantsExample.CwConstants, CwConstantsWithLookupExample.CwConstants,
    CwDictionaryExample.CwConstants, CwDecoratorPanel.CwConstants,
    CwAnimation.CwConstants, CwDatePicker.CwConstants {

  /**
   * The path to source code for examples, raw files, and style definitions.
   */
  String DST_SOURCE = "gwtShowcaseSource/";

  /**
   * The destination folder for parsed source code from Showcase examples.
   */
  String DST_SOURCE_EXAMPLE = DST_SOURCE + "java/";

  /**
   * The destination folder for raw files that are included in entirety.
   */
  String DST_SOURCE_RAW = DST_SOURCE + "raw/";

  /**
   * The destination folder for parsed CSS styles used in Showcase examples.
   */
  String DST_SOURCE_STYLE = DST_SOURCE + "css/";

  /**
   * Link to GWT homepage.
   */
  String HOMEPAGE = "http://www.17fabu.net/";

  /**
   * The available style themes that the user can select.
   */
  String[] STYLE_THEMES = {"standard", "chrome", "dark"};

  String categoryI18N();

  String categoryLists();

  String categoryOther();

  String categoryPanels();

  String categoryPopups();

  String categoryTables();

  String categoryTextInput();

  String categoryWidgets();

  /**
   * @return text for the link to more examples
   */
  String mainLinkExamples();

  /**
   * @return text for the link to the GWT homepage
   */
  String mainLinkHomepage();

  /**
   * @return the title of the main menu
   */
  String mainMenuTitle();

  /**
   * @return the sub title of the application
   */
  String mainSubTitle();

  /**
   * @return the title of the application
   */
  String mainTitle();
  
  /**
   * @return the label for choosing locale
   */
  String chooseLocale();
}
