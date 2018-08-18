package TstDataTable;

/**
 * 数据列--单元格的数据
 * 采用健值对思想，key为列名，value对应key值的单元格元素，为Object类型
 * @author Administrator
 *
 */
public class DataColumn {
	String key;
    Object value;
 
    public DataColumn(String k, Object v) {
        key = k;
        value = v;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
