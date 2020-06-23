package pe.dcalma.springboot.todolist.model;

import org.springframework.data.repository.CrudRepository;

public interface TareaRepository extends CrudRepository<Tarea, Long> {
}
