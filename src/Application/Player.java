package Application;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Domain.Land;
import Domain.Landparser;

public class Player {

	private static int ID = 0;
	public int id;

	private String userName;
	private PrintWriter pw;

	private Landparser lp;
	private int army;
	protected List<Land> myLandList = new ArrayList<>();
	private int thaler;
	private int crop;
	private int achievementPoints;
	private int[] churchCount = { 0, 0, 0, 0, 0 };
	private int[] mansionCount = { 0, 0, 0, 0, 0 };
	private int[] palaceCount = { 0, 0, 0, 0, 0 };
	protected static int playercount = 0;
	private boolean plusCrop;
	private boolean plusThaler;
	private boolean plusArmy;
	private boolean plusDefence;
	private boolean plusAttack;
	private int bidAmount;
	private boolean bidPlaced;
	private boolean actionPlanned;
	private boolean ai=false;
	private int playerOrder;
	private int schalenContent;
	
	private Land actionPalace;
	private Land actionChurch;
	private Land actionMansion;
	private Land actionCrop;
	private Land actionThaler;
	private Land actionArmy5;
	private Land actionArmy3;
	private Land actionMove;
	private Land actionFightA;
	private Land actionFightB;

	private Color color;
	
	///////////////////////////////////// AI variables
	Random rnd = new Random();
	Land attackLand;
	boolean[] planner = {true, true, true, true, true, true, true, true, true, true};
	


	


	List<Land> planned = new ArrayList<Land>();
	private ArrayList <Integer> startArmy = new ArrayList(Arrays.asList(new Integer[] {5, 4, 4, 3, 3, 2, 2, 2}));
	
	///////////////////////////////////

	// Johnny
	public static void setID(int id)
	{
		ID=id;
	}


