package TstDataTable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * �����У����Ǳ���е�ÿһ�����ݵļ��ϣ�ΪList���ͣ�����List��ÿһ��������һ��DataColumn��(��ֵ��)
 * 
 * @author Administrator
 *
 */
public class DataRow {

	List<DataColumn> col;
	
	public DataRow(List<DataColumn> c) {
        col = c;
    }
 
    public List<DataColumn> getCol() {
        return col;
    }
 
    public void setCol(List<DataColumn> col) {
        this.col = col;
    }

	/**
	 * ����ָ��DataColumn���������ж���
	 * 
	 * @param colName ����
	 * @return
	 */
	public DataColumn getColumnObject(String colName) {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c;
				} catch (Exception e) {
					System.out.println("����������" + e.toString());
				}
			}
		}
		return null;
	}

	/**
	 * ����ָ��Object���������ж���
	 * @param colName ����
	 * @return
	 */
	public Object getColumn(String colName) {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c.getValue();
				} catch (Exception e) {
					System.out.println("����������" + e.toString());
				}
			}
		}
		return null;
	}
	
	/**
	 * ����ָ��int���������ж���
	 * @param colName
	 * @return
	 */
	 public int getIntColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Integer.parseInt(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("����������" + e.toString());
	                }
	            }
	        }
	        return 0;
	    }
	 
	 /**
	  * ����ָ��String���������ж���
	  * @param colName
	  * @return
	  */
	 public String getStringColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                	
	                	if(c.getValue()==null) {
	                		return "NULL";
	                	}
	                    return c.getValue().toString();
	                } catch (Exception e) {
	                    System.out.println("����������" + e.toString());
	                }
	 
	            }
	        }
	        return null;
	    }
	 
	 /**
	  * ����ָ��java.sql.Date���������ж���
	  * @param colName
	  * @return
	  */
	 public Date getDateColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Date.valueOf(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("����������" + e.toString());
	                }
	            }
	        }
	        return null;
	    }
	 
	 /**
	  * ����ָ��java.util.Date���������ж���
	  * @param colName
	  * @return
	  */
	 public java.util.Date getUtilDateColumn(String colName){
		 for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                	
	                	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                	java.util.Date date = dateFormat.parse(c.getValue().toString());
	                    return date;
	                } catch (Exception e) {
	                    System.out.println("����������" + e.toString());
	                }
	            }
	        }
	        return null;
	 }
	 
	 
	 /**
	  * ����ָ��float���������ж���
	  * @param colName
	  * @return
	  */
	 public float getFloatColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Float.parseFloat(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("����������" + e.toString());
	                }
	            }
	        }
	        return 0;
	    }

}
