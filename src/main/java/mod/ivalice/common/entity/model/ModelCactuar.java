package mod.ivalice.common.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.ivalice.common.entity.EntityCactuar;
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

public class ModelCactuar<T extends EntityCactuar> extends EntityModel<T> {
	
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "cactuar"), "main");
	private final ModelPart BoneBase;
	private final ModelPart BoneAfro;
	private final ModelPart BoneFlower;
	private final ModelPart BoneHair;
	private final ModelPart BoneArmLeft;
	private final ModelPart BoneArmRight;
	private final ModelPart BoneLegLeft;
	private final ModelPart BoneLegRight;
	private final float baseHeight;
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CONSTRUCTOR  ---------- ---------- ---------- ---------- //
	
	public ModelCactuar(ModelPart root) {
		this.BoneBase     = root.getChild("BoneBase");
		this.BoneAfro     = root.getChild("BoneBase").getChild("BoneAfro");
		this.BoneHair     = root.getChild("BoneBase").getChild("BoneHair");
		this.BoneFlower   = root.getChild("BoneBase").getChild("BoneFlower");
		this.BoneArmLeft  = root.getChild("BoneBase").getChild("BoneArmLeft");
		this.BoneArmRight = root.getChild("BoneBase").getChild("BoneArmRight");
		this.BoneLegLeft  = root.getChild("BoneBase").getChild("BoneLegLeft");
		this.BoneLegRight = root.getChild("BoneBase").getChild("BoneLegRight");
		baseHeight = BoneBase.y;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  CREATE  ---------- ---------- ---------- ---------- //
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition BoneBase = partdefinition.addOrReplaceChild("BoneBase", CubeListBuilder.create().texOffs(0, 0).addBox(6.5F, -29.0F, -3.0F, 7.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 24.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		
		PartDefinition BoneAfro = BoneBase.addOrReplaceChild("BoneAfro", CubeListBuilder.create().texOffs(46, 9).addBox(2.0F, -30.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(11, 49).addBox(1.0F, -33.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(32, 45).addBox(2.0F, -34.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, 0.0F));
		
		PartDefinition BoneFlower = BoneBase.addOrReplaceChild("BoneFlower", CubeListBuilder.create().texOffs(48, 42).addBox(1.5F, -2.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(32, 51).addBox(-1.0F, -1.75F, -4.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -28.0F, 0.0F));
		
		PartDefinition BoneHair = BoneBase.addOrReplaceChild("BoneHair", CubeListBuilder.create().texOffs(0, 0).addBox(2.5F, -5.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(17, 31).addBox(4.0F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(21, 0).addBox(1.0F, -4.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -28.0F, 0.0F));
		
		PartDefinition BoneArmLeft = BoneBase.addOrReplaceChild("BoneArmLeft", CubeListBuilder.create().texOffs(13, 40).addBox(2.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 31).addBox(5.5F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -18.0F, 0.0F));
		
		PartDefinition BoneArmRight = BoneBase.addOrReplaceChild("BoneArmRight", CubeListBuilder.create().texOffs(36, 36).addBox(0.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(23, 27).addBox(-3.5F, -6.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -18.0F, 0.0F));
		
		PartDefinition BoneLegLeft = BoneBase.addOrReplaceChild("BoneLegLeft", CubeListBuilder.create().texOffs(36, 23).addBox(3.0F, 3.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(27, 0).addBox(3.0F, 5.0F, -2.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -8.0F, 0.0F));
		
		PartDefinition BoneLegRight = BoneBase.addOrReplaceChild("BoneLegRight", CubeListBuilder.create().texOffs(0, 44).addBox(1.0F, 3.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(27, 9).addBox(-3.0F, 3.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -12.5F, 0.0F));
		
		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  ANIMATION  ---------- ---------- ---------- ---------- //
	
	
	
	
	
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		BoneAfro.visible = false;
		BoneHair.visible = true;
		BoneFlower.visible = false;
		
		float xRot   = ageInTicks % 16 < 8 ? 180 : 0;
		float zRot   = ageInTicks % 16 < 8 ? -15 : 0;
		float height = ageInTicks % 16 < 8 ?   1 : 0;
		
		BoneArmLeft.xRot = xRot * ((float)Math.PI / 180F);;
		BoneArmRight.xRot = xRot * ((float)Math.PI / 180F);;
		BoneLegLeft.zRot = zRot * ((float)Math.PI / 180F);;
		BoneLegRight.zRot = zRot * ((float)Math.PI / 180F);;
		BoneBase.y = baseHeight + height;
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  RENDER  ---------- ---------- ---------- ---------- //
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		BoneBase.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SUPPORT  ---------- ---------- ---------- ---------- //
	
	// ...
	
	
	
}