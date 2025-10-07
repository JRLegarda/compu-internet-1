import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dtos.Request;
import model.Task;
import model.TaskStage;
import services.TaskServices;

public class ManagerServer {

    private Gson gson;
    private TaskServices services;
    private boolean running;

    public static void main(String[] args) throws Exception {
        new ManagerServer();
    }

    public ManagerServer() throws Exception {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        services = new TaskServices();
        ServerSocket socket = new ServerSocket(5000);
        running = true;
        while (running) {
            Socket sc = socket.accept();
            resolveClient(sc);
        }
        socket.close();
    }

    public void resolveClient(Socket sc) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sc.getOutputStream()));
            while (true) {
                String line = br.readLine();
                Request rq = gson.fromJson(line, Request.class);
                JsonObject obj = rq.getData();
                String resp = null;
                try {
                    switch (rq.getCommand()) {
                        case "CREATE_TASK":
                            Task t = gson.fromJson(obj, Task.class);
                            t = services.saveTask(t);
                            resp = gson.toJson(t);
                            break;
                        case "UPDATE_TASK":
                            String taskId = obj.get("taskId").getAsString();
                            int stage = obj.get("stage").getAsInt();
                            t = services.changeStage(Integer.parseInt(taskId), stage);
                            resp = gson.toJson(t);
                            break;
                        case "GET_TASKS":
                            List<TaskStage> stages = services.getTask();
                            resp = gson.toJson(stages);
                            break;
                        case "HELLO":
                            Map<String,String> hello = Map.of("message", "Hello from Java server");
                            resp = gson.toJson(hello);
                            break;
                        default:
                            Map<String,String> error = Map.of("error", "No support operation");
                            resp = gson.toJson(error);
                            break;
                    }
                } catch (Exception e) {
                    Map<String,String> error = Map.of("error", e.getMessage());
                    resp = gson.toJson(error);
                    e.printStackTrace();
                }
                writer.write(resp+"\n");
                writer.flush();        
            }
        } catch (Exception e) {
            e.printStackTrace();
            sc.close();
        }
    }

}
