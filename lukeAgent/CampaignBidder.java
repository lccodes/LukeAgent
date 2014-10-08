package lukeAgent;

import lukeAgent.SampleAdNetworkProtected.CampaignData;

public abstract class CampaignBidder {

	public abstract long makeCampaignBid(CampaignData pendingCampaign);

}
