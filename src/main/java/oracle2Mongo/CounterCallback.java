package oracle2Mongo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wanglei on 16/7/25.
 */
public class CounterCallback implements ProcessCallback {
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public void callAfterProcess() {
        long count = counter.incrementAndGet();

        if (count % 1_0000 == 0) {
            System.out.println("已处理" + count / 1_0000.0 + "万条数据");
        }
    }
}
