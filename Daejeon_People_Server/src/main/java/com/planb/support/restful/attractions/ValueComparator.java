package com.planb.support.restful.attractions;

import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator<Integer> {
	// HashMap을 실수형 value로 정렬하기 위한 클래스
	
	private final Map<Integer, Double> base;
	
	public ValueComparator(Map<Integer, Double> base) {
		this.base = base;
	}
	
	@Override
	public int compare(Integer a, Integer b) {
		if(base.get(a) <= base.get(b)) {
			// 오름차순 정렬
			return -1;
		} else {
			return 1;
		}
	}
}