	public Player(String userName, PrintWriter pw) {
		lp = new Landparser();
		this.userName = userName;
		this.pw = pw;
		this.thaler = 0;
		this.crop = 0;
		this.id = ID;
		this.army = 62;

		if (id == 0)
			this.color = Color.black;
		else if (id == 1)
			this.color = Color.blue;
		else if (id == 2)
			this.color = Color.red;
		else if (id == 3)
			this.color = Color.green;
		else if (id == 4)
			this.color = Color.yellow;

		Tower.playercount = ID + 1;
		playercount = ID + 1;
		ID++;
	}
	public Player(String userName, int ID) {
		lp = new Landparser();
		this.userName = userName;
		this.thaler = 0;
		this.crop = 0;
		this.id = ID;
		this.army = 62;

		if (id == 0)
			this.color = Color.black;
		else if (id == 1)
			this.color = Color.blue;
		else if (id == 2)
			this.color = Color.red;
		else if (id == 3)
			this.color = Color.green;
		else if (id == 4)
			this.color = Color.yellow;
	}
	public Player(String userName, int ID, boolean AI) {
		lp = new Landparser();
		this.userName = userName;
		this.thaler = 0;
		this.crop = 0;
		this.id = ID;
		this.army = 62;
		this.ai=AI;
		
		if (id == 0)
			this.color = Color.black;
		else if (id == 1)
			this.color = Color.blue;
		else if (id == 2)
			this.color = Color.red;
		else if (id == 3)
			this.color = Color.green;
		else if (id == 4)
			this.color = Color.yellow;
		Tower.playercount = ID + 1;
		playercount = ID + 1;
	}

	
	public void thalerCount(int playercount) {
	if (playercount == 3) {
			this.thaler = 18;
		} else if (playercount == 4) {
			this.thaler = 15;
		} else if (playercount == 5) {
			this.thaler = 12;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getThaler() {
		return thaler;
	}

	public void setThaler(int thaler) {
		this.thaler = thaler;
	}

	public int getCrop() {
		return crop;
	}

	public void setCrop(int crop) {
		this.crop = crop;
	}

	public int getAchievementPoints() {
		return achievementPoints;
	}

	public void setAchievementPoints(int archievementPoints) {
		this.achievementPoints = archievementPoints;
	}

	public static int getplayercount() {
		return playercount;
	}

	public int getArmy() {
		return army;
	}

	public void setArmy(int army) {
		this.army = army;
	}

	public void useArmy(int anzahl) {
		this.army = getArmy() - anzahl;
	}

	public void returnArmy(int anzahl) {
		this.army = getArmy() + anzahl;
	}

	public List<Land> getLand() {
		return myLandList;
	}

	public void chooseLand(Land land) {
		this.myLandList.add(land);
		land.setOwner(this);
	}

	public void loseLand(Land land) {
		this.myLandList.remove(land);
	}

	public void advantageCrop() {
		this.plusCrop = true;
	}
	public void placeBid(int amount){
		this.bidAmount = amount;
	}
	public int getBid(){
		return bidAmount;
	}
	public void advantageThaler() {
		this.plusThaler = true;
	}

	public void advantageArmy() {
		this.plusArmy = true;
	}

	public void advantageAttack() {
		this.plusAttack = true;
	}

	public void advantageDefence() {
		this.plusDefence = true;
	}

	public String getUsername() {
		return userName;
	}

	public boolean getAdvantageCrop() {
		return plusCrop;
	}

	public boolean getAdvantageThaler() {
		return plusThaler;
	}

	public boolean getAdvantageArmy() {
		return plusArmy;
	}

	public boolean getAdvantageAttack() {
		return plusAttack;
	}

	public boolean getAdvantageDefence() {
		return plusDefence;
	}

	public PrintWriter getPrintWriter() {
		return pw;
	}
	
	public boolean getActionPlanned(){
		return actionPlanned;
	}
	
	public void setActionPlanned(boolean actionPlanned){
		this.actionPlanned=actionPlanned;
	}

	public boolean getBidPlaced(){
		return bidPlaced;
	}
	
	public void setBidPlaced(boolean bidPlaced){
		this.bidPlaced = bidPlaced;
	}
	
	public void setPlayerOrder(int playerOrder){
		this.playerOrder= playerOrder;
	}
	
	public int getPlayerOrder(){
		return playerOrder;
	}
	
	public void advantageNeutralize() {
		this.plusCrop = false;
		this.plusThaler = false;
		this.plusArmy = false;
		this.plusAttack = false;
		this.plusDefence = false;
	}
	
///////////setting/getting action end///////////
	
	public void buildPalace(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.palaceCount[0] += 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.palaceCount[1] += 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.palaceCount[2] += 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.palaceCount[3] += 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.palaceCount[4] += 1;
	}

	public void buildMansion(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.mansionCount[0] += 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.mansionCount[1] += 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.mansionCount[2] += 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.mansionCount[3] += 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.mansionCount[4] += 1;
	}

	public void buildChurch(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.churchCount[0] += 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.churchCount[1] += 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.churchCount[2] += 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.churchCount[3] += 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.churchCount[4] += 1;
	}

	public void destroyPalace(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.palaceCount[0] -= 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.palaceCount[1] -= 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.palaceCount[2] -= 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.palaceCount[3] -= 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.palaceCount[4] -= 1;
	}

	public void destroyMansion(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.mansionCount[0] -= 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.mansionCount[1] -= 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.mansionCount[2] -= 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.mansionCount[3] -= 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.mansionCount[4] -= 1;
	}

	public void destroyChurch(String region) {
		if (region.equalsIgnoreCase("Sachsen"))
			this.churchCount[0] -= 1;
		if (region.equalsIgnoreCase("Brandenburg"))
			this.churchCount[1] -= 1;
		if (region.equalsIgnoreCase("Kurpfalz"))
			this.churchCount[2] -= 1;
		if (region.equalsIgnoreCase("Bayern"))
			this.churchCount[3] -= 1;
		if (region.equalsIgnoreCase("Oesterreich"))
			this.churchCount[4] -= 1;
	}

	public int getPalaceCount(int x) {
		return palaceCount[x];
	}

	public int getMansionCount(int x) {
		return mansionCount[x];
	}

	public int getChurchCount(int x) {
		return churchCount[x];
	}

	public Color getColor() {
		return color;
	}
	
	///////setting actions////////
	public void setActionBuildPalace(Land land){
		this.actionPalace = land;
	}
	
	public void setActionBuildChurch(Land land){
		this.actionChurch = land;
	}
	
	public void setActionBuildMansion(Land land){
		this.actionMansion = land;
	}
	
	public void setActionCrop(Land land){
		this.actionCrop = land;
	}
	
	public void setActionThaler(Land land){
		this.actionThaler = land;
	}

	public void setActionPalace(Land land) {
		this.actionPalace = land;
	}

	public void setActionArmy5(Land land){
		this.actionArmy5 = land;
	}
	
	public void setActionArmy3(Land land){
		this.actionArmy3 = land;
	}
	
	public void setActionMove(Land land){
		this.actionMove = land;
	}
	
	public void setActionFightA(Land land){
		this.actionFightA = land;
	}
	
	public void setActionFightB(Land land){
		this.actionFightB = land;
	}
	
	public void nullifyActions(){		//for initialization and end of round
		this.actionPalace = null;
		this.actionChurch = null;
		this.actionMansion = null;
		this.actionCrop = null;
		this.actionThaler = null;
		this.actionArmy5 = null;
		this.actionArmy3 = null;
		this.actionMove = null;
		this.actionFightA = null;
		this.actionFightB = null;
	}
	
	public Land getActionBuildPalace(){
		return actionPalace;
	}
	
	public Land getActionBuildChurch(){
		return actionChurch;
	}
	
	public Land getActionBuildMansion(){
		return actionMansion;
	}
	
	public Land getActionCrop(){
		return actionCrop;
	}
	
	public Land getActionThaler(){
		return actionThaler;
	}

	public Land getActionArmy5(){
		return actionArmy5;
	}
	
	public Land getActionArmy3(){
		return actionArmy3;
	}
	
	public Land getActionMove(){
		return actionMove;
	}
	
	public Land getActionFightA(){
		return actionFightA;
	}
	
	public Land getActionFightB(){
		return actionFightB;
	}
	
	public void setSchalenContent(int content){
		this.schalenContent= content;
	}
	public int getSchalenContent(){
		return schalenContent;
	}

	public List<Land> getRiotOrder(List<Land> landList, int landCount) {
		for (int i = 0; i < landCount; i++) {

		}
		return landList;
	}
	

	
	public String toString(){
		return "Player-" + userName+"-"+ id;
	}
	///////////////////////////////////// Manu
	public void setLands(ArrayList<Land> lands){
		this.myLandList=lands;
	}
	
	public static void  updatePlayer(String[] playerData, Player player){
		player.setThaler(Integer.parseInt(playerData[2]));
		player.setCrop(Integer.parseInt(playerData[3]));
		player.setArmy(Integer.parseInt(playerData[4]));
		player.setAchievementPoints(Integer.parseInt(playerData[5]));
		if(playerData.length>6){
			String[] landList = playerData[6].split("#");
			ArrayList<Land> tempLandList = new ArrayList<Land>();
			Land land=new Land();
				
				for(int j=0;j<landList.length;j++){
					if(j%8==0){
						land=new Land();
						land.setName(landList[j]);
					}
					if(j%8==1){
						land.setArmy(Integer.parseInt(landList[j]));
					}
					if(j%8==2){
						land.setBuildingCount(Integer.parseInt(landList[j]));				
					}
					if(j%8==3){
						land.setRiotMarker(Integer.parseInt(landList[j]));
					}
					if(j%8==4){
						land.setPalace(Boolean.parseBoolean(landList[j]));
					}
					if(j%8==5){
						land.setMansion(Boolean.parseBoolean(landList[j]));
					}
					if(j%8==6){
						land.setChurch(Boolean.parseBoolean(landList[j]));
					}
					if(j%8==7){
						land.setOwner(player);
						tempLandList.add(land);
					}
				}
				player.setLands(tempLandList);		
			}
			player.setSchalenContent(Integer.parseInt(playerData[7]));
		}
				
	
	public String updateToString(){
		String myLandList="";
			if(this.myLandList.size()>0){
				for(int i=0; i<this.myLandList.size();i++){
				myLandList += this.myLandList.get(i).toString();
			}
		}
		
		return "updatePlayer" + "-" + userName + "-" + thaler + "-" + crop + "-" + army + "-" + achievementPoints + "-" + myLandList +"-"+schalenContent;
	}
	
	
	public String actionsToString(){
		return actionPalace  + "-" + actionChurch + "-" + actionMansion + "-" + actionCrop + "-" + actionThaler + "-" + actionArmy5 + "-" + actionArmy3 + "-" + actionMove + "-" + actionFightA + "-" + actionFightB;
	} // update for planned actions
	

	public Land getLandByName(String name){ 	//suche Land nach namen
		Land land = new Land();
		if(name.equalsIgnoreCase("."))
			return null;
		else{
			for(int i = 0; i < myLandList.size(); i++){
				if(name.equals(myLandList.get(i).getName())){
					land = myLandList.get(i);
				}
			}
		}
		return land;
		
	}
	//////////////////////////////////////////////////////////////////// ArtificialIntelligence methods
	public boolean getAI(){
		return ai;
	}
	public int placeBidAI(){
		return rnd.nextInt(6);
	}
	
	public void chooseStartLandAI(Landparser landp, Land[] land){
		int num = rnd.nextInt(3);
		int ran = rnd.nextInt(startArmy.size());
		chooseLand(land[num]);
		land[num].setArmy(startArmy.get(ran));
		this.setArmy(this.getArmy()-startArmy.get(ran));
		startArmy.remove(ran);
		landp.repositionStartLands(land[num].getName());
	}
	
	public int chooseAdvantageAI(ArrayList<String> advantages){
		int num = rnd.nextInt(advantages.size());
		return num;
	}
	
	public void planActionAI(){
//		for(int i=0; i<10;i++){
//			for(int j=0; j<myLandList.size();j++){
//				this.setActionArmy3(myLandList.get(j));
//			}
//		}
		
		
		boolean free[] = new boolean[this.myLandList.size()];
		
		
		for(int i = 0; i<this.myLandList.size(); i++){
			free[i] = true;
		}
		int i = 0;
		while(i<10&&i<this.myLandList.size()){
			int num = rnd.nextInt(this.myLandList.size());
			while(free[num] == false){
				num = rnd.nextInt(this.myLandList.size());
			}
			planned.add(this.myLandList.get(num));
			free[num] = false;
		}
		
	}
	
	public List<Land> getPlanned() {
		return planned;
	}
	
	public void randomActionPLannerAI(Land land){
		int num = rnd.nextInt(10);
		while(planner[num] == false) num = rnd.nextInt(10);
		if(num == 0){
			this.setActionArmy3(land);
			planner[0]=false;
		}
		if(num == 1){
			this.setActionArmy5(land);
			planner[1]=false;
		}
		if(num == 2){
			this.setActionBuildChurch(land);
			planner[2]=false;
		}
		if(num == 3){
			this.setActionBuildMansion(land);
			planner[3]=false;
		}
		if(num == 4){
			this.setActionBuildPalace(land);
			planner[4]=false;
		}
		if(num == 5){
			this.setActionCrop(land);
			planner[5]=false;
		}
		if(num == 6){
			this.setActionFightA(land);
			planner[6]=false;
		}
		if(num == 7){
			this.setActionFightB(land);
			planner[7]=false;
		}
		if(num == 8){
			this.setActionMove(land);
			planner[8]=false;
		}
		if(num == 9){
			this.setActionThaler(land);
			planner[9]=false;
		}
	}


	public Land attackLandAI(List<Land> landList , Land land){
		int count = land.getborderland().size();
		int num = rnd.nextInt(count);
		attackLand = land.getLand(landList, land.getborderland().get(num));
		while(attackLand.getOwner().equals(this)){
			num = rnd.nextInt(count);
			attackLand = land.getLand(landList, land.getborderland().get(num));
		}
		return attackLand;
	}
	public Land getAttackLand() {
		return attackLand;
	}

}
