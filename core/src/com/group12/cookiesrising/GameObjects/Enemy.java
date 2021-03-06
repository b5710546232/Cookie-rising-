package com.group12.cookiesrising.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group12.cookiesrising.Health;
import com.group12.cookiesrising.Hittable;
import com.group12.cookiesrising.State.AliveState;
import com.group12.cookiesrising.State.DeathState;
import com.group12.cookiesrising.State.State;
import com.group12.cookiesrising.util.Assets;
import com.group12.cookiesrising.util.SaveManager;

/**
 * Created by nattapat on 5/6/2016 AD.
 */
public class Enemy extends AbstractGameObject implements Health,Hittable {
    private Animation anim_current;
    private Animation anim_idle;
    private Animation anim_die;
    private Animation anim_att;
    private Animation anim_hitted;


    public static final String TAG = Enemy.class.getName();
    public double healthPoint;

    public void setMaxHealthPoint(double maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
    }

    public double maxHealthPoint;
    public boolean waitForSpawn;
    public double getAttackPoint() {
        return attackPoint;
    }
    private String name;
    public double attackPoint;
    private float speed;
    public void setMoney(double money) {
        this.money = money;
    }

    public void setAttackPoint(double attackPoint) {
        this.attackPoint = attackPoint;
    }

    public double money;
    protected State currentState,deathState,aliveState;
    private float stateTime;
    private boolean isHited;

    public EnemyUpdater getUpdater() {
        return updater;
    }

    private EnemyUpdater updater;
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getDeathState() {
        return deathState;
    }

    public State getAliveState() {
        return aliveState;
    }

    public Enemy() {
        deathState = new DeathState(this);
        aliveState = new AliveState(this);
        currentState = aliveState;
        updater = new EnemyUpdater(this);
        init();
        loadData();
    }
    public Animation getAnim_idle() {
        return anim_idle;
    }

    public Animation getAnim_die() {
        return anim_die;
    }

    public Animation getAnim_att() {
        return anim_att;
    }

    public Animation getAnim_hitted() {
        return anim_hitted;
    }

    public void init(){
        waitForSpawn = true;
        healthPoint = maxHealthPoint;
        currentState = aliveState;
        name = "Cookie Ung";
        stateTime = 0;
        isHited = false;
        isActive = true;
        loadAnimation();
        setAnimation(anim_idle);
    }

    public void loadAnimation(){
        EnemyDataAnimation dataAnim = EnemyData.instance.getEnemyDataAnimation();
        anim_att = dataAnim.getAttack();
        anim_idle = dataAnim.getidle();
        anim_die = dataAnim.getDie();
        anim_hitted = dataAnim.getHiited();
    }

    public String getName() {
        return name;
    }

    public double getMoney(){
        return money;
    }

    public void takeDamage(double dmg){

        currentState.takeDamage(dmg);
    }

    public void changeState(){
        currentState.changeState();
    }

    public void action(Hittable h){
        currentState.action(h);
    }

    public void respawn(){
        currentState.respawn();
    }

    public boolean isAlive(){
        return currentState.isAlive();
    }

    public boolean waitForSpawn(){
        if(waitForSpawn){
            waitForSpawn = false;
            return true;
        }
        return waitForSpawn;
    }

    @Override
    public void update(float delta) {

        stateTime += delta;
        if(anim_current.equals(Assets.anim_enemy01_die)){
            if(anim_current.isAnimationFinished(stateTime)){
                isActive = false;
            }
        }
        if(!anim_current.getPlayMode().equals(Animation.PlayMode.LOOP)){
            if(anim_current.isAnimationFinished(stateTime)){
                setAnimation(anim_idle);
                if(isHited){
                    isHited = false;
                }

            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(isActive) {
            batch.draw(anim_current.getKeyFrame(stateTime), 400, 136);
        }
    }

    @Override
    public double getHp() {
        return healthPoint;
    }

    @Override
    public double getMaxHp() {
        return maxHealthPoint;
    }
    public void setAnimation(Animation anim){
        stateTime = 0;
        this.anim_current = anim;
    }
    public boolean isActive(){ return isActive;}
    public void hitted(){
        isHited = true;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if (speed<=0) speed = 1;
        this.speed = speed;
    }

    private void loadData(){
        if(SaveManager.loadDataValue("enemy_money",double.class) != null){
            money = SaveManager.loadDataValue("enemy_money",double.class);
        } else{
            money = 100;
        }
        if(SaveManager.loadDataValue("enemy_attackpoint",double.class)!=null){
            attackPoint = SaveManager.loadDataValue("enemy_attackpoint",double.class);
        } else{
            attackPoint = 1;
        }
        if(SaveManager.loadDataValue("enemy_maxhealpoint",double.class)!=null){
            maxHealthPoint = SaveManager.loadDataValue("enemy_maxhealpoint",double.class);
            Gdx.app.error("enemy load"," hpmax = "+getMaxHp());
        } else{
            maxHealthPoint = 10;
        }
        if(SaveManager.loadDataValue("enemy_speed",float.class)!=null){
            speed = SaveManager.loadDataValue("enemy_speed",float.class);
        } else{
            speed = 3f;
        }
        healthPoint = maxHealthPoint;
    }

    public boolean isHited() {
        return isHited;
    }
    public void saveData(){
        SaveManager.saveDataValue("enemy_money",getMoney());
        SaveManager.saveDataValue("enemy_attackpoint",getAttackPoint());
        SaveManager.saveDataValue("enemy_maxhealpoint",getMaxHp());
        SaveManager.saveDataValue("enemy_speed",getSpeed());
        SaveManager.saveDataValue("enemy_name",getName());

        Gdx.app.error("enemy save"," hpmax = "+getMaxHp());
//        ObjectMapWrapper wrapper = new ObjectMapWrapper();
//        ObjectMap<String,Object> data = wrapper.data;
//        data.put("enemy_money",getMoney());
//        data.put("enemy_attackpoint",getAttackPoint());
//        data.put("enemy_healpoint",getMaxHp());
//        data.put("enemy_speed",getSpeed());
//        SaveManager.saveDataValue("enemy_data",data);

    }
}
