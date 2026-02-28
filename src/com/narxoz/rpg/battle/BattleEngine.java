package com.narxoz.rpg.battle;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {}

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }
    public void reset() {}

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        EncounterResult result = new EncounterResult();
        int round = 0;
        List<Combatant> sideA = new ArrayList<>(teamA);
        List<Combatant> sideB = new ArrayList<>(teamB);

        result.addLog("=== Battle Start ===");

        while(!sideA.isEmpty() && !sideB.isEmpty()){
            round++;
            result.addLog("\nRound " + round);

            executePhase(sideA,sideB,result);
            sideB.removeIf(c -> !c.isAlive());

            if (!sideB.isEmpty()){
                executePhase(sideB,sideA,result);
                sideA.removeIf(c -> !c.isAlive());
            }
            if (round >= 100) break;
        }

        result.setRounds(round);
        result.setWinner(sideA.isEmpty() ? "Enemies" : "Heroes");
        result.addLog("\nWinner determined: " + result.getWinner());
        return result;
    }

    private void executePhase(List<Combatant> attackers, List<Combatant> defenders, EncounterResult result){
        for (Combatant attacker : attackers){
            defenders.removeIf(c -> !c.isAlive());
            if(defenders.isEmpty()) break;

                Combatant target = defenders.get(0);
                int dmg = attacker.getAttackPower();
                target.takeDamage(dmg);
                result.addLog(attacker.getName()+ " hits " + target.getName() + " for " + dmg + " DMG.");

                if (!target.isAlive())
                    result.addLog(target.getName() + " has fallen.");
            }
        }
    }