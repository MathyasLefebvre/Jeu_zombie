package com.company.zombieGame;

import com.company.engine.*;
import com.company.engine.entities.StaticEntity;
import com.company.zombieGame.perks.Marathon;
import com.company.zombieGame.perks.Mastodonte;
import com.company.zombieGame.perks.DoubleTap;
import com.company.zombieGame.perks.PackPunch;
import com.company.zombieGame.world.Clouds;
import com.company.zombieGame.world.Map;

import java.awt.*;
import java.util.ArrayList;

public class ZombieGame extends Game {

    private final int PERK_PRICE = 2000;
    private GamePad gamePad;
    private ArrayList<BuyingPoint> buyingPoints;
    private Map map;
    private Clouds clouds;
    private ArrayList<Perk> perks;
    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Opponent> opponents;
    private Drop[] d = new Drop[150];
    private GameOver endGame;
    private int cooldown = 100;
    private int round = 0;
    private int numberZombie = 0;
    private int deathCounter = 0;
    private boolean messageOn = false;
    private boolean specialRound = false;
    private int buyCooldown = 0;
    private int perkIndex;
    private int counter = 0;
    private int startSoundOnce = 0;
    private boolean gameOver = false;
    private boolean marathonAvailable = true;
    private boolean doubleTapAvailable = true;
    private boolean mastodonteAvailable = true;
    private boolean packPunchAvailable = true;

    @Override
    public void initialize() {
        gamePad = new GamePad();
        map = new Map();
        map.load();
        Music.MUSIC.start();
        player = new Player(gamePad);
        player.teleport(200, 300);
        bullets = new ArrayList<>();
        opponents = new ArrayList<>();
        clouds = new Clouds();
        endGame = new GameOver();
        initializePerks();
        initializeBuyingPoints();
        for (int i = 0; i < d.length; i++) {
            d[i] = new Drop();
        }
    }

    @Override
    public void update() {
        resetPerkDrawOrder();
        if (gamePad.isQuitPressed()) {
            stop();
        }
        setFullScreen();
        if (isPlayerAlive() && round < 100) {
            updateGame();
        }else {
            gameOver();
            cooldown();
            if (canQuit()) {
                stop();
            }
        }
        updatePerkDrawOrder();
    }

    @Override
    public void draw(Buffer buffer) {
        if (!gameOver) {
            drawGameComponent(buffer);
        }else {
            endGame.draw(buffer);
        }
    }

    @Override
    public void conclude() {

    }

    private void drawGameComponent(Buffer buffer) {
        map.draw(buffer);
        drawBuyingPoint(buffer);
        drawPerkBefore(buffer);
        player.draw(buffer);
        drawPerkAfter(buffer);
        drawBullets(buffer);
        drawOpponent(buffer);
        if (messageOn) {
            displayPerkPrice(buffer);
        }
        drawSpecialRound(buffer);
        GameInformation.getInstance().draw(buffer, round, player);
    }

    private void updateGame() {
        player.update();
        Camera.getInstance().position(player);
        GameInformation.getInstance().updateInformation(Camera.getInstance().getX(), Camera.getInstance().getY());
        updateOpponent();
        checkPerkBlockade();
        perkSystem();
        cooldownBuy();
        specialRoundUpdate();
        playerFire();
        ArrayList<StaticEntity> killedEntity = new ArrayList<>();
        fighting(killedEntity);
        addDeathEntity(killedEntity);
        bulletHitBlockade(killedEntity);
        newRound();
    }

    private void resetPerkDrawOrder() {
        for (Perk perk : perks) {
            perk.setDrawBeforePlayer(false);
        }
    }

    private void drawOpponent(Buffer buffer) {
        for (Opponent opponent : opponents) {
            opponent.draw(buffer);
        }
    }

    private void drawSpecialRound(Buffer buffer) {
        if (specialRound) {
            clouds.draw(buffer);
            for (Drop drop : d) {
                drop.draw(buffer);
            }
        }
    }

    private void drawBullets(Buffer buffer) {
        for (Bullet bullet : bullets) {
            isGunUpgrade(bullet);
            bullet.draw(buffer);
        }
    }

