package Application;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import Domain.Eventcards;
import Domain.Land;

public class Tower {

	private String Region;
	private static int[] TurmInhalt = new int[6];
	int[] schale = new int[6];
	static int playercount;

	public static Eventcards event;

	public Tower() {

	}

	// Methode für 7 armeen und 10 bauern am anfang für den turm

	public void initializeTower(ArrayList<Player> players) {

		for (int i = 0; i < playercount; i++) {
			schale[i] = 7;
		}
		schale[playercount] = 10;

		int[] einwurf = schale.clone();

		// schale neutralisieren
		for (int i = 0; i < schale.length; i++) {
			schale[i] = 0;
		}

		Random rnd = new Random();
		// den einwurf durchgehen
		for (int i = 0; i < einwurf.length; i++) {
			for (int j = 0; j < einwurf[i]; j++)
				if (rnd.nextInt(100) >= 30) {
					schale[i] += 1; // jeden einwurf durchgehen, daher +1
				} else {
					TurmInhalt[i] += 1;// Inhalt bekommt Anzahl der token
				}
		}

		// in der schale enthaltenen tokens werden auf die Länder verteilt
		for (int i = 0; i < playercount; i++) {
			players.get(i).useArmy(TurmInhalt[i]);
		}
		for (int i = 0; i < schale.length; i++) {
			schale[i] = 0;
		}

	}

