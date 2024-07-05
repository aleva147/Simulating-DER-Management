/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author PC
 */
public class Satnica {
    private SatnicaPK satnicaPK;
    private int snaga; 
    // private Raspored raspored;

    public SatnicaPK getSatnicaPK() {
        return satnicaPK;
    }

    public void setSatnicaPK(SatnicaPK satnicaPK) {
        this.satnicaPK = satnicaPK;
    }

    public int getSnaga() {
        return snaga;
    }

    public void setSnaga(int snaga) {
        this.snaga = snaga;
    }

    @Override
    public String toString() {
        return satnicaPK + ", snaga: " + snaga;
    }
}
