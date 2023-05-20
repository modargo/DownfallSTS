package collector.cards;

import collector.powers.ReservePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Ember extends AbstractCollectorCard {
    public final static String ID = makeID(Ember.class.getSimpleName());
    // intellij stuff skill, none, special, , , , , 1, 1

    public Ember() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        applyToSelf(new ReservePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}