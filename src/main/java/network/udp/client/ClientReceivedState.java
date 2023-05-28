package network.udp.client;

import java.nio.charset.StandardCharsets;
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

    private String extractJson(byte[] bytes)
    {
        String jsonString = new String(bytes).trim();
//        ConsoleWriter.write("------------------------------------------");
//        ConsoleWriter.write("HERE THIS SHIT BREAKING OUR CODE MF" + jsonString);
        // Find the index of the first zero character (null termination)
        int indexOfZero = jsonString.indexOf('\0');
        // Extract the JSON part by substring from the beginning to the first zero
        if (indexOfZero >= 0) {
            jsonString = jsonString.substring(0, indexOfZero);
        }
//        ConsoleWriter.write("------------------------------------------");
//        ConsoleWriter.write("HERE WE BROKE THIS SHIT" + jsonString);
//        ConsoleWriter.write("------------------------------------------");
        return jsonString;
    }
}
