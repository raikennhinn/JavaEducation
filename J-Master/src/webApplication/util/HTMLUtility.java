package webApplication.util;

public class HTMLUtility {

	public static String escapeHtml(Object obj) {
		if (obj == null) {
			return "";
		}
		String str = obj.toString();
		str = str.replace("&", "&amp;");
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		str = str.replace("\"", "&quot;");
		str = str.replace("'", "&#39;");
		return str;
	}
}
