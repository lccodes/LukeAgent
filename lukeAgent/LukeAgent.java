package lukeAgent;

import java.util.Map;
import java.util.Queue;
import tau.tac.adx.props.AdxBidBundle;
import tau.tac.adx.report.demand.AdNetBidMessage;
import tau.tac.adx.report.demand.AdNetworkDailyNotification;
import tau.tac.adx.report.demand.CampaignOpportunityMessage;
import tau.tac.adx.report.demand.CampaignReport;

/* The point of this class is to sit between the minimally-edited SampleAdNetwork class
 * and our [compartmentalized] classes that implement our game logic.
 * Wherever possible, the methods of the SampleAdNetwork are used.  Where not possible,
 * they are overwritten so that the control flow is still controlled by the sample agent.
 * The only difference between the sampleAdNetwork class and the SampleAdNetworkProtected
 * class is that many private variables and methods were changed to protected variables
 * and methods.
 * 
 * To use:
 * 
 * Create a new package, **yourName**Agent
 * 
 * Create four classes in that package, extending:
 * 1. AddBidder
 * 2. CampaignBidder
 * 3. UCSBidder
 *
 * Alternatively, you may have a single class extend more than one of the 
 * Bidder classes if you want them to share logic/decision making
 * 
 * Then, create a **YourName**Agent class that extends LukeAgent
 * for each of the addBidder, campaignBidder and ucsBidder methods, 
 * return an instantiation of each of your logic classes
 * 
 */


public abstract class LukeAgent extends SampleAdNetworkProtected{

	//these are the main information storages that the decision makers should need
	protected Map<Integer, CampaignData> myCampaigns;
	protected Queue<CampaignReport> campaignReports;
	//protected AdNetworkDailyNotification[] adNetworkDailyNotifications; -- not saving the whole message anymore
	protected double[] ucsLevels;
	protected double[] ucsPrices;
	protected double[] qualityScores;

	//these are the decision making modules!
	CampaignBidder campaignBidder;
	UCSBidder ucsBidder;
	AddBidder addBidder;
	

	public LukeAgent() {
		super();
		System.out.println("Luke Agent");
		//initialize information storers
		myCampaigns = super.myCampaigns;
		campaignReports = super.campaignReports;

		int duration = 61; //simulation duration: 60 days + day zero 
		//adNetworkDailyNotifications = new AdNetworkDailyNotification[duration]; --not saving the whole message anymore
		ucsLevels = new double[duration];
		ucsPrices = new double[duration];
		qualityScores = new double[duration];

		//initialize decision makers
		campaignBidder = campaignBidder(myCampaigns, campaignReports, ucsLevels, ucsPrices, qualityScores);
		ucsBidder = ucsBidder(myCampaigns, campaignReports, ucsLevels, ucsPrices, qualityScores);
		addBidder = addBidder(myCampaigns, campaignReports, ucsLevels, ucsPrices, qualityScores);
	}

	protected abstract CampaignBidder campaignBidder(Map<Integer, CampaignData> myCampaigns, 
			Queue<CampaignReport> campaignReports,
			double[] ucsLevels,
			double[] ucsPrices,
			double[] qualityScores);
	protected abstract UCSBidder ucsBidder(Map<Integer, CampaignData> myCampaigns, 
			Queue<CampaignReport> campaignReports,
			double[] ucsLevels,
			double[] ucsPrices,
			double[] qualityScores);
	protected abstract AddBidder addBidder(Map<Integer, CampaignData> myCampaigns, 
			Queue<CampaignReport> campaignReports,
			double[] ucsLevels,
			double[] ucsPrices,
			double[] qualityScores);

	/*
	 * This method takes all of the AdNetworkDailyNotifications
	 * and adds them to the AdNetworkDailyNotification list
	 */
	protected void handleAdNetworkDailyNotification(
			AdNetworkDailyNotification notificationMessage) {

		//put the notification message info into respective arrays
		//(this isn't in the stencil)
		//adNetworkDailyNotifications[day] = notificationMessage; --not saving the whole message anymore
		ucsLevels[day] = notificationMessage.getServiceLevel();
		ucsPrices[day] = notificationMessage.getPrice();
		qualityScores[day] = notificationMessage.getQualityScore();

		//the rest is stuff that the stencil needs to happen:
		adNetworkDailyNotification = notificationMessage;
		if ((pendingCampaign.id == adNetworkDailyNotification.getCampaignId())
				&& (notificationMessage.getCostMillis() != 0)) {
			/* add campaign to list of won campaigns */
			pendingCampaign.setBudget(notificationMessage.getCostMillis()/1000.0);
			currCampaign = pendingCampaign;
			genCampaignQueries(currCampaign);
			myCampaigns.put(pendingCampaign.id, pendingCampaign);

		}
	}


	/*
	 * The UCS bid and the Campaign bid
	 * are handled in this method
	 */
	protected void handleICampaignOpportunityMessage(
			CampaignOpportunityMessage com) {

		day = com.getDay();

		//gets the campaign decision--from the campaign decision maker
		pendingCampaign = new CampaignData(com);
		long cmpBidMillis = campaignBidder.makeCampaignBid(pendingCampaign);

		//gets the ucs decision--from the ucs decision maker
		ucsBid = ucsBidder.makeUCSBid(day);

		/* Note: Campaign bid is in millis */
		AdNetBidMessage bids = new AdNetBidMessage(ucsBid, pendingCampaign.id, cmpBidMillis);
		sendMessage(demandAgentAddress, bids);
	}

	//this is called when the 
	protected void sendBidAndAds() {

		AdxBidBundle bidBundle = new AdxBidBundle();
		int dayBiddingFor = day + 1;

		addBidder.setBids(bidBundle, dayBiddingFor);

		if (bidBundle != null) {
			sendMessage(adxAgentAddress, bidBundle);
		}
	}
}
