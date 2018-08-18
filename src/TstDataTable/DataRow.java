package TstDataTable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 数据行，即是表格中的每一行数据的集合，为List类型，其中List中每一个对象是一个DataColumn类(键值对)
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
	 * 返回指定DataColumn类型数据列对象
	 * 
	 * @param colName 列名
	 * @return
	 */
	public DataColumn getColumnObject(String colName) {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c;
				} catch (Exception e) {
					System.out.println("错误描述：" + e.toString());
				}
			}
		}
		return null;
	}

	/**
	 * 返回指定Object类型数据列对象
	 * @param colName 列名
	 * @return
	 */
	public Object getColumn(String colName) {
		for (DataColumn c : col) {
			if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
				try {
					return c.getValue();
				} catch (Exception e) {
					System.out.println("错误描述：" + e.toString());
				}
			}
		}
		return null;
	}
	
	/**
	 * 返回指定int类型数据列对象
	 * @param colName
	 * @return
	 */
	 public int getIntColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Integer.parseInt(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("错误描述：" + e.toString());
	                }
	            }
	        }
	        return 0;
	    }
	 
	 /**
	  * 返回指定String类型数据列对象
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
	                    System.out.println("错误描述：" + e.toString());
	                }
	 
	            }
	        }
	        return null;
	    }
	 
	 /**
	  * 返回指定java.sql.Date类型数据列对象
	  * @param colName
	  * @return
	  */
	 public Date getDateColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Date.valueOf(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("错误描述：" + e.toString());
	                }
	            }
	        }
	        return null;
	    }
	 
	 /**
	  * 返回指定java.util.Date类型数据列对象
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
	                    System.out.println("错误描述：" + e.toString());
	                }
	            }
	        }
	        return null;
	 }
	 
	 
	 /**
	  * 返回指定float类型数据列对象
	  * @param colName
	  * @return
	  */
	 public float getFloatColumn(String colName) {
	        for (DataColumn c : col) {
	            if (c.getKey().toUpperCase().equals(colName.toUpperCase())) {
	                try {
	                    return Float.parseFloat(c.getValue().toString());
	                } catch (Exception e) {
	                    System.out.println("错误描述：" + e.toString());
	                }
	            }
	        }
	        return 0;
	    }

}
