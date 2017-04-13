package base.expand;

import base.constant.BaseConstant;
import base.utils.BaseStringUtil;

import java.beans.PropertyEditorSupport;
import java.text.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * @author shsun
 */
public class MyCustomDateEditor extends PropertyEditorSupport {
	private DateFormat dateFormat = new SimpleDateFormat(BaseConstant.DATEFORMAT_PATTERN);

	public MyCustomDateEditor() {
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (BaseStringUtil.isBlank(text)) {
			this.setValue(null);
			return;
		}

		Date date = new Date();

		int textLength = text.length();
		if (textLength == BaseConstant.DATETIMEFORMAT_PATTERN.length()) {
			this.dateFormat = new SimpleDateFormat(BaseConstant.DATETIMEFORMAT_PATTERN);
			;
		}

		try {
			this.setValue(this.dateFormat.parse(text));
		} catch (ParseException var3) {
			throw new IllegalArgumentException("Could not parse date: " + var3.getMessage(), var3);
		}
	}

	public String getAsText() {
		Date value = (Date) this.getValue();
		return value != null ? this.dateFormat.format(value) : "";
	}
}
