package network.udp.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ClientReceivedState {
    private Queue<String> queue;

    public ClientReceivedState() {
        queue = new LinkedList<>();
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
        return Arrays.toString(package);
    }
}
