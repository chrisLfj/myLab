package com.myLab.corejava.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoinDemo extends RecursiveTask<Integer> {
    protected int start;
    protected int end;

    public ForkJoinDemo(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int m = 2000, s = start, n = end, r = 0;
        List<ForkJoinTask<Integer>> lt = new ArrayList<ForkJoinTask<Integer>>();
        do{
            if(n - s < m * 1.5f){
                for(int i = s; i <= n; i++){
                    r += i;
                }
                System.out.println(String.format("Sum %s~%s=%s", s, n, r));
            }else{
                n = Math.min(s + m - 1, n);
                lt.add(new ForkJoinDemo(s, n).fork());
            }
            s = n+1;
            n = end;
        }while(s <= end);

        for(ForkJoinTask<Integer> ft:lt)
            r += ft.join();
        return r;
    }

    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool();
        int ss = 1, nn = 10001;
        Future<Integer> result = fjp.submit(new ForkJoinDemo(ss, nn));
        try {
            result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
