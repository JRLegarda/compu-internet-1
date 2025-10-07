import java.net.Socket;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dtos.Request;
import model.Task;
import model.TaskStage;


public class ManagerServerTest {
    
    private Socket client;
    private Gson gson = new Gson();

    @BeforeClass
    public static void initConfig() {
        System.out.println("Starting test...");
        new Thread(()->{
            try {
                new ManagerServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
    
    public void escenario1()throws Exception{
        client = new Socket("localhost", 5000);
        Task newTask = new Task();
        newTask.setTitle("Task from unit test");
        newTask.setDescription("Task created from test");
        newTask.setStage(new TaskStage(1, "null", "null"));
        newTask.setPriority("HIGH");
        JsonObject jsonObject = gson.toJsonTree(newTask).getAsJsonObject();
        String rq = gson.toJson(new Request("CREATE_TASK", jsonObject));
        client.getOutputStream().write((rq + "\n").getBytes());
        client.getOutputStream().flush();
    }

    @Test
    public void testCreateTask()throws Exception{
        escenario1();
        byte[] buffer = new byte[4096];
        int read = client.getInputStream().read(buffer);
        String resp = new String(buffer, 0, read);
        Task task = gson.fromJson(resp, Task.class);
        assert task.getId() > 0;
        assert task.getTitle().equals("Task from unit test");
        assert task.getDescription().equals("Task created from test");
        assert task.getPriority().equals("HIGH");
    }

    @After
    public void afterTest()throws Exception{
        if(client!=null){
            client.close();
        }
    }
}
