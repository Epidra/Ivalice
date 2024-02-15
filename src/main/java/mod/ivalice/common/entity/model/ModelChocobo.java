package mod.ivalice.common.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.common.entity.EntityChocobo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static mod.ivalice.Ivalice.MODID;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class ModelChocobo<T extends EntityChocobo> extends EntityModel<T> {
	
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "chocobo"), "main");
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
		this.BoneBase = root.getChild("BoneBase");
		this.BoneHead = root.getChild("BoneBase").getChild("BoneNeck").getChild("BoneHead");
		this.BoneNeck = root.getChild("BoneBase").getChild("BoneNeck");
		this.BoneLegRight = root.getChild("BoneBase").getChild("BoneLegRight");
		this.BoneLegLeft = root.getChild("BoneBase").getChild("BoneLegLeft");
		this.BoneFootRight = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight");
		this.BoneFootLeft = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft");
		this.BoneTalonRight = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight");
		this.BoneTalonRight1 = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight1");
		this.BoneTalonRight2 = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight2");
		this.BoneTalonRight3 = root.getChild("BoneBase").getChild("BoneLegRight").getChild("BoneFootRight").getChild("BoneTalonRight").getChild("BoneTalonRight3");
		this.BoneTalonLeft = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft");
		this.BoneTalonLeft1 = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft1");
		this.BoneTalonLeft2 = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft2");
		this.BoneTalonLeft3 = root.getChild("BoneBase").getChild("BoneLegLeft").getChild("BoneFootLeft").getChild("BoneTalonLeft").getChild("BoneTalonLeft3");
		this.BoneBeakLower = root.getChild("BoneBase").getChild("BoneNeck").getChild("BoneHead").getChild("BoneBeakLower");
		this.BoneWingRight1 = root.getChild("BoneBase").getChild("BoneWingRight1");
		this.BoneWingRight3 = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3");
		this.BoneWingRight5 = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3").getChild("BoneWingRight5");
		this.BoneWingRight7 = root.getChild("BoneBase").getChild("BoneWingRight1").getChild("BoneWingRight3").getChild("BoneWingRight5").getChild("BoneWingRight7");
		this.BoneWingLeft1 = root.getChild("BoneBase").getChild("BoneWingLeft1");
		this.BoneWingLeft3 = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3");
		this.BoneWingLeft5 = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3").getChild("BoneWingLeft5");
		this.BoneWingLeft7 = root.getChild("BoneBase").getChild("BoneWingLeft1").getChild("BoneWingLeft3").getChild("BoneWingLeft5").getChild("BoneWingLeft7");
		this.BoneSaddle = root.getChild("BoneBase").getChild("BoneSaddle");
		this.BoneChest = root.getChild("BoneBase").getChild("BoneChest");
		this.BoneBackFeather = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeather");
		this.BoneBackFeatherRight = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeatherRight");
		this.BoneBackFeatherLeft = root.getChild("BoneBase").getChild("BoneBack").getChild("BoneBackFeatherLeft");
		heightBaseStand = this.BoneBase.y;
		heightBaseSit = this.BoneBase.y + 12;
		
		this.BoneBaseChick = root.getChild("BoneBaseChick");
		this.BoneTalonLeftChick = root.getChild("BoneBaseChick").getChild("BoneTalonLeftChick");
		this.BoneTalonRightChick = root.getChild("BoneBaseChick").getChild("BoneTalonRightChick");
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition BoneBase = partdefinition.addOrReplaceChild("BoneBase", CubeListBuilder.create().texOffs(3, 41).addBox(-5.0F, -10.0F, -8.0F, 10.0F, 10.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(39, 26).addBox(-4.5F, -9.5F, 4.5F, 9.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 2.0F));
		
		PartDefinition BoneChest = BoneBase.addOrReplaceChild("BoneChest", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, 2.9F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -2.0F));
		
		PartDefinition BoneFront = BoneBase.addOrReplaceChild("BoneFront", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -4.25F, -3.5F, 9.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.0F, -8.0F, 0.5236F, 0.0F, 0.0F));
		
		PartDefinition BoneNeck = BoneBase.addOrReplaceChild("BoneNeck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -10.5F));
		
		PartDefinition BoneHead = BoneNeck.addOrReplaceChild("BoneHead", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(19, 10).addBox(-2.5F, -5.75F, 3.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(30, 18).addBox(2.5F, -5.75F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(31, 32).addBox(2.5F, -3.75F, 3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(23, 26).addBox(-2.5F, -5.75F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(24, 11).addBox(-2.5F, -3.75F, 3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));
		
		PartDefinition BoneBeakUpper = BoneHead.addOrReplaceChild("BoneBeakUpper", CubeListBuilder.create().texOffs(42, 11).addBox(-2.0F, -0.5F, -7.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -3.0F, 0.0873F, 0.0F, 0.0F));
		
		PartDefinition BoneBeakLower = BoneHead.addOrReplaceChild("BoneBeakLower", CubeListBuilder.create().texOffs(30, 10).addBox(-2.0F, -0.5F, -6.5F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -3.0F, 0.0873F, 0.0F, 0.0F));
		
		PartDefinition BoneLegLeft = BoneBase.addOrReplaceChild("BoneLegLeft", CubeListBuilder.create().texOffs(52, 41).addBox(-1.5F, -2.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, -0.5F));
		
		PartDefinition BoneFootLeft = BoneLegLeft.addOrReplaceChild("BoneFootLeft", CubeListBuilder.create().texOffs(36, 41).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		
		PartDefinition BoneTalonLeft = BoneFootLeft.addOrReplaceChild("BoneTalonLeft", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));
		
		PartDefinition BoneTalonLeft1 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft1", CubeListBuilder.create().texOffs(44, 47).addBox(0.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, -0.2182F, 0.0F));
		
		PartDefinition BoneTalonLeft2 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft2", CubeListBuilder.create().texOffs(44, 47).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.2182F, 0.0F));
		
		PartDefinition BoneTalonLeft3 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft3", CubeListBuilder.create().texOffs(45, 47).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		PartDefinition BoneLegRight = BoneBase.addOrReplaceChild("BoneLegRight", CubeListBuilder.create().texOffs(52, 53).addBox(-1.5F, -2.0F, -1.25F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, -0.5F));
		
		PartDefinition BoneFootRight = BoneLegRight.addOrReplaceChild("BoneFootRight", CubeListBuilder.create().texOffs(36, 41).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		
		PartDefinition BoneTalonRight = BoneFootRight.addOrReplaceChild("BoneTalonRight", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));
		
		PartDefinition BoneTalonRight1 = BoneTalonRight.addOrReplaceChild("BoneTalonRight1", CubeListBuilder.create().texOffs(44, 47).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, 0.2182F, 0.0F));
		
		PartDefinition BoneTalonRight2 = BoneTalonRight.addOrReplaceChild("BoneTalonRight2", CubeListBuilder.create().texOffs(44, 47).addBox(0.0F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217F, -0.2182F, 0.0F));
		
		PartDefinition BoneTalonRight3 = BoneTalonRight.addOrReplaceChild("BoneTalonRight3", CubeListBuilder.create().texOffs(45, 47).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		PartDefinition BoneBack = BoneBase.addOrReplaceChild("BoneBack", CubeListBuilder.create(), PartPose.offset(0.0F, -8.5F, 5.0F));
		
		PartDefinition BoneBackFeather = BoneBack.addOrReplaceChild("BoneBackFeather", CubeListBuilder.create().texOffs(19, 20).addBox(-2.0F, -0.75F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(19, 29).addBox(-2.0F, 0.25F, 0.0F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0908F, 0.0F, 0.0F));
		
		PartDefinition BoneBackFeatherRight = BoneBack.addOrReplaceChild("BoneBackFeatherRight", CubeListBuilder.create().texOffs(14, 30).addBox(-4.0F, -0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-3.5F, -0.25F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(12, 18).addBox(-4.0F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(14, 13).addBox(-4.0F, 0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, -0.1745F, 0.0F));
		
		PartDefinition BoneBackFeatherLeft = BoneBack.addOrReplaceChild("BoneBackFeatherLeft", CubeListBuilder.create().texOffs(14, 10).addBox(1.0F, -0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(12, 22).addBox(0.0F, -0.25F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(12, 11).addBox(1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(14, 28).addBox(1.0F, 0.5F, 0.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, 0.1745F, 0.0F));
		
		PartDefinition BoneSaddle = BoneBase.addOrReplaceChild("BoneSaddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.5F, -1.5F, -4.0F, 11.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -2.0F));
		
		PartDefinition BoneWingLeft1 = BoneBase.addOrReplaceChild("BoneWingLeft1", CubeListBuilder.create().texOffs(44, 24).addBox(0.0F, -0.5F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -8.0F, -7.0F));
		
		PartDefinition BoneWingLeft2 = BoneWingLeft1.addOrReplaceChild("BoneWingLeft2", CubeListBuilder.create().texOffs(48, 20).addBox(4.85F, -20.6F, 1.75F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 20.0F, 5.0F));
		
		PartDefinition BoneWingLeft3 = BoneWingLeft1.addOrReplaceChild("BoneWingLeft3", CubeListBuilder.create().texOffs(34, 29).addBox(-0.1F, -0.1F, 0.1F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 0.0F));
		
		PartDefinition BoneWingLeft4 = BoneWingLeft3.addOrReplaceChild("BoneWingLeft4", CubeListBuilder.create().texOffs(36, 20).addBox(4.75F, -18.7F, 2.85F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 18.5F, 5.0F));
		
		PartDefinition BoneWingLeft5 = BoneWingLeft3.addOrReplaceChild("BoneWingLeft5", CubeListBuilder.create().texOffs(46, 25).addBox(-0.2F, 0.8F, 0.2F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		
		PartDefinition BoneWingLeft6 = BoneWingLeft5.addOrReplaceChild("BoneWingLeft6", CubeListBuilder.create().texOffs(48, 30).addBox(4.65F, -15.8F, 2.95F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.5F, 5.0F));
		
		PartDefinition BoneWingLeft7 = BoneWingLeft5.addOrReplaceChild("BoneWingLeft7", CubeListBuilder.create().texOffs(35, 27).addBox(-0.3F, 1.7F, 0.3F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		
		PartDefinition BoneWingLeft8 = BoneWingLeft7.addOrReplaceChild("BoneWingLeft8", CubeListBuilder.create().texOffs(41, 20).addBox(4.55F, -12.9F, 2.05F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 14.5F, 5.0F));
		
		PartDefinition BoneWingRight1 = BoneBase.addOrReplaceChild("BoneWingRight1", CubeListBuilder.create().texOffs(35, 25).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -8.0F, -7.0F));
		
		PartDefinition BoneWingRight2 = BoneWingRight1.addOrReplaceChild("BoneWingRight2", CubeListBuilder.create().texOffs(35, 20).addBox(-5.85F, -20.5F, 1.75F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 20.0F, 5.0F));
		
		PartDefinition BoneWingRight3 = BoneWingRight1.addOrReplaceChild("BoneWingRight3", CubeListBuilder.create().texOffs(46, 27).addBox(-0.9F, -0.6F, 0.1F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		
		PartDefinition BoneWingRight4 = BoneWingRight3.addOrReplaceChild("BoneWingRight4", CubeListBuilder.create().texOffs(34, 28).addBox(-5.75F, -18.6F, 2.85F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 18.0F, 5.0F));
		
		PartDefinition BoneWingRight5 = BoneWingRight3.addOrReplaceChild("BoneWingRight5", CubeListBuilder.create().texOffs(34, 22).addBox(-0.8F, 0.3F, 0.2F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		
		PartDefinition BoneWingRight6 = BoneWingRight5.addOrReplaceChild("BoneWingRight6", CubeListBuilder.create().texOffs(45, 20).addBox(-5.65F, -15.7F, 2.95F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 16.0F, 5.0F));
		
		PartDefinition BoneWingRight7 = BoneWingRight5.addOrReplaceChild("BoneWingRight7", CubeListBuilder.create().texOffs(39, 26).addBox(-0.7F, 1.2F, 0.3F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));
		
		PartDefinition BoneWingRight8 = BoneWingRight7.addOrReplaceChild("BoneWingRight8", CubeListBuilder.create().texOffs(34, 20).addBox(-5.55F, -12.8F, 2.05F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 14.0F, 5.0F));
		
		// return LayerDefinition.create(meshdefinition, 64, 64);
		
		// MeshDefinition meshdefinition = new MeshDefinition();
		// PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition BoneBaseChick = partdefinition.addOrReplaceChild("BoneBaseChick", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		
		PartDefinition BoneHeadChick = BoneBaseChick.addOrReplaceChild("BoneHeadChick", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -7.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 14).addBox(-0.5F, -5.1F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneWingLeftChick = BoneBaseChick.addOrReplaceChild("BoneWingLeftChick", CubeListBuilder.create().texOffs(0, 4).addBox(1.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneWingRightChick = BoneBaseChick.addOrReplaceChild("BoneWingRightChick", CubeListBuilder.create().texOffs(6, 6).addBox(-2.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonLeftChick = BoneBaseChick.addOrReplaceChild("BoneTalonLeftChick", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonLeft1Chick = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft1Chick", CubeListBuilder.create().texOffs(12, 1).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		
		PartDefinition BoneTalonLeft2Chick = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft2Chick", CubeListBuilder.create().texOffs(12, 1).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		
		PartDefinition BoneTalonLeft3Chick = BoneTalonLeftChick.addOrReplaceChild("BoneTalonLeft3Chick", CubeListBuilder.create().texOffs(12, 1).addBox(-0.6F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonRightChick = BoneBaseChick.addOrReplaceChild("BoneTalonRightChick", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonRight1Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight1Chick", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		
		PartDefinition BoneTalonRight2Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight2Chick", CubeListBuilder.create().texOffs(0, 1).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		
		PartDefinition BoneTalonRight3Chick = BoneTalonRightChick.addOrReplaceChild("BoneTalonRight3Chick", CubeListBuilder.create().texOffs(0, 1).addBox(-0.3F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		// return LayerDefinition.create(meshdefinition, 16, 16);
		
		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ANIMATION  ---------- ---------- ---------- ---------- //
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.renderChild = entity.AnimYoung();
		ResetRotations();
		this.BoneHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
		float rotSIN = Mth.sin(ageInTicks * 0.6662F) * 0.04F;
		float rotCOS = Mth.cos(ageInTicks * 0.6662F) * 0.04F;
		SetLegRotation(entity);
		this.BoneBase.y = entity.AnimSit() ? heightBaseSit : heightBaseStand;
		if(!entity.AnimFly()){
			this.BoneLegRight.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.BoneLegLeft.xRot  += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.BoneTalonRight.xRot -= Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
			this.BoneTalonLeft.xRot  -= Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			if(!entity.AnimRide())this.BoneBase.y -= Mth.cos(limbSwing * 0.7F + (float)Math.PI) * 1.5F * limbSwingAmount;
			// this.ChickLeftFootPart.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.05F * limbSwingAmount;
			// this.ChickRightFootPart.xRot  += Mth.cos(limbSwing * 0.6662F) * 0.05F * limbSwingAmount;
		}
		BoneBeakLower.xRot = entity.AnimKweh() ? 3 * beakXrot : beakXrot;
		// BoneTalonLeft1.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
		// BoneTalonLeft2.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
		// BoneTalonLeft3.xRot  = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;
		// BoneTalonRight1.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
		// BoneTalonRight2.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
		// BoneTalonRight3.xRot = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;
		if(entity.AnimFly() || entity.AnimRun()){
			if(!entity.AnimRun()){
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
			if( (entity.AnimFly() && entity.AnimRun()) || entity.AnimRun()){
				BoneNeck.xRot = 20*ROT;
				BoneHead.xRot = -18*ROT;
				// BoneHeadFeather1.xRot  = + rotSIN;
				// BoneHeadFeather2.xRot  = - rotCOS;
				// BoneHeadFeather3.xRot  = - rotSIN;
				// BoneHeadFeather4.xRot  = + rotCOS;
				// BoneHeadFeather5.xRot  = + rotSIN;
				// BoneHeadFeather6.zRot  = - rotCOS;
				// BoneHeadFeather7.zRot  = - rotSIN;
				// BoneHeadFeather8.zRot  = + rotCOS;
				// BoneHeadFeather9.zRot  = + rotSIN;
				// BoneHeadFeather10.zRot = - rotCOS;
				// BoneHeadFeather11.zRot = - rotSIN;
				BoneWingLeft1.xRot = -ROT;
				BoneWingLeft3.xRot = -ROT;
				BoneWingLeft5.xRot = -ROT;
				BoneWingLeft7.xRot = -ROT;
				BoneWingRight1.xRot = -ROT;
				BoneWingRight3.xRot = -ROT;
				BoneWingRight5.xRot = -ROT;
				BoneWingRight7.xRot = -ROT;
			}
			if(entity.AnimFly() && (entity.AnimRide() || entity.AnimRun()) ){
				BoneLegLeft.xRot = 26*ROT;
				BoneLegRight.xRot = 26*ROT;
				BoneFootLeft.xRot = 0;
				BoneFootRight.xRot = 0;
			}
		}
		BoneSaddle.visible = entity.AnimSaddle();
		BoneChest.visible = entity.AnimChest();
		BoneBackFeather.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		BoneBackFeatherRight.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		BoneBackFeatherLeft.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather4.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather5.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather6.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather7.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather8.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather9.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather10.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneBackFeather11.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
		// BoneHeadFeather1.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather2.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather3.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather4.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather5.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather6.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather7.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather8.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather9.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather10.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
		// BoneHeadFeather11.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
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
		BoneLegLeft.xRot = 0;
		BoneLegRight.xRot = 0;
		BoneFootLeft.xRot = 0;
		BoneFootRight.xRot = 0;
		BoneNeck.xRot = 0;
		BoneHead.xRot = 0;
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
		// BoneHeadFeather1.xRot  = 0;
		// BoneHeadFeather2.xRot  = 0;
		// BoneHeadFeather3.xRot  = 0;
		// BoneHeadFeather4.xRot  = 0;
		// BoneHeadFeather5.xRot  = 0;
		// BoneHeadFeather6.zRot  = 0;
		// BoneHeadFeather7.zRot  = 0;
		// BoneHeadFeather8.zRot  = 0;
		// BoneHeadFeather9.zRot  = 0;
		// BoneHeadFeather10.zRot = 0;
		// BoneHeadFeather11.zRot = 0;
		BoneWingLeft1.xRot = 0;
		BoneWingLeft3.xRot = 0;
		BoneWingLeft5.xRot = 0;
		BoneWingLeft7.xRot = 0;
		BoneWingRight1.xRot = 0;
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
		float height = 4;
		float rot = 2 * ROT;
		int mod = 0;
		if(entity.AnimSit()){
			mod = 16;
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
		// BoneBase.y = height + (baseHeight * mod);
	}
	
	
	
}


// Backup

// package mod.acecraft.common.entity.model;
//
// // Made with Blockbench 3.8.4
// // Exported for Minecraft version 1.15 - 1.16
// // Paste this class into your mod and generate all required imports
//
//
// 		import com.google.common.collect.ImmutableList;
// 		import com.mojang.blaze3d.vertex.PoseStack;
// 		import com.mojang.blaze3d.vertex.VertexConsumer;
// 		import mod.acecraft.common.entity.EntityChocobo;
// 		import net.minecraft.client.model.AgeableListModel;
// 		import net.minecraft.client.model.geom.ModelPart;
// 		import net.minecraft.client.model.geom.PartPose;
// 		import net.minecraft.client.model.geom.builders.CubeListBuilder;
// 		import net.minecraft.client.model.geom.builders.LayerDefinition;
// 		import net.minecraft.client.model.geom.builders.MeshDefinition;
// 		import net.minecraft.client.model.geom.builders.PartDefinition;
// 		import net.minecraft.util.Mth;
// 		import net.minecraftforge.api.distmarker.Dist;
// 		import net.minecraftforge.api.distmarker.OnlyIn;
//
// @OnlyIn(Dist.CLIENT)
// public class ModelChocobo<T extends EntityChocobo>  extends AgeableListModel<T> {
//
// 	// ----- Chocobo ----- //
// 	private final ModelPart BoneBase;
// 	private final ModelPart BoneBody;
// 	private final ModelPart BoneFront;
// 	private final ModelPart BoneNeck;
// 	private final ModelPart BoneHead;
// 	private final ModelPart BoneBeak1;
// 	private final ModelPart BoneBeak2;
// 	private final ModelPart BoneHeadFeather;
// 	private final ModelPart BoneHeadFeather1;
// 	private final ModelPart BoneHeadFeather2;
// 	private final ModelPart BoneHeadFeather3;
// 	private final ModelPart BoneHeadFeather4;
// 	private final ModelPart BoneHeadFeather5;
// 	private final ModelPart BoneHeadFeather6;
// 	private final ModelPart BoneHeadFeather7;
// 	private final ModelPart BoneHeadFeather8;
// 	private final ModelPart BoneHeadFeather9;
// 	private final ModelPart BoneHeadFeather10;
// 	private final ModelPart BoneHeadFeather11;
// 	private final ModelPart BoneBack;
// 	private final ModelPart BoneBackFeather1;
// 	private final ModelPart BoneBackFeather2;
// 	private final ModelPart BoneBackFeather3;
// 	private final ModelPart BoneBackFeather4;
// 	private final ModelPart BoneBackFeather5;
// 	private final ModelPart BoneBackFeather6;
// 	private final ModelPart BoneBackFeather7;
// 	private final ModelPart BoneBackFeather8;
// 	private final ModelPart BoneBackFeather9;
// 	private final ModelPart BoneBackFeather10;
// 	private final ModelPart BoneBackFeather11;
// 	private final ModelPart BoneWingLeft1;
// 	private final ModelPart BoneWingLeft2;
// 	private final ModelPart BoneWingLeft3;
// 	private final ModelPart BoneWingLeft4;
// 	private final ModelPart BoneWingRight1;
// 	private final ModelPart BoneWingRight2;
// 	private final ModelPart BoneWingRight3;
// 	private final ModelPart BoneWingRight4;
// 	private final ModelPart BoneLegRight;
// 	private final ModelPart BoneFootRight;
// 	private final ModelPart BoneTalonRight;
// 	private final ModelPart BoneFalonRight1;
// 	private final ModelPart BoneFalonRight2;
// 	private final ModelPart BoneFalonRight3;
// 	private final ModelPart BoneLegLeft;
// 	private final ModelPart BoneFootLeft;
// 	private final ModelPart BoneTalonLeft;
// 	private final ModelPart BoneFalonLeft1;
// 	private final ModelPart BoneFalonLeft2;
// 	private final ModelPart BoneFalonLeft3;
// 	private final ModelPart BoneSaddle;
//
// 	// ----- Chicken ----- //
// 	private final ModelPart ChickBody;
// 	private final ModelPart ChickFront_r1;
// 	private final ModelPart ChickLeftFootPart;
// 	private final ModelPart ChickTalonLeftBack_r1;
// 	private final ModelPart ChickTalonLeftLeft_r1;
// 	private final ModelPart ChickTalonLeftRight_r1;
// 	private final ModelPart ChickRightFootPart;
// 	private final ModelPart ChickTalonRightBack_r1;
// 	private final ModelPart ChickTalonRightLeft_r1;
// 	private final ModelPart ChickTalonRightRight_r1;
// 	private final ModelPart ChickWingLeft;
// 	private final ModelPart ChickWingRight;
// 	private final ModelPart ChickUpperPart;
// 	private final ModelPart ChickHeadPart;
//
// 	// ----- Variable ----- //
// 	private float talonXRot = 0.0436F * 6;
// 	private float beakXrot = 0.0436F * 4;
// 	private float headFeatherZPos = 0.5f;
// 	private float backFeatherZPos = 0.5f;
// 	private float ROT = 0.0436F;
// 	private boolean baby = false;
//
//
//
//
//
// 	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
//
// 	public ModelChocobo(ModelPart part) {
// 		super(false, 6.0F, 0.0F);
//
// 		// ----- Chocobo ----- //
// 		this.BoneBase          = part.getChild("base");
// 		this.BoneBody          = part.getChild("base").getChild("body");
// 		this.BoneFront         = part.getChild("base").getChild("front");
// 		this.BoneNeck          = part.getChild("base").getChild("neck");
// 		this.BoneHead          = part.getChild("base").getChild("neck").getChild("head");
// 		this.BoneBeak1         = part.getChild("base").getChild("neck").getChild("head").getChild("beak1");
// 		this.BoneBeak2         = part.getChild("base").getChild("neck").getChild("head").getChild("beak2");
// 		this.BoneHeadFeather   = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather");
// 		this.BoneHeadFeather1  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather1");
// 		this.BoneHeadFeather2  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather2");
// 		this.BoneHeadFeather3  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather3");
// 		this.BoneHeadFeather4  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather4");
// 		this.BoneHeadFeather5  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather5");
// 		this.BoneHeadFeather6  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather6");
// 		this.BoneHeadFeather7  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather7");
// 		this.BoneHeadFeather8  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather8");
// 		this.BoneHeadFeather9  = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather9");
// 		this.BoneHeadFeather10 = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather10");
// 		this.BoneHeadFeather11 = part.getChild("base").getChild("neck").getChild("head").getChild("head_feather").getChild("head_feather11");
// 		this.BoneBack          = part.getChild("base").getChild("back");
// 		this.BoneBackFeather1  = part.getChild("base").getChild("back").getChild("back_feather1");
// 		this.BoneBackFeather2  = part.getChild("base").getChild("back").getChild("back_feather2");
// 		this.BoneBackFeather3  = part.getChild("base").getChild("back").getChild("back_feather3");
// 		this.BoneBackFeather4  = part.getChild("base").getChild("back").getChild("back_feather4");
// 		this.BoneBackFeather5  = part.getChild("base").getChild("back").getChild("back_feather5");
// 		this.BoneBackFeather6  = part.getChild("base").getChild("back").getChild("back_feather6");
// 		this.BoneBackFeather7  = part.getChild("base").getChild("back").getChild("back_feather7");
// 		this.BoneBackFeather8  = part.getChild("base").getChild("back").getChild("back_feather8");
// 		this.BoneBackFeather9  = part.getChild("base").getChild("back").getChild("back_feather9");
// 		this.BoneBackFeather10 = part.getChild("base").getChild("back").getChild("back_feather10");
// 		this.BoneBackFeather11 = part.getChild("base").getChild("back").getChild("back_feather11");
// 		this.BoneWingLeft1     = part.getChild("base").getChild("wing_left1");
// 		this.BoneWingLeft2     = part.getChild("base").getChild("wing_left1").getChild("wing_left2");
// 		this.BoneWingLeft3     = part.getChild("base").getChild("wing_left1").getChild("wing_left2").getChild("wing_left3");
// 		this.BoneWingLeft4     = part.getChild("base").getChild("wing_left1").getChild("wing_left2").getChild("wing_left3").getChild("wing_left4");
// 		this.BoneWingRight1    = part.getChild("base").getChild("wing_right1");
// 		this.BoneWingRight2    = part.getChild("base").getChild("wing_right1").getChild("wing_right2");
// 		this.BoneWingRight3    = part.getChild("base").getChild("wing_right1").getChild("wing_right2").getChild("wing_right3");
// 		this.BoneWingRight4    = part.getChild("base").getChild("wing_right1").getChild("wing_right2").getChild("wing_right3").getChild("wing_right4");
// 		this.BoneLegRight      = part.getChild("base").getChild("leg_right");
// 		this.BoneFootRight     = part.getChild("base").getChild("leg_right").getChild("foot_right");
// 		this.BoneTalonRight    = part.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right");
// 		this.BoneFalonRight1   = part.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").getChild("talon_right1");
// 		this.BoneFalonRight2   = part.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").getChild("talon_right2");
// 		this.BoneFalonRight3   = part.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").getChild("talon_right3");
// 		this.BoneLegLeft       = part.getChild("base").getChild("leg_left");
// 		this.BoneFootLeft      = part.getChild("base").getChild("leg_left").getChild("foot_left");
// 		this.BoneTalonLeft     = part.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left");
// 		this.BoneFalonLeft1    = part.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").getChild("talon_left1");
// 		this.BoneFalonLeft2    = part.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").getChild("talon_left2");
// 		this.BoneFalonLeft3    = part.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").getChild("talon_left3");
// 		this.BoneSaddle        = part.getChild("base").getChild("saddle");
//
// 		// ----- Chicken ----- //
// 		ChickBody               = part.getChild("chick_body");
// 		ChickFront_r1           = part.getChild("chick_body").getChild("chick_front");
// 		ChickLeftFootPart       = part.getChild("chick_left_foot");
// 		ChickTalonLeftBack_r1   = part.getChild("chick_left_foot").getChild("chick_talon_left_back");
// 		ChickTalonLeftLeft_r1   = part.getChild("chick_left_foot").getChild("chick_talon_left_left");
// 		ChickTalonLeftRight_r1  = part.getChild("chick_left_foot").getChild("chick_talon_left_right");
// 		ChickRightFootPart      = part.getChild("chick_right_foot");
// 		ChickTalonRightBack_r1  = part.getChild("chick_right_foot").getChild("chick_talon_right_back");
// 		ChickTalonRightLeft_r1  = part.getChild("chick_right_foot").getChild("chick_talon_right_left");
// 		ChickTalonRightRight_r1 = part.getChild("chick_right_foot").getChild("chick_talon_right_right");
// 		ChickWingLeft           = part.getChild("chick_wing_left");
// 		ChickWingRight          = part.getChild("chick_wing_right");
// 		ChickUpperPart          = part.getChild("chick_upper");
// 		ChickHeadPart           = part.getChild("chick_upper").getChild("chick_head");
// 	}
//
//
//
//
//
// 	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
//
// 	public static LayerDefinition createBodyLayer() {
// 		MeshDefinition modelDefinition = new MeshDefinition();
// 		PartDefinition def = modelDefinition.getRoot();
//
// 		// ----- Chocobo ----- //
// 		def.addOrReplaceChild("base",CubeListBuilder.create(), PartPose.offset(0.0F, 6.5F, 1.0F));
// 		def.getChild("base").addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 37).addBox(-5.0F, -9.25F, -7.0F, 10.0F, 11.0F, 16.0F), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.0436F, 0.0F, 0.0F));
// 		def.getChild("base").addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 49).addBox(-4.5F, -0.5F, -5.0F, 9.0F, 9.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -5.5F, -7.0F, 0.5236F, 0.0F, 0.0F));
// 		def.getChild("base").addOrReplaceChild("neck", CubeListBuilder.create().texOffs(48, 12).addBox(-2.0F, -14.0F, -3.5F, 4.0F, 14.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -3.5F, -7.5F, 0f, 0f, 0f));
// 		def.getChild("base").getChild("neck").addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -13.0F, -1.5F, 0f, 0f, 0f));
// 		def.getChild("base").getChild("neck").getChild("head").addOrReplaceChild("beak1", CubeListBuilder.create().texOffs(16, 2).addBox(-2.5F, -2.25F, -6.5F, 5.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.1745F, 0.0F, 0.0F));
// 		def.getChild("base").getChild("neck").getChild("head").addOrReplaceChild("beak2", CubeListBuilder.create().texOffs(17, 3).addBox(-2.0F, -0.25F, -6.5F, 4.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.1745F, 0.0F, 0.0F));
// 		def.getChild("base").getChild("neck").getChild("head").addOrReplaceChild("head_feather", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather1", CubeListBuilder.create().texOffs(23, 33).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F), PartPose.offset(2.0F, -3.5F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather2", CubeListBuilder.create().texOffs(52, 37).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F), PartPose.offset(1.0F, -3.5F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather3", CubeListBuilder.create().texOffs(0, 39).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F), PartPose.offset(0.0F, -3.5F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather4", CubeListBuilder.create().texOffs(52, 52).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F), PartPose.offset(-1.0F, -3.5F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather5", CubeListBuilder.create().texOffs(54, 60).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 4.0F), PartPose.offset(-2.0F, -3.5F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather6", CubeListBuilder.create().texOffs(32, 29).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F), PartPose.offset(2.5F, -3.0F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather7", CubeListBuilder.create().texOffs(49, 46).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F), PartPose.offset(2.5F, -2.0F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather8", CubeListBuilder.create().texOffs(4, 45).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F), PartPose.offset(2.5F, -1.0F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather9", CubeListBuilder.create().texOffs(56, 29).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 4.0F), PartPose.offset(-2.5F, -3.0F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather10", CubeListBuilder.create().texOffs(22, 41).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 3.0F), PartPose.offset(-2.5F, -2.0F, 0.5F));
// 		def.getChild("base").getChild("neck").getChild("head").getChild("head_feather").addOrReplaceChild("head_feather11", CubeListBuilder.create().texOffs(27, 48).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F), PartPose.offset(-2.5F, -1.0F, 0.5F));
// 		def.getChild("base").addOrReplaceChild("back", CubeListBuilder.create().texOffs(2, 31).addBox(-4.5F, -1.0F, -0.25F, 9.0F, 2.0F, 1.0F), PartPose.offset(0.0F, -4.75F, 9.5F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather1", CubeListBuilder.create().texOffs(9, 35).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F), PartPose.offsetAndRotation(3.0F, -0.75F, 0.5F, 0.8727F, 0.1745F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather2", CubeListBuilder.create().texOffs(19, 38).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 9.0F), PartPose.offsetAndRotation(1.0F, -0.75F, 0.5F, 0.8727F, 0.0436F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather3", CubeListBuilder.create().texOffs(34, 33).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 9.0F), PartPose.offsetAndRotation(-1.0F, -0.75F, 0.5F, 0.9163F, -0.0436F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather4", CubeListBuilder.create().texOffs(44, 47).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F), PartPose.offsetAndRotation(-3.0F, -0.75F, 0.5F, 0.8727F, -0.1309F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather5", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 11.0F), PartPose.offsetAndRotation(3.0F, 0.5F, 0.5F, 0.6981F, 0.0436F, -0.0436F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather6", CubeListBuilder.create().texOffs(25, 44).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 10.0F), PartPose.offsetAndRotation(1.0F, 0.5F, 0.5F, 0.6545F, 0.0F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather7", CubeListBuilder.create().texOffs(20, 51).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 10.0F), PartPose.offsetAndRotation(-1.0F, 0.5F, 0.5F, 0.6109F, 0.0F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather8", CubeListBuilder.create().texOffs(6, 39).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 11.0F), PartPose.offsetAndRotation(-3.0F, 0.5F, 0.5F, 0.6545F, 0.0F, -0.0436F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather9", CubeListBuilder.create().texOffs(9, 35).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F), PartPose.offsetAndRotation(2.0F, -0.25F, 0.5F, 0.8727F, 0.1745F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather10", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 7.0F), PartPose.offsetAndRotation(0.0F, -0.25F, 0.5F, 0.5672F, 0.0F, 0.0F));
// 		def.getChild("base").getChild("back").addOrReplaceChild("back_feather11", CubeListBuilder.create().texOffs(44, 56).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 8.0F), PartPose.offsetAndRotation(-2.0F, -0.25F, 0.5F, 0.6109F, 0.0F, 0.0873F));
// 		def.getChild("base").addOrReplaceChild("wing_left1", CubeListBuilder.create().texOffs(28, 30).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F).texOffs(14, 39).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F), PartPose.offset(5.0F, -5.0F, -5.7F));
// 		def.getChild("base").getChild("wing_left1").addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(48, 35).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F).texOffs(40, 48).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F), PartPose.offset(0.0F, 2.0F, -0.1F));
// 		def.getChild("base").getChild("wing_left1").getChild("wing_left2").addOrReplaceChild("wing_left3", CubeListBuilder.create().texOffs(15, 30).addBox(0.0F, 1.0F, 0.0F, 1.0F, 3.0F, 7.0F).texOffs(18, 53).addBox(-0.1F, 1.0F, 7.0F, 1.0F, 3.0F, 7.0F), PartPose.offset(0.0F, 2.0F, 0.1F));
// 		def.getChild("base").getChild("wing_left1").getChild("wing_left2").getChild("wing_left3").addOrReplaceChild("wing_left4", CubeListBuilder.create().texOffs(32, 55).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F).texOffs(1, 36).addBox(-0.1F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F), PartPose.offset(0.0F, 4.0F, 0.2F));
// 		def.getChild("base").addOrReplaceChild("wing_right1", CubeListBuilder.create().texOffs(30, 40).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F).texOffs(18, 31).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F), PartPose.offset(-5.0F, -5.0F, -5.7F));
// 		def.getChild("base").getChild("wing_right1").addOrReplaceChild("wing_right2", CubeListBuilder.create().texOffs(4, 42).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F).texOffs(40, 40).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F), PartPose.offset(0.0F, 2.0F, -0.1F));
// 		def.getChild("base").getChild("wing_right1").getChild("wing_right2").addOrReplaceChild("wing_right3", CubeListBuilder.create().texOffs(17, 46).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 7.0F).texOffs(33, 50).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 3.0F, 7.0F), PartPose.offset(0.0F, 3.0F, 0.1F));
// 		def.getChild("base").getChild("wing_right1").getChild("wing_right2").getChild("wing_right3").addOrReplaceChild("wing_right4", CubeListBuilder.create().texOffs(8, 36).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 7.0F).texOffs(33, 29).addBox(-0.9F, 0.0F, 7.0F, 1.0F, 2.0F, 7.0F), PartPose.offset(0.0F, 3.0F, 0.2F));
// 		def.getChild("base").addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(52, 43).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(-3.0F, 4.0F, 0.5F));
// 		def.getChild("base").getChild("leg_right").addOrReplaceChild("foot_right", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 6.25F, 0.0F));
// 		def.getChild("base").getChild("leg_right").getChild("foot_right").addOrReplaceChild("talon_right", CubeListBuilder.create(), PartPose.offset(0.0F, 6.25F, 0.0F));
// 		def.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").addOrReplaceChild("talon_right1", CubeListBuilder.create().texOffs(2, 3).addBox(-1.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.1309F, 0.0F));
// 		def.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").addOrReplaceChild("talon_right2", CubeListBuilder.create().texOffs(2, 3).addBox(0.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.1309F, 0.0F));
// 		def.getChild("base").getChild("leg_right").getChild("foot_right").getChild("talon_right").addOrReplaceChild("talon_right3", CubeListBuilder.create().texOffs(10, 3).addBox(-0.5F, -0.25F, 0.25F, 1.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
// 		def.getChild("base").addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 7.0F, 3.0F), PartPose.offset(3.0F, 4.0F, 0.5F));
// 		def.getChild("base").getChild("leg_left").addOrReplaceChild("foot_left", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 6.25F, 0.0F));
// 		def.getChild("base").getChild("leg_left").getChild("foot_left").addOrReplaceChild("talon_left", CubeListBuilder.create(), PartPose.offset(0.0F, 6.25F, 0.0F));
// 		def.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").addOrReplaceChild("talon_left1", CubeListBuilder.create().texOffs(2, 3).addBox(0.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.1309F, 0.0F));
// 		def.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").addOrReplaceChild("talon_left2", CubeListBuilder.create().texOffs(2, 3).addBox(-1.0F, -0.25F, -6.5F, 1.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.1309F, 0.0F));
// 		def.getChild("base").getChild("leg_left").getChild("foot_left").getChild("talon_left").addOrReplaceChild("talon_left3", CubeListBuilder.create().texOffs(10, 3).addBox(-0.5F, -0.25F, 0.5F, 1.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
// 		def.getChild("base").addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(0, 19).addBox(-4.5F, -9.75F, -3.0F, 9.0F, 1.0F, 8.0F), PartPose.offset(0.0F, 3.0F, 0.0F));
//
// 		// ----- Chicken ----- //
// 		def.addOrReplaceChild("chick_body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 6.0F), PartPose.offset(0.0F, 18.75F, 0.0F));
// 		def.getChild("chick_body").addOrReplaceChild("chick_front", CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.0F, -0.3415F, -2.9085F, 0.5236F, 0.0F, 0.0F));
// 		def.addOrReplaceChild("chick_left_foot", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(1.5F, 19.75F, 0.0F));
// 		def.getChild("chick_left_foot").addOrReplaceChild("chick_talon_left_back", CubeListBuilder.create().texOffs(20, 12).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, -0.2618F, 0.0F, 0.0F));
// 		def.getChild("chick_left_foot").addOrReplaceChild("chick_talon_left_left", CubeListBuilder.create().texOffs(21, 20).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, 0.2618F, -0.3491F, 0.0F));
// 		def.getChild("chick_left_foot").addOrReplaceChild("chick_talon_left_right", CubeListBuilder.create().texOffs(14, 20).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, 0.2618F, 0.3491F, 0.0F));
// 		def.addOrReplaceChild("chick_right_foot", CubeListBuilder.create().texOffs(24, 16).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.offset(-1.5F, 19.75F, 0.0F));
// 		def.getChild("chick_right_foot").addOrReplaceChild("chick_talon_right_back", CubeListBuilder.create().texOffs(23, 3).addBox(-0.5F, 1.5F, 0.25F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, -0.2618F, 0.0F, 0.0F));
// 		def.getChild("chick_right_foot").addOrReplaceChild("chick_talon_right_left", CubeListBuilder.create().texOffs(11, 24).addBox(-0.5F, 1.25F, -2.75F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, 0.2618F, -0.3491F, 0.0F));
// 		def.getChild("chick_right_foot").addOrReplaceChild("chick_talon_right_right", CubeListBuilder.create().texOffs(23, 7).addBox(-0.5F, 1.5F, -2.75F, 1.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 1.25F, 0.0F, 0.2618F, 0.3491F, 0.0F));
// 		def.addOrReplaceChild("chick_wing_left", CubeListBuilder.create().texOffs(0, 19).addBox(-0.25F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(2.5F, 18.5F, -2.0F));
// 		def.addOrReplaceChild("chick_wing_right", CubeListBuilder.create().texOffs(13, 12).addBox(-5.75F, -1.5F, 0.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(2.5F, 18.5F, -2.0F));
// 		def.addOrReplaceChild("chick_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, -2.5F));
// 		def.getChild("chick_upper").addOrReplaceChild("chick_head", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -2.5F, -2.25F, 3.0F, 3.0F, 3.0F).texOffs(7, 19).addBox(-1.0F, -1.75F, -3.0F, 2.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 0.0F, -0.5F));
//
// 		return LayerDefinition.create(modelDefinition, 64, 64);
// 	}
//
//
//
//
//
// 	// ---------- ---------- ---------- ----------  ANIMATION  ---------- ---------- ---------- ---------- //
//
// 	@Override
// 	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
// 		// ResetRotations();
// 		// this.BoneHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
// 		// float rotSIN = Mth.sin(ageInTicks * 0.6662F) * 0.04F;
// 		// float rotCOS = Mth.cos(ageInTicks * 0.6662F) * 0.04F;
// 		// SetLegRotation(entity);
// 		// if(!entity.AnimFly()){
// 		// 	this.BoneLegRight.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
// 		// 	this.BoneLegLeft.xRot  += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
// 		// 	this.BoneTalonRight.xRot -= Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
// 		// 	this.BoneTalonLeft.xRot  -= Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
// 		// 	this.ChickLeftFootPart.xRot += Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.05F * limbSwingAmount;
// 		// 	this.ChickRightFootPart.xRot  += Mth.cos(limbSwing * 0.6662F) * 0.05F * limbSwingAmount;
// 		// }
// 		// BoneBeak2.xRot = entity.AnimKweh() ? 3 * beakXrot : beakXrot;
// 		// BoneFalonLeft1.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
// 		// BoneFalonLeft2.xRot  = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
// 		// BoneFalonLeft3.xRot  = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;
// 		// BoneFalonRight1.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
// 		// BoneFalonRight2.xRot = entity.AnimFly() ? 4 *  talonXRot :  talonXRot;
// 		// BoneFalonRight3.xRot = entity.AnimFly() ? 4 * -talonXRot : -talonXRot;
// 		// if(entity.AnimFly() || entity.AnimRun()){
// 		// 	if(!entity.AnimRun()){
// 		// 		BoneWingLeft1.zRot = -32*ROT + rotSIN*20;
// 		// 		BoneWingRight1.zRot = 32*ROT - rotSIN*20;
// 		// 	} else {
// 		// 		BoneWingLeft1.zRot = -32*ROT;
// 		// 		BoneWingRight1.zRot = 32*ROT;
// 		// 	}
// 		// 	BoneWingLeft1.yRot = 0 + rotSIN;
// 		// 	BoneWingLeft2.yRot = 0 - rotCOS;
// 		// 	BoneWingLeft3.yRot = 0 - rotSIN;
// 		// 	BoneWingLeft4.yRot = 0 + rotCOS;
// 		// 	BoneWingRight1.yRot = 0 + rotSIN;
// 		// 	BoneWingRight2.yRot = 0 - rotCOS;
// 		// 	BoneWingRight3.yRot = 0 - rotSIN;
// 		// 	BoneWingRight4.yRot = 0 + rotCOS;
// 		// 	if( (entity.AnimFly() && entity.AnimRun()) || entity.AnimRun()){
// 		// 		BoneNeck.xRot = 20*ROT;
// 		// 		BoneHead.xRot = -18*ROT;
// 		// 		BoneHeadFeather1.xRot  = + rotSIN;
// 		// 		BoneHeadFeather2.xRot  = - rotCOS;
// 		// 		BoneHeadFeather3.xRot  = - rotSIN;
// 		// 		BoneHeadFeather4.xRot  = + rotCOS;
// 		// 		BoneHeadFeather5.xRot  = + rotSIN;
// 		// 		BoneHeadFeather6.zRot  = - rotCOS;
// 		// 		BoneHeadFeather7.zRot  = - rotSIN;
// 		// 		BoneHeadFeather8.zRot  = + rotCOS;
// 		// 		BoneHeadFeather9.zRot  = + rotSIN;
// 		// 		BoneHeadFeather10.zRot = - rotCOS;
// 		// 		BoneHeadFeather11.zRot = - rotSIN;
// 		// 		BoneWingLeft1.xRot = -ROT;
// 		// 		BoneWingLeft2.xRot = -ROT;
// 		// 		BoneWingLeft3.xRot = -ROT;
// 		// 		BoneWingLeft4.xRot = -ROT;
// 		// 		BoneWingRight1.xRot = -ROT;
// 		// 		BoneWingRight2.xRot = -ROT;
// 		// 		BoneWingRight3.xRot = -ROT;
// 		// 		BoneWingRight4.xRot = -ROT;
// 		// 	}
// 		// 	if(entity.AnimFly() && (entity.AnimRide() || entity.AnimRun()) ){
// 		// 		BoneLegLeft.xRot = 26*ROT;
// 		// 		BoneLegRight.xRot = 26*ROT;
// 		// 		BoneFootLeft.xRot = 0;
// 		// 		BoneFootRight.xRot = 0;
// 		// 	}
// 		// }
// 		// BoneSaddle.visible = entity.AnimSaddle();
// 		// BoneBackFeather1.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather2.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather3.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather4.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather5.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather6.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather7.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather8.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather9.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather10.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneBackFeather11.z = backFeatherZPos - ( entity.AnimFemale() ? 5 : 0 );
// 		// BoneHeadFeather1.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather2.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather3.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather4.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather5.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather6.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather7.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather8.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather9.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather10.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// BoneHeadFeather11.z = headFeatherZPos - ( entity.AnimFemale() ? 1 : 0 );
// 		// if(entity.AnimPick()){
// 		// 	BoneBase.y = 7;
// 		// 	BoneBase.xRot =  ROT * 10;
// 		// 	BoneNeck.xRot =  34*ROT - rotSIN*2;
// 		// 	BoneHead.xRot =   -14*ROT + rotSIN*2;
// 		// 	BoneLegLeft.xRot = -8*ROT;
// 		// 	BoneFootLeft.xRot = -16*ROT;
// 		// 	BoneTalonLeft.xRot = 12.5f * ROT;
// 		// 	BoneWingLeft1.xRot = 10*ROT;
// 		// 	BoneLegRight.xRot = -8*ROT;
// 		// 	BoneFootRight.xRot = -16*ROT;
// 		// 	BoneTalonRight.xRot = 12.5f*ROT;
// 		// 	BoneWingRight1.xRot = 10*ROT;
// 		// }
// 	}
//
// 	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
// 		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
// 		// baby = entity.AnimYoung();
// 	}
//
//
//
//
//
// 	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
//
// 	@Override
// 	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
// 		if(baby){
// 			ChickBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 			ChickLeftFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 			ChickRightFootPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 			ChickWingLeft.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 			ChickWingRight.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 			ChickUpperPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 		} else {
// 			BoneBase.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
// 		}
// 	}
//
//
//
//
//
// 	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
//
// 	private void ResetRotations(){
// 		BoneLegLeft.xRot = 0;
// 		BoneLegRight.xRot = 0;
// 		BoneFootLeft.xRot = 0;
// 		BoneFootRight.xRot = 0;
// 		BoneNeck.xRot = 0;
// 		BoneHead.xRot = 0;
// 		BoneWingLeft1.zRot = 0;
// 		BoneWingLeft1.yRot = 0;
// 		BoneWingLeft2.yRot = 0;
// 		BoneWingLeft3.yRot = 0;
// 		BoneWingLeft4.yRot = 0;
// 		BoneWingRight1.zRot = 0;
// 		BoneWingRight1.yRot = 0;
// 		BoneWingRight2.yRot = 0;
// 		BoneWingRight3.yRot = 0;
// 		BoneWingRight4.yRot = 0;
// 		BoneHeadFeather1.xRot  = 0;
// 		BoneHeadFeather2.xRot  = 0;
// 		BoneHeadFeather3.xRot  = 0;
// 		BoneHeadFeather4.xRot  = 0;
// 		BoneHeadFeather5.xRot  = 0;
// 		BoneHeadFeather6.zRot  = 0;
// 		BoneHeadFeather7.zRot  = 0;
// 		BoneHeadFeather8.zRot  = 0;
// 		BoneHeadFeather9.zRot  = 0;
// 		BoneHeadFeather10.zRot = 0;
// 		BoneHeadFeather11.zRot = 0;
// 		BoneWingLeft1.xRot = 0;
// 		BoneWingLeft2.xRot = 0;
// 		BoneWingLeft3.xRot = 0;
// 		BoneWingLeft4.xRot = 0;
// 		BoneWingRight1.xRot = 0;
// 		BoneWingRight2.xRot = 0;
// 		BoneWingRight3.xRot = 0;
// 		BoneWingRight4.xRot = 0;
// 		BoneBase.xRot =  0;
// 		BoneNeck.xRot =  0;
// 		BoneHead.xRot =   0;
// 		BoneLegLeft.xRot = 0;
// 		BoneFootLeft.xRot = 0;
// 		BoneTalonLeft.xRot = 0;
// 		BoneWingLeft1.xRot = 0;
// 		BoneLegRight.xRot = 0;
// 		BoneFootRight.xRot = 0;
// 		BoneTalonRight.xRot = 0;
// 		BoneWingRight1.xRot = 0;
// 	}
//
// 	private void SetLegRotation(T entity){
// 		// float height = 4;
// 		// float rot = 2 * ROT;
// 		// int mod = 0;
// 		//
// 		// if(entity.AnimSit()){
// 		// 	mod = 16;
// 		// } else {
// 		// 	mod = 4;
// 		// }
// 		// float baseHeight = 0.75f;
// 		// BoneLegLeft.xRot = rot * mod;
// 		// BoneFootLeft.xRot = - (rot * 2) * mod;
// 		// BoneTalonLeft.xRot = rot * mod;
// 		// BoneLegRight.xRot = rot * mod;
// 		// BoneFootRight.xRot = - (rot * 2) * mod;
// 		// BoneTalonRight.xRot = rot * mod;
// 		// BoneBase.y = height + (baseHeight * mod);
// 	}
//
// 	@Override
// 	protected Iterable<ModelPart> headParts() {
// 		return ImmutableList.of(this.BoneNeck);
// 	}
//
// 	@Override
// 	protected Iterable<ModelPart> bodyParts() {
// 		return ImmutableList.of(this.BoneBody);
// 	}
//
//
//
// }