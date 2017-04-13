package base.tool.excel.parser;



/**
 * <p><b>Description:</b></p>
 *
 * @author sang
 * @date 2015-10-22下午5:05:00
 * @version 1.0
 */
public interface ISheetRecord {

	/**
	 * 获取Sheet索引
	 * @return
	 */
	int getSheetIndex();
	
	/**
	 * 赋值
	 * @param v
	 */
	void setData(String[] v);
}
