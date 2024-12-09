package model;

public interface Seller {

    default void listItem(Item item){

        //void listItem(Item item);
    }

    void startAuction(Item item);
}
