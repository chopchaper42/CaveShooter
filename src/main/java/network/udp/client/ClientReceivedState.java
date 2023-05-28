package network.udp.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

 public class ClientReceivedState
 {
    private ConcurrentLinkedQueue<String> queue;

    public ClientReceivedState()
    {
        queue = new ConcurrentLinkedQueue<>();
    }

    public void enqueue(byte[] data) {
        String json = extractJson(data);
        queue.offer(json);
    }

    public String dequeue() {
        if (!isEmpty()) {
            return queue.poll();
        }
        return null;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    private String extractJson(byte[] bytes) {
        return Arrays.toString(bytes);
    }
}