	// Methode für jeden Kampf
	public void towerFight(Land countryAttack, Land countryDefend,
			int attackCount, List<Player> players) {
		Land[] land = new Land[2];

		if (countryDefend.getOwner() == null) {
			int attackID = countryAttack.getOwner().getId();
			int defendID = playercount;
			schale[attackID] += attackCount;
			// schale[defendID]+=countryDefend.getArmy();
			int[] einwurf = new int[playercount + 1];
			Random rnd = new Random();

			countryAttack.setArmy(countryAttack.getArmy() - attackCount);

			if (countryAttack.getOwner().getAdvantageAttack()) { // setting the
																	// number of
																	// soldiers
																	// and
																	// checking
																	// if player
																	// has
																	// certain
																	// advantages
				einwurf[attackID] = schale[attackID] + 1;
				countryAttack.getOwner().useArmy(1);// that strengthen his army
			} else
				einwurf[attackID] = schale[attackID];

			if (event.getEvent().equalsIgnoreCase("farmer")) {
				einwurf[playercount] = schale[playercount] + 2;
			} else
				einwurf[playercount] = schale[playercount] + 1;

			for (int i = 0; i <= playercount; i++) {
				if (i == attackID || i == defendID) {
					schale[i] = 0;
				} else {
					einwurf[i] = schale[i];
					schale[i] = 0;
				}
			}

			for (int j = 0; j <= playercount; j++) { // chance of soldiers
														// dropping out of the
														// tower
				for (int k = 0; k < TurmInhalt[j]; k++) {
					if (rnd.nextInt(100) >= 40) {
						schale[j] += 1;
						TurmInhalt[j] -= 1;
					}
				}
				for (int k = 0; k < einwurf[j]; k++) {
					if (rnd.nextInt(100) >= 50) {
						schale[j] += 1;
					} else {
						TurmInhalt[j] += 1;
					}
				}
			}

			if (schale[attackID] > schale[playercount]) {
				countryAttack.getOwner().chooseLand(countryDefend);
				countryAttack.getOwner().returnArmy(schale[playercount]);
				countryDefend.setArmy(schale[attackID] - schale[playercount]);
				schale[attackID] = 0;

			} else {
				countryAttack.getOwner().returnArmy(schale[attackID]);
				schale[attackID] = 0;
			}
		} else if (countryDefend.getRiotMarker() == 0) {
			int attackID = countryAttack.getOwner().getId();
			int defendID = countryDefend.getOwner().getId();
			schale[attackID] += attackCount;
			schale[defendID] += countryDefend.getArmy();
			int[] einwurf = new int[playercount + 1];
			Random rnd = new Random();

			countryAttack.setArmy(countryAttack.getArmy() - attackCount);

			if (countryAttack.getOwner().getAdvantageAttack()) { // setting the
																	// number of
																	// soldiers
																	// and
																	// checking
																	// if player
																	// has
																	// certain
																	// advantages
				einwurf[attackID] = schale[attackID] + 1;
				countryAttack.getOwner().useArmy(1);// that strengthen his army
			} else
				einwurf[attackID] = schale[attackID];

			if (countryDefend.getOwner().getAdvantageDefence()) {
				einwurf[defendID] = schale[defendID] + 1;
				countryDefend.getOwner().useArmy(1);
			} else
				einwurf[defendID] = schale[defendID];

			if (event.getEvent().equalsIgnoreCase("palace")
					&& countryDefend.getPalace()) {
				einwurf[defendID] += 1;
				countryDefend.getOwner().useArmy(1);
			}
			for (int i = 0; i <= playercount; i++) {
				if (i == attackID || i == defendID) {
					schale[i] = 0;
				} else {
					einwurf[i] = schale[i];
					schale[i] = 0;
				}
			}

			if (countryDefend.getRiotMarker() == 0) { // farmers count as
														// soldiers for
														// defending country
				for (int j = 0; j <= playercount; j++) { // chance of soldiers
															// dropping out of
															// the tower
					for (int k = 0; k < TurmInhalt[j]; k++) {
						if (rnd.nextInt(100) >= 40) {
							schale[j] += 1;
							TurmInhalt[j] -= 1;
						}
					}
					for (int k = 0; k < einwurf[j]; k++) {
						if (rnd.nextInt(100) >= 50) {
							schale[j] += 1;
						} else {
							TurmInhalt[j] += 1;
						}
					}
				}

				int resultA = schale[attackID];
				int resultD = schale[defendID] + schale[playercount];
				int diff = Math.abs(resultA - resultD);
				if (resultA > resultD) {
					countryDefend.getOwner().loseLand(countryDefend); // land
																		// transaction
					countryAttack.getOwner().chooseLand(countryDefend);
					countryDefend.getOwner().returnArmy(schale[defendID]); // army
																			// distribution
					countryDefend.setArmy(diff);
					countryAttack.getOwner().returnArmy(resultD);
					System.out.println("attacker won with " + resultA + " to "
							+ resultD);
				} else if (resultA < resultD) {
					countryDefend.setArmy(countryDefend.getArmy() - resultA);
					countryAttack.getOwner().returnArmy(resultA);
					countryDefend.getOwner().returnArmy(diff);
					System.out.println("attacker lost with " + resultA + " to "
							+ resultD);
				} else {
					countryDefend.getOwner().returnArmy(schale[defendID]);
					countryAttack.getOwner().returnArmy(resultA);
					countryDefend.getOwner().loseLand(countryDefend);
					countryDefend.neutralizeLand();
					System.out.println("bot lost with " + resultA + " to "
							+ resultD);
				}
				schale[attackID] = 0;
				schale[defendID] = 0;

			}

			else {
				for (int j = 0; j <= playercount; j++) { // chance of soldiers
															// dropping out of
															// the tower
					for (int k = 0; k < TurmInhalt[j]; k++) {
						if (rnd.nextInt(100) >= 40) {
							schale[j] += 1;
							TurmInhalt[j] -= 1;
						}
					}
					for (int k = 0; k < einwurf[j]; k++) {
						if (rnd.nextInt(100) >= 50) {
							schale[j] += 1;
						} else {
							TurmInhalt[j] += 1;
						}
					}
				}

				int resultA = schale[attackID];
				int resultD = schale[defendID];
				int diff = Math.abs(resultA - resultD);
				if (resultA > resultD) {
					countryDefend.getOwner().loseLand(countryDefend);
					countryAttack.getOwner().chooseLand(countryDefend);
					countryDefend.getOwner().returnArmy(resultD); // army
																	// distribution
					countryAttack.setArmy(countryAttack.getArmy() + diff);
					countryAttack.getOwner().returnArmy(resultD);
				} else if (resultA < resultD) {
					countryDefend.setArmy(countryDefend.getArmy() - resultA);
					countryAttack.getOwner().returnArmy(resultA);
					countryDefend.getOwner().returnArmy(diff);
				} else {
					countryDefend.getOwner().loseLand(countryDefend);
					countryDefend.neutralizeLand();
					countryDefend.getOwner().returnArmy(resultD);
					countryAttack.getOwner().returnArmy(resultA);
				}
				schale[attackID] = 0;
				schale[defendID] = 0;
			}
		}
		for(int i = 0; i<playercount; i++){
			players.get(i).setSchalenContent(schale[i]);
		}

	}

