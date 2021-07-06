package mod.ivalice.model;

// Made with Blockbench 3.8.3
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.ivalice.entity.EntityChocobo;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelChick<T extends EntityChocobo>  extends AgeableModel<T> {

    private final ModelRenderer Body;
    private final ModelRenderer Front_r1;
    private final ModelRenderer LeftFootPart;
    private final ModelRenderer TalonLeftBack_r1;
    private final ModelRenderer TalonLeftLeft_r1;
    private final ModelRenderer TalonLeftRight_r1;
    private final ModelRenderer RightFootPart;
    private final ModelRenderer TalonRightBack_r1;
    private final ModelRenderer TalonRightLeft_r1;
    private final ModelRenderer TalonRightRight_r1;
    private final ModelRenderer WingLeft;
    private final ModelRenderer WingRight;
    private final ModelRenderer UpperPart;
    private final ModelRenderer HeadPart;

    private float headXRot;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ModelChick() {

        texWidth = 32;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 18.75F, 0.0F);
        Body.texOffs(0, 0).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, false);

        Front_r1 = new ModelRenderer(this);
        Front_r1.setPos(0.0F, -0.3415F, -2.9085F);
        Body.addChild(Front_r1);
        setRotationAngle(Front_r1, 0.5236F, 0.0F, 0.0F);
        Front_r1.texOffs(17, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, false);

        LeftFootPart = new ModelRenderer(this);
        LeftFootPart.setPos(1.5F, 19.75F, 0.0F);
        LeftFootPart.texOffs(0, 0).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        TalonLeftBack_r1 = new ModelRenderer(this);
        TalonLeftBack_r1.setPos(0.0F, 1.25F, 0.0F);
        LeftFootPart.addChild(TalonLeftBack_r1);
        setRotationAngle(TalonLeftBack_r1, -0.2618F, 0.0F, 0.0F);
        TalonLeftBack_r1.texOffs(20, 12).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        TalonLeftLeft_r1 = new ModelRenderer(this);
        TalonLeftLeft_r1.setPos(0.0F, 1.25F, 0.0F);
        LeftFootPart.addChild(TalonLeftLeft_r1);
        setRotationAngle(TalonLeftLeft_r1, 0.2618F, -0.3491F, 0.0F);
        TalonLeftLeft_r1.texOffs(21, 20).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        TalonLeftRight_r1 = new ModelRenderer(this);
        TalonLeftRight_r1.setPos(0.0F, 1.25F, 0.0F);
        LeftFootPart.addChild(TalonLeftRight_r1);
        setRotationAngle(TalonLeftRight_r1, 0.2618F, 0.3491F, 0.0F);
        TalonLeftRight_r1.texOffs(14, 20).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        RightFootPart = new ModelRenderer(this);
        RightFootPart.setPos(-1.5F, 19.75F, 0.0F);
        RightFootPart.texOffs(24, 16).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        TalonRightBack_r1 = new ModelRenderer(this);
        TalonRightBack_r1.setPos(0.0F, 1.25F, 0.0F);
        RightFootPart.addChild(TalonRightBack_r1);
        setRotationAngle(TalonRightBack_r1, -0.2618F, 0.0F, 0.0F);
        TalonRightBack_r1.texOffs(23, 3).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        TalonRightLeft_r1 = new ModelRenderer(this);
        TalonRightLeft_r1.setPos(0.0F, 1.25F, 0.0F);
        RightFootPart.addChild(TalonRightLeft_r1);
        setRotationAngle(TalonRightLeft_r1, 0.2618F, -0.3491F, 0.0F);
        TalonRightLeft_r1.texOffs(11, 24).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        TalonRightRight_r1 = new ModelRenderer(this);
        TalonRightRight_r1.setPos(0.0F, 1.25F, 0.0F);
        RightFootPart.addChild(TalonRightRight_r1);
        setRotationAngle(TalonRightRight_r1, 0.2618F, 0.3491F, 0.0F);
        TalonRightRight_r1.texOffs(23, 7).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        WingLeft = new ModelRenderer(this);
        WingLeft.setPos(2.5F, 18.5F, -2.0F);
        WingLeft.texOffs(0, 19).addBox(-0.25F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);

        WingRight = new ModelRenderer(this);
        WingRight.setPos(2.5F, 18.5F, -2.0F);
        WingRight.texOffs(13, 12).addBox(-5.75F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);

        UpperPart = new ModelRenderer(this);
        UpperPart.setPos(0.0F, 17.5F, -2.5F);


        HeadPart = new ModelRenderer(this);
        HeadPart.setPos(0.0F, 0.0F, -0.5F);
        UpperPart.addChild(HeadPart);
        HeadPart.texOffs(0, 12).addBox(-1.5F, -2.5F, -2.25F, 3.0F, 3.0F, 3.0F, 0.0F, false);
        HeadPart.texOffs(7, 19).addBox(-1.0F, -1.75F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    }




    //----------------------------------------ANIMATION----------------------------------------//

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.headXRot = entityIn.getHeadEatAngleScale(partialTick);
    }




    //----------------------------------------RENDER----------------------------------------//

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        RightFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        WingRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        UpperPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }




    //----------------------------------------SUPPORT----------------------------------------//

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.UpperPart);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.Body, this.WingLeft, this.WingRight, this.RightFootPart, this.LeftFootPart);
    }

}
