package com.provendor.ui.main.Items;

public class XPBoost
{
    private int boost; //boost ratio (ex: 200% boost is 2 because it is 2x the original amount)

    public XPBoost()
    {
        boost = 1;
    }

    public int getBoost()
    {
        return boost;
    }

    public void setBoost(int newBoost)
    {
        boost = newBoost;
    }

    public void increaseBoost ()
    {
        boost *= 2;
    }
}
