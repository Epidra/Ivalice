package mod.ivalice.crafting;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class ModifierAddItem extends LootModifier {

    private final Item addedItem;





    //----------------------------------------CONSTRUCTOR----------------------------------------//

    /** This loot modifier adds an item to the loot table, given the conditions specified. */
    public ModifierAddItem(LootItemCondition[] conditionsIn, Item addedItemIn) {
        super(conditionsIn);
        this.addedItem = addedItemIn;
    }





    //----------------------------------------SUPPORT----------------------------------------//

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(addedItem));
        return generatedLoot;
    }





    //----------------------------------------SERIALIZER----------------------------------------//

    public static class Serializer extends GlobalLootModifierSerializer<ModifierAddItem> {
        @Override
        public ModifierAddItem read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            return new ModifierAddItem(ailootcondition, addedItem);
        }

        @Override
        public JsonObject write(ModifierAddItem instance) {
            return new JsonObject();
        }
    }



}