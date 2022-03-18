package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.powers.FocusedDefPower;
import champ.util.OnFinisherSubscriber;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import sneckomod.SneckoMod;

public class FocusedDefense extends AbstractChampCard {

    public final static String ID = makeID("FocusedDefense");

    //stupid intellij stuff skill, self, common

    public FocusedDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        baseBlock = 5;
        baseMagicNumber = magicNumber = 5;

        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new VigorPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}