package com.bb.beckn.common.model;

import java.util.List;

public class CacheModel {
	private String key;
	private List<String> value;

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof CacheModel))
			return false;
		CacheModel other = (CacheModel) o;
		if (!other.canEqual(this))
			return false;
		Object this$key = getKey(), other$key = other.getKey();
		if ((this$key == null) ? (other$key != null) : !this$key.equals(other$key))
			return false;
		List<String> this$value = (List<String>) getValue(), other$value = (List<String>) other.getValue();
		return !((this$value == null) ? (other$value != null) : !this$value.equals(other$value));
	}

	protected boolean canEqual(Object other) {
		return other instanceof CacheModel;
	}

	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $key = getKey();
		result = result * 59 + (($key == null) ? 43 : $key.hashCode());
		List<String> $value = (List<String>) getValue();
		return result * 59 + (($value == null) ? 43 : $value.hashCode());
	}

	public String toString() {
		return "CacheModel(key=" + getKey() + ", value=" + getValue() + ")";
	}

	public String getKey() {
		return this.key;
	}

	public List<String> getValue() {
		return this.value;
	}
}
