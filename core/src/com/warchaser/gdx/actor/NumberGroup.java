package com.warchaser.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warchaser.gdx.actor.base.BaseGroup;
import com.warchaser.gdx.actor.framework.ImageActor;

import java.util.ArrayList;
import java.util.List;

public class NumberGroup extends BaseGroup {

    private TextureRegion[] mDigitRegions;

    private final List<ImageActor> mDigitActorList = new ArrayList<ImageActor>();

    private int mCurrentNumber;

    public NumberGroup(){

    }

    public NumberGroup(TextureRegion[] digitRegions){
        setDigitRegions(digitRegions);
    }

    protected void setDigitRegions(TextureRegion[] digitRegions){
        mDigitRegions = digitRegions;
        refreshDigitNumbers();
    }

    public int getCurrentNumber(){
        return mCurrentNumber;
    }

    public void setCurrentNumber(int currentNumber){
        if(mCurrentNumber != currentNumber && currentNumber >= 0){
            mCurrentNumber = currentNumber;
            refreshDigitNumbers();
        }
    }

    public void addNumber(int numberStep){
        setCurrentNumber(mCurrentNumber + numberStep);
    }

    private void refreshDigitNumbers(){
        if(mDigitRegions == null){
            return;
        }

        String currentNumberString = String.valueOf(mCurrentNumber);

//        char [] charNumbers = String.valueOf(mCurrentNumber).toCharArray();

        if(mDigitActorList.size() > currentNumberString.length()){
            int operationRemoved = mDigitActorList.size() - currentNumberString.length();
            for(int i = 0; i < operationRemoved; i++){
                removeActor(mDigitActorList.remove(0));
            }
        } else if(mDigitActorList.size() < currentNumberString.length()){
            int operationAdded = currentNumberString.length() - mDigitActorList.size();
            for(int i = 0; i < operationAdded; i++){
                ImageActor digitActor = new ImageActor();
                mDigitActorList.add(digitActor);
                addActor(digitActor);
            }
        }

        for(int i = 0; i < mDigitActorList.size(); i++){
            TextureRegion region = mDigitRegions[Integer.valueOf(String.valueOf(currentNumberString.charAt(i)))];
            mDigitActorList.get(i).setTextureRegion(region);
        }

        refreshDigitLayout();
    }

    private void refreshDigitLayout(){
        final ImageActor actor = mDigitActorList.get(0);
        final float digitWidth = actor.getWidth();
        final float digitHeight = actor.getHeight();
        final int size = mDigitActorList.size();
        for(int i = 0; i < size; i++){
            mDigitActorList.get(i).setX(digitWidth * i);
        }
        setSize(digitWidth * size, digitHeight);
    }

}
