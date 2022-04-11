package com.github.budison.parking;

import java.util.concurrent.Semaphore;

/**
 * @author Kevin Nowak
 */
class Parking {
    private final Semaphore parking;
    private boolean[] parkingSlots;

    Parking(int parkingSlots) {
        parking = new Semaphore(parkingSlots);
        this.parkingSlots = new boolean[parkingSlots];
    }

    boolean tryEntering() {
        return parking.tryAcquire();
    }

    void exitParking() {
        parking.release();
    }

    int freeParkingSlots() {
        return parking.availablePermits();
    }

    public Semaphore getParking() {
        return parking;
    }

    public boolean[] getParkingSlots() {
        return parkingSlots;
    }
}
