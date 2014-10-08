package brendanAgent;

import java.util.Map;
import java.util.Queue;

import brownAgent.AddBidder;
import brownAgent.BrownAgent;
import brownAgent.CampaignBidder;
import brownAgent.UCSBidder;
import tau.tac.adx.report.demand.CampaignReport;

public class BrendanNaiveBot extends BrownAgent {

	@Override
	protected
	CampaignBidder campaignBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		// TODO Auto-generated method stub
		return new NaiveCampaignBidder(myCampaigns, campaignReports, ucsLevels, ucsPrices, qualityScores);
	}
	
	@Override
	protected UCSBidder ucsBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected
	AddBidder addBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		// TODO Auto-generated method stub
		return null;
	}



}
