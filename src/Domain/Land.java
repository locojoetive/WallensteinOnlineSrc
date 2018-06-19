package Domain;

import java.util.ArrayList;
import java.util.List;  

import com.sun.xml.internal.fastinfoset.util.StringArray;

import Application.Player;


public class Land {
	private String name;
	private String region;
	private List<String> borderland = new ArrayList<>();
	private int taler;
	private int getreide;
	private int anzahlBauplatz;
	private int buildingCount = 0;
	private int army;
	private int riotMarker;
	private Player owner;
	private boolean palace, church, mansion = false;

	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getRegion(){
		return region;
	}
	public void setRegion(String region){
		this.region = region;
	}
	
	public int getTaler(){
		return taler;
	}
	public void setTaler(int taler){
		this.taler = taler;
	}
	
	public int getGetreide(){
		return getreide;
	}
	public void setGetreide(int getreide){
		this.getreide = getreide;
	}
	
	public int getAnzahlBauplatz(){
		return anzahlBauplatz;
	}
	public void setAnzahlBauplatz(int anzahlBauplatz){
		this.anzahlBauplatz = anzahlBauplatz;
	}
	
	public List<String> getborderland(){
		return borderland;
	}
	
	public void setBorderland(String nachbar){
		borderland.add(nachbar);
	}													//up to here methods only for initialization
	
	public void setArmy(int army){
		this.army = army;
	}
	public int getArmy(){
		return army;
	}
	
	public void setBuildingCount(int i){
		this.buildingCount = i;
	}
	
	public int getBuildingCount(){
		return buildingCount;
	}
	
	public Land getLand(List<Land> landList, String name){ 	//suche Land nach namen
		Land land = new Land();
		for(int i = 0; i < landList.size(); i++){
			if(name.equals(landList.get(i).getName())){
				land = landList.get(i);
			}
			else{
				continue;
			}
		}
		return land;
	}
	
	public void setPalace(){
		this.palace = true;
		this.buildingCount +=1;
		this.owner.buildPalace(this.region);
	}
	public boolean getPalace(){
		return palace;
	}
	
	public void setChurch(){
		this.church = true;
		this.buildingCount +=1;
		this.owner.buildChurch(this.region);
	}
	public boolean getChurch(){
		return church;
	}
	
	public void setMansion(){
		this.mansion = true;
		this.buildingCount +=1;
		this.owner.buildMansion(this.region);
	}
	public boolean getMansion(){
		return mansion;
	}
	
	public void setOwner(Player player){
		this.owner = player;
	}
	public Player getOwner(){
		return owner;
	}
	
	public void addRiotMarker(){
		this.riotMarker += 1;
	}
	public void removeRiotMarker(){
		this.riotMarker -= 1;
	}
	public void neutralizeRiotMarker(){
		this.riotMarker = 0;
	}
	public int getRiotMarker(){
		return riotMarker;
	}
	
	public void neutralizeLand(){		//neutralisieren für unentschieden
		this.riotMarker = 0;
		if(this.mansion) this.owner.destroyMansion(region);
		if(this.church) this.owner.destroyChurch(region);	
		if(this.palace) this.owner.destroyPalace(region);
		this.palace = false;
		this.mansion = false;
		this.church = false;
		this.owner = null;
	}

	public String toString(){
		return name + "#" + army + "#" + buildingCount + "#" + riotMarker + "#" + palace + "#" + mansion + "#" + church + "#" + owner.getUsername() +"#";
	}
///////////////////////////////////////////////////////////////////////// Manu
	
public Land() {
}

public void setRiotMarker(int i){
this.riotMarker=i;
}
public void setPalace(boolean b){
this.palace=b;
}

public void setMansion(boolean b){
this.mansion=b;
}

public void setChurch(boolean b){
this.church=b;
}
	
}
