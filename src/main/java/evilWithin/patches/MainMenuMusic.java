package evilWithin.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;

@SpirePatch(clz = MainMusic.class, method = "getSong")
public class MainMenuMusic {
    @SpirePostfixPatch
    public static Music Postfix(Music __result, MainMusic __instance, String key) {
        switch (key) {
            case "MENU": {
                return MainMusic.newMusic("evilWithinResources/music/MenuMusic.mp3");
            }
            default: {
                return __result;
            }
        }

    }

}