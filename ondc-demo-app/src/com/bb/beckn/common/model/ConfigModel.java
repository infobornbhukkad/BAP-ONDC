// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ConfigModel implements Serializable
{
	
    private static final long serialVersionUID = -5465633216093570849L;
    private String subscriberId;
    private String subscriberUrl;
    private String becknGateway;
    private String keyid;
    private String disableAdaptorCalls;
    private String whitelistBaps;
    private SigningModel signing;
    private List<ApiParamModel> api;
    private ApiParamModel matchedApi;
    private String becknTtl;
    private String buyerappfinderfeeType;
    private String buyerappfinderfeeAmount;
    private String groceryCategory;
    private String foodCategory;
    private String version;
    private String bapDomain;
    private String gstBapfinderfee;
    private String waitTime;
    
    
	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public String getGstBapfinderfee() {
		return gstBapfinderfee;
	}

	public void setGstBapfinderfee(String gstBapfinderfee) {
		this.gstBapfinderfee = gstBapfinderfee;
	}

	public String getBapDomain() {
		return bapDomain;
	}

	public void setBapDomain(String bapDomain) {
		this.bapDomain = bapDomain;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
   

	public String getGroceryCategory() {
		return groceryCategory;
	}

	public void setGroceryCategory(String groceryCategory) {
		this.groceryCategory = groceryCategory;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}

	public String getBuyerappfinderfeeType() {
		return buyerappfinderfeeType;
	}

	public void setBuyerappfinderfeeType(String buyerappfinderfeeType) {
		this.buyerappfinderfeeType = buyerappfinderfeeType;
	}

	public String getBuyerappfinderfeeAmount() {
		return buyerappfinderfeeAmount;
	}

	public void setBuyerappfinderfeeAmount(String buyerappfinderfeeAmount) {
		this.buyerappfinderfeeAmount = buyerappfinderfeeAmount;
	}

	public String getBecknTtl() {
		return becknTtl;
	}

	public void setBecknTtl(String becknTtl) {
		this.becknTtl = becknTtl;
	}

	public ConfigModel() {
        this.signing = new SigningModel();
        this.api = new ArrayList<ApiParamModel>();
    }
    
    public String getSubscriberId() {
        return this.subscriberId;
    }
    
    public String getKeyid() {
        return this.keyid;
    }
    
    public SigningModel getSigning() {
        return this.signing;
    }
    
    public List<ApiParamModel> getApi() {
        return this.api;
    }
    
    public ApiParamModel getMatchedApi() {
        return this.matchedApi;
    }
    
    public void setSubscriberId(final String subscriberId) {
        this.subscriberId = subscriberId;
    }
    
    public void setKeyid(final String keyid) {
        this.keyid = keyid;
    }
    
    public void setSigning(final SigningModel signing) {
        this.signing = signing;
    }
    
    public void setApi(final List<ApiParamModel> api) {
        this.api = api;
    }
    
    public void setMatchedApi(final ApiParamModel matchedApi) {
        this.matchedApi = matchedApi;
    }
    
    public String getSubscriberUrl() {
		return subscriberUrl;
	}

	public void setSubscriberUrl(String subscriberUrl) {
		this.subscriberUrl = subscriberUrl;
	}

	public String getBecknGateway() {
		return becknGateway;
	}

	public void setBecknGateway(String becknGateway) {
		this.becknGateway = becknGateway;
	}

	public String getDisableAdaptorCalls() {
		return disableAdaptorCalls;
	}

	public void setDisableAdaptorCalls(String disableAdaptorCalls) {
		this.disableAdaptorCalls = disableAdaptorCalls;
	}

	public String getWhitelistBaps() {
		return whitelistBaps;
	}

	public void setWhitelistBaps(String whitelistBaps) {
		this.whitelistBaps = whitelistBaps;
	}

	@Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConfigModel)) {
            return false;
        }
        final ConfigModel other = (ConfigModel)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$subscriberId = this.getSubscriberId();
        final Object other$subscriberId = other.getSubscriberId();
        Label_0065: {
            if (this$subscriberId == null) {
                if (other$subscriberId == null) {
                    break Label_0065;
                }
            }
            else if (this$subscriberId.equals(other$subscriberId)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$keyid = this.getKeyid();
        final Object other$keyid = other.getKeyid();
        Label_0102: {
            if (this$keyid == null) {
                if (other$keyid == null) {
                    break Label_0102;
                }
            }
            else if (this$keyid.equals(other$keyid)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$signing = this.getSigning();
        final Object other$signing = other.getSigning();
        Label_0139: {
            if (this$signing == null) {
                if (other$signing == null) {
                    break Label_0139;
                }
            }
            else if (this$signing.equals(other$signing)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$api = this.getApi();
        final Object other$api = other.getApi();
        Label_0176: {
            if (this$api == null) {
                if (other$api == null) {
                    break Label_0176;
                }
            }
            else if (this$api.equals(other$api)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$matchedApi = this.getMatchedApi();
        final Object other$matchedApi = other.getMatchedApi();
        if (this$matchedApi == null) {
            if (other$matchedApi == null) {
                return true;
            }
        }
        else if (this$matchedApi.equals(other$matchedApi)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ConfigModel;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $subscriberId = this.getSubscriberId();
        result = result * 59 + (($subscriberId == null) ? 43 : $subscriberId.hashCode());
        final Object $keyid = this.getKeyid();
        result = result * 59 + (($keyid == null) ? 43 : $keyid.hashCode());
        final Object $signing = this.getSigning();
        result = result * 59 + (($signing == null) ? 43 : $signing.hashCode());
        final Object $api = this.getApi();
        result = result * 59 + (($api == null) ? 43 : $api.hashCode());
        final Object $matchedApi = this.getMatchedApi();
        result = result * 59 + (($matchedApi == null) ? 43 : $matchedApi.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "ConfigModel(subscriberId=" + this.getSubscriberId() + ", keyid=" + this.getKeyid() + ", signing=" + this.getSigning() + ", api=" + this.getApi() + ", matchedApi=" + this.getMatchedApi() + ")";
    }
}
