package base.tool.excel.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;



/**
 * <p><b>Description:</b>题解析</p>
 *
 * @author sang
 * @date 2015-10-22下午4:18:59
 * @version 1.0
 */
public class ExcelParser {

	/**
	 * 读取Excel
     * @param path
     * @return
     */
    public static Workbook getWorkbook(String path) {
        Workbook workBook = FastExcel.createReadableWorkbook(new File(path));
        workBook.setSSTType(BIFFSetting.SST_TYPE_DEFAULT);// memory storage
        return workBook;
    }
	
	/**
	 * 获取Sheet内容
	 * @param path
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ExcelException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSheetRecords(Workbook workBook,Class<T> clazz) throws InstantiationException, IllegalAccessException, ExcelException {
		
		if ( !ISheetRecord.class.isAssignableFrom(clazz) ) {
			throw new RuntimeException("该方法clazz参数必须实现ISheetRecord接口");
		}

		List<ISheetRecord> records = new ArrayList<ISheetRecord>();
		
		if ( workBook == null || clazz == null) {
			//System.out.println("参数不能为空");
			return (List<T>) records;
		}
		
		ISheetRecord sheetRecord = (ISheetRecord)clazz.newInstance();
		Sheet sheet = workBook.getSheet(sheetRecord.getSheetIndex());
        
    	if ( sheet == null )
    		return (List<T>) records;
		
    	
    	int firstRow = sheet.getFirstRow();
        int lastRow = sheet.getLastRow();
        int firstColumn = sheet.getFirstColumn();
        
        //第一行为标题，从第二行获取数据
        for (int i = firstRow + 1; i < lastRow; i++) {
        	
        	ISheetRecord record = (ISheetRecord)clazz.newInstance();
        	if (sheet.getCell(i, firstColumn + 0) == null) {
                continue;
            }
        	record.setData(sheet.getRow(i));
        	records.add(record);
        }
        
        return (List<T>) records;
    	
    		
	}
}
