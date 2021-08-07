package mod.ivalice.block;

import mod.ivalice.ShopKeeper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;

public class BlockCrop extends CropBlock {

    private final String id;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** Contructor with predefined BlockProperty */
    public BlockCrop(String id, Block block) {
        super(Properties.copy(block));
        this.id = id;
    }




    //----------------------------------------HELPER----------------------------------------//

    protected ItemLike getBaseSeedId() {
        if(id.matches("gysahl")) return ShopKeeper.SEEDS_GYSAHL.get();
        if(id.matches("krakka")) return ShopKeeper.SEEDS_KRAKKA.get();
        if(id.matches("mimett")) return ShopKeeper.SEEDS_MIMETT.get();
        if(id.matches("sylkis")) return ShopKeeper.SEEDS_SYLKIS.get();
        if(id.matches("tantal")) return ShopKeeper.SEEDS_TANTAL.get();
        return ShopKeeper.SEEDS_GYSAHL.get();
    }
}
