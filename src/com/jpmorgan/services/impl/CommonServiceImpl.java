package com.jpmorgan.services.impl;

import com.jpmorgan.services.CommonService;

public class CommonServiceImpl implements CommonService {

    @Override
    public double getGeometricMean(double[] inputArray) {
        if (inputArray != null && inputArray.length > 0) {
            double geometricMean = inputArray[0];
            for (int i = 1; i < inputArray.length; i++) {
                geometricMean *= inputArray[i];
            }
            Integer n = new Integer(inputArray.length);
            return Math.pow(geometricMean, (1 / n.doubleValue()));
        }
        return 0d;
    }

}
