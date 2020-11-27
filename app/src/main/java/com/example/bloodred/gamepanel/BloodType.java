package com.example.bloodred.gamepanel;

import com.example.bloodred.Data;

public class BloodType {


    private Data.BloodGroups bloodGroup;
    private Data.Rh rhType;

    public BloodType(Data.Rh rhType, Data.BloodGroups bloodGroup) {
        this.rhType = rhType;
        this.bloodGroup = bloodGroup;
    }

    public Data.BloodGroups getBloodGroup() {
        return this.bloodGroup;
    }

    public Data.Rh getRhType() {
        return this.rhType;
    }

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
