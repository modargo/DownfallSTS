package hermit.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hermit.powers.Bounty;
import hermit.powers.Rugged;
import hermit.rewards.BountyGold;

import java.util.Iterator;
import java.util.UUID;

public class DeadOrAliveAction extends AbstractGameAction {
    private int increaseAmount;
    private DamageInfo info;
    private UUID uuid;
    private AbstractCard c;

    public DeadOrAliveAction(AbstractCreature target, AbstractPlayer p, AbstractCard c) {
        this.actionType = ActionType.DAMAGE;
        this.c = c;
        this.target = target;
        this.source = p;
        this.amount = 0;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null && !(this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX+MathUtils.random(-8f,8f), this.target.hb.cY+MathUtils.random(-8f,8f), AttackEffect.BLUNT_LIGHT));
            this.c.calculateCardDamage((AbstractMonster)this.target);
            this.info = new DamageInfo(this.source, this.c.damage, this.c.damageTypeForTurn);
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                int amount = 15;

                if ((AbstractDungeon.getCurrRoom()).eliteTrigger)
                    amount = 40;

                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (m.type == AbstractMonster.EnemyType.BOSS)
                        amount = 100;
                }

                AbstractDungeon.player.gainGold(this.amount);

                for(int i = 0; i < this.amount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                }

                //AbstractDungeon.getCurrRoom().rewards.add(new BountyGold(amount));
                //this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Bounty(AbstractDungeon.player, amount)));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

        }

        this.tickDuration();
    }
}
