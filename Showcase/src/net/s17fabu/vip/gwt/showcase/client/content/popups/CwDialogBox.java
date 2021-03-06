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
package net.s17fabu.vip.gwt.showcase.client.content.popups;

import net.s17fabu.vip.gwt.showcase.client.ContentWidget;
import net.s17fabu.vip.gwt.showcase.client.Showcase;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example file.
 */
public class CwDialogBox extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants,
      ContentWidget.CwConstants {
    String cwDialogBoxCaption();

    String cwDialogBoxClose();

    String cwDialogBoxDescription();

    String cwDialogBoxDetails();

    String cwDialogBoxItem();

    String cwDialogBoxListBoxInfo();

    String cwDialogBoxMakeTransparent();

    String cwDialogBoxName();

    String cwDialogBoxShowButton();
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
  public CwDialogBox(CwConstants constants) {
    super(constants);
    this.constants = constants;
  }

  @Override
  public String getDescription() {
    return constants.cwDialogBoxDescription();
  }

  @Override
  public String getName() {
    return constants.cwDialogBoxName();
  }

  /**
   * Initialize this example.
   */
  @Override
  public Widget onInitialize() {
    // Create the dialog box
    final DialogBox dialogBox = createDialogBox();
    dialogBox.setAnimationEnabled(true);

    // Create a button to show the dialog Box
    Button openButton = new Button(constants.cwDialogBoxShowButton(),
        new ClickHandler() {
          public void onClick(ClickEvent sender) {
            dialogBox.center();
            dialogBox.show();
          }
        });

    // Create a ListBox
    HTML listDesc = new HTML("<br><br><br>"
        + constants.cwDialogBoxListBoxInfo());

    ListBox list = new ListBox();
    list.setVisibleItemCount(1);
    for (int i = 10; i > 0; i--) {
      list.addItem(constants.cwDialogBoxItem() + " " + i);
    }

    // Add the button and list to a panel
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.setSpacing(8);
    vPanel.add(openButton);
    vPanel.add(listDesc);
    vPanel.add(list);

    // Return the panel
    return vPanel;
  }

  /**
   * Create the dialog box for this example.
   * 
   * @return the new dialog box
   */
  private DialogBox createDialogBox() {
    // Create a dialog box and set the caption text
    final DialogBox dialogBox = new DialogBox();
    dialogBox.ensureDebugId("cwDialogBox");
    dialogBox.setText(constants.cwDialogBoxCaption());

    // Create a table to layout the content
    VerticalPanel dialogContents = new VerticalPanel();
    dialogContents.setSpacing(4);
    dialogBox.setWidget(dialogContents);

    // Add some text to the top of the dialog
    HTML details = new HTML(constants.cwDialogBoxDetails());
    dialogContents.add(details);
    dialogContents.setCellHorizontalAlignment(details,
        HasHorizontalAlignment.ALIGN_CENTER);

    // Add an image to the dialog
    Image image = Showcase.images.jimmy().createImage();
    dialogContents.add(image);
    dialogContents.setCellHorizontalAlignment(image,
        HasHorizontalAlignment.ALIGN_CENTER);

    // Add a close button at the bottom of the dialog
    Button closeButton = new Button(constants.cwDialogBoxClose(),
        new ClickHandler() {
          public void onClick(ClickEvent event) {
            dialogBox.hide();
          }
        });
    dialogContents.add(closeButton);
    if (LocaleInfo.getCurrentLocale().isRTL()) {
      dialogContents.setCellHorizontalAlignment(closeButton,
          HasHorizontalAlignment.ALIGN_LEFT);

    } else {
      dialogContents.setCellHorizontalAlignment(closeButton,
          HasHorizontalAlignment.ALIGN_RIGHT);
    }

    // Return the dialog box
    return dialogBox;
  }
}
