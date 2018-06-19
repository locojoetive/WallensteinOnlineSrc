package Domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Landparser {
	public List<Land> landList;
	public List<Land> landList2;
	public Land getLandByName(String landName){
		for(int i=0;i<landList.size();i++){
			if(landList.get(i).getName().equals(landName)){
				return landList.get(i);
			}
		}
		return null;
	}
	public void remove3PlayerLands(){
		landList.remove(getLandByName("Luettich"));
		landList.remove(getLandByName("Burgund"));
		landList.remove(getLandByName("Konstanz"));
		landList.remove(getLandByName("Bayern"));
		landList.remove(getLandByName("Bremen"));
		landList.remove(getLandByName("Holstein"));
		landList.remove(getLandByName("Tirol"));
		landList.remove(getLandByName("Steiermark"));
	}
	
	public Land[] startLands = new Land[3];
	
	public Landparser(){
		landList = new ArrayList<>();
		try{
			File landXML = new File("data/laender.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(landXML);
			
			doc.getDocumentElement().normalize();
			
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("land");
			
			for (int temp = 0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				Element mElement = (Element) nNode;
				NodeList mList = mElement.getElementsByTagName("nachbarland");
				Land land = new Land();
				if (nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					land.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
					land.setRegion(eElement.getElementsByTagName("region").item(0).getTextContent());
					land.setTaler(Integer.parseInt(eElement.getElementsByTagName("taler").item(0).getTextContent()));
					land.setGetreide(Integer.parseInt(eElement.getElementsByTagName("getreide").item(0).getTextContent()));
					land.setAnzahlBauplatz(Integer.parseInt(eElement.getElementsByTagName("anzahlBauplatz").item(0).getTextContent()));
					land.neutralizeLand();
					for(int i = 0; i < mList.getLength(); i++){
						land.setBorderland(eElement.getElementsByTagName("nachbarland").item(i).getTextContent());
					}
					landList.add(land);
//					System.out.println("nachbarland " + eElement.getElementsByTagName("nachbarland").item(0).getTextContent());
//					System.out.println("name " + eElement.getElementsByTagName("name").item(0).getTextContent());
//					System.out.println("region " + eElement.getElementsByTagName("region").item(0).getTextContent());
//					System.out.println("taler " + eElement.getElementsByTagName("taler").item(0).getTextContent());
//					System.out.println("getreide " + eElement.getElementsByTagName("getreide").item(0).getTextContent());
//					System.out.println("anzahlbauplatz " + eElement.getElementsByTagName("anzahlBauplatz").item(0).getTextContent());
				}
			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Land[] getStartLands(){
		return startLands;
	}
	public Land getLand(String landName){
		for(int i=0;i<landList.size();i++){
			if(landList.get(i).getName().equals(landName)){
				return landList.get(i);
			}
		}
		return null;
	}
	
	public Land getStartLand(String landName){
		for(int i=0;i<3;i++)
		{
			if(startLands[i].getName().equals(landName)){
				return startLands[i];
			}
		}
		return null;
	}
	
	public void initStartLands(){
		landList2 = landList;
		
		Random rm = new Random();
		int rnd = rm.nextInt(landList2.size());
		startLands[0] = landList.get(rnd);
		landList2.remove(rnd);
		
		rnd = rm.nextInt(landList2.size());
		startLands[1] = landList.get(rnd);
		landList2.remove(rnd);
		
		rnd = rm.nextInt(landList2.size());
		startLands[2] = landList.get(rnd);
		landList2.remove(rnd);	
	}
	
	public void repositionStartLands(String landToReposition){
		Random rm = new Random();
		int rnd = rm.nextInt(landList2.size());
		for(int i=0;i<3;i++){
			if(startLands[i].getName().equals(landToReposition)){
				startLands[i] = landList2.get(rnd);
				landList2.remove(rnd);
			}
		}
		
	}
	
	public List<Land> getLands(){
		return landList;
	}
	public String startLandsToString(){
		String str= "chooseLand";
		for(int i = 0; i< 3; i++){
			str+="-" + startLands[i].getName();
		}
		
		return str;
	}
}