    private void drawBuyingPoint(Buffer buffer) {
        for (BuyingPoint buyingPoint: buyingPoints) {
            buyingPoint.draw(buffer);
        }
    }

    private void playerFire() {
        if (gamePad.isFirePressed() && player.canFire()) {
            bullets.add(player.fire());
        }
    }

    public void spawn() {
        if (specialRound && deathCounter == numberZombie) {
            Music.RAIN.stop();
            specialRound = false;
        }
        numberZombie += 2;
        deathCounter = 0;
        typeOfOpponent();
    }

    private void typeOfOpponent() {
        for (int i = 0; i < numberZombie; i ++) {
            if (round % 4 == 0) {
                opponents.add(new Boss(player));
                specialRound = true;
            }
            if (round % 4 != 0) {
                opponents.add(new Zombie(player));
            }
        }
    }

    private void updateOpponent() {
        for (Opponent opponent : opponents) {
            opponent.update();
        }
    }

    private void perkSystem() {
        messageOn = false;
        for (BuyingPoint buyingPoint: buyingPoints) {
            boolean canBuy = buyingPoint.canBuy(buyingPoint.getX(), buyingPoint.getY(), player);
            if (canBuy) {
                messageOn = true;
                buyPerk(buyingPoint);
            }
        }
    }

    private void addDeathEntity(ArrayList<StaticEntity> killedEntity) {
        for (StaticEntity entity : killedEntity) {
            if (entity instanceof Opponent) {
                opponents.remove(entity);
            }
            if (entity instanceof Bullet) {
                bullets.remove(entity);
            }
            CollatableRepository.getInstance().unregisteredEntity(entity);
        }
    }

    private void newRound() {
        if (deathCounter == numberZombie) {
            round++;
            spawn();
        }
    }

    private void checkPerkBlockade() {
        for (Perk perk : perks) {
            if (player.getY() < perk.getY() + 52) {
                perk.blockadeFromTop();
            }else {
                perk.blockadeFromBottom();
            }

        }
    }

    private void updatePerkDrawOrder() {
        for (Perk perk : perks) {
            if (!(player.getY() < perk.getY() + 52)) {
                perk.setDrawBeforePlayer(true);
            }
        }
    }

    private void drawPerkBefore(Buffer buffer) {
        for (Perk perk : perks) {
            if (perk.isDrawBeforePlayer()) {
                perk.draw(buffer);
            }
        }
    }

    private void drawPerkAfter(Buffer buffer) {
        for (Perk perk : perks) {
            if (!perk.isDrawBeforePlayer()) {
                perk.draw(buffer);
            }
        }
    }



    private boolean isPlayerAlive() {
        return player.getHealth() >= 0;
    }

    private void fighting(ArrayList<StaticEntity> killedEntity) {
        for (Bullet bullet : bullets) {
            bullet.update();
            for (Opponent opponent : opponents) {
                if (bullet.hitBoxIntersection(opponent)) {
                    opponent.health = opponent.health - player.getDamage();
                    killedEntity.add(bullet);
                    if (opponent.health <= 0) {
                        zombieIsKilled(killedEntity, opponent);
                    }
                }
            }
        }
    }

    private void zombieIsKilled(ArrayList<StaticEntity> killedEntity, Opponent opponent) {
        killedEntity.add(opponent);
        deathCounter++;
        Sound.MARLOC.start();
        player.setMoney(player.getMoney() + 300);
    }

    private void specialRoundUpdate() {
        if (specialRound) {
            if (counter == 0) {
                Sound.LIGHTNING.start();
                Music.RAIN.start();
                counter++;
            }
            for (Drop drop : d) {
                drop.fall();
            }
        }
    }

    private void buyPerk(BuyingPoint buyingPoint) {
        if (playerIsInZone(buyingPoint)) {
            if (gamePad.isActionPressed()) {
                if (cantBuy()) {
                    if (setPerk()) {
                        Sound.BURP.start();
                        buyCooldown = 40;
                        return;
                    }
                    Sound.NOPE.start();
                }
            }
        }
    }

    private void setFullScreen() {
        if (gamePad.isWindowPressed()) {
            RenderingEngine.getInstance().getScreen().toggleFullscreen();
        }
    }

