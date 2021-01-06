package downfall.patches;

import automaton.AutomatonChar;
import champ.ChampChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import downfall.cards.curses.*;
import downfall.downfallMod;
import downfall.relics.Hecktoplasm;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.cards.*;
import expansioncontent.relics.StudyCardRelic;
import guardian.characters.GuardianCharacter;
import guardian.potions.BlockOnCardUsePotion;
import guardian.relics.BottledAnomaly;
import guardian.relics.GemstoneGun;
import guardian.relics.PocketSentry;
import slimebound.characters.SlimeboundCharacter;
import slimebound.potions.ThreeZeroPotion;
import slimebound.relics.PreparedRelic;
import slimebound.relics.StickyStick;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.potions.MuddlingPotion;
import sneckomod.relics.BlankCard;
import sneckomod.relics.SneckoTalon;
import sneckomod.relics.SuperSneckoEye;
import theHexaghost.TheHexaghost;
import theHexaghost.potions.SoulburnPotion;
import theHexaghost.relics.BolsterEngine;
import theHexaghost.relics.CandleOfCauterizing;
import theHexaghost.relics.Sixitude;

public class BanSharedContentPatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class CardPatch {
        public static void Postfix(AbstractDungeon __instance) {
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_colorlessCards) {
                AbstractDungeon.colorlessCardPool.removeCard(AwakenDeath.ID);
                AbstractDungeon.colorlessCardPool.removeCard(BronzeBeam.ID);
                AbstractDungeon.colorlessCardPool.removeCard(CaCaw.ID);
                AbstractDungeon.colorlessCardPool.removeCard(ChargeUp.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Chronoboost.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Collect.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Corrupt.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DarkVoid.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DashGenerateEvil.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DecasProtection.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DefensiveMode.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DefensiveStance.ID);
                AbstractDungeon.colorlessCardPool.removeCard(DonusPower.ID);
                AbstractDungeon.colorlessCardPool.removeCard(FaceSlap.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Flail.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SuperHexaguard.ID);
                AbstractDungeon.colorlessCardPool.removeCard(GoopSpray.ID);
                AbstractDungeon.colorlessCardPool.removeCard(GuardianWhirl.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Hexaburn.ID);
                AbstractDungeon.colorlessCardPool.removeCard(HyperBeam.ID);
                AbstractDungeon.colorlessCardPool.removeCard(LastStand.ID);
                AbstractDungeon.colorlessCardPool.removeCard(ManipulateTime.ID);
                AbstractDungeon.colorlessCardPool.removeCard(PolyBeam.ID);
                AbstractDungeon.colorlessCardPool.removeCard(PrepareCrush.ID);
                AbstractDungeon.colorlessCardPool.removeCard(QuickStudy.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Sear.ID);
                AbstractDungeon.colorlessCardPool.removeCard(SlimeTackle.ID);
                AbstractDungeon.colorlessCardPool.removeCard(StudyTheSpire.ID);
                AbstractDungeon.colorlessCardPool.removeCard(TimeRipple.ID);
                AbstractDungeon.colorlessCardPool.removeCard(Torchfire.ID);
                AbstractDungeon.colorlessCardPool.removeCard(YouAreMine.ID);

                AbstractDungeon.curseCardPool.removeCard(Aged.ID);
                AbstractDungeon.curseCardPool.removeCard(Icky.ID);
                AbstractDungeon.curseCardPool.removeCard(Bewildered.ID);
                AbstractDungeon.curseCardPool.removeCard(Haunted.ID);
                AbstractDungeon.curseCardPool.removeCard(PrideStandard.ID);
                AbstractDungeon.curseCardPool.removeCard(Malfunctioning.ID);
            } else {
                if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                    AbstractDungeon.colorlessCardPool.removeCard(PrepareCrush.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(SlimeTackle.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(GoopSpray.ID);
                }
                if (AbstractDungeon.player instanceof TheHexaghost || RandomCardWithTagAction.hexaLocked()) {
                    AbstractDungeon.colorlessCardPool.removeCard(SuperHexaguard.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(Sear.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(Hexaburn.ID);
                }
                if (AbstractDungeon.player instanceof GuardianCharacter || RandomCardWithTagAction.guardianLocked()) {
                    AbstractDungeon.colorlessCardPool.removeCard(ChargeUp.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(GuardianWhirl.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(DefensiveMode.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(BronzeBeam.ID);
                }
                if (AbstractDungeon.player instanceof ChampChar || RandomCardWithTagAction.champLocked()) {
                    AbstractDungeon.colorlessCardPool.removeCard(FaceSlap.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(DefensiveStance.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(LastStand.ID);
                }
                if (AbstractDungeon.player instanceof AutomatonChar || RandomCardWithTagAction.autoLocked()) {
                    AbstractDungeon.colorlessCardPool.removeCard(Flail.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(BronzeBeam.ID);
                    AbstractDungeon.colorlessCardPool.removeCard(HyperBeam.ID);
                }
            }

            if (AbstractDungeon.player instanceof TheSnecko) {
                if (SneckoMod.validColors != null && !SneckoMod.pureSneckoMode) {
                    AbstractDungeon.commonCardPool.group.removeIf(c -> c instanceof UnknownClass && !SneckoMod.validColors.contains(((UnknownClass) c).myColor));
                }
            }
        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeRelicList"
    )
    public static class RelicPatch {

        public static void Prefix(AbstractDungeon __instance) {
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_colorlessCards) {
                AbstractDungeon.relicsToRemoveOnStart.add(GemstoneGun.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(PocketSentry.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(BottledAnomaly.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(PreparedRelic.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(StickyStick.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(StudyCardRelic.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(SuperSneckoEye.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(SneckoTalon.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(BlankCard.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(BolsterEngine.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(CandleOfCauterizing.ID);
                AbstractDungeon.relicsToRemoveOnStart.add(Sixitude.ID);
            }
            if (EvilModeCharacterSelect.evilMode) {
                AbstractDungeon.relicsToRemoveOnStart.add(Ectoplasm.ID);
            } else {
                AbstractDungeon.relicsToRemoveOnStart.add(Hecktoplasm.ID);
            }
        }
    }

    @SpirePatch(
            clz = PotionHelper.class,
            method = "initialize"
    )
    public static class PotionPatch {
        public static void Postfix(AbstractPlayer.PlayerClass chosenClass) {
            if (!EvilModeCharacterSelect.evilMode && !downfallMod.contentSharing_potions) {
                PotionHelper.potions.remove(SoulburnPotion.POTION_ID);
                PotionHelper.potions.remove(MuddlingPotion.POTION_ID);
                PotionHelper.potions.remove(ThreeZeroPotion.POTION_ID);
                PotionHelper.potions.remove(BlockOnCardUsePotion.POTION_ID);
            }
        }
    }
}