package com.bcr.spartaland.screen;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.util.TpSelectorUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;

import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TpSelectorScreen extends Screen {
    PlayerEntity player;
    World world;

    public TpSelectorScreen(World world, PlayerEntity player) {
        super(ITextComponent.nullToEmpty(""));
        this.world = world;
        this.player = player;
    }

    protected void init() {
        TpSelectorUtils portal = new TpSelectorUtils();

        Button pasarela = this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 24, 200, 20, ITextComponent.nullToEmpty("Pasarela"), (p_213021_1_) -> {
            portal.pasarela();
            this.minecraft.setScreen((Screen) null);
        }));
        Button vacio = this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 48, 200, 20, ITextComponent.nullToEmpty("Vacio"), (p_213021_1_) -> {
            portal.vacio();
            this.minecraft.setScreen((Screen)null);
        }));
        Button desierto = this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 72, 200, 20, ITextComponent.nullToEmpty("Desierto"), (p_213021_1_) -> {
            portal.desierto();
            this.minecraft.setScreen((Screen)null);
        }));
        Button hogwarts = this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 96, 200, 20, ITextComponent.nullToEmpty("Hogwarts"), (p_213021_1_) -> {
            portal.hogwarts();
            this.minecraft.setScreen((Screen)null);
        }));
        Button undergarden = this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 120, 200, 20, ITextComponent.nullToEmpty("Undergarden"), (p_213021_1_) -> {
            portal.undergarden();
            this.minecraft.setScreen((Screen)null);
        }));

        if (player.getMainHandItem().getItem().getRegistryName().toString().equals("mahoutsukai:weapon_projectile_bow")) {
            Button xxx = this.addButton(new Button(this.width / 2 - 100, this.height - 40, 200, 20, ITextComponent.nullToEmpty("???"), (p_213021_1_) -> {
                portal.xxx();
                this.minecraft.setScreen((Screen)null);
            }));
            xxx.active = TpSelectorUtils.isActive(world, 5);
        }

        pasarela.active = TpSelectorUtils.isActive(world, 0);
        vacio.active = TpSelectorUtils.isActive(world, 1);
        desierto.active = TpSelectorUtils.isActive(world, 2);
        hogwarts.active = TpSelectorUtils.isActive(world, 3);
        undergarden.active = TpSelectorUtils.isActive(world, 4);
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    private void confirmResult(boolean p_213022_1_) {
        if (p_213022_1_) {
            this.exitToTitleScreen();
        } else {
            this.minecraft.player.respawn();
            this.minecraft.setScreen((Screen)null);
        }

    }

    private void exitToTitleScreen() {
        if (this.minecraft.level != null) {
            this.minecraft.level.disconnect();
        }

        this.minecraft.clearLevel(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        this.minecraft.setScreen(new MainMenuScreen());
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(p_230430_1_, this.font, this.title, this.width / 2 / 2, 30, 16777215);
        RenderSystem.popMatrix();

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Nullable
    private Style getClickedComponentStyleAt(int p_238623_1_) {
        return null;
    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
    }
}
