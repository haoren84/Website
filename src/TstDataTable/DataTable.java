package TstDataTable;

import java.util.List;

/**
 * ���ݱ����Ǳ�񼯺ϣ�ΪList���ͣ�����List��ÿһ��������һ��DataRow��(List)
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
