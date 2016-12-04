package net.eztest;

import java.util.logging.Logger;

import org.openqa.selenium.By;

public class EzTestUtils {
	private static Logger log = Logger.getLogger(EzTest.class.getCanonicalName());


	public static By buildBy(String selectorType, String selector) {
		if ("class".equals(selectorType)) {
			return By.className(selector);
		} else if ("css".equals(selectorType)) {
			return By.cssSelector(selector);
		} else if ("id".equals(selectorType)) {
			return By.id(selector);
		} else if ("linkText".equals(selectorType)) {
			return By.linkText(selector);
		} else if ("name".equals(selectorType)) {
			return By.name(selector);
		} else if ("partialLinkText".equals(selectorType)) {
			return By.partialLinkText(selector);
		} else if ("tag".equals(selectorType)) {
			return By.tagName(selector);
		} else if ("xpath".equals(selectorType)) {
			return By.xpath(selector);
		} else if ("partialText".equals(selectorType)) {
			return By.xpath("//*[contains(., \"" + selector + "\")]");
		} else if ("text".equals(selectorType)) {
			return By.xpath("//*[. = \"" + selector + "\"]");
		}
		return null;
	}
	
	/**
	 * Unescapes a string that contains standard Java escape sequences.
	 * <ul>
	 * <li><strong>&#92;b &#92;f &#92;n &#92;r &#92;t &#92;" &#92;'</strong> :
	 * BS, FF, NL, CR, TAB, double and single quote.</li>
	 * <li><strong>&#92;X &#92;XX &#92;XXX</strong> : Octal character
	 * specification (0 - 377, 0x00 - 0xFF).</li>
	 * <li><strong>&#92;uXXXX</strong> : Hexadecimal based Unicode character.</li>
	 * </ul>
	 * 
	 * @param st
	 *            A string optionally containing standard java escape sequences.
	 * @return The translated string.
	 */
	public static String unescapeJavaString(String st) {
		if(!st.startsWith("\"")) return st;
		
	    StringBuilder sb = new StringBuilder(st.length());

	    for (int i = 1; i < st.length()-1; i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st
	                    .charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            // Hex Unicode: u????
	            case 'u':
	                if (i >= st.length() - 5) {
	                    ch = 'u';
	                    break;
	                }
	                int code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
	                sb.append(Character.toChars(code));
	                i += 5;
	                continue;
	            }
	            i++;
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}

}