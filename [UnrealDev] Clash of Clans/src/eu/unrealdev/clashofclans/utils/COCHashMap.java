package eu.unrealdev.clashofclans.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class COCHashMap<KeyType, ValueType> {

	
	private HashMap<KeyType, ValueType> wrappedHashMap = new HashMap<KeyType, ValueType>();
	
	
	public boolean containsKey(KeyType key) {
		
		return wrappedHashMap.containsKey(key);
	}
	
	
	public boolean containsValue(ValueType value) {
		
		return wrappedHashMap.containsValue(value);
	}
	
	
	public Set<Entry<KeyType, ValueType>> entrySet() {
		
		return wrappedHashMap.entrySet();
	}
	
	
	public Set<KeyType> keySet() {
		
		return wrappedHashMap.keySet();
	}
	
	
	public Collection<ValueType> values() {
		
		return wrappedHashMap.values();
	}
	
	
	public ValueType get(KeyType key) {
		
		return wrappedHashMap.get(key);
	}
	
	
	public ValueType remove(KeyType key) {
		
		return wrappedHashMap.remove(key);
	}
	
	
	public ValueType put(KeyType key, ValueType value) {
		
		return wrappedHashMap.put(key, value);
	}
	
	
	public void clear() {
		
		wrappedHashMap.clear();
	}
	
	
	public int size() {
		
		return wrappedHashMap.size();
	}
	
	
	public boolean isEmpty() {
		
		return wrappedHashMap.isEmpty();
	}
}
