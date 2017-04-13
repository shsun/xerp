package base.bean;

import java.io.Serializable;

import base.utils.BaseIdUtil;
import base.utils.BasePageUtil;

/**
 * @author shsun
 */
public class PageBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int from;// 分页查询开始记录位置
	private int to; // 分页查看下结束位置
	private int length;// 每页显示记录数
	private int pageNo; // 当前页码
	private int totalRow;// 查询结果总记录数
	private int totalPage;// 总共页数
	private int limit; // 最多显示多少个页，其他用省略号显示

	public PageBean() {
	}

	public PageBean(int length) {
		this.length = length;
		this.init();
	}

	public PageBean(int length, int pageNo) {
		this.length = length;
		this.pageNo = pageNo;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void init(int count) {
		if (count == 0) {
			return;
		}
		this.totalRow = count;
		this.totalPage = BasePageUtil.getTotalPage(count, length);
		if (this.pageNo > totalPage && totalPage != 0) {
			this.pageNo = pageNo % totalPage;
		}
		if (this.pageNo < 1) {
			this.pageNo = 1;
		}
		this.from = (this.pageNo - 1) * this.length;
		this.to = this.from + this.length;
	}

	public void init(String ids) {
		int count = BaseIdUtil.getIntArrayLength(ids);
		init(count);
	}

	public void init() {
		if (this.length < 1) {
			this.length = 20;
		}

		if (this.pageNo < 1) {
			// 没指定 页码
			this.to = this.from + this.length;
			this.pageNo = 1;
		} else {
			// 指定了 页码
			this.from = (this.pageNo - 1) * this.length;
			this.to = this.from + this.length;
		}
	}
	//
	// /**
	// * 构造函数
	// *
	// * @param from
	// * @param length
	// */
	// public PageBean(int from, int length) {
	// this.from = from;
	// this.length = length;
	// this.to = this.from + this.length;
	// this.pageNo = (int) Math.floor((this.from * 1.0d) / this.length) + 1;
	// }

	/**
	 * @param from
	 * @param length
	 * @param totalRow
	 */
	public PageBean(int from, int length, int totalRow) {
		this(from, length);
		this.totalRow = totalRow;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(int from) {
		this.from = from;
		if (this.length != 0) {
			this.pageNo = (int) Math.floor((this.from * 1.0d) / this.length) + 1;
		}
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		if (length == 0) {
			length = 20;
		}
		this.length = length;
		if (this.from != 0) {
			this.pageNo = (int) Math.floor((this.from * 1.0d) / this.length) + 1;
		}
	}

	/**
	 * @return the totalRow
	 */
	public int getTotalRow() {
		return totalRow;
	}

	/**
	 * @param totalRow
	 *            the totalRow to set
	 */
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		if (this.length == 0) {
			this.length = 20;
		}
		this.totalPage = BasePageUtil.getTotalPage(this.totalRow, this.length);
		if (this.pageNo > this.totalPage) {
			this.pageNo = this.totalPage;
		}
		this.init();
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		if (totalPage == 0) {
			return 1;
		}
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 后台分页用
	 *
	 * @return
	 */
	public String getNewPageBar() {
		StringBuffer str = new StringBuffer(1024);
		str.append(
				"<table class=\"page-table\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"><tr><td class='right'><div>");
		if (pageNo > 1) {
			str.append(
					"<a href=\"#\" class='page' onclick='$(\"#pageNo\").val(1);document.forms[0].submit();'>首页</a>&nbsp;");
		} else {
			str.append("<a href=\"#\" class='page'>首页</a>&nbsp;");
		}
		if (pageNo > 1) {
			str.append("<a href=\"#\" class='page' onclick='$(\"#pageNo\").val(" + (pageNo - 1)
					+ ");document.forms[0].submit();'>上一页</a>&nbsp;");
		} else {
			str.append("<a href=\"#\" class='page'>上一页</a>&nbsp;");
		}

		if (limit == 0) {
			limit = 15;
		}

		if (totalPage <= limit) {
			str.append(getStrFromTo(pageNo, 1, totalPage));
		} else {
			if (pageNo <= limit) {
				// 当前页足够小
				str.append(getStrFromTo(pageNo, 1, limit + 1));
				if (limit + 2 < totalPage) {
					str.append(getStrHellip(limit + 2));
				}
				str.append(getStrOne(totalPage));
			} else if (pageNo > totalPage - limit) {
				// 页面足够大
				str.append(getStrOne(1));

				if (totalPage - limit > 2) {
					str.append(getStrHellip(2));
				}
				str.append(getStrFromTo(pageNo, totalPage - limit, totalPage));
			} else {
				// 当前页显示在可见区中间
				str.append(getStrOne(1));

				int leftPageNo = pageNo - limit / 2;
				int rightPageNo = pageNo + (limit - limit / 2);

				if (leftPageNo > 2) {
					str.append(getStrHellip(2));
				}
				str.append(getStrFromTo(pageNo, leftPageNo, rightPageNo));
				if (rightPageNo + 1 < totalPage) {
					str.append(getStrHellip(rightPageNo + 1));
				}
				str.append(getStrOne(totalPage));
			}

		}

		if (pageNo < totalPage) {
			str.append("<a href=\"#\" class='page' onclick='$(\"#pageNo\").val(" + (pageNo + 1)
					+ ");document.forms[0].submit();'>下一页</a>&nbsp;");
		} else {
			str.append("<a href=\"#\" class='page'>下一页</a>&nbsp;");
		}
		if (totalPage > 1 && pageNo != totalPage) {
			str.append("<a href=\"#\" class='page' onclick='$(\"#pageNo\").val(" + totalPage
					+ ");document.forms[0].submit();'>末页</a>&nbsp;");
		} else {
			str.append("<a href=\"#\" class='page'>末页</a>&nbsp;");
		}

		str.append(
				"<span class=\"pages\">每页<SELECT class='page_input' name=\"length\" id=\"length\" onchange='$(\"#pageNo\").val(1);document.forms[0].submit();'>");

		str.append("<OPTION value='" + this.getLength() + "'>" + this.getLength() + "</OPTION>");
		if (this.getLength() != 20) {
			str.append("<OPTION value='20'>20</OPTION>");
		}
		if (this.getLength() != 50) {
			str.append("<OPTION value='50'>50</OPTION>");
		}
		if (this.getLength() != 100) {
			str.append("<OPTION value='100'>100</OPTION>");
		}
		if (this.getLength() != 200) {
			str.append("<OPTION value='200'>200</OPTION>");
		}
		if (this.getLength() != 500) {
			str.append("<OPTION value='500'>500</OPTION>");
		}
		if (this.getLength() != 1000) {
			str.append("<OPTION value='1000'>1000</OPTION>");
		}
		str.append("</SELECT>");
		str.append("条,（共" + this.totalRow + "条）</span>&nbsp;<span>转到");

		str.append(
				"<SELECT name=\"Pagelist\" class='page_input' onchange='$(\"#pageNo\").val(this.value);document.forms[0].submit();'>");
		if (totalPage > 300) {
			totalPage = 300;
		}
		for (int i = 1; i < totalPage + 1; i++) {
			if (i == pageNo) {
				str.append("<OPTION value=" + i + " selected>" + i + "</OPTION>");
			} else {
				str.append("<OPTION value=" + i + ">" + i + "</OPTION>");
			}
		}
		str.append("</SELECT>页&nbsp;&nbsp;</span></td></tr></table>");

		str.append("<script type=\"text/javascript\">$('#length').val(" + length + ") </script>");
		str.append("<INPUT type='hidden'  value='" + pageNo + "' name=\"pageNo\" id=\"pageNo\" > ");
		str.append("<INPUT type='hidden'  value='" + totalPage + "' name=\"totalPage\"> ");

		return "<div class=\"pageblk\">" + str.toString() + "</div>";
	}

	// -------------- 辅助方法 -------------------------------

	private String getStrIndex(int pageNo, int i) {
		String str = "";
		if (pageNo == i) {
			str = "<a style=\"cursor:pointer;\" class=\"on\" onclick='$(\"#pageNo\").val(" + i
					+ ");document.forms[0].submit();' >" + i + "</a>";
		} else {
			str = "<a style=\"cursor:pointer;\" onclick='$(\"#pageNo\").val(" + i + ");document.forms[0].submit();' >"
					+ i + "</a>";
		}

		// System.out.println("第" + i + "页");
		return str;
	}

	private String getStrOne(int i) {
		String str = "<a style=\"cursor:pointer;\" onclick='$(\"#pageNo\").val(" + i
				+ ");document.forms[0].submit();' >" + i + "</a>";
		return str;
	}

	private String getStrFromTo(int pageNo, int from, int to) {
		String str = "";
		for (int i = from; i <= to; i++) {
			str += getStrIndex(pageNo, i);
		}
		return str;
	}

	private String getStrHellip(int i) {
		String str = "<span>...</span>";
		// System.out.println("省略号");
		return str;
	}

}