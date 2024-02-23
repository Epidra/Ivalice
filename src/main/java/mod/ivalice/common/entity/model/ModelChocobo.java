package mod.ivalice.common.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class ModelChocobo<T extends EntityChocobo> extends EntityModel<T> {
	
	private final ModelPart BoneBase;
	private final ModelPart BoneHead;
	private final ModelPart BoneNeck;
	private final ModelPart BoneLegRight;
	private final ModelPart BoneLegLeft;
	private final ModelPart BoneTalonRight;
	private final ModelPart BoneTalonRight1;
	private final ModelPart BoneTalonRight2;
	private final ModelPart BoneTalonRight3;
	private final ModelPart BoneTalonLeft;
	private final ModelPart BoneTalonLeft1;
	private final ModelPart BoneTalonLeft2;
	private final ModelPart BoneTalonLeft3;
	private final ModelPart BoneBeakLower;
	private final ModelPart BoneFootRight;
	private final ModelPart BoneFootLeft;
	private final ModelPart BoneWingRight1;
	private final ModelPart BoneWingRight3;
	private final ModelPart BoneWingRight5;
	private final ModelPart BoneWingRight7;
	private final ModelPart BoneWingLeft1;
	private final ModelPart BoneWingLeft3;
	private final ModelPart BoneWingLeft5;
	private final ModelPart BoneWingLeft7;
	private final ModelPart BoneSaddle;
	private final ModelPart BoneChest;
	private final ModelPart BoneBackFeather;
	private final ModelPart BoneBackFeatherRight;
	private final ModelPart BoneBackFeatherLeft;
	
	private final ModelPart BoneBaseChick;
	private final ModelPart BoneTalonLeftChick;
	private final ModelPart BoneTalonRightChick;
	
	private float talonXRot = 0.0436F * 6;
	private float beakXrot = 0.0436F * 4;
	private float headFeatherZPos = 0.5f;
	private float backFeatherZPos = 0.5f;
	private float ROT = 0.0436F;
	private boolean baby = false;
	private float heightBaseStand = 0;
	private float heightBaseSit   = 0;
	
	private boolean renderChild = false;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ModelChocobo(ModelPart root) {
		this.BoneBase             = root.getChild("BoneBase");
		this.BoneHead             = root.getChild("BoneBase").getChild("BoneNeck").getChild("BoneHead");
		this.BoneNeck             = root.getChild("BoneBase").getChild("BoneNeck");
		this.BoneLegRight         = root.getChild("BoneBase").getChild("BoneLegRight");
		this.BoneLegLeft          = root.getChild("BoneBase").getChild("BoneLegLeft");
		this.BoneFootRight        = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight");
		this.BoneFootLeft         = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft");
		this.BoneTalonRight       = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight");
		this.BoneTalonRight1      = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight1");
		this.BoneTalonRight2      = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight2");
		this.BoneTalonRight3      = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight3");
		this.BoneTalonLeft        = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft");
		this.BoneTalonLeft1       = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft1");
		this.BoneTalonLeft2       = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft2");
		this.BoneTalonLeft3       = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft3");
		this.BoneBeakLower        = root.getChild("BoneBase").getChild("BoneNeck").getChild("BoneHead").getChild("BoneBeakLower");
		this.BoneWingRight1       = root.getChild("BoneBase").getChild("BoneWingRight1");
		this.BoneWingRight3       = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3");
		this.BoneWingRight5       = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3").getChild("BoneWingRight5");
		this.BoneWingRight7       = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3").getChild("BoneWingRight5").getChild("BoneWingRight7");
		this.BoneWingLeft1        = root.getChild("BoneBase").getChild("BoneWingLeft1");
		this.BoneWingLeft3        = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3");
		this.BoneWingLeft5        = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3").getChild("BoneWingLeft5");
		this.BoneWingLeft7        = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3").getChild("BoneWingLeft5").getChild("BoneWingLeft7");
		this.BoneSaddle           = root.getChild("BoneBase").getChild("BoneSaddle");
		this.BoneChest            = root.getChild("BoneBase").getChild("BoneChest");
		this.BoneBackFeather      = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeather");
		this.BoneBackFeatherRight = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeatherRight");
		this.BoneBackFeatherLeft  = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeatherLeft");
		
		heightBaseStand = this.BoneBase.y;
		heightBaseSit   = this.BoneBase.y + 12;
		
		this.BoneBaseChick       = root.getChild("BoneBaseChick");
		this.BoneTalonLeftChick  = root.getChild("BoneBaseChick").getChild("BoneTalonLeftChick");
		this.BoneTalonRightChick = root.getChild("BoneBaseChick").getChild("BoneTalonRightChick");
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition BoneBase             = partdefinition.addOrReplaceChild("BoneBase", CubeListBuilder.create().texOffs(3, 41).addBox(-5.0F, -10.0F, -8.0F, 10.0F, 10.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(39, 26).addBox(-4.5F, -9.5F, 4.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));
		PartDefinition BoneChest            = BoneBase.addOrReplaceChild("BoneChest", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, 2.9F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -2.0F));
		PartDefinition BoneFront            = BoneBase.addOrReplaceChild("BoneFront", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -4.25F, -3.5F, 9.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.0F, -8.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition BoneNeck             = BoneBase.addOrReplaceChild("BoneNeck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -10.5F));
		PartDefinition BoneHead             = BoneNeck.addOrReplaceChild("BoneHead", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(19, 10).addBox(-2.5F, -5.75F, 3.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(30, 18).addBox(2.5F, -5.75F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(31, 32).addBox(2.5F, -3.75F, 3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))				.texOffs(23, 26).addBox(-2.5F, -5.75F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))				.texOffs(24, 11).addBox(-2.5F, -3.75F, 3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));
		PartDefinition BoneBeakUpper        = BoneHead.addOrReplaceChild("BoneBeakUpper", CubeListBuilder.create().texOffs(42, 11).addBox(-2.0F, -0.5F, -7.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -3.0F, 0.0873F, 0.0F, 0.0F));
		PartDefinition BoneBeakLower        = BoneHead.addOrReplaceChild("BoneBeakLower", CubeListBuilder.create().texOffs(30, 10).addBox(-2.0F, -0.5F, -6.5F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -3.0F, 0.0873F, 0.0F, 0.0F));
		PartDefinition BoneLegLeft          = BoneBase.addOrReplaceChild("BoneLegLeft", CubeListBuilder.create().texOffs(52, 41).addBox(-1.5F, -2.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, -0.5F));
		PartDefinition BoneFootLeft         = BoneLegLeft.addOrReplaceChild("BoneFootLeft", CubeListBuilder.create().texOffs(36, 41).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition BoneTalonLeft        = BoneFootLeft.addOrReplaceChild("BoneTalonLeft", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));
		PartDefinition BoneTalonLeft1       = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft1", CubeListBuilder.create().texOffs(44, 47).addBox(0.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, -0.2182F, 0.0F));
		PartDefinition BoneTalonLeft2       = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft2", CubeListBuilder.create().texOffs(44, 47).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.2182F, 0.0F));
		PartDefinition BoneTalonLeft3       = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft3", CubeListBuilder.create().texOffs(45, 47).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1345F, 0.0F, 0.0F));
		PartDefinition BoneLegRight         = BoneBase.addOrReplaceChild("BoneLegRight", CubeListBuilder.create().texOffs(52, 53).addBox(-1.5F, -2.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, -0.5F));
		PartDefinition BoneFootRight        = BoneLegRight.addOrReplaceChild("BoneFootRight", CubeListBuilder.create().texOffs(36, 41).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition BoneTalonRight       = BoneFootRight.addOrReplaceChild("BoneTalonRight", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));
		PartDefinition BoneTalonRight1      = BoneTalonRight.addOrReplaceChild("BoneTalonRight1", CubeListBuilder.create().texOffs(44, 47).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.2182F, 0.0F));
		PartDefinition BoneTalonRight2      = BoneTalonRight.addOrReplaceChild("BoneTalonRight2", CubeListBuilder.create().texOffs(44, 47).addBox(0.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, -0.2182F, 0.0F));
		PartDefinition BoneTalonRight3      = BoneTalonRight.addOrReplaceChild("BoneTalonRight3", CubeListBuilder.create().texOffs(45, 47).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1345F, 0.0F, 0.0F));
		PartDefinition BoneBack             = BoneBase.addOrReplaceChild("BoneBack", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 5.0F));
		PartDefinition BoneBackFeather      = BoneBack.addOrReplaceChild("BoneBackFeather", CubeListBuilder.create().texOffs(19, 20).addBox(-2.0F, -0.75F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(19, 29).addBox(-2.0F, 0.25F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0908F, 0.0F, 0.0F));
		PartDefinition BoneBackFeatherRight = BoneBack.addOrReplaceChild("BoneBackFeatherRight", CubeListBuilder.create().texOffs(14, 30).addBox(-4.0F, -0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(12, 24).addBox(-3.5F, -0.25F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(12, 18).addBox(-4.0F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(14, 13).addBox(-4.0F, 0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, -0.1745F, 0.0F));
		PartDefinition BoneBackFeatherLeft  = BoneBack.addOrReplaceChild("BoneBackFeatherLeft", CubeListBuilder.create().texOffs(14, 10).addBox(1.0F, -0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(12, 22).addBox(0.0F, -0.25F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(12, 11).addBox(1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(14, 28).addBox(1.0F, 0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, 0.1745F, 0.0F));
		PartDefinition BoneSaddle           = BoneBase.addOrReplaceChild("BoneSaddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.5F, -1.5F, -4.0F, 11.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -2.0F));
		PartDefinition BoneWingLeft1        = BoneBase.addOrReplaceChild("BoneWingLeft1", CubeListBuilder.create().texOffs(44, 24).addBox(0.0F, -0.5F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -8.0F, -7.0F));
		PartDefinition BoneWingLeft2        = BoneWingLeft1.addOrReplaceChild("BoneWingLeft2", CubeListBuilder.create().texOffs(48, 20).addBox(4.85F, -20.6F, 1.75F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 20.0F, 5.0F));
		PartDefinition BoneWingLeft3        = BoneWingLeft1.addOrReplaceChild("BoneWingLeft3", CubeListBuilder.create().texOffs(34, 29).addBox(-0.1F, -0.1F, 0.1F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 0.0F));
		PartDefinition BoneWingLeft4        = BoneWingLeft3.addOrReplaceChild("BoneWingLeft4", CubeListBuilder.create().texOffs(36, 20).addBox(4.75F, -18.7F, 2.85F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 18.5F, 5.0F));
		PartDefinition BoneWingLeft5        = BoneWingLeft3.addOrReplaceChild("BoneWingLeft5", CubeListBuilder.create().texOffs(46, 25).addBox(-0.2F, 0.8F, 0.2F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition BoneWingLeft6        = BoneWingLeft5.addOrReplaceChild("BoneWingLeft6", CubeListBuilder.create().texOffs(48, 30).addBox(4.65F, -15.8F, 2.95F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.5F, 5.0F));
		PartDefinition BoneWingLeft7        = BoneWingLeft5.addOrReplaceChild("BoneWingLeft7", CubeListBuilder.create().texOffs(35, 27).addBox(-0.3F, 1.7F, 0.3F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition BoneWingLeft8        = BoneWingLeft7.addOrReplaceChild("BoneWingLeft8", CubeListBuilder.create().texOffs(41, 20).addBox(4.55F, -12.9F, 2.05F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 14.5F, 5.0F));
		PartDefinition BoneWingRight1       = BoneBase.addOrReplaceChild("BoneWingRight1", CubeListBuilder.create().texOffs(35, 25).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -8.0F, -7.0F));
		PartDefinition BoneWingRight2       = BoneWingRight1.addOrReplaceChild("BoneWingRight2", CubeListBuilder.create().texOffs(35, 20).addBox(-5.85F, -20.5F, 1.75F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 20.0F, 5.0F));
		PartDefinition BoneWingRight3       = BoneWingRight1.addOrReplaceChild("BoneWingRight3", CubeListBuilder.create().texOffs(46, 27).addBox(-0.9F, -0.6F, 0.1F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition BoneWingRight4       = BoneWingRight3.addOrReplaceChild("BoneWingRight4", CubeListBuilder.create().texOffs(34, 28).addBox(-5.75F, -18.6F, 2.85F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 18.0F, 5.0F));
		PartDefinition BoneWingRight5       = BoneWingRight3.addOrReplaceChild("BoneWingRight5", CubeListBuilder.create().texOffs(34, 22).addBox(-0.8F, 0.3F, 0.2F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition BoneWingRight6       = BoneWingRight5.addOrReplaceChild("BoneWingRight6", CubeListBuilder.create().texOffs(45, 20).addBox(-5.65F, -15.7F, 2.95F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 16.0F, 5.0F));
		PartDefinition BoneWingRight7       = BoneWingRight5.addOrReplaceChild("BoneWingRight7", CubeListBuilder.create().texOffs(39, 26).addBox(-0.7F, 1.2F, 0.3F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition BoneWingRight8       = BoneWingRight7.addOrReplaceChild("BoneWingRight8", CubeListBuilder.create().texOffs(34, 20).addBox(-5.55F, -12.8F, 2.05F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 14.0F, 5.0F));
		
		PartDefinition BoneBaseChick        = partdefinition.addOrReplaceChild("BoneBaseChick", CubeListBuilder.create().texOffs(44, 2).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition BoneHeadChick        = BoneBaseChick.addOrReplaceChild("BoneHeadChick", CubeListBuilder.create().texOffs(41, 0).addBox(-1.5F, -7.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(52, 0).addBox(-0.5F, -5.1F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition BoneWingLeftChick    = BoneBaseChick.addOrReplaceChild("BoneWingLeftChick", CubeListBuilder.create().texOffs(54, 3).addBox(1.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition BoneWingRightChick   = BoneBaseChick.addOrReplaceChild("BoneWingRightChick", CubeListBuilder.create().texOffs(46, 3).addBox(-2.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition BoneTalonLeftChick   = BoneBaseChick.addOrReplaceChild("BoneTalonLeftChick", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition BoneTalonLeft1Chick  = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft1Chick", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		PartDefinition BoneTalonLeft2Chick  = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft2Chick", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		PartDefinition BoneTalonLeft3Chick  = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft3Chick", CubeListBuilder.create().texOffs(56, 0).addBox(-0.6F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		PartDefinition BoneTalonRightChick  = BoneBaseChick.addOrReplaceChild("BoneTalonRightChick", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition BoneTalonRight1Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight1Chick", CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		PartDefinition BoneTalonRight2Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight2Chick", CubeListBuilder.create().texOffs(60, 0).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		PartDefinition BoneTalonRight3Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight3Chick", CubeListBuilder.create().texOffs(60, 0).addBox(-0.3F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ANIMATION  ---------- ---------- ---------- ---------- //
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.renderChild = entity.isBaby();
		ResetRotations();
		this.BoneHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
		float rotSIN = Mth.sin(ageInTicks * 0.6662F) * 0.04F;
		float rotCOS = Mth.cos(ageInTicks * 0.6662F) * 0.04F;
		SetLegRotation(entity);
		this.BoneBase.y = entity.animSit() ? heightBaseSit : heightBaseStand;
		if(!entity.animFly()){
			this.BoneLegRight.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.BoneLegLeft.xRot  += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.BoneTalonRight.xRot -= Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.BoneTalonLeft.xRot  -= Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			if(!entity.animRide())this.BoneBase.y -= Mth.cos(limbSwing * 0.7F + (float)Math.PI) * 1.5F * limbSwingAmount;
		}
		BoneBeakLower.xRot = entity.animKweh() ? 3 * beakXrot : beakXrot;
		if(entity.animFly() || entity.animRun()){
			if(!entity.animRun()){
				BoneWingLeft1.zRot = -32*ROT + rotSIN*20;
				BoneWingRight1.zRot = 32*ROT - rotSIN*20;
			} else {
				BoneWingLeft1.zRot = -32*ROT;
				BoneWingRight1.zRot = 32*ROT;
			}
			BoneWingLeft1.yRot = 0 + rotSIN;
			BoneWingLeft3.yRot = 0 - rotCOS;
			BoneWingLeft5.yRot = 0 - rotSIN;
			BoneWingLeft7.yRot = 0 + rotCOS;
			BoneWingRight1.yRot = 0 + rotSIN;
			BoneWingRight3.yRot = 0 - rotCOS;
			BoneWingRight5.yRot = 0 - rotSIN;
			BoneWingRight7.yRot = 0 + rotCOS;
			if( (entity.animFly() && entity.animRun()) || entity.animRun()){
				BoneNeck.xRot = 20*ROT;
				BoneHead.xRot = -18*ROT;
				BoneWingLeft1.xRot = -ROT;
				BoneWingLeft3.xRot = -ROT;
				BoneWingLeft5.xRot = -ROT;
				BoneWingLeft7.xRot = -ROT;
				BoneWingRight1.xRot = -ROT;
				BoneWingRight3.xRot = -ROT;
				BoneWingRight5.xRot = -ROT;
				BoneWingRight7.xRot = -ROT;
			}
			if(entity.animFly() && (entity.animRide() || entity.animRun()) ){
				BoneLegLeft.xRot = 26*ROT;
				BoneLegRight.xRot = 26*ROT;
				BoneFootLeft.xRot = 0;
				BoneFootRight.xRot = 0;
			}
		}
		BoneSaddle.visible = entity.isSaddled();
		BoneChest.visible = entity.isChested();
		if(entity.animPick()){
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
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if(renderChild){
			BoneBaseChick.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		} else {
			BoneBase.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	private void ResetRotations(){
		BoneWingLeft1.zRot = 0;
		BoneWingLeft1.yRot = 0;
		BoneWingLeft3.yRot = 0;
		BoneWingLeft5.yRot = 0;
		BoneWingLeft7.yRot = 0;
		BoneWingRight1.zRot = 0;
		BoneWingRight1.yRot = 0;
		BoneWingRight3.yRot = 0;
		BoneWingRight5.yRot = 0;
		BoneWingRight7.yRot = 0;
		BoneWingLeft3.xRot = 0;
		BoneWingLeft5.xRot = 0;
		BoneWingLeft7.xRot = 0;
		BoneWingRight3.xRot = 0;
		BoneWingRight5.xRot = 0;
		BoneWingRight7.xRot = 0;
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
		float rot = 2 * ROT;
		int mod = 0;
		if(entity.animSit()){
			mod = 16;
		} else {
			mod = 4;
		}
		BoneLegLeft.xRot = rot * mod;
		BoneFootLeft.xRot = - (rot * 2) * mod;
		BoneTalonLeft.xRot = rot * mod;
		BoneLegRight.xRot = rot * mod;
		BoneFootRight.xRot = - (rot * 2) * mod;
		BoneTalonRight.xRot = rot * mod;
	}
	
	
	
}