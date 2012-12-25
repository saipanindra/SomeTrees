package myinterfaces;
/*
 * All the trees and treehashtables implement this interface. This ensures that the put and get methods
 * in the trees and treehashes follow the standard of the put ad get methods of the TreeMap interface
 */
public interface MyTreesandHashes<K,V> {
	public V put(K key, V value);
	
	public V get(K key);
	
}
