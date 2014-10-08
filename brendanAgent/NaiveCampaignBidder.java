package brendanAgent;

import java.util.Map;
import java.util.Queue;

import brownAgent.CampaignBidder;
import brownAgent.SampleAdNetworkProtected.CampaignData;
import tau.tac.adx.report.demand.CampaignReport;

public class NaiveCampaignBidder extends CampaignBidder {

	public NaiveCampaignBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public long makeCampaignBid(CampaignData pendingCampaign) {
		// assumes that it will be able to buy each impression for 0.01
		return pendingCampaign.reachImps * 10;
	}

}
