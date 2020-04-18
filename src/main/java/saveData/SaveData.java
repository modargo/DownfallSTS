package saveData;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import evilWithin.EvilWithinMod;
import evilWithin.events.Cleric_Evil;
import evilWithin.monsters.FleeingMerchant;
import evilWithin.patches.EvilModeCharacterSelect;
import evilWithin.patches.ui.campfire.AddBustKeyButtonPatches;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {
    public static final String EVIL_MODE_KEY = "EVIL_MODE";
    public static final String RED_KEY_CONSUMED = "RED_KEY_CONSUMED";
    public static final String GREEN_KEY_CONSUMED = "GREEN_KEY_CONSUMED";
    public static final String BLUE_KEY_CONSUMED = "BLUE_KEY_CONSUMED";
    public static final String KILLED_CLERIC = "KILLED_CLERIC";
    public static final String ENCOUNTERED_CLERIC = "ENCOUNTERED_CLERIC";
    public static final String UPCOMING_BOSSES = "UPCOMING_BOSSES";
    public static final String MERCHANT_HEALTH = "MERCHANT_HEALTH";
    public static final String MERCHANT_STRENGTH = "MERCHANT_STRENGTH";
    public static final String MERCHANT_SOULS = "MERCHANT_SOULS";
    public static final String MERCHANT_DEAD = "MERCHANT_DEAD";
    private static Logger saveLogger = LogManager.getLogger("EvilWithinSaveData");
    //data is stored here in addition to the actual location
    //when data is "saved" it is saved here, and written to the actual save file slightly later
    private static boolean evilMode;

    private static boolean consumedRed;
    private static boolean consumedGreen;
    private static boolean consumedBlue;

    private static boolean killedCleric;
    private static boolean encounteredCleric;

    private static ArrayList<String> myVillains;

    private static int merchantHealth;
    private static int merchantStrength;
    private static int merchantSouls;
    private static boolean merchantDead;

    //Save data whenever SaveFile is constructed
    @SpirePatch(
            clz = SaveFile.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {SaveFile.SaveType.class}
    )
    public static class SaveTheSaveData {
        @SpirePostfixPatch
        public static void saveAllTheSaveData(SaveFile __instance, SaveFile.SaveType type) {
            evilMode = EvilModeCharacterSelect.evilMode;

            consumedRed = AddBustKeyButtonPatches.KeyFields.bustedRuby.get(AbstractDungeon.player);
            consumedGreen = AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(AbstractDungeon.player);
            consumedBlue = AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(AbstractDungeon.player);

            killedCleric = Cleric_Evil.heDead;
            encounteredCleric = Cleric_Evil.encountered;

            myVillains = EvilWithinMod.possEncounterList;

            merchantHealth = FleeingMerchant.CURRENT_HP;
            merchantStrength = FleeingMerchant.CURRENT_STRENGTH;
            merchantSouls = FleeingMerchant.CURRENT_SOULS;
            merchantDead = FleeingMerchant.DEAD;

            saveLogger.info("Saved Evil Mode: " + evilMode);
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "save",
            paramtypez = {SaveFile.class}
    )
    public static class SaveDataToFile {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"params"}
        )
        public static void addCustomSaveData(SaveFile save, HashMap<Object, Object> params) {
            params.put(EVIL_MODE_KEY, evilMode);
            params.put(RED_KEY_CONSUMED, consumedRed);
            params.put(GREEN_KEY_CONSUMED, consumedGreen);
            params.put(BLUE_KEY_CONSUMED, consumedBlue);
            params.put(KILLED_CLERIC, killedCleric);
            params.put(ENCOUNTERED_CLERIC, encounteredCleric);
            params.put(UPCOMING_BOSSES, myVillains);
            params.put(MERCHANT_HEALTH, merchantHealth);
            params.put(MERCHANT_STRENGTH, merchantStrength);
            params.put(MERCHANT_SOULS, merchantSouls);
            params.put(MERCHANT_DEAD, merchantDead);
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(GsonBuilder.class, "create");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "loadSaveFile",
            paramtypez = {String.class}
    )
    public static class LoadDataFromFile {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"gson", "savestr"}
        )
        public static void loadCustomSaveData(String path, Gson gson, String savestr) {
            try {
                EvilWithinSaveData data = gson.fromJson(savestr, EvilWithinSaveData.class);

                evilMode = data.EVIL_MODE;

                consumedRed = data.RED_KEY_CONSUMED;
                consumedGreen = data.GREEN_KEY_CONSUMED;
                consumedBlue = data.BLUE_KEY_CONSUMED;

                killedCleric = data.KILLED_CLERIC;
                encounteredCleric = data.ENCOUNTERED_CLERIC;

                myVillains = data.UPCOMING_BOSSES;

                merchantHealth = data.MERCHANT_HEALTH;
                merchantStrength = data.MERCHANT_STRENGTH;
                merchantDead = data.MERCHANT_DEAD;

                saveLogger.info("Loaded EvilWithin save data successfully.");
            } catch (Exception e) {
                saveLogger.error("Failed to load EvilWithin save data.");
                e.printStackTrace();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(Gson.class, "fromJson");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    //Ensure data is loaded/generated
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "loadSave"
    )
    public static class loadSave {
        @SpirePostfixPatch
        public static void loadSave(AbstractDungeon __instance, SaveFile file) {
            //Some data, after loading into this file, will need to actually be assigned here.
            //When the game loads, the SaveFile is instantiated first, which loads data from save into itself.
            //AbstractDungeon then loads that data from the SaveFile.

            AddBustKeyButtonPatches.KeyFields.bustedRuby.set(AbstractDungeon.player, consumedRed);
            AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(AbstractDungeon.player, consumedGreen);
            AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(AbstractDungeon.player, consumedBlue);

            Cleric_Evil.heDead = killedCleric;
            Cleric_Evil.encountered = encounteredCleric;

            EvilWithinMod.possEncounterList = myVillains;

            FleeingMerchant.CURRENT_HP = merchantHealth;
            FleeingMerchant.CURRENT_STRENGTH = merchantStrength;
            FleeingMerchant.DEAD = merchantDead;

            saveLogger.info("Save loaded.");
            //Anything that triggers on load goes here
        }
    }
}
