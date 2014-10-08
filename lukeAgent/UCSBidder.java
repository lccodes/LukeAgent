package lukeAgent;

public abstract class UCSBidder {

	public NaiveUCSBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		
	}

	public abstract double makeUCSBid(int day);

}
