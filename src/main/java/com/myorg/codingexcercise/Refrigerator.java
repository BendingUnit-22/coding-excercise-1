package com.myorg.codingexcercise;

import java.util.ArrayList;

/**   Shelf : HELPPER CLASS
 *  The Shelf class has 3 required attributes. capacity, currentCap, and container.
 */
class Shelf{
  // 1. capacity is the maximum cubicFt shelf can hold.*/
  private int capacity;
  // 2. currentCap changes each time putItem() or getItem() method is called. */
  private int currentCap = 0;
  // 3. container is an arraylist keeping track of items, this is necessary becauase we need to retrive item with getItem().
  private ArrayList<Item> container;

  Shelf(int capacity){
    this.capacity = capacity;
    this.container = new ArrayList<Item>();
  }

  /**
  * @param item test object in question
  * return
  *        true if remaining space is more than item
  */
  boolean canFit(Item item){
    return capacity >= this.currentCap + item.getCubicFt();
  }

  /**
  * @param item
  * Assuming shelf has enough space for item.
  */
  void putItem(Item item){
    this.container.add(item);
    this.currentCap += item.getCubicFt();
  }

  /**
  * @param itemId
  * Search wether item with itemId is in container
  * return
  *        object if found
  *        else null
  */
  Item getItem(String itemId){
    for (Item item: this.container){
      if (item.getItemId().equals(itemId)){
        this.currentCap -= item.getCubicFt();
        return item;
      }
    }
    return null;
  }


  // java getter
  int getCurrentCapacity(){
    // System.out.print("space filled: " + this.currentCap + "/ " + this.capacity + "          [" );
    // for (Item item: this.container){
    //   System.out.print(item.getCubicFt() + ",");
    // }
    // System.out.println("]");
    return currentCap;
  }

  static Shelf[] generateShelves(int count, int capacity){
    Shelf[] shelves = new Shelf[count];
    for (int i = 0; i < shelves.length; i++){
      shelves[i] = new Shelf(capacity);
    }
    return shelves;
  }

}



/**
 *
 * You are about to build a Refrigerator which has SMALL, MEDIUM, and LARGE sized shelves.
 *
 * Method signature are given below. You need to implement the logic to
 *
 *  1. To keep track of items put in to the Refrigerator (add or getItem)
 *  2. Make sure enough space available before putting it in
 *  3. Make sure space is used as efficiently as possible
 *  4. Make sure code runs efficiently
 *
 *
 * Created by kamoorr on 7/14/17.
 */
public class Refrigerator {

    /**
     * Refrigerator Total Cubic Feet (CuFt)
     */
    private int cubicFt;

    /**
     * Large size shelf count and size of one shelf
     */
    private int largeShelfCount;
    private int largeShelfCuFt;

    /**
     * Medium size shelf count and size of one shelf
     */
    private int mediumShelfCount;
    private int mediumShelfCuFt;

    /**
     * Medium size shelf count and size of one shelf
     */
    private int smallShelfCount;
    private int smallShelfCuFt;


    private ArrayList<Shelf> shelves;

    /**
     *
     *  Create a new refrigerator by specifying shelfSize and count for SMALL, MEDIUM, LARGE shelves
     * @param largeShelfCount
     * @param largeShelfCuFt
     * @param mediumShelfCount
     * @param mediumShelfCuFt
     * @param smallShelfCount
     * @param smallShelfCuFt
     */
   public Refrigerator(int largeShelfCount, int largeShelfCuFt, int mediumShelfCount, int mediumShelfCuFt, int smallShelfCount, int smallShelfCuFt) {

       /**
        * Calculating total cuft as local variable to improve performance. Assuming no vacant space in the refrigerator
        *
        */
        this.cubicFt = (largeShelfCount * largeShelfCuFt) + (mediumShelfCount * mediumShelfCuFt) + (smallShelfCount* smallShelfCuFt);

        this.largeShelfCount = largeShelfCount;
        this.largeShelfCuFt = largeShelfCuFt;

        this.mediumShelfCount = mediumShelfCount;
        this.mediumShelfCuFt = mediumShelfCuFt;

        this.smallShelfCount = smallShelfCount;
        this.smallShelfCuFt = smallShelfCuFt;


        //
        Shelf[] lgShelves = Shelf.generateShelves(largeShelfCount, largeShelfCuFt);
        Shelf[] mdShelves = Shelf.generateShelves(mediumShelfCount, mediumShelfCuFt);
        Shelf[] smShelves = Shelf.generateShelves(smallShelfCount, smallShelfCuFt);

        /* In order to maximum space or use least number of shelves,
        *  we want to fit as much as we can from large to small.
        * The order of shelves does matter.
        */
        this.shelves = new ArrayList<Shelf>();

        //LARGER Shelves (fill these first)
        for (int i = 0; i < largeShelfCount; i++){
          this.shelves.add(lgShelves[i]);
        }

        //MEDIUM Shelves
        for (int i = 0; i < mediumShelfCount; i++){
          this.shelves.add(mdShelves[i]);
        }

        //SMALL Shelves
        for (int i = 0; i < smallShelfCount; i++){
          this.shelves.add(smShelves[i]);
        }


    }

    /**
     * Implement logic to put an item to this refrigerator. Make sure
     *  -- You have enough vacant space in the refrigerator
     *  -- Make this action efficient in a way to increase maximum utilization of the space, re-arrange items when necessary
     *
     * Return
     *      true if put is successful
     *      false if put is not successful, for example, if you don't have enough space any shelf, even after re-arranging
     *
     *
     * @param item
     */
    public boolean put(Item item) {
        for (Shelf s : this.shelves){
          if (s.canFit(item)){
            // add to shelf
            s.putItem(item);
            return true;
          }
        }
        return false;
    }


    /**
     * getItem and return the requested item
     * Return null when not available
     * @param itemId
     * @return
     */
    public Item get(String itemId) {
      Item item = null;
      for (Shelf s : this.shelves){
        item = s.getItem(itemId);
        if (item != null) {
          return item;
        }
      }
      return item;
    }

    /**
     * Return current utilization of the space
     * @return
     */
    public float getUtilizationPercentage() {
        return ((float)getUsedSpace())/this.cubicFt;
    }

    /**
     * Return current utilization in terms of cuft
     * @return
     */
    public int getUsedSpace() {
      int space = 0;
      for (Shelf s : this.shelves){
        space += s.getCurrentCapacity();
      }
      return space;
    }

}
