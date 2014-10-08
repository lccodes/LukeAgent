package brownAgent;

import brownAgent.SampleAdNetworkProtected.CampaignData;

public abstract class CampaignBidder {

	public abstract long makeCampaignBid(CampaignData pendingCampaign);

}
