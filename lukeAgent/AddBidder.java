package lukeAgent;

import tau.tac.adx.props.AdxBidBundle;

public abstract class AddBidder {

	CampaignData theCampaigns;
	/*
	 * Constructor for the AddBidder logic
	 */
	public NaiveAddBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		theCampaigns = myCampaigns;
	}

	/*
	 * Decisiona s to what to bid
	 */
	@Override
	public void setBids(AdxBidBundle bidBundle, int dayBiddingFor) {

	}
		

}
