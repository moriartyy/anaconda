package org.anaconda;

public class Version implements Comparable<Version> {

	private float value;

	public Version(float value) {
		this.value = value;
	}

	public final Version Current = new Version(1.0f);

	@Override
	public int compareTo(Version o) {
		return Float.compare(value, o.value);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (this == obj)
			return true;
		
		if (!(obj instanceof Version)) 
			return false;
		
		Version v = (Version)obj;
		return this.value == v.value;
	}
}
