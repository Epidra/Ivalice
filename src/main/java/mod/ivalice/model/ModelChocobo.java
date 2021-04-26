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
public class ModelChocobo<T extends EntityChocobo>  extends AgeableModel<T> {

    private final ModelRenderer Saddle;
    private final ModelRenderer Body;
    private final ModelRenderer BackFeather3_r1;
    private final ModelRenderer BackFeather2_r1;
    private final ModelRenderer BackFeather1_r1;
    private final ModelRenderer Front_r1;
    private final ModelRenderer LeftFootPart;
    private final ModelRenderer LegLeftUpper_r1;
    private final ModelRenderer LeftTalon;
    private final ModelRenderer TalonLeftBack_r1;
    private final ModelRenderer TalonLeftLeft_r1;
    private final ModelRenderer TalonLeftRight_r1;
    private final ModelRenderer LegLeftLower_r1;
    private final ModelRenderer RightFootPart;
    private final ModelRenderer LegRightUpper_r1;
    private final ModelRenderer RightTalon;
    private final ModelRenderer TalonRightBack_r1;
    private final ModelRenderer TalonRightLeft_r1;
    private final ModelRenderer TalonRightRight_r1;
    private final ModelRenderer LegRightLower_r1;
    private final ModelRenderer WingLeft;
    private final ModelRenderer WingRight;
    private final ModelRenderer UpperPart;
    private final ModelRenderer HeadPart;
    private final ModelRenderer HeadFeather3_r1;
    private final ModelRenderer HeadFeather2_r1;
    private final ModelRenderer HeadFeather1_r1;
    private final ModelRenderer Beak_r1;
    private final ModelRenderer Beak_r2;

    private float headXRot;




    //----------------------------------------CONSTRUCTOR----------------------------------------//

    public ModelChocobo() {

        texWidth = 64;
        texHeight = 64;

        Saddle = new ModelRenderer(this);
        Saddle.setPos(0.0F, 2.0F, 0.0F);
        Saddle.texOffs(0, 0).addBox(-5.0F, -6.5F, -4.0F, 10.0F, 1.0F, 8.0F, 0.0F, false);

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 2.0F, 0.0F);
        Body.texOffs(0, 36).addBox(-6.0F, -6.0F, -8.0F, 12.0F, 12.0F, 16.0F, 0.0F, false);

