package org.geogebra.web.html5.gui.textbox;

import org.geogebra.common.util.TextObject;
import org.geogebra.web.html5.gui.util.MathKeyboardListener;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This class is created so that the bluetooth keyboard works in Safari iOS.
 * 
 * @author Balazs
 */
public class GTextBox extends TextBox
		implements NativePreviewHandler, MathKeyboardListener, TextObject {
	// On iOS when using a bluetooth keyboard, the onkeyup event reports
	// the charcode to be 0. To solve this, we save the character code
	// in the onkeydown event, and we use that for the onkeyup

	protected int keyCode;
	protected boolean isControlKeyDown;
	protected boolean isAltKeyDown;
	protected boolean isShiftKeyDown;
	protected boolean isMetaKeyDown;
	private  boolean isFocused = false;

	public GTextBox(Element e) {
		super(e);
	}

	public GTextBox() {
		this(false);
	}

	/**
	 * @param autocomplete
	 *            allow browser autocomplete ?
	 */
	public GTextBox(boolean autocomplete) {
		Event.addNativePreviewHandler(this);

		if (!autocomplete) {
			// suggestion from here to disable autocomplete
			// https://code.google.com/p/google-web-toolkit/issues/detail?id=6065
			//
			// #3878
			getElement().setAttribute("autocomplete", "off");
			getElement().setAttribute("autocapitalize", "off");
		}
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		final KeyUpHandler finalHandler = handler;
		return super.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 0) {
					NativeEvent nativeEvent = Document.get().createKeyUpEvent(
					        isControlKeyDown, isAltKeyDown, isShiftKeyDown,
					        isMetaKeyDown, keyCode);
					event.setNativeEvent(nativeEvent);

				}
				finalHandler.onKeyUp(event);
			}
		});
	}

	@Override
	public void onPreviewNativeEvent(NativePreviewEvent event) {
		if (event.getTypeInt() == Event.ONKEYDOWN) {
			NativeEvent nativeEvent = event.getNativeEvent();
			keyCode = nativeEvent.getKeyCode();
			isAltKeyDown = nativeEvent.getAltKey();
			isShiftKeyDown = nativeEvent.getShiftKey();
			isControlKeyDown = nativeEvent.getCtrlKey();
			isMetaKeyDown = nativeEvent.getMetaKey();
		}
	}

	@Override
	public void ensureEditing() {
		this.setFocus(true);

	}

	@Override
	public void setFocus(boolean b) {
		super.setFocus(b);
		isFocused = b;
	}

	@Override
	public boolean needsAutofocus() {
		return false;
	}

	@Override
	public boolean hasFocus() {
		return isFocused;
	}

	@Override
	public void setEditable(boolean editable) {
		this.setReadOnly(!editable);
	}
}
