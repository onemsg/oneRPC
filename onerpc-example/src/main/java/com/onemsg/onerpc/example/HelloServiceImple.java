package com.onemsg.onerpc.example;

import java.util.Arrays;
import java.util.Objects;

/**
 * HelloServiceImple
 */
public class HelloServiceImple implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public int[] findTopK(int[] nums, int k) {
        
        if(Objects.isNull(nums)) return null;
        if(k < 0 || k > nums.length) k = nums.length;

        for(int i = 0; i < k; i++){
            int max = i;
            for(int j = i+1, end = nums.length; j < end; j++ ){
                if(nums[max] < nums[j]) max = j;
            }
            int tmp = nums[max];
            nums[max] = nums[i];
            nums[i] = tmp;
        }
        return Arrays.copyOf(nums, k);
    }
}
