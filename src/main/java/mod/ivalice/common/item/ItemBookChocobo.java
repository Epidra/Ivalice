package mod.ivalice.common.item;

import mod.lucky77.item.ItemBook;

import static mod.ivalice.Ivalice.MODID;

public class ItemBookChocobo extends ItemBook {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	/** Default Constructor */
	public ItemBookChocobo(int _colorID, int _bookID){
		super(_colorID);
		createPages(_bookID);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void createPages(int id){
		if(id == 1){ // Book 1
			this.addPage("CHOCOBOS 1", "Explain here all the different stats about the chocobos and how they interact.", "", -1, MODID);
			this.addPage("CHOCOBOS 2", "Explain breeding and transmitting colors.", "", -1, MODID);
			this.addPage("CHOCOBOS 3", "Explain catching and favourite food.", "", -1, MODID);
		}
		if(id == 2){ // Book 2
			this.addPage("Plants 1", "Explain all the different crops and where to find them.", "", -1, MODID);
			this.addPage("Plants 2", "Explain what how to use crops to catch chocobos", "", -1, MODID);
		}
	}
	
	
	
}