	public void riotTower(Land land, List<Player> players) {
		int[] einwurf = new int[playercount + 1];
		Random rnd = new Random();
		int playerID = land.getOwner().getId();

		for (int i = 0; i < playercount; i++) {
			if (i == playerID) {
				einwurf[i] = schale[i] + land.getArmy();
				schale[i] = 0;
			} else {
				einwurf[i] = schale[i];
				schale[i] = 0;
			}
		}
		einwurf[playercount] = schale[playercount] + land.getRiotMarker();
		schale[playercount] = 0;

		for (int j = 0; j <= playercount; j++) { // chance of soldiers dropping
													// out of the tower
			for (int k = 0; k < TurmInhalt[j]; k++) {
				if (rnd.nextInt(100) >= 40) {
					schale[j] += 1;
					TurmInhalt[j] -= 1;
				}
			}
			for (int k = 0; k < einwurf[j]; k++) {
				if (rnd.nextInt(100) >= 50) {
					schale[j] += 1;
				} else {
					TurmInhalt[j] += 1;
				}
			}
		}

		if (schale[playerID] > schale[playercount]) {
			land.getOwner().returnArmy(schale[playercount]);
			land.setArmy(schale[playerID] - schale[playercount]);
			land.addRiotMarker();
		} else {
			land.getOwner().returnArmy(schale[playerID]);
			land.neutralizeLand();
		}
		for(int i = 0; i<playercount; i++){
			players.get(i).setSchalenContent(schale[i]);
		}
	}

	// Winter round
	public void winterRiotTower(Land land, int farmers, List<Player> players) {
		int[] einwurf = new int[playercount + 1];
		Random rnd = new Random();
		int playerID = land.getOwner().getId();

		for (int i = 0; i < playercount; i++) {
			if (i == playerID) {
				einwurf[i] = schale[i] + land.getArmy();
				schale[i] = 0;
			} else {
				einwurf[i] = schale[i];
				schale[i] = 0;
			}
		}
		einwurf[playercount] = schale[playercount] + land.getRiotMarker() + farmers;
		schale[playercount] = 0;

		for (int j = 0; j <= playercount; j++) { // chance of soldiers dropping
													// out of the tower
			for (int k = 0; k < TurmInhalt[j]; k++) {
				if (rnd.nextInt(100) >= 40) {
					schale[j] += 1;
					TurmInhalt[j] -= 1;
				}
			}
			for (int k = 0; k < einwurf[j]; k++) {
				if (rnd.nextInt(100) >= 50) {
					schale[j] += 1;
				} else {
					TurmInhalt[j] += 1;
				}
			}
		}

		if (schale[playerID] > schale[playercount]) {
			land.getOwner().returnArmy(schale[playercount]);
			land.setArmy(schale[playerID] - schale[playercount]);
			land.addRiotMarker();
		} else {
			land.getOwner().returnArmy(schale[playerID]);
			land.neutralizeLand();
		}
		for(int i = 0; i<playercount; i++){
			players.get(i).setSchalenContent(schale[i]);
		}
	}


