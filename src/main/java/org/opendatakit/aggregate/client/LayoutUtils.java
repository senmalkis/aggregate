package org.opendatakit.aggregate.client;

import static org.opendatakit.aggregate.buildconfig.BuildConfig.VERSION;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.UIObject;

public class LayoutUtils {
  private static String latestVersion;

  static native String getLatestVersion() /*-{
    var req = new XMLHttpRequest();
    req.open('GET', 'https://api.github.com/repos/opendatakit/aggregate/releases/latest', false);
    req.send(null);
    if (req.readyState === 4 && req.status === 200)
      return JSON.parse(req.responseText).tag_name;
  }-*/;

  static HTML buildVersionNote(UIObject parent) {
    if (latestVersion == null)
      latestVersion = getLatestVersion();
    String shortVersion = VERSION.contains("-") ? VERSION.substring(0, VERSION.indexOf("-")) : VERSION;
    String versionNote = shortVersion.equals(latestVersion)
        ? "You're up to date!"
        : "<a href=\"https://github.com/opendatakit/aggregate/releases/latest\" target=\"_blank\">Update available</a>";
    HTML html = new HTML("<small>" + shortVersion + " - " + versionNote + "</small>");
    Style style = html.getElement().getStyle();
    style.setProperty("position", "fixed");
    style.setProperty("bottom", "0");
    style.setProperty("left", "0");
    style.setProperty("padding", "5px 10px");
    style.setProperty("backgroundColor", "rgba(255,255,255,.9)");

    Style parentStyle = parent.getElement().getStyle();
    parentStyle.setProperty("paddingBottom", "40px");

    return html;
  }
}
