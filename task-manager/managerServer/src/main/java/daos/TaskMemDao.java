package daos;

import java.util.ArrayList;
import java.util.List;

import model.Task;

public class TaskMemDao implements Dao<Task, Integer>{
    private List<Task> tasks = new ArrayList<>();

    private int count = 0;

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Task findById(Integer id) {
        return tasks.stream()
            .filter(t -> t.getId() == id)
            .findFirst().orElse(null);
    }

    @Override
    public Task update(Task newEntity) {
        Task exist = findById(newEntity.getId());
        if(exist != null){
            exist.setDescription(newEntity.getDescription());
            exist.setTitle(newEntity.getTitle());
            exist.setStage(newEntity.getStage());
            exist.setDueDate(newEntity.getDueDate());
            exist.setPriority(newEntity.getPriority());
        }
        return exist;
    }

    @Override
    public void delete(Task entity) {
        Task ent = findById(entity.getId());
        if(ent != null){
            tasks.remove(ent);
        }
    }

    public void save(Task entity){
        entity.setId(++count);
        tasks.add(entity);
    }
    
}