	public void winterRound(List<Player> players) {
		Random rnd = new Random();
		int[][] palace = new int[players.size()][5];
		int[][] mansion = new int[players.size()][5];
		int[][] church = new int[players.size()][5];

		for (int i = 0; i < players.size(); i++) {
			int sumCropLoss = event.getCropLoss() + players.get(i).getLand().size();
			List<Land> landList = players.get(i).getLand();
			List<Land> riotLand = new ArrayList<Land>();
			if (sumCropLoss > players.get(i).getCrop()) {
				int loss = sumCropLoss - players.get(i).getCrop();
				int numLand = 0;
				int x = 0;

				if (loss >= 7) {
					numLand = 3;
					x = 3;
				} else if ((loss >= 5) && (loss <= 6)) {
					numLand = 2;
					x = 3;
				} else if ((loss >= 3) && (loss <= 4)) {
					numLand = 2;
					x = 2;
				} else if (loss == 2) {
					numLand = 1;
					x = 2;
				} else {
					numLand = 1;
					x = 1;
				}

				boolean[] free = new boolean[numLand];

				for (int k = 0; k < numLand; k++) {
					free[k] = true;
				}
				for (int j = 0; j < numLand; j++) {
					int num = rnd.nextInt(loss);
					while (free[num] == false) {
						num = rnd.nextInt(loss);
					}
					riotLand.add(landList.get(num));
					free[num] = false;
				}
				
				List<Land> orderList = new ArrayList<Land>();
				if(players.get(i).getAI()){
					orderList = riotLand;
				}
				else{
					orderList = players.get(i).getRiotOrder(riotLand, x);
				}
				
				for (int l = 0; l < orderList.size(); l++) {
					winterRiotTower(orderList.get(l), x, players);
				}
			}
			palace[i][0] = players.get(i).getPalaceCount(0);
			palace[i][1] = players.get(i).getPalaceCount(1);
			palace[i][2] = players.get(i).getPalaceCount(2);
			palace[i][3] = players.get(i).getPalaceCount(3);
			palace[i][4] = players.get(i).getPalaceCount(4);

			mansion[i][0] = players.get(i).getMansionCount(0);
			mansion[i][1] = players.get(i).getMansionCount(1);
			mansion[i][2] = players.get(i).getMansionCount(2);
			mansion[i][3] = players.get(i).getMansionCount(3);
			mansion[i][4] = players.get(i).getMansionCount(4);

			church[i][0] = players.get(i).getChurchCount(0);
			church[i][1] = players.get(i).getChurchCount(1);
			church[i][2] = players.get(i).getChurchCount(2);
			church[i][3] = players.get(i).getChurchCount(3);
			church[i][4] = players.get(i).getChurchCount(4);
		}

		int[] maxPalace = { 0, 0, 0, 0, 0 };
		int[] maxChurch = { 0, 0, 0, 0, 0 };
		int[] maxMansion = { 0, 0, 0, 0, 0 };

		Player[] winnerPalace = new Player[5], winnerChurch = new Player[5], winnerMansion = new Player[5];
		List<Player> equalsPalace = new ArrayList<Player>(), equalsChurch = new ArrayList<Player>(), equalsMansion = new ArrayList<Player>();

		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < 5; j++) {
				if (palace[i][j] > maxPalace[j]) {
					maxPalace[j] = palace[i][j];
					winnerPalace[j] = players.get(i);
				}
				if (mansion[i][j] > maxMansion[j]) {
					maxMansion[j] = mansion[i][j];
					winnerMansion[j] = players.get(i);
				}
				if (church[i][j] > maxChurch[j]) {
					maxChurch[j] = church[i][j];
					winnerChurch[j] = players.get(i);
				}
			}
		}
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < 5; j++) {
				if ((palace[i][j] == maxPalace[j])
						&& (winnerPalace[j] != players.get(i))) {
					equalsPalace.add(players.get(i));
				}
				if ((mansion[i][j] == maxMansion[j])
						&& (winnerMansion[j] != players.get(i))) {
					equalsMansion.add(players.get(i));
				}
				if ((church[i][j] == maxChurch[j])
						&& (winnerChurch[j] != players.get(i))) {
					equalsChurch.add(players.get(i));
				}
			}
		}

		for (int i = 0; i < players.size(); i++) {
			int landPoints = players.get(i).getLand().size();
			int buildingPoints = 0;
			int palacePoints = 0;
			int mansionPoints = 0;
			int churchPoints = 0;
			for (int j = 0; j < landPoints; j++) {
				buildingPoints += players.get(i).getLand().get(j).getBuildingCount();
			}
			for (int j = 0; j < 5; j++) {
				if (winnerPalace[j] == players.get(i)) {
					if (equalsPalace.size() > 0) {
						palacePoints = 2;
					} else
						palacePoints = 3;
				} else if (equalsPalace.contains(players.get(i))) {
					palacePoints = 2;
				}

				if (winnerChurch[j] == players.get(i)) {
					if (equalsChurch.size() > 0) {
						churchPoints = 1;
					} else
						churchPoints = 2;
				}
				if (equalsChurch.contains(players.get(i))) {
					churchPoints = 1;
				}

				if (winnerMansion[j] == players.get(i)) {
					if (equalsMansion.size() > 0) {
						mansionPoints = 0;
					} else
						mansionPoints = 1;
				}
				if (equalsMansion.contains(players.get(i))) {
					mansionPoints = 0;
				}
			}
			// all points added together and set
			players.get(i).setAchievementPoints(players.get(i).getAchievementPoints()
					+ landPoints + buildingPoints + palacePoints + churchPoints
					+ mansionPoints);

			for (int j = 0; j < landPoints; j++) {
				players.get(i).getLand().get(j).neutralizeRiotMarker();
			}
			players.get(i).setCrop(0);
		}
	}

	public int getSchaleFarmer(){
		return schale[playercount];
	}

}
