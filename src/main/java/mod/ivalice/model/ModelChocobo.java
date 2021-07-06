package mod.ivalice.model;

// Made with Blockbench 3.8.4
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

    private final ModelRenderer BoneBase;
    private final ModelRenderer BoneBody;
    private final ModelRenderer BoneFront;
    private final ModelRenderer BoneNeck;
    private final ModelRenderer BoneHead;
    private final ModelRenderer BoneBeak1;
    private final ModelRenderer BoneBeak2;
    private final ModelRenderer BoneHeadFeather;
    private final ModelRenderer BoneHeadFeather1;
    private final ModelRenderer BoneHeadFeather2;
    private final ModelRenderer BoneHeadFeather3;
    private final ModelRenderer BoneHeadFeather4;
    private final ModelRenderer BoneHeadFeather5;
    private final ModelRenderer BoneHeadFeather6;
    private final ModelRenderer BoneHeadFeather7;
    private final ModelRenderer BoneHeadFeather8;
    private final ModelRenderer BoneHeadFeather9;
    private final ModelRenderer BoneHeadFeather10;
    private final ModelRenderer BoneHeadFeather11;
    private final ModelRenderer BoneBack;
    private final ModelRenderer BoneBackFeather1;
    private final ModelRenderer BoneBackFeather2;
    private final ModelRenderer BoneBackFeather3;
    private final ModelRenderer BoneBackFeather4;
    private final ModelRenderer BoneBackFeather5;
    private final ModelRenderer BoneBackFeather6;
    private final ModelRenderer BoneBackFeather7;
    private final ModelRenderer BoneBackFeather8;
    private final ModelRenderer BoneBackFeather9;
    private final ModelRenderer BoneBackFeather10;
    private final ModelRenderer BoneBackFeather11;
    private final ModelRenderer BoneWingLeft1;
    private final ModelRenderer BoneWingLeft2;
    private final ModelRenderer BoneWingLeft3;
    private final ModelRenderer BoneWingLeft4;
    private final ModelRenderer BoneWingRight1;
    private final ModelRenderer BoneWingRight2;
    private final ModelRenderer BoneWingRight3;
    private final ModelRenderer BoneWingRight4;
    private final ModelRenderer BoneLegRight;
    private final ModelRenderer BoneFootRight;
    private final ModelRenderer BoneTalonRight;
    private final ModelRenderer BoneFalonRight1;
    private final ModelRenderer BoneFalonRight2;
    private final ModelRenderer BoneFalonRight3;
    private final ModelRenderer BoneLegLeft;
    private final ModelRenderer BoneFootLeft;
    private final ModelRenderer BoneTalonLeft;
    private final ModelRenderer BoneFalonLeft1;
    private final ModelRenderer BoneFalonLeft2;
    private final ModelRenderer BoneFalonLeft3;
    private final ModelRenderer BoneSaddle;

    // --- Chicken ---

    private final ModelRenderer ChickBody;
    private final ModelRenderer ChickFront_r1;
    private final ModelRenderer ChickLeftFootPart;
    private final ModelRenderer ChickTalonLeftBack_r1;
    private final ModelRenderer ChickTalonLeftLeft_r1;
    private final ModelRenderer ChickTalonLeftRight_r1;
    private final ModelRenderer ChickRightFootPart;
    private final ModelRenderer ChickTalonRightBack_r1;
    private final ModelRenderer ChickTalonRightLeft_r1;
    private final ModelRenderer ChickTalonRightRight_r1;
    private final ModelRenderer ChickWingLeft;
    private final ModelRenderer ChickWingRight;
    private final ModelRenderer ChickUpperPart;
    private final ModelRenderer ChickHeadPart;

    // --- Variable ---

    private float headXRot;
    private float talonXRot = 0.0436F * 6;
    private float beakXrot = 0.0436F * 4;
    private float headFeatherZPos = 0.5f;
    private float backFeatherZPos = 0.5f;
    private float ROT = 0.0436F;
    private boolean baby = false;

    public ModelChocobo() {

        texWidth = 64;
        texHeight = 64;

        BoneBase = new ModelRenderer(this);
        BoneBase.setPos(0.0F, 6.5F, 1.0F);


        BoneBody = new ModelRenderer(this);
        BoneBody.setPos(0.0F, 3.0F, 0.0F);
        BoneBase.addChild(BoneBody);
        setRotationAngle(BoneBody, -0.0436F, 0.0F, 0.0F);
        BoneBody.texOffs(12, 37).addBox(-5.0F, -9.25F, -7.0F, 10.0F, 11.0F, 16.0F, 0.0F, false);

        BoneFront = new ModelRenderer(this);
        BoneFront.setPos(0.0F, -5.5F, -7.0F);
        BoneBase.addChild(BoneFront);
        setRotationAngle(BoneFront, 0.5236F, 0.0F, 0.0F);
        BoneFront.texOffs(0, 49).addBox(-4.5F, -0.5F, -5.0F, 9.0F, 9.0F, 6.0F, 0.0F, false);

        BoneNeck = new ModelRenderer(this);
        BoneNeck.setPos(0.0F, -3.5F, -7.5F);
        BoneBase.addChild(BoneNeck);
        BoneNeck.texOffs(48, 12).addBox(-2.0F, -14.0F, -3.5F, 4.0F, 14.0F, 4.0F, 0.0F, false);

        BoneHead = new ModelRenderer(this);
        BoneHead.setPos(0.0F, -13.0F, -1.5F);
        BoneNeck.addChild(BoneHead);
        BoneHead.texOffs(40, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        BoneBeak1 = new ModelRenderer(this);
        BoneBeak1.setPos(0.0F, 0.0F, -2.5F);
        BoneHead.addChild(BoneBeak1);
        setRotationAngle(BoneBeak1, 0.1745F, 0.0F, 0.0F);
        BoneBeak1.texOffs(16, 2).addBox(-2.5F, -2.25F, -6.5F, 5.0F, 2.0F, 7.0F, 0.0F, false);

        BoneBeak2 = new ModelRenderer(this);
        BoneBeak2.setPos(0.0F, 0.0F, -2.5F);
        BoneHead.addChild(BoneBeak2);
        setRotationAngle(BoneBeak2, 0.1745F, 0.0F, 0.0F);
        BoneBeak2.texOffs(17, 3).addBox(-2.0F, -0.25F, -6.5F, 4.0F, 2.0F, 7.0F, 0.0F, false);

        BoneHeadFeather = new ModelRenderer(this);
        BoneHeadFeather.setPos(0.0F, 0.0F, 2.5F);
        BoneHead.addChild(BoneHeadFeather);


        BoneHeadFeather1 = new ModelRenderer(this);
        BoneHeadFeather1.setPos(2.0F, -3.5F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather1);
        BoneHeadFeather1.texOffs(23, 33).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F, 0.0F, false);

        BoneHeadFeather2 = new ModelRenderer(this);
        BoneHeadFeather2.setPos(1.0F, -3.5F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather2);
        BoneHeadFeather2.texOffs(52, 37).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F, 0.0F, false);

        BoneHeadFeather3 = new ModelRenderer(this);
        BoneHeadFeather3.setPos(0.0F, -3.5F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather3);
        BoneHeadFeather3.texOffs(0, 39).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F, 0.0F, false);

        BoneHeadFeather4 = new ModelRenderer(this);
        BoneHeadFeather4.setPos(-1.0F, -3.5F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather4);
        BoneHeadFeather4.texOffs(52, 52).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F, 0.0F, false);

        BoneHeadFeather5 = new ModelRenderer(this);
        BoneHeadFeather5.setPos(-2.0F, -3.5F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather5);
        BoneHeadFeather5.texOffs(54, 60).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F, 0.0F, false);

        BoneHeadFeather6 = new ModelRenderer(this);
        BoneHeadFeather6.setPos(2.5F, -3.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather6);
        BoneHeadFeather6.texOffs(32, 29).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);

        BoneHeadFeather7 = new ModelRenderer(this);
        BoneHeadFeather7.setPos(2.5F, -2.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather7);
        BoneHeadFeather7.texOffs(49, 46).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);

        BoneHeadFeather8 = new ModelRenderer(this);
        BoneHeadFeather8.setPos(2.5F, -1.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather8);
        BoneHeadFeather8.texOffs(4, 45).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        BoneHeadFeather9 = new ModelRenderer(this);
        BoneHeadFeather9.setPos(-2.5F, -3.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather9);
        BoneHeadFeather9.texOffs(56, 29).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);

        BoneHeadFeather10 = new ModelRenderer(this);
        BoneHeadFeather10.setPos(-2.5F, -2.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather10);
        BoneHeadFeather10.texOffs(22, 41).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, false);

        BoneHeadFeather11 = new ModelRenderer(this);
        BoneHeadFeather11.setPos(-2.5F, -1.0F, 0.5F);
        BoneHeadFeather.addChild(BoneHeadFeather11);
        BoneHeadFeather11.texOffs(27, 48).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, false);

        BoneBack = new ModelRenderer(this);
        BoneBack.setPos(0.0F, -4.75F, 9.5F);
        BoneBase.addChild(BoneBack);
        BoneBack.texOffs(2, 31).addBox(-4.5F, -1.0F, -0.25F, 9.0F, 2.0F, 1.0F, 0.0F, false);

        BoneBackFeather1 = new ModelRenderer(this);
        BoneBackFeather1.setPos(3.0F, -0.75F, 0.5F);
        BoneBack.addChild(BoneBackFeather1);
        setRotationAngle(BoneBackFeather1, 0.8727F, 0.1745F, 0.0F);
        BoneBackFeather1.texOffs(9, 35).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F, 0.0F, false);

        BoneBackFeather2 = new ModelRenderer(this);
        BoneBackFeather2.setPos(1.0F, -0.75F, 0.5F);
        BoneBack.addChild(BoneBackFeather2);
        setRotationAngle(BoneBackFeather2, 0.8727F, 0.0436F, 0.0F);
        BoneBackFeather2.texOffs(19, 38).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 9.0F, 0.0F, false);

        BoneBackFeather3 = new ModelRenderer(this);
        BoneBackFeather3.setPos(-1.0F, -0.75F, 0.5F);
        BoneBack.addChild(BoneBackFeather3);
        setRotationAngle(BoneBackFeather3, 0.9163F, -0.0436F, 0.0F);
        BoneBackFeather3.texOffs(34, 33).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 9.0F, 0.0F, false);

        BoneBackFeather4 = new ModelRenderer(this);
        BoneBackFeather4.setPos(-3.0F, -0.75F, 0.5F);
        BoneBack.addChild(BoneBackFeather4);
        setRotationAngle(BoneBackFeather4, 0.8727F, -0.1309F, 0.0F);
        BoneBackFeather4.texOffs(44, 47).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F, 0.0F, false);

        BoneBackFeather5 = new ModelRenderer(this);
        BoneBackFeather5.setPos(3.0F, 0.5F, 0.5F);
        BoneBack.addChild(BoneBackFeather5);
        setRotationAngle(BoneBackFeather5, 0.6981F, 0.0436F, -0.0436F);
        BoneBackFeather5.texOffs(0, 33).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 11.0F, 0.0F, false);

        BoneBackFeather6 = new ModelRenderer(this);
        BoneBackFeather6.setPos(1.0F, 0.5F, 0.5F);
        BoneBack.addChild(BoneBackFeather6);
        setRotationAngle(BoneBackFeather6, 0.6545F, 0.0F, 0.0F);
        BoneBackFeather6.texOffs(25, 44).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 10.0F, 0.0F, false);

        BoneBackFeather7 = new ModelRenderer(this);
        BoneBackFeather7.setPos(-1.0F, 0.5F, 0.5F);
        BoneBack.addChild(BoneBackFeather7);
        setRotationAngle(BoneBackFeather7, 0.6109F, 0.0F, 0.0F);
        BoneBackFeather7.texOffs(20, 51).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 10.0F, 0.0F, false);

        BoneBackFeather8 = new ModelRenderer(this);
        BoneBackFeather8.setPos(-3.0F, 0.5F, 0.5F);
        BoneBack.addChild(BoneBackFeather8);
        setRotationAngle(BoneBackFeather8, 0.6545F, 0.0F, -0.0436F);
        BoneBackFeather8.texOffs(6, 39).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 11.0F, 0.0F, false);

        BoneBackFeather9 = new ModelRenderer(this);
        BoneBackFeather9.setPos(2.0F, -0.25F, 0.5F);
        BoneBack.addChild(BoneBackFeather9);
        setRotationAngle(BoneBackFeather9, 0.6109F, 0.0F, -0.0873F);
        BoneBackFeather9.texOffs(34, 44).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F, 0.0F, false);

        BoneBackFeather10 = new ModelRenderer(this);
        BoneBackFeather10.setPos(0.0F, -0.25F, 0.5F);
        BoneBack.addChild(BoneBackFeather10);
        setRotationAngle(BoneBackFeather10, 0.5672F, 0.0F, 0.0F);
        BoneBackFeather10.texOffs(0, 29).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 7.0F, 0.0F, false);

        BoneBackFeather11 = new ModelRenderer(this);
        BoneBackFeather11.setPos(-2.0F, -0.25F, 0.5F);
        BoneBack.addChild(BoneBackFeather11);
        setRotationAngle(BoneBackFeather11, 0.6109F, 0.0F, 0.0873F);
        BoneBackFeather11.texOffs(44, 56).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F, 0.0F, false);

        BoneWingLeft1 = new ModelRenderer(this);
        BoneWingLeft1.setPos(5.0F, -5.0F, -5.7F);
        BoneBase.addChild(BoneWingLeft1);
        BoneWingLeft1.texOffs(28, 30).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
        BoneWingLeft1.texOffs(14, 39).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);

        BoneWingLeft2 = new ModelRenderer(this);
        BoneWingLeft2.setPos(0.0F, 2.0F, -0.1F);
        BoneWingLeft1.addChild(BoneWingLeft2);
        BoneWingLeft2.texOffs(48, 35).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);
        BoneWingLeft2.texOffs(40, 48).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);

        BoneWingLeft3 = new ModelRenderer(this);
        BoneWingLeft3.setPos(0.0F, 2.0F, 0.1F);
        BoneWingLeft2.addChild(BoneWingLeft3);
        BoneWingLeft3.texOffs(15, 30).addBox(0.0F, 1.0F, 0.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);
        BoneWingLeft3.texOffs(18, 53).addBox(-0.1F, 1.0F, 7.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);

        BoneWingLeft4 = new ModelRenderer(this);
        BoneWingLeft4.setPos(0.0F, 4.0F, 0.2F);
        BoneWingLeft3.addChild(BoneWingLeft4);
        BoneWingLeft4.texOffs(32, 55).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
        BoneWingLeft4.texOffs(1, 36).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);

        BoneWingRight1 = new ModelRenderer(this);
        BoneWingRight1.setPos(-5.0F, -5.0F, -5.7F);
        BoneBase.addChild(BoneWingRight1);
        BoneWingRight1.texOffs(30, 40).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
        BoneWingRight1.texOffs(18, 31).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);

        BoneWingRight2 = new ModelRenderer(this);
        BoneWingRight2.setPos(0.0F, 2.0F, -0.1F);
        BoneWingRight1.addChild(BoneWingRight2);
        BoneWingRight2.texOffs(4, 42).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);
        BoneWingRight2.texOffs(40, 40).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);

        BoneWingRight3 = new ModelRenderer(this);
        BoneWingRight3.setPos(0.0F, 3.0F, 0.1F);
        BoneWingRight2.addChild(BoneWingRight3);
        BoneWingRight3.texOffs(17, 46).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);
        BoneWingRight3.texOffs(33, 50).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F, 0.0F, false);

        BoneWingRight4 = new ModelRenderer(this);
        BoneWingRight4.setPos(0.0F, 3.0F, 0.2F);
        BoneWingRight3.addChild(BoneWingRight4);
        BoneWingRight4.texOffs(8, 36).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);
        BoneWingRight4.texOffs(33, 29).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F, 0.0F, false);

        BoneLegRight = new ModelRenderer(this);
        BoneLegRight.setPos(-3.0F, 4.0F, 0.5F);
        BoneBase.addChild(BoneLegRight);
        BoneLegRight.texOffs(52, 43).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, false);

        BoneFootRight = new ModelRenderer(this);
        BoneFootRight.setPos(0.0F, 6.25F, 0.0F);
        BoneLegRight.addChild(BoneFootRight);
        BoneFootRight.texOffs(0, 0).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        BoneTalonRight = new ModelRenderer(this);
        BoneTalonRight.setPos(0.0F, 6.25F, 0.0F);
        BoneFootRight.addChild(BoneTalonRight);


        BoneFalonRight1 = new ModelRenderer(this);
        BoneFalonRight1.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonRight.addChild(BoneFalonRight1);
        setRotationAngle(BoneFalonRight1, 0.2618F, 0.1309F, 0.0F);
        BoneFalonRight1.texOffs(2, 3).addBox(-1.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        BoneFalonRight2 = new ModelRenderer(this);
        BoneFalonRight2.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonRight.addChild(BoneFalonRight2);
        setRotationAngle(BoneFalonRight2, 0.2618F, -0.1309F, 0.0F);
        BoneFalonRight2.texOffs(2, 3).addBox(0.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        BoneFalonRight3 = new ModelRenderer(this);
        BoneFalonRight3.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonRight.addChild(BoneFalonRight3);
        setRotationAngle(BoneFalonRight3, -0.2618F, 0.0F, 0.0F);
        BoneFalonRight3.texOffs(10, 3).addBox(-0.5F, -0.25F, 0.25F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        BoneLegLeft = new ModelRenderer(this);
        BoneLegLeft.setPos(3.0F, 4.0F, 0.5F);
        BoneBase.addChild(BoneLegLeft);
        BoneLegLeft.texOffs(0, 28).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, false);

        BoneFootLeft = new ModelRenderer(this);
        BoneFootLeft.setPos(0.0F, 6.25F, 0.0F);
        BoneLegLeft.addChild(BoneFootLeft);
        BoneFootLeft.texOffs(0, 0).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        BoneTalonLeft = new ModelRenderer(this);
        BoneTalonLeft.setPos(0.0F, 6.25F, 0.0F);
        BoneFootLeft.addChild(BoneTalonLeft);


        BoneFalonLeft1 = new ModelRenderer(this);
        BoneFalonLeft1.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonLeft.addChild(BoneFalonLeft1);
        setRotationAngle(BoneFalonLeft1, 0.2618F, -0.1309F, 0.0F);
        BoneFalonLeft1.texOffs(2, 3).addBox(0.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        BoneFalonLeft2 = new ModelRenderer(this);
        BoneFalonLeft2.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonLeft.addChild(BoneFalonLeft2);
        setRotationAngle(BoneFalonLeft2, 0.2618F, 0.1309F, 0.0F);
        BoneFalonLeft2.texOffs(2, 3).addBox(-1.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        BoneFalonLeft3 = new ModelRenderer(this);
        BoneFalonLeft3.setPos(0.0F, 0.0F, 0.0F);
        BoneTalonLeft.addChild(BoneFalonLeft3);
        setRotationAngle(BoneFalonLeft3, -0.2618F, 0.0F, 0.0F);
        BoneFalonLeft3.texOffs(10, 3).addBox(-0.5F, -0.25F, 0.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        BoneSaddle = new ModelRenderer(this);
        BoneSaddle.setPos(0.0F, 3.0F, 0.0F);
        BoneBase.addChild(BoneSaddle);
        BoneSaddle.texOffs(0, 19).addBox(-4.5F, -9.75F, -3.0F, 9.0F, 1.0F, 8.0F, 0.0F, false);


        // --- Chicken ---

        ChickBody = new ModelRenderer(this);
        ChickBody.setPos(0.0F, 18.75F, 0.0F);
        ChickBody.texOffs(0, 0).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 6.0F, 0.0F, false);

        ChickFront_r1 = new ModelRenderer(this);
        ChickFront_r1.setPos(0.0F, -0.3415F, -2.9085F);
        ChickBody.addChild(ChickFront_r1);
        setRotationAngle(ChickFront_r1, 0.5236F, 0.0F, 0.0F);
        ChickFront_r1.texOffs(17, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, false);

        ChickLeftFootPart = new ModelRenderer(this);
        ChickLeftFootPart.setPos(1.5F, 19.75F, 0.0F);
        ChickLeftFootPart.texOffs(0, 0).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        ChickTalonLeftBack_r1 = new ModelRenderer(this);
        ChickTalonLeftBack_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickLeftFootPart.addChild(ChickTalonLeftBack_r1);
        setRotationAngle(ChickTalonLeftBack_r1, -0.2618F, 0.0F, 0.0F);
        ChickTalonLeftBack_r1.texOffs(20, 12).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickTalonLeftLeft_r1 = new ModelRenderer(this);
        ChickTalonLeftLeft_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickLeftFootPart.addChild(ChickTalonLeftLeft_r1);
        setRotationAngle(ChickTalonLeftLeft_r1, 0.2618F, -0.3491F, 0.0F);
        ChickTalonLeftLeft_r1.texOffs(21, 20).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickTalonLeftRight_r1 = new ModelRenderer(this);
        ChickTalonLeftRight_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickLeftFootPart.addChild(ChickTalonLeftRight_r1);
        setRotationAngle(ChickTalonLeftRight_r1, 0.2618F, 0.3491F, 0.0F);
        ChickTalonLeftRight_r1.texOffs(14, 20).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickRightFootPart = new ModelRenderer(this);
        ChickRightFootPart.setPos(-1.5F, 19.75F, 0.0F);
        ChickRightFootPart.texOffs(24, 16).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        ChickTalonRightBack_r1 = new ModelRenderer(this);
        ChickTalonRightBack_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickRightFootPart.addChild(ChickTalonRightBack_r1);
        setRotationAngle(ChickTalonRightBack_r1, -0.2618F, 0.0F, 0.0F);
        ChickTalonRightBack_r1.texOffs(23, 3).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickTalonRightLeft_r1 = new ModelRenderer(this);
        ChickTalonRightLeft_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickRightFootPart.addChild(ChickTalonRightLeft_r1);
        setRotationAngle(ChickTalonRightLeft_r1, 0.2618F, -0.3491F, 0.0F);
        ChickTalonRightLeft_r1.texOffs(11, 24).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickTalonRightRight_r1 = new ModelRenderer(this);
        ChickTalonRightRight_r1.setPos(0.0F, 1.25F, 0.0F);
        ChickRightFootPart.addChild(ChickTalonRightRight_r1);
        setRotationAngle(ChickTalonRightRight_r1, 0.2618F, 0.3491F, 0.0F);
        ChickTalonRightRight_r1.texOffs(23, 7).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        ChickWingLeft = new ModelRenderer(this);
        ChickWingLeft.setPos(2.5F, 18.5F, -2.0F);
        ChickWingLeft.texOffs(0, 19).addBox(-0.25F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);

        ChickWingRight = new ModelRenderer(this);
        ChickWingRight.setPos(2.5F, 18.5F, -2.0F);
        ChickWingRight.texOffs(13, 12).addBox(-5.75F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F, 0.0F, false);

        ChickUpperPart = new ModelRenderer(this);
        ChickUpperPart.setPos(0.0F, 17.5F, -2.5F);


        ChickHeadPart = new ModelRenderer(this);
        ChickHeadPart.setPos(0.0F, 0.0F, -0.5F);
        ChickUpperPart.addChild(ChickHeadPart);
        ChickHeadPart.texOffs(0, 12).addBox(-1.5F, -2.5F, -2.25F, 3.0F, 3.0F, 3.0F, 0.0F, false);
        ChickHeadPart.texOffs(7, 19).addBox(-1.0F, -1.75F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
    }

    //----------------------------------------ANIMATION----------------------------------------//

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        ResetRotations();
        this.BoneHead.xRot = this.headXRot;
        this.BoneHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
        float rotSIN = MathHelper.sin(ageInTicks * 0.6662F) * 0.04F;
        float rotCOS = MathHelper.cos(ageInTicks * 0.6662F) * 0.04F;
        SetLegRotation(entity);
        if(!entity.AnimFly()){
            this.BoneLegRight.xRot += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.BoneLegLeft.xRot  += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.BoneTalonRight.xRot -= MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.BoneTalonLeft.xRot  -= MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.ChickLeftFootPart.xRot += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.05F * limbSwingAmount;
            this.ChickRightFootPart.xRot  += MathHelper.cos(limbSwing * 0.6662F) * 0.05F * limbSwingAmount;
        }
        BoneBeak2.xRot = entity.AnimKweh() ? 3 * beakXrot : beakXrot;

        BoneFalonLeft1.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
        BoneFalonLeft2.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
        BoneFalonLeft3.xRot  = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;
        BoneFalonRight1.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
        BoneFalonRight2.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
        BoneFalonRight3.xRot = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;

        if(entity.AnimFly() || entity.AnimRun()){

            if(!entity.AnimRun()){
                BoneWingLeft1.zRot = -32*ROT + rotSIN*20;
                BoneWingRight1.zRot = 32*ROT - rotSIN*20;
            } else {
                BoneWingLeft1.zRot = -32*ROT;
                BoneWingRight1.zRot = 32*ROT;
            }
            BoneWingLeft1.yRot = 0 + rotSIN;
            BoneWingLeft2.yRot = 0 - rotCOS;
            BoneWingLeft3.yRot = 0 - rotSIN;
            BoneWingLeft4.yRot = 0 + rotCOS;
            BoneWingRight1.yRot = 0 + rotSIN;
            BoneWingRight2.yRot = 0 - rotCOS;
            BoneWingRight3.yRot = 0 - rotSIN;
            BoneWingRight4.yRot = 0 + rotCOS;

            if( (entity.AnimFly() && entity.AnimRun()) || entity.AnimRun()){
                BoneNeck.xRot = 20*ROT;
                BoneHead.xRot = -18*ROT;
                BoneHeadFeather1.xRot  = + rotSIN;
                BoneHeadFeather2.xRot  = - rotCOS;
                BoneHeadFeather3.xRot  = - rotSIN;
                BoneHeadFeather4.xRot  = + rotCOS;
                BoneHeadFeather5.xRot  = + rotSIN;
                BoneHeadFeather6.zRot  = - rotCOS;
                BoneHeadFeather7.zRot  = - rotSIN;
                BoneHeadFeather8.zRot  = + rotCOS;
                BoneHeadFeather9.zRot  = + rotSIN;
                BoneHeadFeather10.zRot = - rotCOS;
                BoneHeadFeather11.zRot = - rotSIN;
                BoneWingLeft1.xRot = -ROT;
                BoneWingLeft2.xRot = -ROT;
                BoneWingLeft3.xRot = -ROT;
                BoneWingLeft4.xRot = -ROT;
                BoneWingRight1.xRot = -ROT;
                BoneWingRight2.xRot = -ROT;
                BoneWingRight3.xRot = -ROT;
                BoneWingRight4.xRot = -ROT;
            }

            if(entity.AnimFly() && (entity.AnimRide() || entity.AnimRun()) ){

                BoneLegLeft.xRot = 26*ROT;
                BoneLegRight.xRot = 26*ROT;
                BoneFootLeft.xRot = 0;
                BoneFootRight.xRot = 0;


            }
        }

        BoneSaddle.visible = entity.AnimSaddle();

        BoneBackFeather1.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather2.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather3.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather4.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather5.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather6.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather7.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather8.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather9.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather10.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneBackFeather11.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
        BoneHeadFeather1.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather2.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather3.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather4.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather5.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather6.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather7.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather8.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather9.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather10.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
        BoneHeadFeather11.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );

        if(entity.AnimPick()){
            BoneBase.y = 7;
            BoneBase.xRot =  ROT * 10;
            BoneNeck.xRot =  34*ROT - rotSIN*2;
            BoneHead.xRot =   -14*ROT + rotSIN*2;
            BoneLegLeft.xRot = -8*ROT;
            BoneFootLeft.xRot = -16*ROT;
            BoneTalonLeft.xRot = 12.5f * ROT;
            BoneWingLeft1.xRot = 10*ROT;
            BoneLegRight.xRot = -8*ROT;
            BoneFootRight.xRot = -16*ROT;
            BoneTalonRight.xRot = 12.5f*ROT;
            BoneWingRight1.xRot = 10*ROT;
        }

    }

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        //this.headXRot = entity.getHeadEatAngleScale(partialTick);
        baby = entity.AnimYoung();
    }




    //----------------------------------------RENDER----------------------------------------//

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        if(baby){
            ChickBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickFront_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ChickLeftFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonLeftBack_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonLeftLeft_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonLeftRight_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ChickRightFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonRightBack_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonRightLeft_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickTalonRightRight_r1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ChickWingLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ChickWingRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            ChickUpperPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            //ChickHeadPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        } else {
            BoneBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }




    //----------------------------------------SUPPORT----------------------------------------//

    private void ResetRotations(){
        BoneLegLeft.xRot = 0;
        BoneLegRight.xRot = 0;
        BoneFootLeft.xRot = 0;
        BoneFootRight.xRot = 0;
        BoneNeck.xRot = 0;
        BoneHead.xRot = 0;
        BoneWingLeft1.zRot = 0;
        BoneWingLeft1.yRot = 0;
        BoneWingLeft2.yRot = 0;
        BoneWingLeft3.yRot = 0;
        BoneWingLeft4.yRot = 0;
        BoneWingRight1.zRot = 0;
        BoneWingRight1.yRot = 0;
        BoneWingRight2.yRot = 0;
        BoneWingRight3.yRot = 0;
        BoneWingRight4.yRot = 0;
        BoneHeadFeather1.xRot  = 0;
        BoneHeadFeather2.xRot  = 0;
        BoneHeadFeather3.xRot  = 0;
        BoneHeadFeather4.xRot  = 0;
        BoneHeadFeather5.xRot  = 0;
        BoneHeadFeather6.zRot  = 0;
        BoneHeadFeather7.zRot  = 0;
        BoneHeadFeather8.zRot  = 0;
        BoneHeadFeather9.zRot  = 0;
        BoneHeadFeather10.zRot = 0;
        BoneHeadFeather11.zRot = 0;
        BoneWingLeft1.xRot = 0;
        BoneWingLeft2.xRot = 0;
        BoneWingLeft3.xRot = 0;
        BoneWingLeft4.xRot = 0;
        BoneWingRight1.xRot = 0;
        BoneWingRight2.xRot = 0;
        BoneWingRight3.xRot = 0;
        BoneWingRight4.xRot = 0;
        BoneBase.xRot =  0;
        BoneNeck.xRot =  0;
        BoneHead.xRot =   0;
        BoneLegLeft.xRot = 0;
        BoneFootLeft.xRot = 0;
        BoneTalonLeft.xRot = 0;
        BoneWingLeft1.xRot = 0;
        BoneLegRight.xRot = 0;
        BoneFootRight.xRot = 0;
        BoneTalonRight.xRot = 0;
        BoneWingRight1.xRot = 0;
    }

    private void SetLegRotation(T entity){
        float height = 4;
        float rot = 2 * ROT;
        int mod = 0;

        if(entity.AnimSit()){
            mod = 16;
            //} else if(entity.AnimSaddle()){
            //    mod = 8;
            //} else if(entity.AnimRide()){
            //    mod = 10;
        } else {
            mod = 4;
        }

        float baseHeight = 0.75f;
        BoneLegLeft.xRot = rot * mod;
        BoneFootLeft.xRot = - (rot * 2) * mod;
        BoneTalonLeft.xRot = rot * mod;
        BoneLegRight.xRot = rot * mod;
        BoneFootRight.xRot = - (rot * 2) * mod;
        BoneTalonRight.xRot = rot * mod;
        BoneBase.y = height + (baseHeight * mod);


    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.BoneNeck);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.BoneBody);
    }
}