package TstDataTable;

import java.util.List;

/**
 * 数据表，即是表格集合，为List类型，其中List中每一个对象是一个DataRow类(List)
 * @author Administrator
 *
 */
public class DataTable {
	
	List<DataRow> row;
	 
    public DataTable() {
 
    }
 
    public DataTable(List<DataRow> r) {
        row = r;
    }
 
    public List<DataRow> getRow() {
        return row;
    }
 
    public void setRow(List<DataRow> row) {
        this.row = row;
    }
}
