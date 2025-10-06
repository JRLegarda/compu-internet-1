package daos;

import java.util.ArrayList;
import java.util.List;

import model.TaskStage;

public class StageDao implements Dao<TaskStage, Integer>{

    private List<TaskStage> stages = new ArrayList<>();
    private int count = 0;

    public StageDao(){
        TaskStage todo = new TaskStage();
        todo.setName("To Do");
        todo.setDescription("Tasks to be done");
        save(todo);

        TaskStage inProgress = new TaskStage();
        inProgress.setName("In Progress");
        inProgress.setDescription("Tasks in progress");
        save(inProgress);

        TaskStage done = new TaskStage();
        done.setName("Done");
        done.setDescription("Completed tasks");
        save(done);
    }

    @Override
    public List<TaskStage> findAll() {
        return stages;
    }

    @Override
    public TaskStage findById(Integer id) {
        return stages.stream()
            .filter(s -> s.getId() == id)
            .findFirst().orElse(null);
    }

    @Override
    public TaskStage update(TaskStage newEntity) {
        TaskStage exist = findById(newEntity.getId());
        if(exist != null){
            exist.setDescription(newEntity.getDescription());
            exist.setName(newEntity.getName());
            exist.setTasks(newEntity.getTasks());
        }
        return exist;
    }

    @Override
    public void delete(TaskStage entity) {
        TaskStage ent = findById(entity.getId());
        if(ent != null){
            stages.remove(ent);
        }
    }

    @Override
    public void save(TaskStage entity) {
        entity.setId(++count);
        stages.add(entity);
    }
    
}
