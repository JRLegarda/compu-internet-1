package services;

import java.util.List;

import daos.Dao;
import daos.StageDao;
import daos.TaskMemDao;
import model.Task;
import model.TaskStage;

public class TaskServices {

    private Dao<Task,Integer> repository;
    private Dao<TaskStage,Integer> repositoryStage;

    public TaskServices(){
        repository = new TaskMemDao();
        repositoryStage = new StageDao();
    }
    
    public Task saveTask(Task t){
        TaskStage s = repositoryStage.findById(t.getStage().getId());
        s.getTasks().add(t);
        t.setStage(s);
        repository.save(t);
        return t;
    }

    public Task changeStage(int taskId, int stageId){
        Task t = repository.findById(taskId);
        TaskStage oldStage = t.getStage();
        oldStage.getTasks().remove(t);
        TaskStage s = repositoryStage.findById(stageId);
        s.getTasks().add(t);
        t.setStage(s);
        repository.update(t);

        return t;
    }

    public List<TaskStage> getTask(){
        return repositoryStage.findAll();
    }
}
