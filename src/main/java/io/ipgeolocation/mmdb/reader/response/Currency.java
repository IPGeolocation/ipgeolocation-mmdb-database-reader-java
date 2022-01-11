package io.ipgeolocation.mmdb.reader.response;

import com.maxmind.db.MaxMindDbConstructor;
import com.maxmind.db.MaxMindDbParameter;

import java.util.LinkedHashMap;
import java.util.Map;

public class Currency {
    private final String code;
    private final String name;
    private final String symbol;

    @MaxMindDbConstructor
    public Currency(
            @MaxMindDbParameter(name = "code") String code,
            @MaxMindDbParameter(name = "name") String name,
            @MaxMindDbParameter(name = "symbol") String symbol) {
        this.code = code;
        this.symbol = symbol;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public final Map<String, Object> asMap() {
        Map<String, Object> responseMap = new LinkedHashMap<>();

        responseMap.put("code", code);
        responseMap.put("name", name);
        responseMap.put("symbol", symbol);

        return responseMap;
    }
}
