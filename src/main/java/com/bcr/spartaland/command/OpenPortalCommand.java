package com.bcr.spartaland.command;

import com.bcr.spartaland.util.TpSelectorUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class OpenPortalCommand {

    public OpenPortalCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("openportal")
                .then(Commands.argument("name", MessageArgument.message()).executes((p_198539_0_) -> {
                    return openPortal(p_198539_0_.getSource(), MessageArgument.getMessage(p_198539_0_, "name"));
                })));
    }

    private static int openPortal(CommandSource source, ITextComponent name) {
        World world = source.getLevel();

        switch (name.getString()) {
            case "Pasarela":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.pasarela, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
            case "Vacio":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.vacio, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
            case "Desierto":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.desierto, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
            case "Hogwarts":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.hogwarts, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
            case "Undergarden":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.undergarden, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
            case "xxx":
                if (TpSelectorUtils.check(world)) {
                    world.setBlockAndUpdate(TpSelectorUtils.xxx, Blocks.REDSTONE_BLOCK.defaultBlockState());
                }
                break;
        }

        return 1;
    }
}
