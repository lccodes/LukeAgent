package brendanAgent;

import java.util.Map;
import java.util.Queue;

import brownAgent.UCSBidder;
import brownAgent.SampleAdNetworkProtected.CampaignData;
import tau.tac.adx.report.demand.CampaignReport;

public class NaiveUCSBidder extends UCSBidder {
	
	private double targetUCSLevel = 0.6;
	private double ucsBid = 1.0;
	double[] ucsLevels;


	public NaiveUCSBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		this.ucsLevels = ucsLevels;
	}

	@Override
	public double makeUCSBid(int day) {
		if (day > 1) {
			if (ucsLevels[day - 1] > targetUCSLevel) {
				ucsBid = ucsBid * 0.8;
			} else if (ucsLevels[day - 1] < targetUCSLevel) {
				ucsBid = ucsBid * 1.2;
			}
		}
		System.out.println("Spending: " + ucsBid + " for UCS on day " + day);
		return ucsBid;
	}

}
