package com.github.budison.parking;

import java.util.concurrent.Semaphore;

/**
 * @author Kevin Nowak
 */
class Car implements Runnable {
    private final int carNumber;
    private final Semaphore parking;
    private final boolean[] parkingSlots;


    Car(int carNumber, Semaphore parking, boolean[] parkingSlots) {
        this.carNumber = carNumber;
        this.parking = parking;
        this.parkingSlots = parkingSlots;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNumber=" + carNumber +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Car " + carNumber + " is driving to parking");

        try {
            parking.acquire();

            int parkingNumber = 0;

            synchronized (parkingSlots) {
                for (int i = 1; i <= parkingSlots.length; i++) {
                    if (!parkingSlots[i]) {
                        parkingSlots[i] = true;
                        parkingNumber = i;
                        System.out.println("Car " + carNumber + " parked on place " + parkingNumber);
                        break;
                    }
                }
            }

            Thread.sleep(5000);

            synchronized (parkingSlots) {
                parkingSlots[parkingNumber] = false;
            }

            parking.release();
            System.out.println("Car " + carNumber + " left the parking");
        } catch (InterruptedException ignored) {}
    }
}
