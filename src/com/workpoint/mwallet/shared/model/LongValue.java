package com.workpoint.mwallet.shared.model;

public class LongValue implements Value{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long value;
	private String key;
	private Long id;
	
	public LongValue() {
	}
	
	public LongValue(Number value){
		this();
		if(value!=null){
			this.value = value.longValue();
		}else{
			this.value=null;
		}
	}
	
	public LongValue(Long id, String key, Number val){
		this.id=id;
		this.key=key;
		setValue(val);
	}
	
	@Override
	public void setValue(Object val) {
		if(val!=null){
			this.value=((Number)val).longValue();
		}else{
			this.value=null;
		}
		
	}

	@Override
	public Object getValue() {
		
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public DataType getDataType() {
		return DataType.INTEGER;
	}

	public LongValue clone(boolean fullClone){
		Long identify=null;
		if(fullClone){
			identify = id;
		}
		LongValue lvalue = new LongValue(identify, key, value);
		return lvalue;
	}
	
	@Override
	public String toString() {
		return value==null? "NULL": value.toString();
	}
}
