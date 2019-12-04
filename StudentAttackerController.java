package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.Defender;
import game.models.Game;
import game.models.Node;
import game.system.Pair;

import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		// pair data structure is generated with the defender and distance integer value, false and true used to indicate which defender type
		// pair data structure is used so that multiple array lists are not needed, also allows to store the defender as a key with the distance being the value pertaining to that "key"

		Pair<Defender, Integer> pairedData = closestDefender(game, false);
		Defender vulnerableDefender = pairedData.first();
		int vulnerableDefDist = pairedData.second();

		pairedData = closestDefender(game, true);
		Defender normalDefender = pairedData.first();
		int normalDefenderDist = pairedData.second();

		// checking if there is a defender near me and its not vulnerable
		if(normalDefender != null && (normalDefenderDist <= vulnerableDefDist && normalDefenderDist <= 4)){ //flee, the integer value given determines time from where the gator flees optimal range from 4 to around 9
																													// , anything lower is too close and dies anything higher is too large and does not use power pill time efficiently
			return game.getAttacker().getNextDir(normalDefender.getLocation(), false);
		}// if above statement is false there is either no threat or can chase vulnerable

		else if(vulnerableDefender != null){
			return game.getAttacker().getNextDir(vulnerableDefender.getLocation(), true);
		}

		else if (powerPillNearby(game)){// if there is a power pill near the attacker, stay there
			if(normalDefenderDist <= 4){//if there is a defender in the way of the vulnerable one eat pill instead of chase
				return eatPill(game);
			}
			return game.getAttacker().getReverse();
		}


		else if(normalDefenderDist <= 4){
			return game.getAttacker().getNextDir(normalDefender.getLocation(), false);
		}


		else return eatPill(game);



	}
	private int eatPill(Game game) {// sends the attacker to eat power pills and when there are none eat regular pills kinda

		Node _nodePowerPill = betterGetTarget(game.getPowerPillList(), game);

		Node _nodePill = betterGetTarget(game.getPillList(), game);


		try {
			return game.getAttacker().getNextDir(_nodePowerPill, true);
		} catch (NullPointerException e) {
			return game.getAttacker().getNextDir(_nodePill, true);
		}
	}


	private Pair<Defender,Integer> closestDefender(Game game, boolean defenderCondition) { // true for distance from defender to attacker and false for the opposite

		Defender defender = null;
		int temporaryVal = 10000;


		Node gatorLocation = game.getAttacker().getLocation();
		if (defenderCondition) { //checking the boolean that was passed in Update (defender condition)
			for (int i = 0; i < 4; i++) { //iterating through the defenders
				Defender temporaryDefender = game.getDefenders().get(i);
				if (temporaryVal > temporaryDefender.getPathTo(gatorLocation).size() && !temporaryDefender.isVulnerable() && temporaryDefender.getLairTime() == 0 ) {
					defender = temporaryDefender;
					temporaryVal =   temporaryDefender.getPathTo((gatorLocation)).size();
				}
			}
		} else {		// path from the attacker to the closest defender that is vulnerable

			for (int i = 0; i < 4; i++) {

				Defender temp = game.getDefenders().get(i);
				if (temporaryVal > gatorLocation.getPathDistance(temp.getLocation()) && temp.isVulnerable()) {
					defender = temp;
					temporaryVal = gatorLocation.getPathDistance(temp.getLocation());

				}
			}
		}
		return new Pair<>(defender,temporaryVal); // returns pair of data (defender, distance)
	}



	private boolean powerPillNearby(Game game) { // checks for nearby power pills

		List<Node> nodes =  game.getAttacker().getLocation().getNeighbors(); // creates array list to iterate through and check if it is a power pill nearby

		for(int i = 0; i < nodes.size(); i ++) {
			if (nodes.get(i) != null && game.checkPowerPill(nodes.get(i))) {
				return true;
			}
		}
		return false;

	}
	public static Node betterGetTarget(List<Node> node, Game game) { // Code snippet from slack
		if (node.size() == 0) {
			return null;
		}
		int minDistance = Integer.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < node.size(); i++) {
			int currentDistance = node.get(i).getPathDistance(game.getAttacker().getLocation());
			if (currentDistance < minDistance) {
				minDistance = node.get(i).getPathDistance(game.getAttacker().getLocation());
				minIndex = i;
			}
		}
		return node.get(minIndex);
	}
}