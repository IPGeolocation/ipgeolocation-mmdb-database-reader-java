package io.ipgeolocation.mmdb.reader.response;

import com.maxmind.db.MaxMindDbConstructor;
import com.maxmind.db.MaxMindDbParameter;

public class IPSecurity {
    private final Integer threatScore;
    private final String proxyType, isTor, isProxy, isAnonymous, isKnownAttacker, isBot, isSpam;

    @MaxMindDbConstructor
    public IPSecurity(
            @MaxMindDbParameter(name = "threat_score") Integer threatScore,
            @MaxMindDbParameter(name = "proxy_type") String proxyType,
            @MaxMindDbParameter(name = "is_tor") String isTor,
            @MaxMindDbParameter(name = "is_proxy") String isProxy,
            @MaxMindDbParameter(name = "is_anonymous") String isAnonymous,
            @MaxMindDbParameter(name = "is_known_attacker") String isKnownAttacker,
            @MaxMindDbParameter(name = "is_bot") String isBot,
            @MaxMindDbParameter(name = "isSpam") String isSpam
    ) {
        this.threatScore = threatScore;
        this.proxyType = proxyType;
        this.isTor = isTor;
        this.isProxy = isProxy;
        this.isAnonymous = isAnonymous;
        this.isKnownAttacker = isKnownAttacker;
        this.isBot = isBot;
        this.isSpam = isSpam;
    }

    public Integer getThreatScore() {
        return threatScore;
    }

    public String getProxyType() {
        return proxyType;
    }

    public String getIsTor() {
        return isTor;
    }

    public String getIsProxy() {
        return isProxy;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public String getIsKnownAttacker() {
        return isKnownAttacker;
    }

    public String getIsBot() {
        return isBot;
    }

    public String getIsSpam() {
        return isSpam;
    }
}
