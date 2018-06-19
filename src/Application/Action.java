package Application;

import java.util.List;
import java.util.Random;

import Application.Server.Server;
import Domain.Eventcards;
import Domain.Land;

public class Action {

	public static String[] actions = { "palace", "church", "mansion", "crop",
			"thaler", "army5", "army3", "move", "fightA", "fightB" };
	public static boolean[] free = { true, true, true, true, true, true, true,
			true, true, true };
	public static String[] advantage = { "crop", "thaler", "defend", "attack",
			"army" };
	public static boolean[] advantageFree = { true, true, true, true, true };

	Random rnd = new Random();
	Tower tower;
	Server server;

	public static Eventcards event;

	public Action(Tower tower, Server server) {
		this.tower = tower;
		this.server = server;
	}

	public void actionBuildPalace(Player player) {
		if (player.getActionBuildPalace() != null) {
			System.out.println(player.getActionBuildPalace().getBuildingCount());
			System.out.println(player.getActionBuildPalace().getAnzahlBauplatz());
			System.out.println(player.getActionBuildPalace().getOwner());
			System.out.println(player.getActionBuildPalace().getPalace());
			if ((player.getActionBuildPalace().getBuildingCount() < player
					.getActionBuildPalace().getAnzahlBauplatz())
					&& (player.getActionBuildPalace().getPalace() == false)
					&& (player.getThaler() >= 3)
					&& (player.getActionBuildPalace().getOwner().equals(player))) {
				player.getActionBuildPalace().setPalace();
				player.setThaler(player.getThaler() - 3);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "palaceDone");
				}

			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "palaceFailed");
			}
		}
	}

	public void actionBuildChurch(Player player) {
		if (player.getActionBuildChurch() != null) {
			if ((player.getActionBuildChurch().getBuildingCount() < player
					.getActionBuildChurch().getAnzahlBauplatz())
					&& (player.getActionBuildChurch().getChurch() == false)
					&& (player.getThaler() >= 2)
					&& (player.getActionBuildChurch().getOwner().equals(player))) {
				player.getActionBuildChurch().setChurch();
				player.setThaler(player.getThaler() - 2);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, " churchDone");
				}

			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "churchFailed");
			}
		}
	}

	public void actionBuildMansion(Player player) {
		if (player.getActionBuildMansion() != null) {
			if ((player.getActionBuildMansion().getBuildingCount() < player
					.getActionBuildMansion().getAnzahlBauplatz())
					&& (player.getActionBuildMansion().getMansion() == false)
					&& (player.getThaler() >= 1)
					&& (player.getActionBuildMansion().getOwner()
							.equals(player))) {
				player.getActionBuildMansion().setMansion();
				player.setThaler(player.getThaler() - 1);
				if (event.getEvent().equalsIgnoreCase("riot")
						&& player.getActionBuildMansion().getRiotMarker() > 0) {
					player.getActionBuildMansion().removeRiotMarker();
				}
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "mansionDone");
				}

			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "mansionFailed");
			}
		}
	}

	public void actionThaler(Player player, List<Player> players) {
		if (player.getActionThaler() != null) {
			if (player.getActionThaler().getOwner().equals(player)) {
				if (player.getAdvantageThaler()) {
					player.setThaler(player.getThaler()
							+ player.getActionThaler().getTaler() + 1);
				} else {
					player.setThaler(player.getThaler()
							+ player.getActionThaler().getTaler());
				}
				if (player.getActionThaler().getRiotMarker() == 0) {
					player.getActionThaler().addRiotMarker();
				} else {
					tower.riotTower(player.getActionThaler(), players);
				}
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "thalerDone");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "thalerFailed");
			}
		}
	}

	public void actionCrop(Player player, List<Player> players) {
		if (player.getActionCrop() != null) {
			if (player.getActionCrop().getOwner().equals(player)) {
				if (player.getAdvantageCrop()) {
					player.setCrop(player.getCrop()
							+ player.getActionCrop().getGetreide() + 1);
				} else
					player.setCrop(player.getCrop()
							+ player.getActionCrop().getGetreide());
				if (player.getActionCrop().getRiotMarker() != 0) {
					tower.riotTower(player.getActionCrop(), players);// kampf
				}
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "cropDone");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "cropFailed");
			}
		}
	}

	public void actionArmy5(Player player) {
		if (player.getActionArmy5() != null) {
			if ((player.getThaler() >= 3) && (player.getArmy() >= 5)
					&& (player.
							getActionArmy5().
							getOwner().equals(player))) {
				int army = 0;
				if (event.getEvent().equalsIgnoreCase("army")) {
					army = 4;
				} else
					army = 5;
				if (player.getAdvantageArmy()) {
					army += 1;
				}
				player.getActionArmy5().setArmy(
						player.getActionArmy5().getArmy() + army);
				player.setThaler(player.getThaler() - 3);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "army5Done");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "army5Failed");
			}
		}
	}

	public void actionArmy3(Player player) {
		if (player.getActionArmy3() != null) {
			if ((player.getThaler() >= 2) && (player.getArmy() >= 3)
					&& (player.getActionArmy3().getOwner().equals(player))) {
				int army = 0;
				if (event.getEvent().equalsIgnoreCase("army")) {
					army = 2;
				} else
					army = 3;
				player.getActionArmy3().setArmy(
						player.getActionArmy3().getArmy() + army);
				player.setThaler(player.getThaler() - 2);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "army3Done");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "army3Failed");
			}
		}
	}

	public void actionMove(Player player, Land land, int anzahl) {
		if (player.getActionMove() != null) {
			if ((player.getThaler() >= 1)
					&& (player.getActionMove().getArmy() > 1)
					&& (player.getActionMove().getOwner().equals(player))) {
				player.getActionMove().setArmy(
						player.getActionMove().getArmy() + 1);
				player.setThaler(player.getThaler() - 1);
				if (land.getOwner() == player) {
					land.setArmy(land.getArmy() + anzahl);
				}
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "moveDone");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "moveFailed");
			}
		}
	}

	public void actionFightA(Player player, Land land, int anzahl,
			List<Player> players) {
		if (player.getActionFightA() != null) {
			if ((player.getActionFightA().getOwner().equals(player))
					&& (player.getActionFightA().getArmy() > 1)) {
				tower.towerFight(player.getActionFightA(), land, anzahl,
						players);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "fightADone");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "fightAFailed");
			}
		}
	}

	public void actionFightB(Player player, Land land, int anzahl,
			List<Player> players) {
		if (player.getActionFightB() != null) {
			if ((player.getActionFightA().getOwner().equals(player))
					&& (player.getActionFightB().getArmy() > 1)) {
				tower.towerFight(player.getActionFightB(), land, anzahl,
						players);
				if (player.getAI() == false) {
					server.deliverToSinglePlayer(player, "fightBDone");
				}
			} else if (player.getAI() == false) {
				server.deliverToSinglePlayer(player, "fightBFailed");
			}
		}
	}

	public String[] setActionOrder() {
		String[] actionOrder = new String[10];

		for (int i = 0; i < 10; i++) {
			int rand = rnd.nextInt(10);
			while (free[rand] == false) {
				rand = rnd.nextInt(10);
			}
			System.out.println(free[i]);
			free[rand] = false;
			actionOrder[i] = actions[rand];

		}
		return actionOrder;
	}

	public String[] setAdvantageOrder() {
		String[] advantageOrder = new String[5];

		for (int j = 0; j < 5; j++) {
			advantageFree[j] = true;
		}
		for (int i = 0; i < 5; i++) {
			int rand = rnd.nextInt(5);
			while (advantageFree[rand] == false) {
				rand = rnd.nextInt(5);
			}
			advantageFree[rand] = false;
			advantageOrder[i] = advantage[rand];
		}
		return advantageOrder;
	}

}
