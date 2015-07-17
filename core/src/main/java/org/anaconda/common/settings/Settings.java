package org.anaconda.common.settings;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.anaconda.common.collections.Maps;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class Settings {
    
    public static Settings Empty = new Settings();
    
    private Map<String, String> map;
    
    private Settings() {
        this(Maps.newHashMap());
    }
    
    public Settings(Properties source) {
    	this(Maps.fromProperties(source));
    }
    
    public Settings(Map<String, String> source) {
        this.map = new HashMap<>(Preconditions.checkNotNull(source));
    }
    
    public String get(String key) {
        return map.get(key);
    }
    
    public void merge(Settings target) {
        this.map.putAll(target.map);
    }
    
    public Map<String, String> asMap() {
        return ImmutableMap.copyOf(map);
    }
    
    public Settings getSettingsByPrefix(String prefix) {
    	Map<String, String> subSettings = new HashMap<>();
        this.map.forEach((key, value) -> {
            if (key.startsWith(prefix)) {
                subSettings.put(key.substring(prefix.length()), value);
            }
        });
        return new Settings(subSettings);
    }
    
    public <T> T get(String key, T defaultVal, Function<String, T> convertor) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
        String settingVal = get(key);
        return settingVal == null ? defaultVal : convertor.apply(settingVal);
    }
    
    public String get(String key, String defaultVal){
        return get(key, defaultVal, (sv) -> {return sv;});
    }
    
    public Map<String, String> getAsMap(String name, Map<String, String> defVal) {
        String paramVal = get(name);
        if (paramVal == null) {
            return defVal;
        } else {
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            
            if (paramVal == "")
                return builder.build();
            
            String[] kvpairs = paramVal.split(",");
            for (String kvpair : kvpairs) {
                String[] parts = kvpair.split(":");
                if (parts.length == 1) {
                    builder.put(parts[0].trim(), "");
                } else {
                    builder.put(parts[0].trim(), parts[1].trim());
                }
            }
            return builder.build();
        }
    }
    
    public InetSocketAddress[] getServerNodes(String key) {
        return getServerNodes(key, null);
    }
    
    public InetSocketAddress[] getServerNodes(String key, InetSocketAddress[] defaultVal) {
        return get(key, defaultVal, (sv) -> {
            List<String> hostAddressList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(sv);
            InetSocketAddress[] nodes = new InetSocketAddress[hostAddressList.size()];
            int index = 0;
            for(String hostAddress : hostAddressList) {
                String[] hostAddressComponents = hostAddress.split(":");
                String host = hostAddressComponents[0].trim();
                int port = Integer.valueOf(hostAddressComponents[1].trim());
                nodes[index++] = new InetSocketAddress(host, port);
            }
            return nodes;
        });
    }
    
    public Iterable<String> getAsIterable(String key) {
        return getAsIterable(key, null);
    }
    
    public Iterable<String> getAsIterable(String key, Iterable<String> defaultVal) {
        return get(key, defaultVal, (sv) -> {
            return Splitter.on(',').trimResults().omitEmptyStrings().split(sv);
        });
    }
    
    public int getAsInt(String key, int defaultVal) {
        return get(key, defaultVal, Integer::parseInt);
    }
    
    public long getAsLong(String key, long defaultVal) {
        return get(key, defaultVal, Long::parseLong);
    }
    
    public double getAsDouble(String key, double defaultVal) {
        return get(key, defaultVal, Double::parseDouble);
    }
    
    public float getAsFloat(String key, float defaultVal) {
        return get(key, defaultVal, Float::parseFloat);
    }
   
    public boolean getAsBoolean(String key, boolean defaultVal) {
        return get(key, defaultVal, Boolean::parseBoolean);
    }
    
    public static Builder builder() {
    	return new Builder();
    }
    
    static class Builder {
    	
    	private Map<String, String> map = new LinkedHashMap<>();
    	
    	public Builder() {
    	}
    	
    	public Builder put(Settings settings) {
    		map.putAll(settings.map);
    		return this;
    	}
    	
    	public Builder put(Properties settings) {
    		settings.forEach((k, v) -> {
    			map.put((String)k, (String)v);
    		});
    		return this;
    	}
    	
    	public Builder put(Map<String, String> settings) {
    		map.putAll(settings);
    		return this;
    	}
    	
    	public Builder put(String key, String value) {
    		map.put(key, value);
    		return this;
        }
        
    	public Settings build() {
    		return new Settings(map);
    	}
    }

}