    private boolean setPerk() {
        if (player.getMoney() >= PERK_PRICE && mastodonteAvailable && perkIndex == 4) {
            mastodontePerk();
            return true;
        }
        if (player.getMoney() >= PERK_PRICE && doubleTapAvailable && perkIndex == 2) {
            doubleTapPerk();
            return true;
        }
        if (player.getMoney() >= PERK_PRICE && marathonAvailable && perkIndex == 1) {
            marathonPerk();
            return true;
        }
        if (player.getMoney() >= PERK_PRICE && packPunchAvailable && perkIndex == 3) {
            packPunchPerk();
            return true;
        }
        return false;
    }

    private void mastodontePerk() {
        player.setMoney(player.getMoney() - PERK_PRICE);
        mastodonteAvailable = false;
        player.setHealth(player.getHealth() + 100);
    }

    private void doubleTapPerk() {
        player.setMoney(player.getMoney() - PERK_PRICE);
        doubleTapAvailable = false;
        player.setShootTime(20);
    }

    private void marathonPerk() {
        player.setMoney(player.getMoney() - PERK_PRICE);
        player.setSpeed(6);
        marathonAvailable = false;
    }

    private void packPunchPerk() {
        player.setMoney(player.getMoney() - PERK_PRICE);
        packPunchAvailable = false;
        player.setDamage(60);
        player.setChangeGun(true);
    }

    private void initializePerks() {
        perks = new ArrayList<>();
        perks.add(new Marathon());
        perks.add(new Mastodonte());
        perks.add(new DoubleTap());
        perks.add(new PackPunch());
    }

    private void initializeBuyingPoints() {
        buyingPoints = new ArrayList<>();
        buyingPoints.add(new BuyingPoint(1380, 456));
        buyingPoints.add(new BuyingPoint(212, 731));
        buyingPoints.add(new BuyingPoint(816, 681));
        buyingPoints.add(new BuyingPoint(370, 1332));
    }

    private boolean canQuit() {
        return cooldown == 0;
    }

    private void cooldown() {
        cooldown--;
        if (cooldown <= 0) {
            cooldown = 0;
        }
    }

    private boolean playerIsInZone(BuyingPoint buyingPoint) {
        if (buyingPoint.getX() <= 212 && buyingPoint.getX() + 50 >= 260) {
            perkIndex = 1;
            return true;
        }
        if (buyingPoint.getX() <= 370 && buyingPoint.getX() + 50 >= 415) {
            perkIndex = 2;
            return true;
        }
        if (buyingPoint.getX() <= 816 && buyingPoint.getX() + 50 >= 860) {
            perkIndex = 3;
            return true;
        }
        if (buyingPoint.getX() <= 1380 && buyingPoint.getX() + 50 >= 1425) {
            perkIndex = 4;
            return true;
        }
        return false;
    }

    private void displayPerkPrice(Buffer buffer) {
        buffer.drawText("Voulez-vous acheter cette amélioration (2000$) ?", player.getX() - 100, player.getY() + 100, Color.WHITE);
    }

    private void gameOver() {
        gameOver = true;
        player.teleport(0, 0);
        Camera.getInstance().position(player);
        if (startSoundOnce == 0) {
            Sound.ENDING.start();
            startSoundOnce++;
        }
    }

    private void isGunUpgrade(Bullet bullet) {
        if (!packPunchAvailable) {
            bullet.setGunPackPunch(true);
        }
    }

    private void cooldownBuy() {
        buyCooldown--;
        if (buyCooldown <= 0) {
            buyCooldown = 0;
        }
    }

    private boolean cantBuy() {
        return buyCooldown == 0;
    }

    private void bulletHitBlockade(ArrayList<StaticEntity> killedEntity) {
        for (Blockade map : map.getWorldBorders()) {
            for (Bullet bullet : bullets) {
                if (bullet.hitBoxIntersection(map)) {
                    killedEntity.add(bullet);
                }
            }
        }
        for (StaticEntity entity : killedEntity) {
            if (entity instanceof Bullet) {
                bullets.remove(entity);
            }
            CollatableRepository.getInstance().unregisteredEntity(entity);
        }

    }
}