        BackFeather3_r1 = new ModelRenderer(this);
        BackFeather3_r1.setPos(0.0F, 0.0F, 8.0F);
        Body.addChild(BackFeather3_r1);
        setRotationAngle(BackFeather3_r1, 0.1745F, 0.0F, 0.0F);
        BackFeather3_r1.texOffs(40, 0).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 0.0F, 10.0F, 0.0F, false);

        BackFeather2_r1 = new ModelRenderer(this);
        BackFeather2_r1.setPos(0.0F, -3.0F, 8.0F);
        Body.addChild(BackFeather2_r1);
        setRotationAngle(BackFeather2_r1, 0.3491F, 0.0F, 0.0F);
        BackFeather2_r1.texOffs(40, 0).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 0.0F, 10.0F, 0.0F, false);

        BackFeather1_r1 = new ModelRenderer(this);
        BackFeather1_r1.setPos(0.0F, -6.0F, 8.0F);
        Body.addChild(BackFeather1_r1);
        setRotationAngle(BackFeather1_r1, 0.5236F, 0.0F, 0.0F);
        BackFeather1_r1.texOffs(40, 0).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 0.0F, 10.0F, 0.0F, false);

        Front_r1 = new ModelRenderer(this);
        Front_r1.setPos(0.0F, 0.0F, -8.0F);
        Body.addChild(Front_r1);
        setRotationAngle(Front_r1, 0.5236F, 0.0F, 0.0F);
        Front_r1.texOffs(0, 20).addBox(-5.0F, -5.0F, -3.0F, 10.0F, 10.0F, 6.0F, 0.0F, false);

        LeftFootPart = new ModelRenderer(this);
        LeftFootPart.setPos(4.0F, 8.0F, 0.0F);


        LegLeftUpper_r1 = new ModelRenderer(this);
        LegLeftUpper_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftFootPart.addChild(LegLeftUpper_r1);
        setRotationAngle(LegLeftUpper_r1, 0.3491F, 0.0F, 0.0F);
        LegLeftUpper_r1.texOffs(32, 27).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

        LeftTalon = new ModelRenderer(this);
        LeftTalon.setPos(0.0F, 12.0F, 0.0F);
        LeftFootPart.addChild(LeftTalon);


        TalonLeftBack_r1 = new ModelRenderer(this);
        TalonLeftBack_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftTalon.addChild(TalonLeftBack_r1);
        setRotationAngle(TalonLeftBack_r1, -0.2618F, 0.0F, 0.0F);
        TalonLeftBack_r1.texOffs(41, 30).addBox(-1.0F, 0.25F, 0.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        TalonLeftLeft_r1 = new ModelRenderer(this);
        TalonLeftLeft_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftTalon.addChild(TalonLeftLeft_r1);
        setRotationAngle(TalonLeftLeft_r1, 0.2618F, -0.3491F, 0.0F);
        TalonLeftLeft_r1.texOffs(41, 30).addBox(-1.0F, 0.25F, -8.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        TalonLeftRight_r1 = new ModelRenderer(this);
        TalonLeftRight_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftTalon.addChild(TalonLeftRight_r1);
        setRotationAngle(TalonLeftRight_r1, 0.2618F, 0.3491F, 0.0F);
        TalonLeftRight_r1.texOffs(41, 30).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        LegLeftLower_r1 = new ModelRenderer(this);
        LegLeftLower_r1.setPos(0.0F, 0.0F, 0.0F);
        LeftTalon.addChild(LegLeftLower_r1);
        setRotationAngle(LegLeftLower_r1, -0.1745F, 0.0F, 0.0F);
        LegLeftLower_r1.texOffs(56, 53).addBox(-1.0F, -8.5F, -0.5F, 2.0F, 9.0F, 2.0F, 0.0F, false);

        RightFootPart = new ModelRenderer(this);
        RightFootPart.setPos(-4.0F, 8.0F, 0.0F);


        LegRightUpper_r1 = new ModelRenderer(this);
        LegRightUpper_r1.setPos(0.0F, 0.0F, 0.0F);
        RightFootPart.addChild(LegRightUpper_r1);
        setRotationAngle(LegRightUpper_r1, 0.3491F, 0.0F, 0.0F);
        LegRightUpper_r1.texOffs(32, 27).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

        RightTalon = new ModelRenderer(this);
        RightTalon.setPos(0.0F, 0.0F, 0.0F);
        RightFootPart.addChild(RightTalon);


        TalonRightBack_r1 = new ModelRenderer(this);
        TalonRightBack_r1.setPos(0.0F, 12.0F, 0.0F);
        RightTalon.addChild(TalonRightBack_r1);
        setRotationAngle(TalonRightBack_r1, -0.2618F, 0.0F, 0.0F);
        TalonRightBack_r1.texOffs(41, 30).addBox(-1.0F, 0.25F, 0.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        TalonRightLeft_r1 = new ModelRenderer(this);
        TalonRightLeft_r1.setPos(0.0F, 12.0F, 0.0F);
        RightTalon.addChild(TalonRightLeft_r1);
        setRotationAngle(TalonRightLeft_r1, 0.2618F, -0.3491F, 0.0F);
        TalonRightLeft_r1.texOffs(41, 30).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        TalonRightRight_r1 = new ModelRenderer(this);
        TalonRightRight_r1.setPos(0.0F, 12.0F, 0.0F);
        RightTalon.addChild(TalonRightRight_r1);
        setRotationAngle(TalonRightRight_r1, 0.2618F, 0.3491F, 0.0F);
        TalonRightRight_r1.texOffs(41, 30).addBox(-1.0F, 0.25F, -8.0F, 2.0F, 2.0F, 8.0F, 0.0F, false);

        LegRightLower_r1 = new ModelRenderer(this);
        LegRightLower_r1.setPos(0.0F, 12.0F, 0.0F);
        RightTalon.addChild(LegRightLower_r1);
        setRotationAngle(LegRightLower_r1, -0.1745F, 0.0F, 0.0F);
        LegRightLower_r1.texOffs(56, 53).addBox(-1.0F, -8.5F, -0.5F, 2.0F, 9.0F, 2.0F, 0.0F, false);

        WingLeft = new ModelRenderer(this);
        WingLeft.setPos(6.0F, 3.0F, -7.0F);
        WingLeft.texOffs(34, 0).addBox(0.0F, -6.0F, 0.0F, 1.0F, 10.0F, 14.0F, 0.0F, false);

        WingRight = new ModelRenderer(this);
        WingRight.setPos(6.0F, 2.0F, -7.0F);
        WingRight.texOffs(34, 0).addBox(-13.0F, -5.0F, 0.0F, 1.0F, 10.0F, 14.0F, 0.0F, false);

        UpperPart = new ModelRenderer(this);
        UpperPart.setPos(0.0F, 0.0F, -10.0F);
        UpperPart.texOffs(0, 33).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 15.0F, 4.0F, 0.0F, false);

        HeadPart = new ModelRenderer(this);
        HeadPart.setPos(0.0F, -13.0F, 0.0F);
        UpperPart.addChild(HeadPart);
        HeadPart.texOffs(40, 40).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        HeadFeather3_r1 = new ModelRenderer(this);
        HeadFeather3_r1.setPos(0.0F, 0.0F, 0.0F);
        HeadPart.addChild(HeadFeather3_r1);
        setRotationAngle(HeadFeather3_r1, 0.0F, -0.0873F, 1.5708F);
        HeadFeather3_r1.texOffs(28, 0).addBox(-5.5F, -2.5F, 3.0F, 5.0F, 0.0F, 5.0F, 0.0F, false);

        HeadFeather2_r1 = new ModelRenderer(this);
        HeadFeather2_r1.setPos(0.0F, 0.0F, 0.0F);
        HeadPart.addChild(HeadFeather2_r1);
        setRotationAngle(HeadFeather2_r1, 0.0F, 0.0873F, -1.5708F);
        HeadFeather2_r1.texOffs(28, 0).addBox(0.5F, -2.5F, 3.0F, 5.0F, 0.0F, 5.0F, 0.0F, false);

        HeadFeather1_r1 = new ModelRenderer(this);
        HeadFeather1_r1.setPos(0.0F, 0.0F, 0.0F);
        HeadPart.addChild(HeadFeather1_r1);
        setRotationAngle(HeadFeather1_r1, 0.0873F, 0.0F, 0.0F);
        HeadFeather1_r1.texOffs(28, 0).addBox(-2.5F, -5.5F, 3.0F, 5.0F, 0.0F, 5.0F, 0.0F, false);

        Beak_r1 = new ModelRenderer(this);
        Beak_r1.setPos(0.0F, 0.0F, 0.0F);
        HeadPart.addChild(Beak_r1);
        setRotationAngle(Beak_r1, 0.1745F, 0.0F, 0.0F);
        Beak_r1.texOffs(1, 10).addBox(-2.0F, -2.25F, -8.75F, 4.0F, 2.0F, 7.0F, 0.0F, false);

        Beak_r2 = new ModelRenderer(this);
        Beak_r2.setPos(0.0F, 0.0F, 0.0F);
        HeadPart.addChild(Beak_r2);
        setRotationAngle(Beak_r2, 0.2182F, 0.0F, 0.0F);
        Beak_r2.texOffs(0, 10).addBox(-2.5F, -5.25F, -8.75F, 5.0F, 3.0F, 7.0F, 0.0F, false);
    }




    //----------------------------------------ANIMATION----------------------------------------//

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.HeadPart.xRot = this.headXRot;
        this.HeadPart.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.RightFootPart.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.LeftFootPart.xRot  = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        if(entity.isFlying){
            this.WingRight.zRot = -2f;
            this.WingLeft.zRot  =  2f;
            this.WingRight.yRot = 0f;
            this.WingLeft.yRot  = 0f;
            this.WingRight.xRot =  1.5f;
            this.WingLeft.xRot  =  1.5f;
            this.WingLeft.z = -1;
            this.WingRight.z = -1;
            this.WingLeft.y = 0;
            this.WingRight.y = 0;
        } else {
            this.WingRight.zRot = 0f;
            this.WingLeft.zRot =  0f;
            this.WingRight.yRot  = MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
            this.WingLeft.yRot   = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.2F * limbSwingAmount;
            this.WingRight.xRot =  0f;
            this.WingLeft.xRot  =  0f;
            this.WingLeft.z = -7;
            this.WingRight.z = -7;
            this.WingLeft.y = 3;
            this.WingRight.y = 3;
        }
        Saddle.visible = entity.isSaddled();

    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.headXRot = entityIn.getHeadEatAngleScale(partialTick);
    }




    //----------------------------------------RENDER----------------------------------------//

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Saddle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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
        return ImmutableList.of(this.HeadPart);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.Body, this.LeftFootPart, this.RightFootPart, this.WingLeft, this.WingRight);
    }

}
