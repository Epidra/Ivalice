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
			this.addPage("???", "", "", -1, MODID);
		}
		if(id == 2){ // Book 2
			this.addPage("???", "", "", -1, MODID);
		}
		if(id == 3){ // Book 3
			this.addPage("???", "", "", -1, MODID);
		}
	}
	
	
	
}
