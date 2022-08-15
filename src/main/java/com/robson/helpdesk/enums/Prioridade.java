package com.robson.helpdesk.enums;

public enum Prioridade {

	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");
	
	private Integer key;
	private String value;
	
	private Prioridade(Integer key, String value) {
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
	
	public static Prioridade toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Prioridade x : Prioridade.values()) {
			if(cod.equals(x.getKey())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Prioridade inválida");
	}
}
