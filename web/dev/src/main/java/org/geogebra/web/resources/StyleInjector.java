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
package org.geogebra.web.resources;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLLinkElement;
import elemental2.dom.HTMLStyleElement;


/**
 * Injects stylesheets to the parent document
 */
public class StyleInjector {

	public static final String CLASSNAME = "ggw_resource";

	private static List<String> stylesInLoading = new ArrayList<>();
	private static List<Runnable> onStylesReady = new ArrayList<>();

	/**
	 * @param baseUrl (relative or absolute) base url of css file
	 * @param name name of the css file, without extension
	 */
	public static void inject(String baseUrl, String name) {
		if (DOM.getElementById(name) == null) {
			HTMLLinkElement element
					= (HTMLLinkElement) DomGlobal.document.createElement("link");

			stylesInLoading.add(name);
			element.onload = (e) -> {
				stylesInLoading.remove(name);
				checkIfAllStylesLoaded();
			};

			element.className = CLASSNAME;
			element.id = name;
			element.rel = "stylesheet";
			element.type = "text/css";
			element.href = GWT.getModuleBaseURL() + "../" + baseUrl + "/" + name + ".css";
			DomGlobal.document.head.appendChild(element);
		}
	}

	private static void checkIfAllStylesLoaded() {
		if (stylesInLoading.isEmpty()) {
			for (Runnable r : onStylesReady) {
				r.run();
			}
			onStylesReady.clear();
		}
	}

	public static HTMLStyleElement injectStyleSheet(String style) {
		HTMLStyleElement element
				= (HTMLStyleElement) DomGlobal.document.createElement("style");
		element.className = CLASSNAME;
		element.innerHTML = style;
		return element;
	}

	public static void onStylesLoaded(Runnable runnable) {
		onStylesReady.add(runnable);
		checkIfAllStylesLoaded();
	}
}
