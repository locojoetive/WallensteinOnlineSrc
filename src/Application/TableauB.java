package Application;

import java.util.List;

public class TableauB {
	private int height;
	private int width;
	private int heightAction;
	private int widthAction;
	Cards[][]actionCards=new Cards[9][9];
	
	
	public TableauB(int height, int width){
		this.height=height;
		this.width=width;
	}
	
	

	//aktionskarten verteilen
	public void PlaceActionCardsOnTableau(List<Cards>actionList){
	for(int i=0;i<actionList.size();i++){
			for(int z=9;z<=height;z++){
				for(int y=0;y<width;y++){
					while(i<10){
						actionCards[z][y]=actionList.get(i);
					}
				}
			}
			
		}
	}
}

	

