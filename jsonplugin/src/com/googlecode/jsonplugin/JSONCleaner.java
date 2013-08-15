package com.googlecode.jsonplugin;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * Isolate the process of cleaning JSON data from the Interceptor class itself.
 */
public abstract class JSONCleaner {

    public Object clean(String ognlPrefix, Object data) throws JSONException {
        if (data == null)
            return null;
        else if (data instanceof List)
            return cleanList(ognlPrefix, data);
        else if (data instanceof Map)
            return cleanMap(ognlPrefix, data);
        else
            return cleanValue(ognlPrefix, data);
    }

    protected Object cleanList(String ognlPrefix, Object data) throws JSONException {
        List list = (List) data;
        int count = list.size();
        for (int i = 0; i < count; i++) {
            list.set(i, clean(ognlPrefix + "[" + i + "]", list.get(i)));
        }
        return list;
    }

    protected Object cleanMap(String ognlPrefix, Object data) throws JSONException {
        Map map = (Map) data;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry e = (Map.Entry) iter.next();
            e.setValue(clean((ognlPrefix.length() > 0 ? ognlPrefix + "." : "") + e.getKey(), e.getValue()));
        }
        return map;
    }

    protected abstract Object cleanValue(String ognlName, Object data) throws JSONException;

}
