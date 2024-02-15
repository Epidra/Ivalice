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

import static mod.ivalice.Ivalice.MODID;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class ModelChick<T extends EntityChocobo> extends EntityModel<T> {
	
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "chocochick"), "main");
	private final ModelPart BoneBase;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ModelChick(ModelPart root) {
		this.BoneBase = root.getChild("BoneBase");
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition BoneBase = partdefinition.addOrReplaceChild("BoneBase", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		
		PartDefinition BoneHead = BoneBase.addOrReplaceChild("BoneHead", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -7.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 14).addBox(-0.5F, -5.1F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneWingLeft = BoneBase.addOrReplaceChild("BoneWingLeft", CubeListBuilder.create().texOffs(0, 4).addBox(1.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneWingRight = BoneBase.addOrReplaceChild("BoneWingRight", CubeListBuilder.create().texOffs(6, 6).addBox(-2.75F, -4.5F, -1.75F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonLeft = BoneBase.addOrReplaceChild("BoneTalonLeft", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonLeft1 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft1", CubeListBuilder.create().texOffs(12, 1).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		
		PartDefinition BoneTalonLeft2 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft2", CubeListBuilder.create().texOffs(12, 1).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		
		PartDefinition BoneTalonLeft3 = BoneTalonLeft.addOrReplaceChild("BoneTalonLeft3", CubeListBuilder.create().texOffs(12, 1).addBox(-0.6F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonRight = BoneBase.addOrReplaceChild("BoneTalonRight", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		
		PartDefinition BoneTalonRight1 = BoneTalonRight.addOrReplaceChild("BoneTalonRight1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, -0.1309F, 0.0F));
		
		PartDefinition BoneTalonRight2 = BoneTalonRight.addOrReplaceChild("BoneTalonRight2", CubeListBuilder.create().texOffs(0, 1).addBox(-1.0F, -0.25F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, -1.1345F, 0.1309F, 0.0F));
		
		PartDefinition BoneTalonRight3 = BoneTalonRight.addOrReplaceChild("BoneTalonRight3", CubeListBuilder.create().texOffs(0, 1).addBox(-0.3F, -0.25F, -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.75F, 0.0F, 1.1345F, 0.0F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 16, 16);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ANIMATION  ---------- ---------- ---------- ---------- //
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		BoneBase.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}