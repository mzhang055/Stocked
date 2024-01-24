/*
 * i chose to use an interface here to allow different classes to implement risk
 * calculations differently. 
 */

package model;

import java.util.Map;


public interface Risk {
     public String calculateRisk(Map<Integer, Integer> buttonValues);
}
