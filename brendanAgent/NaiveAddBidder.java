package brendanAgent;

import java.util.Map;
import java.util.Queue;

import brownAgent.AddBidder;
import brownAgent.SampleAdNetworkProtected.CampaignData;
import edu.umich.eecs.tac.props.Ad;
import tau.tac.adx.props.AdxBidBundle;
import tau.tac.adx.props.AdxQuery;
import tau.tac.adx.report.demand.CampaignReport;

public class NaiveAddBidder extends AddBidder {
	Map<Integer, CampaignData> myCampaigns;


	public NaiveAddBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		this.myCampaigns = myCampaigns;
	}

	@Override
	public void setBids(AdxBidBundle bidBundle, int dayBiddingFor) {
		for (CampaignData c : myCampaigns.values()) {
			if (dayBiddingFor <= c.dayEnd) {
				//bids honestly: budget / imps to get
				double bid = c.budget / c.reachImps;
				//adds those bids to the bundle
				for (AdxQuery ms : c.campaignQueries) {
					bidBundle.addQuery(ms, bid, new Ad(null), c.id, 1);
				}
				//sets a reasonable budget
				double dailyBudget = bid * c.impsTogo();
				bidBundle.setCampaignDailyLimit(c.id, c.impsTogo(), dailyBudget);
			}
		}

	}


}
