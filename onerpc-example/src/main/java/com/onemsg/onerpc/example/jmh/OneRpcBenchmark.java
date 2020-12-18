package com.onemsg.onerpc.example.jmh;

import java.util.concurrent.TimeUnit;

import com.onemsg.onerpc.client.OneRpcClient;
import com.onemsg.onerpc.example.HelloService;
import com.onemsg.onerpc.example.HelloServiceImple;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import io.netty.util.internal.ThreadLocalRandom;

/**
 * OneRpcBenchmark
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(6)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class OneRpcBenchmark {

    
    HelloService rpcHello = OneRpcClient.getService(HelloService.class);
    HelloService localHello = new HelloServiceImple();
    
    int[] nums = null;
    int k;

    @Setup
    public void initData(){
        var random = ThreadLocalRandom.current();
        nums = random.ints(100, 1, 100000).toArray();
        k = random.nextInt(1, 50);
    }

    @Benchmark
    public void testRpcInvoke() {
        rpcHello.sayHello("qinian");
    }

    // @Benchmark
    public void testLocalInvoke() {
        int[] topK = localHello.findTopK(nums, k);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(OneRpcBenchmark.class.getSimpleName())
            .output("Benchmark3.log")
            .build();
        new Runner(options).run();
    }
    
}