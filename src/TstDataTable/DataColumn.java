package TstDataTable;

/**
 * ������--��Ԫ�������
 * ���ý�ֵ��˼�룬keyΪ������value��Ӧkeyֵ�ĵ�Ԫ��Ԫ�أ�ΪObject����
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
