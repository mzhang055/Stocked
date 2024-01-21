/*
 * i chose to use an interface here to allow different classes to implement risk
 * calculations differently. 
 */

package model;

import java.util.HashMap;

public interface Risk {
	
	int calculateRisk (HashMap<Integer, Integer> buttonValues);

}
