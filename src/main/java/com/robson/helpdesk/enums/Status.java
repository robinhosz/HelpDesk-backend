package com.robson.helpdesk.enums;

public enum Status {

	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");
	
	private Integer key;
	private String value;
	
	private Status(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static Status toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Status x : Status.values()) {
			if(cod.equals(x.getKey())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status inválido");
	}
}
