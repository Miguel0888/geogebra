package org.geogebra.web.html5.gui.util;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Accessible menu item: use &lt;li&gt; instead of &lt;td&gt; as a tag
 *
 */
public class AriaMenuItem extends SimplePanel {
	private ScheduledCommand cmd;
	private AriaMenuBar submenu;
	private boolean enabled = true;

	/**
	 * @param text
	 *            content
	 * @param asHTML
	 *            whether to use it as raw HTML
	 * @param cmd
	 *            command to run when clicked
	 */
	public AriaMenuItem(String text, boolean asHTML, ScheduledCommand cmd) {
		this();
		setContent(text, asHTML);
		this.cmd = cmd;
	}

	/**
	 * @param text
	 *            content
	 * @param asHTML
	 *            whether to use it as raw HTML
	 * @param submenu
	 *            submenu to open when clicked
	 */
	public AriaMenuItem(String text, boolean asHTML, AriaMenuBar submenu) {
		this();
		setContent(text, asHTML);
		this.submenu = submenu;
	}

	/**
	 * Constructor
	 */
	public AriaMenuItem() {
		super(Document.get().createLIElement());
		getElement().setClassName("gwt-MenuItem listMenuItem");
		getElement().setAttribute("role", "menuitem");
		getElement().setTabIndex(0);
	}

	/**
	 * @param text
	 *            content
	 * @param asHTML
	 *            whether to parse it as HTML
	 */
	public void setContent(String text, boolean asHTML) {
		if (asHTML) {
			getElement().setInnerHTML(text);
		} else {
			getElement().setInnerText(text);
		}
	}

	/**
	 * @return command
	 */
	public ScheduledCommand getScheduledCommand() {
		return cmd;
	}

	/**
	 * @param enabled
	 *            whether this button is active
	 */
	public void setEnabled(boolean enabled) {
		if (enabled) {
			removeStyleName("gwt-MenuItem-disabled");
		} else {
			addStyleName("gwt-MenuItem-disabled");
		}
		this.enabled = enabled;
	}

	/**
	 * @param cmd
	 *            command to run when clicked
	 */
	public void setScheduledCommand(ScheduledCommand cmd) {
		this.cmd = cmd;
	}

	/**
	 * @return submenu
	 */
	public AriaMenuBar getSubMenu() {
		return submenu;
	}

	/**
	 * @return content as HTML
	 */
	public String getHTML() {
		return getElement().getInnerHTML();
	}

	/**
	 * @param string
	 *            content as HTML
	 */
	public void setHTML(String string) {
		setContent(string, true);
	}

	/**
	 * @return whether the item is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
}
