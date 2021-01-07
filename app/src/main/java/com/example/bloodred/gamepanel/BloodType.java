package com.example.bloodred.gamepanel;

import com.example.bloodred.Data;
/**
 * BloodType class contains all information of blood types and methods to compare them
 *
 **/
public class BloodType {

    private Data.BloodGroups bloodGroup;
    private Data.Rh rhType;

    public BloodType(Data.Rh rhType, Data.BloodGroups bloodGroup) {
        this.rhType = rhType;
        this.bloodGroup = bloodGroup;
    }

    //Return blood type
    public Data.BloodGroups getBloodGroup() {
        return this.bloodGroup;
    }

    public Data.Rh getRhType() {
        return this.rhType;
    }

    public String getRhTypeString() {
        return this.rhType.toString().contains("Plus") ? "Rh+" : "Rh-";
    }

    //Calculate number of possible blood types that can be donated to recipient
    public static int numberOfPossibleDonors(BloodType recipient) {
        if (recipient.rhType == Data.Rh.RhPlus) {
            if (recipient.bloodGroup == Data.BloodGroups.AB) {
                return 8;
            } else if (recipient.bloodGroup == Data.BloodGroups.B || recipient.bloodGroup == Data.BloodGroups.A) {
                return 4;
            } else if (recipient.bloodGroup == Data.BloodGroups.Zero) {
                return 2;
            }
        } else if (recipient.rhType == Data.Rh.RhMinus) {
            if (recipient.bloodGroup == Data.BloodGroups.AB) {
                return 4;
            } else if (recipient.bloodGroup == Data.BloodGroups.B || recipient.bloodGroup == Data.BloodGroups.A) {
                return 2;
            } else if (recipient.bloodGroup == Data.BloodGroups.Zero) {
                return 1;
            }
        } return 0;
    }

    //Check if two blood types can be mixed
    public static boolean checkBloodCompatibility(BloodType donor, BloodType recipient) {
        if (recipient.rhType == Data.Rh.RhPlus) {
            if (recipient.bloodGroup == Data.BloodGroups.AB) {
                return true;
            } else if (recipient.bloodGroup == Data.BloodGroups.B && (donor.bloodGroup == Data.BloodGroups.Zero || donor.bloodGroup == Data.BloodGroups.B)) {
                return true;
            } else if (recipient.bloodGroup == Data.BloodGroups.A && (donor.bloodGroup == Data.BloodGroups.Zero || donor.bloodGroup == Data.BloodGroups.A)) {
                return true;
            } else if (recipient.bloodGroup == Data.BloodGroups.Zero && donor.bloodGroup == Data.BloodGroups.Zero) {
                return true;
            }
        } else {
            if (donor.rhType == Data.Rh.RhMinus) {
                if (recipient.bloodGroup == Data.BloodGroups.AB) {
                    return true;
                } else if (recipient.bloodGroup == Data.BloodGroups.B && (donor.bloodGroup == Data.BloodGroups.Zero || donor.bloodGroup == Data.BloodGroups.B)) {
                    return true;
                } else if (recipient.bloodGroup == Data.BloodGroups.A && (donor.bloodGroup == Data.BloodGroups.Zero || donor.bloodGroup == Data.BloodGroups.A)) {
                    return true;
                } else if (recipient.bloodGroup == Data.BloodGroups.Zero && donor.bloodGroup == Data.BloodGroups.Zero) {
                    return true;
                }
            } else return false;
        } return false;
    }
}
