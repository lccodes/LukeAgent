package lukeAgent;

import lukeAgent.SampleAdNetworkProtected.CampaignData;
import java.util.HashMap;
import java.util.Map;

public abstract class CampaignBidder {
	HashMap<Integer, CampaignData> myCampaigns;
	double[] ucsPrices;
	/*
	 * CampaignBidder
	 */
	public CampaignBidder(Map<Integer, CampaignData> myCampaigns,
			Queue<CampaignReport> campaignReports, double[] ucsLevels,
			double[] ucsPrices, double[] qualityScores) {
		this.myCampaigns = myCampaigns;
		this.ucsPrices = ucsPrices;
	}

	@Override
	public long makeCampaignBid(CampaignData pendingCampaign) {
		//Get the average budget
		int avg = 0;
		int total = 0;
		for(Map.Entry<Integer, CampaignData> camps : myCampaigns){
			avg+=camps.budget;
			total++;
		}
		//Our average
		avg/=total;
		//Is this new campaign too far from our average?
			//or do we not have enough
		if(total < 3 || pendingCampaign.budget < avg + 100 || pendingCampaign.budget > avg - 100){
			//Bid the budget
			return pendingCampaign.budget;
		} 

		return 1;

	}

}
