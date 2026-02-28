package com.narxoz.rpg;

import com.narxoz.rpg.adapter.*;
import com.narxoz.rpg.enemy.*;
import com.narxoz.rpg.hero.*;
import com.narxoz.rpg.battle.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Battle Engine Demo ===\n");

        BattleEngine engine1 = BattleEngine.getInstance();
        BattleEngine engine2 = BattleEngine.getInstance();
        System.out.println("Singleton Check: Same instance? " + (engine1 == engine2) );

        Warrior warrior = new Warrior("Arthas");
        Mage mage = new Mage("Jaina");
        Goblin goblin = new Goblin();


        List<Combatant> heroes = new ArrayList<>();
        heroes.add(new HeroCombatantAdapter(warrior));
        heroes.add(new HeroCombatantAdapter(mage));

        List<Combatant> enemies = new ArrayList<>();
        enemies.add(new EnemyCombatantAdapter(goblin));


        BattleEngine engineA = BattleEngine.getInstance();
        BattleEngine engineB = BattleEngine.getInstance();
        System.out.println("Same instance? " + (engineA == engineB));
        System.out.println();


        engineA.setRandomSeed(42L);
        EncounterResult result = engineA.runEncounter(heroes, enemies);

        System.out.println("\n--- BATTLE RECAP ---");
        for (String log : result.getBattleLog()){
            System.out.println(log);
        }
        System.out.println("\nWinner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println("\n=== Demo Complete ===");
    }
}
